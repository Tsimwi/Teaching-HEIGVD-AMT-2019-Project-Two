package ch.heigvd.amt.users.filters;

import ch.heigvd.amt.users.api.exceptions.ApiException;
import ch.heigvd.amt.users.business.TokenImplementation;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenFilter extends GenericFilterBean {

    @Autowired
    TokenImplementation tokenImplementation;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String fullHeaderToken = req.getHeader("Authorization");
        try {
            String token = tokenImplementation.getTokenFromHeaderAuthorization(fullHeaderToken);
            DecodedJWT decodedJWT = tokenImplementation.decodeJWT(token);

            if (!tokenImplementation.tokenUserExist(decodedJWT)) {
                resp.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid token");
                return;
            }
            servletRequest.setAttribute("email", decodedJWT.getSubject());
            servletRequest.setAttribute("role", decodedJWT.getClaim("role").asString());

        } catch (ApiException e) {
            resp.sendError(e.getCode(), e.getMessage());
            return;
        } catch (Exception e) {
            resp.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid token");
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }


}
