package org.curtis.filter;

import org.curtis.database.DBSessionFactory;

import javax.servlet.*;
import java.io.IOException;

public class DatabaseFilter implements Filter {
    protected FilterConfig filterConfig = null;

    public void init(FilterConfig config) {
        this.filterConfig = config;
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } finally {
            commitDBTransaction();
            closeDBTransaction();
        }
    }

    private void commitDBTransaction() {
        try {
            DBSessionFactory.getInstance().commitTransaction();
        } catch (Exception e) {
            System.err.println("ERROR COMMITTING DB TRANSACTION: " + e.getMessage());
        }
    }

    private void closeDBTransaction() {
        try {
            DBSessionFactory.getInstance().closeTransaction();
        } catch (Exception e) {
            System.err.println("ERROR CLOSING DB TRANSACTION: " + e.getMessage());
        }
    }
}
