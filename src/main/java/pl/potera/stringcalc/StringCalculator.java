package pl.potera.stringcalc;

import java.util.Arrays;
import java.util.stream.Stream;

public class StringCalculator implements Calculator {

    public int add(String numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return 0;
        }
        return split(numbers)
                .map(this::validate)
                .map(Integer::parseInt)
                .reduce(Integer::sum)
                .orElse(0);
    }

    private Stream<String> split(String input) {
        return splitToStream(input, "\n")
                .flatMap(line -> splitToStream(line, ","));
    }

    private Stream<String> splitToStream(String input, String delimiter) {
        return Arrays.stream(input.split(delimiter, -1));
    }

    private String validate(String line) {
        if (line.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            return line;
        }
    }
}
