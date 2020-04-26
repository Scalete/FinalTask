package db.web.filter;

import db.DBManager;
import db.entity.Trip;
import org.apache.log4j.Logger;
import path.Path;
import servlets.user.ControllerLoginUserOrAdmin;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.logging.Level;

/**
 * Is login filter.
 *
 * @author M.Palaguta
 *
 */
public class IsLoginFilter implements Filter {

    private static final org.apache.log4j.Logger LOG = Logger.getLogger(IsLoginFilter.class);
    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void destroy() {
        filterConfig = null;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOG.debug("Filter starts");

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        if(req.getSession().getAttribute("active_user") == null){
            req.setAttribute("active_user", false);
            LOG.trace("Set the request attribute: active_user --> " + false);

            LOG.trace("Forward address --> " + Path.PAGE_LOGIN);
            LOG.debug("Filter finished, now go to forward address --> " + Path.PAGE_LOGIN);
            filterConfig.getServletContext().getRequestDispatcher(Path.PAGE_LOGIN).forward(servletRequest, servletResponse);
            return;
        }

        LOG.debug("Filter finished");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
