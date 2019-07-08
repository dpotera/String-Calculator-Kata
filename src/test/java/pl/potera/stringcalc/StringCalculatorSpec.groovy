package pl.potera.stringcalc

import spock.lang.Specification
import spock.lang.Unroll

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
        input           | result
        "//'\n1'2'3"    | 6
        "//;\n1;2"      | 3
    }

    @Unroll
    def "should throw #exception for invalid input"() {
        when:
        calculator.add(input)

        then:
        def e = thrown(exception)
        e.message == message

        where:
        input       | exception                     | message
        "1,\n"      | IllegalArgumentException      | null
        "\n"        | IllegalArgumentException      | null
        "//;\n1;"   | IllegalArgumentException      | null
        "-10"       | NegativesNotAllowedException  | [-10].toString()
        "-10,3,-2"  | NegativesNotAllowedException  | [-10, -2].toString()
    }

    def "should throw IllegalArgumentException for invalid input"() {
        when:
        calculator.add(input)

        then:
        thrown(IllegalArgumentException)

        where:
        input  << ["1,\n", "\n", "//;\n1;"]
    }

    def "should handle null input"() {
        when:
        def result = calculator.add(null)

        then:
        result == 0
    }
}
