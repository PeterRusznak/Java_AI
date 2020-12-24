package genetic_algorithms.tsp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class TravellingUtils {

    private final static Random random = new Random(42);

    public static final Gene[] CITIES = generateData(35);

    private TravellingUtils() {
        throw new RuntimeException("Do not instantiate this class");
    }

    private static Gene[] generateData(int num) {
        Gene[] data = new Gene[num];
        for (int i = 0; i < num; i++) {
            data[i] = new Gene(TravellingUtils.randomIndex(Main.WIDTH), TravellingUtils.randomIndex(Main.HEIGHT));
        }
        return data;
    }

    public static int randomIndex(int limit) {
        return random.nextInt(limit);
    }

    public static <T> List<T>[] split(List<T> list) {
        List<T> first = new ArrayList<>();
        List<T> second = new ArrayList<>();
        int size = list.size();
        IntStream.range(0, size).forEach(i -> {
            if (i < (size + 1) / 2) {
                first.add(list.get(i));
            } else {
                second.add(list.get(i));
            }
        });
        return (List<T>[]) new List[]{first, second};
    }


}
