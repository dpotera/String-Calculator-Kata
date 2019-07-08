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
        input   | result
        ""      | 0
        "1"     | 1
        "1,2"   | 3
    }

    def "should handle null input"() {
        when:
        def result = calculator.add(null)

        then:
        result == 0
    }
}
