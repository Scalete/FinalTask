package db.web.filter.admin;

import db.DBManager;
import db.entity.Station;
import exeption.DBException;
import org.apache.log4j.Logger;
import path.Path;
import servlets.user.ControllerLoginUserOrAdmin;

import javax.servlet.*;
import java.io.IOException;

/**
 * Admin station filter.
 *
 * @author M.Palaguta
 *
 */
public class AdminStationFilter implements Filter {

    private static final org.apache.log4j.Logger LOG = Logger.getLogger(ControllerLoginUserOrAdmin.class);
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
        if(servletRequest.getParameter("edit_station") != null || servletRequest.getParameter("add_station") != null){
            final String enterNewStation = servletRequest.getParameter("station").trim();
            LOG.trace("Request parameter: station --> " + enterNewStation);

            int count = 0;

            servletRequest.setAttribute("isDuplicate", false);
            LOG.trace("Set the request attribute: isDuplicate --> " + false);
            servletRequest.setAttribute("shortName", false);
            LOG.trace("Set the request attribute: shortName --> " + false);

            Station station = new Station(enterNewStation);

            try {
                if(dbManager.isDuplicateStation(station)){
                    servletRequest.setAttribute("isDuplicate", true);
                    LOG.trace("Set the request attribute: isDuplicate --> " + true);
                    count++;
                }
            } catch (DBException e) {
                e.printStackTrace();
            }

            if(enterNewStation.length() < 3){
                servletRequest.setAttribute("shortName", true);
                LOG.trace("Set the request attribute: shortName --> " + true);
                count++;
            }

            if(count > 0){
                if(servletRequest.getParameter("edit_station") != null){
                    try {
                        servletRequest.setAttribute("station", dbManager.findStationById(
                                Integer.parseInt(servletRequest.getParameter("station_id"))));
                    } catch (DBException e) {
                        e.printStackTrace();
                    }
                    doUpdate(servletRequest,servletResponse, Path.PAGE_EDIT_STATION);
                }else {
                    doUpdate(servletRequest,servletResponse,Path.PAGE_ADD_STATION);
                }
                return;
            }
        }

        LOG.debug("Filter finished");
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
