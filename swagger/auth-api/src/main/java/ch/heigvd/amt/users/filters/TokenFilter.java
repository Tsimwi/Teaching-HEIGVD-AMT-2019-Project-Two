package ch.heigvd.amt.users.filters;

import ch.heigvd.amt.users.api.exceptions.ApiException;
import ch.heigvd.amt.users.business.TokenImplementation;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        TokenImplementation tokenImplementation = new TokenImplementation();

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String fullHeaderToken = req.getHeader("Authorization");
        try {
            String token = tokenImplementation.getTokenFromHeaderAuthorization(fullHeaderToken);
            tokenImplementation.decodeJWT(token);
        } catch (ApiException e) {
            resp.sendError(e.getCode(), e.getMessage());
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }


}
