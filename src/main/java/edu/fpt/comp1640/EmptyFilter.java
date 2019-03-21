package edu.fpt.comp1640;

import javax.servlet.*;
import java.io.IOException;

public class EmptyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("initiate custom filter");
    }

    @Override
    public void doFilter(
        ServletRequest request,
        ServletResponse response,
        FilterChain chain
    ) throws IOException, ServletException {
        System.out.println("in empty filter chain");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
