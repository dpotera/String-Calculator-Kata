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
        "1,\n"       | 10
    }

    def "should throw IllegalArgumentException for invalid input"() {
        when:
        calculator.add(input)

        then:
        thrown(IllegalArgumentException)

        where:
        input  << ["1,\n", "\n"]
    }

    def "should handle null input"() {
        when:
        def result = calculator.add(null)

        then:
        result == 0
    }
}
