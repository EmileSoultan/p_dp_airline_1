package app.config.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class CustomAccessDenied implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        response.setContentType("text/html;charset=UTF-8");
        response.setStatus(403);
        response.getWriter().write("Дружок-пирожок, тебе бы надо залогиниться -> ");

        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head> <title> Error 403</title> </head>");
        out.println("<body>");
        out.println("<a href=\"/login\">Тыкни сюда</a>");
        out.println("</body>");
        out.println("</html>");

    }
}
