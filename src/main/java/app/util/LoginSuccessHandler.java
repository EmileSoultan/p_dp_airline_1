package app.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException{

        var rolesSet = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if(rolesSet.contains("ROLE_ADMIN")) {
            httpServletResponse.sendRedirect("/admin");
        } else if(rolesSet.contains("ROLE_MANAGER")) {
            httpServletResponse.sendRedirect("/manager");
        } else if(rolesSet.contains("ROLE_PASSENGER")) {
            httpServletResponse.sendRedirect("/passenger");
        } else {
            httpServletResponse.sendRedirect("/");
        }
    }
}
