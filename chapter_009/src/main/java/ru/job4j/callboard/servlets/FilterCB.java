package ru.job4j.callboard.servlets;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class FilterCB implements Filter {
    private static final Logger LOG = Logger.getLogger("APP2");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        if (request.getRequestURI().contains("/update")
                || request.getRequestURI().contains("/new")) {
            HttpSession session = request.getSession();
            if (session.getAttribute("email") == null) {
                //todo: filter should exclude the possibility of updating advert if you aren't owner
                response.sendRedirect(String.format("%s/signin", request.getContextPath()));
                return;
            }
        }
        chain.doFilter(req, resp);
    }
}
