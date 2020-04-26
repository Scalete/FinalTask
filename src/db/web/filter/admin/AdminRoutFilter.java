package db.web.filter.admin;

import db.DBManager;
import db.web.filter.user.UserRegistrationFilter;
import exeption.DBException;
import org.apache.log4j.Logger;
import path.Path;

import javax.servlet.*;
import java.io.IOException;

/**
 * Admin rout filter.
 *
 * @author M.Palaguta
 *
 */
public class AdminRoutFilter implements Filter {

    private static final org.apache.log4j.Logger LOG = Logger.getLogger(UserRegistrationFilter.class);
    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void destroy() {
        filterConfig = null;
    }

    private void doUpdate(ServletRequest servletRequest, ServletResponse servletResponse, String page)
            throws ServletException, IOException {
        LOG.debug("Filter finished");
        filterConfig.getServletContext().getRequestDispatcher(page).forward(servletRequest, servletResponse);
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOG.debug("Filter starts");

        final DBManager dbManager = DBManager.getInstance();
        servletRequest.setCharacterEncoding("UTF-8");
        if(servletRequest.getParameter("edit_rout") != null || servletRequest.getParameter("add_rout") != null){
            final String enterDepartureStation = servletRequest.getParameter("departure_station").trim();
            LOG.trace("Request parameter: departure_station --> " + enterDepartureStation);
            final String enterDestinationStation = servletRequest.getParameter("destination_station").trim();
            LOG.trace("Request parameter: destination_station --> " + enterDestinationStation);
            int count = 0;

            servletRequest.setAttribute("nullStation", false);
            LOG.trace("Set the request attribute: nullStation --> " + false);
            servletRequest.setAttribute("DuplicateStation", false);
            LOG.trace("Set the request attribute: DuplicateStation --> " + false);

            try {
                if(dbManager.findStationByName(enterDepartureStation) == null
                        || dbManager.findStationByName(enterDestinationStation) == null){
                    servletRequest.setAttribute("nullStation", true);
                    LOG.trace("Set the request attribute: nullStation --> " + true);
                    count++;
                }
            } catch (DBException e) {
                e.printStackTrace();
            }

            if(enterDepartureStation.equals(enterDestinationStation)){
                servletRequest.setAttribute("DuplicateStation", true);
                LOG.trace("Set the request attribute: DuplicateStation --> " + true);
                count++;
            }

            if(count > 0){
                if(servletRequest.getParameter("edit_rout") != null){
                    try {
                        servletRequest.setAttribute("rout", dbManager.findRoutById(
                                Integer.parseInt(servletRequest.getParameter("rout_id"))));
                    } catch (DBException e) {
                        e.printStackTrace();
                    }
                    doUpdate(servletRequest,servletResponse, Path.PAGE_EDIT_ROUT);
                }else {
                    doUpdate(servletRequest,servletResponse,Path.PAGE_ADD_ROUT);
                }
                return;
            }
        }

        LOG.debug("Filter finished");
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
