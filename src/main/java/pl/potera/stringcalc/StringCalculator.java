package pl.potera.stringcalc;

import java.util.Arrays;
import java.util.stream.Stream;

public class StringCalculator implements Calculator {

    public int add(String numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return 0;
        }
        return split(numbers)
                .map(Integer::parseInt)
                .reduce(Integer::sum)
                .orElse(0);
    }

    private Stream<String> split(String input) {
        return Arrays.stream(input.split(","));
    }
}
