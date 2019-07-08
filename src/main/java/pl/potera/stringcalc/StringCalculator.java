package pl.potera.stringcalc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
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
        List<String> delimiters = findDelimiters(lines.get(0));
        lines = skipDelimitersLine(lines, delimiters);
        List<Integer> intList = parseValues(lines, delimiters);
        validateNegativeValues(intList);
        return intList.stream()
                .filter(value -> value <= 1000)
                .reduce(Integer::sum)
                .orElse(0);
    }

    private List<String> skipDelimitersLine(List<String> lines, List<String> delimiters) {
        if (!delimiters.equals(Collections.singletonList(DEFAULT_DELIMITER))) {
            lines = lines.subList(1, lines.size());
        }
        return lines;
    }

    private List<Integer> parseValues(List<String> lines, List<String> delimiters) {
        return splitValues(lines.stream(), delimiters)
                .map(this::validateEmptyValues)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private List<String> findDelimiters(String line) {
        if (line.startsWith(DELIMITER_PREFIX)) {
            String delimiters = line.replace(DELIMITER_PREFIX, "");
            if (line.contains("[")) {
                return Pattern.compile("(?<=\\[)([^\\]]+)(?=\\])")
                        .matcher(delimiters)
                        .results()
                        .map(MatchResult::group)
                        .collect(Collectors.toList());
            } else {
                return Collections.singletonList(delimiters);
            }
        } else return Collections.singletonList(DEFAULT_DELIMITER);
    }

    private Stream<String> splitValues(Stream<String> lines, List<String> delimiters) {
        return lines.flatMap(line -> splitStringToStream(line, delimiters));
    }

    private Stream<String> splitStringToStream(String input, List<String> delimiters) {
        return Arrays.stream(input.split(createDelimitersRegex(delimiters), -1));
    }

    private String createDelimitersRegex(List<String> delimiters) {
        return "[" + String.join("|", delimiters) + "]+";
    }

    private String validateEmptyValues(String line) {
        if (line.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            return line;
        }
    }

    private void validateNegativeValues(List<Integer> values) {
        List<Integer> negativeValues = values.stream().filter(value -> value < 0).collect(Collectors.toList());
        if (!negativeValues.isEmpty()) {
            throw new NegativesNotAllowedException(negativeValues.toString());
        }
    }
}
