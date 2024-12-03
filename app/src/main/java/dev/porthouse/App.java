package dev.porthouse;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

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

        try {
            Class<?> klass = Class.forName(String.format("dev.porthouse.solutions.Day%dSolution", day));
            Constructor<?> ctor = klass.getDeclaredConstructor(String.class);


            var solution = (ISolution) ctor.newInstance(String.format("day%02d.txt", day));
            System.out.println(solution.runPart1());
            solution = (ISolution) ctor.newInstance(String.format("day%02d.txt", day));
            System.out.println(solution.runPart2());

        } catch (ClassNotFoundException e) {
            System.err.println(String.format("Unable to find class %s", e.getMessage()));
            System.exit(1);
        } catch (NoSuchMethodException | RuntimeException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }





        try {

        } catch (Exception e) {
        }

    }
}
