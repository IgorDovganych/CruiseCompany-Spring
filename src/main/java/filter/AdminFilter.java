package filter;


import model.User;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/admin/*")
public class AdminFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(AdminFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        User user = (User) ((HttpServletRequest) request).getSession().getAttribute("user");

        if (user == null || !user.getRole().equals("admin")) {
            LOGGER.info("attempt to visit admin page without permission");
            ((HttpServletResponse) response).sendRedirect("/FinalProjectSpring");
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
