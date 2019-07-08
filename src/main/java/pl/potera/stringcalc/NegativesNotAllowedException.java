package pl.potera.stringcalc;

class NegativesNotAllowedException extends RuntimeException {

    NegativesNotAllowedException(String message) {
        super(message);
    }
}
