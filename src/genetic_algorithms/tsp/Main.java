package genetic_algorithms.tsp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.concurrent.atomic.AtomicInteger;

public class Main extends JPanel {
    private Population population;
    private final AtomicInteger generation;
    static final int WIDTH = 800;
    static final int HEIGHT = 600;
    private int counter = 0;


    private Main() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        this.population = new Population(TravellingUtils.CITIES, 500);
        this.generation = new AtomicInteger(0);

        final Timer timer = new Timer(5, (ActionEvent e) -> {
            this.population.update();
            repaint();
            counter++;
            if(counter>= 300){
                ((Timer)e.getSource()).stop();
            }
        });
        timer.start();


    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        final Graphics2D g = (Graphics2D) graphics;
        g.setColor(Color.CYAN);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawString("generation: " + this.generation.incrementAndGet(), 350, 15);
        g.drawString("shortest path :"
                + String.format("%.2f", this.population.getAlpha().calculateDistance()), 500, 15);
        drowWinnerChromosome(g);
    }

    private void drowWinnerChromosome(Graphics2D g) {
        java.util.List<Gene> chromosome = this.population.getAlpha().getChromosome();
        g.setColor(Color.WHITE);
        for (int i = 0; i < chromosome.size() - 1; i++) {
            Gene gene = chromosome.get(i);
            Gene other = chromosome.get(i + 1);
            g.drawLine(gene.getX(), gene.getY(), other.getX(), other.getY());
        }
        g.setColor(Color.RED);
        for(Gene gene:chromosome){
            g.fillOval(gene.getX(), gene.getY(), 5,5);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            final JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setTitle("Travelling Salesman");
            frame.setResizable(false);
            frame.add(new Main(), BorderLayout.CENTER);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

}
