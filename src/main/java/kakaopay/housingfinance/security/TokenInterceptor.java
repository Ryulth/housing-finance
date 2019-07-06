package kakaopay.housingfinance.security;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    private static final String HEADER_AUTH = "Authorization";

    private final TokenChecker tokenChecker;

    public TokenInterceptor(TokenChecker tokenChecker) {
        this.tokenChecker = tokenChecker;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        final String token = request.getHeader(HEADER_AUTH).split("bearer ")[1];
        try {
            if (token != null && tokenChecker.isUsable(token)) {
                return true;
            }
        } catch (UnauthorizedException e) {
            response.getWriter().write("{ \"error\" : \"" + e.getMessage() + "\" }");
            response.setStatus(401);
            return false;
        }
        response.getWriter().write("{ \"error\" : \"Unauthorized Access Token\" }");
        response.setStatus(401);
        return false;
    }
}
