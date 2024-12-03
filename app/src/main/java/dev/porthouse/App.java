package dev.porthouse;

import java.util.logging.Logger;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import dev.porthouse.solutions.Day1Solution;
import dev.porthouse.solutions.Day2Solution;
import dev.porthouse.solutions.Day3Solution;

public class App {

    private static final Logger LOGGER = Logger.getLogger(App.class.getName() );

    @Parameter(names = {"--day", "-d"})
    private Integer day = 1;

    public static void main(String ... args) {

        App app = new App();

        JCommander.newBuilder()
            .addObject(app)
            .build()
            .parse(args);

        app.run();
    }

    public void run() {

        ISolution<Integer> solution;

        switch (day) {
            case 1 -> {
                solution = new Day1Solution("day01.txt");
                System.out.println(solution.runPart1());
                solution = new Day1Solution("day01.txt");
                System.out.println(solution.runPart2());
                return;
            }
            case 2 -> {
                solution = new Day2Solution("day02.txt");
                System.out.println(solution.runPart1());
                solution = new Day2Solution("day02.txt");
                System.out.println(solution.runPart2());
                return;
            }
            case 3 -> {
                solution = new Day3Solution("day03.txt");
                System.out.println(solution.runPart1());
                solution = new Day3Solution("day03.txt");
                System.out.println(solution.runPart2());
                return;
            }
            default -> throw new RuntimeException(String.format("No Solution for day %02d", day));
        }
    }
}
