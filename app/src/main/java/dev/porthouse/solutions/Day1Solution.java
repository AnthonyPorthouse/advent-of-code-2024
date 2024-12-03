package dev.porthouse.solutions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.porthouse.BaseSolution;

public class Day1Solution extends BaseSolution {

    public Day1Solution(String file) {
        super(file);
    }

    @Override
    public Integer runPart1() {
        List<Integer> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();

        this.input.lines()
            .forEach((line) -> {
                String[] numbers = line.split("   ");
                a.add(Integer.valueOf(numbers[0]));
                b.add(Integer.valueOf(numbers[1]));
            });

        Collections.sort(a);
        Collections.sort(b);

        Integer total = 0;

        for (int i = 0; i < a.size(); i++) {
            Integer valA = a.get(i);
            Integer valB = b.get(i);

            total += Integer.max(valA, valB) - Integer.min(valA, valB);
        }

        return total;
    }

    @Override
    public Integer runPart2() {
        List<Integer> a = new ArrayList<>();
        Map<Integer, Integer> b = new HashMap<>();

        this.input.lines()
            .forEach((line) -> {
                String[] numbers = line.split("   ");
                a.add(Integer.valueOf(numbers[0]));

                Integer key = Integer.valueOf(numbers[1]);

                b.put(key, b.getOrDefault(key, 0) + 1);
            });

        Integer total = 0;

        for (Integer key: a) {
            Integer count = b.getOrDefault(key, 0);

            total += key * count;
        }

        return total;
    }

}
