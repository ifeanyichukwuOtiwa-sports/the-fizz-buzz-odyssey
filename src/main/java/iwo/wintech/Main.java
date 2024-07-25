package iwo.wintech;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final ByteArrayOutputStream OUTPUTSTREAM = new ByteArrayOutputStream();
    private static final PrintStream OUTPUT = new PrintStream(OUTPUTSTREAM);

    public static void main(String[] args) {
        IntStream.range(1, 100 + 1)
                .forEach(Main::usingStrategyOOP2);
    }

    private static void usingStrategyOOP2(final Integer i) {
        Set<FizzBuzzStrategy> strategies = Stream.of(
                new Fizz(),
                new FizzBuzz(),
                new Buzz()
        ).collect(Collectors.toCollection(TreeSet::new));
        for (FizzBuzzStrategy strategy : strategies) {
            if (strategy.appliesTo(i)) {
                System.out.println(strategy.output());
                return;
            }
        }

        System.out.println(i);
    }

    private static void usingIfElse(final int i) {
        if (i % 5 == 0 && i % 3 == 0) {
            System.out.println("FizzBuzz");
        } else if (i % 3 == 0) {
            System.out.println("Fizz");
        } else if (i % 5 == 0) {
            System.out.println("Buzz");
        } else {
            System.out.println(i);
        }
    }

    private static void usingSwitchCase(final Integer i) {
        switch (i) {
            case Integer j when j % 5 == 0 && j % 3 == 0 -> System.out.println("FizzBuzz");
            case Integer j when j % 3 == 0 -> System.out.println("Fizz");
            case Integer j when j % 5 == 0 -> System.out.println("Buzz");
            default -> System.out.println(i);
        }
    }

    private static void usingStrategy(final Integer i) {
        System.out.println(usingStrategyOOP1(i));
    }

    private static String usingStrategyOOP1(final Integer i) {
        Set<FizzBuzzStrategy> strategies = Stream.of(
                new FizzBuzz(),
                new Fizz(),
                new Buzz()
        ).collect(Collectors.toCollection(TreeSet::new));
        for (FizzBuzzStrategy strategy : strategies) {
            if (strategy.appliesTo(i)) {
                return strategy.output();
            }
        }

        return i.toString();
    }


    @Test
    void testFizzBuzz() {
        System.setOut(OUTPUT);
        IntStream.range(1, 100 + 1)
                .forEach(Main::usingStrategy);

        final String usingStrategy = OUTPUTSTREAM.toString();

        OUTPUTSTREAM.reset();

        IntStream.range(1, 100 + 1)
                .forEach(Main::usingIfElse);

        final String usingIfElse = OUTPUTSTREAM.toString();

        OUTPUTSTREAM.reset();

        IntStream.range(1, 100 + 1)
                .forEach(Main::usingSwitchCase);

        final String usingSwitchCase = OUTPUTSTREAM.toString();

        assertThat(usingStrategy).isEqualTo(usingIfElse)
                .isEqualTo(usingSwitchCase);
    }

    interface FizzBuzzStrategy extends Comparable<FizzBuzzStrategy> {
        boolean appliesTo(Integer i);

        String output();

        int priority();

        default int compareTo(FizzBuzzStrategy o) {
            return Integer.compare(this.priority(), o.priority());
        }
    }

    static class FizzBuzz implements FizzBuzzStrategy {
        @Override
        public boolean appliesTo(Integer i) {
            return i % 5 == 0 && i % 3 == 0;
        }

        @Override
        public String output() {
            return "FizzBuzz";
        }

        @Override
        public int priority() {
            return 1;
        }
    }

    static class Fizz implements FizzBuzzStrategy {
        @Override
        public boolean appliesTo(final Integer i) {
            return i % 3 == 0;
        }

        @Override
        public String output() {
            return "Fizz";
        }

        @Override
        public int priority() {
            return 2;
        }
    }

    static class Buzz implements FizzBuzzStrategy {
        @Override
        public boolean appliesTo(final Integer i) {
            return i % 5 == 0;
        }

        @Override
        public String output() {
            return "Buzz";
        }

        @Override
        public int priority() {
            return 3;
        }
    }
}