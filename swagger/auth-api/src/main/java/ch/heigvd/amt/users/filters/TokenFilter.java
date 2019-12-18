package ch.heigvd.amt.users.filters;

import ch.heigvd.amt.users.api.exceptions.ApiException;
import ch.heigvd.amt.users.business.TokenImplementation;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenFilter implements Filter {

    @Autowired
    private TokenImplementation tokenImplementation;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

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
