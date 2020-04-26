package db.web.filter.admin;

import db.DBManager;
import db.entity.IntermediateStation;
import db.entity.Rout;
import db.entity.Station;
import exeption.DBException;
import org.apache.log4j.Logger;
import path.Path;
import servlets.user.ControllerLoginUserOrAdmin;

import javax.servlet.*;
import java.io.IOException;
import java.util.List;

/**
 * Admin intermediate station filter.
 *
 * @author M.Palaguta
 *
 */
public class AdminIntermediateStationFilter implements Filter {
    private static final org.apache.log4j.Logger LOG = Logger.getLogger(AdminIntermediateStationFilter.class);
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
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        LOG.debug("Filter starts");

        servletRequest.setCharacterEncoding("UTF-8");
        final DBManager dbManager = DBManager.getInstance();
        final String intermediateStation = servletRequest.getParameter("intermediate_station");
        LOG.trace("Request parameter: intermediate_station --> " + intermediateStation);
        int count = 0;

        servletRequest.setAttribute("isRoutMainStation", false);
        LOG.trace("Set the request attribute: isRoutMainStation --> " + false);
        servletRequest.setAttribute("isDuplicateStation", false);
        LOG.trace("Set the request attribute: isDuplicateStation --> " + false);
        servletRequest.setAttribute("nullStation", false);
        LOG.trace("Set the request attribute: nullStation --> " + false);

        if(servletRequest.getParameter("add_intermediate_station") != null){
            int idRout = Integer.parseInt(servletRequest.getParameter("id_rout"));
            servletRequest.setAttribute("id_rout", idRout);
            LOG.trace("Set the request attribute: id_rout --> " + idRout);
        }

        if(servletRequest.getParameter("add_intermediate_station") != null || servletRequest.getParameter("edit_intermediate_station") != null){
            Rout rout = null;
            List<IntermediateStation> stationList = null;
            try {
                rout = dbManager.findRoutById(Integer.parseInt(servletRequest.getParameter("id_rout")));
                LOG.trace("Found in DB: rout --> " + rout);
                stationList = dbManager.findAllIntermediateStationInRout(rout);
                LOG.trace("Found in DB: stationList --> " + stationList);
            } catch (DBException e) {
                e.printStackTrace();
            }

            if(rout.getDepartureStation().getName().equals(intermediateStation)
                    || rout.getDestinationStation().getName().equals(intermediateStation)){
                servletRequest.setAttribute("isRoutMainStation", true);
                LOG.trace("Set the request attribute: isRoutMainStation --> " + true);
                count++;
            }

            if(servletRequest.getParameter("add_intermediate_station") != null){
                for(IntermediateStation station: stationList){
                    if(station.getStation().getName().equals(intermediateStation)){
                        servletRequest.setAttribute("isDuplicateStation", true);
                        LOG.trace("Set the request attribute: isDuplicateStation --> " + true);
                        count++;
                        break;
                    }
                }
            }else {
                if(!intermediateStation.equals(servletRequest.getParameter("old_station_name"))){
                    for(IntermediateStation station: stationList){
                        if(station.getStation().getName().equals(intermediateStation)){
                            servletRequest.setAttribute("isDuplicateStation", true);
                            LOG.trace("Set the request attribute: isDuplicateStation --> " + true);
                            count++;
                            break;
                        }
                    }
                }
            }

            try {
                if (dbManager.findStationByName(intermediateStation) == null){
                    servletRequest.setAttribute("nullStation", true);
                    LOG.trace("Set the request attribute: nullStation --> " + true);
                    count++;
                }
            } catch (DBException e) {
                e.printStackTrace();
            }

            if(count > 0){
                if(servletRequest.getParameter("edit_intermediate_station") != null){
                    try {
                        servletRequest.setAttribute("intermediate_station", dbManager.findIntermediateStationById(
                                Integer.parseInt(servletRequest.getParameter("id_station"))));
                    } catch (DBException e) {
                        e.printStackTrace();
                    }
                    doUpdate(servletRequest,servletResponse, Path.PAGE_EDIT_INTERMEDIATE_STATION);
                }else {
                    doUpdate(servletRequest,servletResponse,Path.PAGE_ADD_INTERMEDIATE_STATION);
                }
                return;
            }

        }

        LOG.debug("Filter finished");
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
