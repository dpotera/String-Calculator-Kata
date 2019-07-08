package pl.potera.stringcalc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class StringCalculator implements Calculator {

    private static final String NEW_LINE = "\n";
    private static final String DEFAULT_DELIMITER = ",";
    private static final String DELIMITER_PREFIX = "//";

    public int add(String numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return 0;
        }
        List<String> lines = Arrays.asList(numbers.split(NEW_LINE, -1));
        String delimiter = findDelimiter(lines.get(0));
        if (!delimiter.equals(DEFAULT_DELIMITER)) {
            lines = lines.subList(1, lines.size());
        }
        return splitValues(lines.stream(), delimiter)
                .map(this::validateEmptyValues)
                .map(Integer::parseInt)
                .reduce(Integer::sum)
                .orElse(0);
    }

    private String findDelimiter(String line) {
        if (line.startsWith(DELIMITER_PREFIX)) {
            return line.replace(DELIMITER_PREFIX, "");
        } else return DEFAULT_DELIMITER;
    }

    private Stream<String> splitValues(Stream<String> lines, String delimiter) {
        return lines.flatMap(line -> splitStringToStream(line, delimiter));
    }

    private Stream<String> splitStringToStream(String input, String delimiter) {
        return Arrays.stream(input.split(delimiter, -1));
    }

    private String validateEmptyValues(String line) {
        if (line.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            return line;
        }
    }
}
