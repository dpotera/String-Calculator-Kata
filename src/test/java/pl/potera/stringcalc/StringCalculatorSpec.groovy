package pl.potera.stringcalc

import spock.lang.Specification
import spock.lang.Unroll

import java.util.regex.PatternSyntaxException

class StringCalculatorSpec extends Specification {

    def calculator

    def setup() {
        calculator = new StringCalculator()
    }

    @Unroll
    def "sum of expression \"#input\" should be #result"() {
        expect:
        calculator.add(input) == result

        where:
        input           | result
        null            | 0
        ""              | 0
        "1"             | 1
        "1,2"           | 3
        "1,2,10"        | 13
        "1,2,4,5,2"     | 14
        "1,2\n4\n5,2"   | 14
        "1\n4\n5"       | 10
        "1\n4\n5,1000"  | 1010
        "1\n4\n5,1050"  | 10
    }

    @Unroll
    def "should parse delimiter and sum expression \"#input\" = #result"() {
        expect:
        calculator.add(input) == result

        where:
        input                       | result
        "//'\n1'2'3"                | 6
        "//;\n1;2"                  | 3
        "//[;]\n1;2"                | 3
        "//[;][,]\n1;2,3"           | 6
        "//[;][,][p]\n1;2\n3p1"     | 7
        "//[xd][p]\n1xd2\n3p1"      | 7
        "//[1][p]\n2121217"         | 13
    }

    @Unroll
    def "should throw #exception for #input"() {
        when:
        calculator.add(input)

        then:
        def e = thrown(exception)
        if (message != null) {
            e.message == message
        }

        where:
        input           | exception                    | message
        "1,\n"          | IllegalArgumentException     | null
        "\n"            | IllegalArgumentException     | null
        "//;\n1;"       | IllegalArgumentException     | null
        "//[;\n1;"      | PatternSyntaxException       | null
        "-10"           | NegativesNotAllowedException | [-10].toString()
        "-10,3,-2"      | NegativesNotAllowedException | [-10, -2].toString()
    }


    @Unroll
    def "should extract delimiters from #input to #delimiters"() {
        expect:
        calculator.findDelimiters(input) == delimiters

        where:
        input                   | delimiters
        ""                      | [ StringCalculator.DEFAULT_DELIMITER ]
        "//'"                   | ["'"]
        "//;"                   | [";"]
        "//[;]"                 | [";"]
        "//[;][x]"              | [";", "x"]
        "//[;][x][,]"           | [";", "x", ","]
        "//[xd][x][,]"          | ["xd", "x", ","]
        "//[1][x][,]"           | ["1", "x", ","]
    }
}
