package pl.potera.stringcalc

import spock.lang.Specification
import spock.lang.Unroll

class StringCalculatorSpec extends Specification {

    @Unroll
    def "sum of expression \"#input\" should be #result"() {
        given:
        def calculator = new StringCalculator()

        expect:
        calculator.add(input) == result

        where:
        input   | result
        ""      | 0
        "1"     | 1
        "1,2"   | 3
    }
}
