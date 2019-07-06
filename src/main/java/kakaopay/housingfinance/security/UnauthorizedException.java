package kakaopay.housingfinance.security;

public class UnauthorizedException extends RuntimeException{

    public UnauthorizedException() {
        super("Unauthorized Access Token");
    }

    public UnauthorizedException(String message) {
        super(message);
    }

}
