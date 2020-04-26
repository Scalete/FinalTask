package servlets.user.search;

import db.DBManager;
import db.entity.Rout;
import db.entity.Station;
import db.entity.Trip;
import exeption.DBException;
import org.apache.log4j.Logger;
import path.Path;
import servlets.user.ControllerLoginUserOrAdmin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Search rout controller.
 *
 * @author M.Palaguta
 *
 */
@WebServlet("/loadSearchTrip")
public class ControllerSearchRout extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(ControllerSearchRout.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("Controller starts");

        DBManager dbManager = DBManager.getInstance();
        Rout rout = new Rout();
        rout.setDepartureDateTime(req.getParameter("departure_date").trim().replace("T", " "));
        try {
            //get departure and destination station from DB and set to route
            rout.setDepartureStation(dbManager.findStationByName(req.getParameter("departure_station").trim()));
            rout.setDestinationStation(dbManager.findStationByName(req.getParameter("destination_station").trim()));
        } catch (DBException e) {
            e.printStackTrace();
        }

        req.setAttribute("listIsEmpty", false);
        LOG.trace("Set the request attribute: listIsEmpty --> " + false);
        req.setAttribute("nullStation", false);
        LOG.trace("Set the request attribute: nullStation --> " + false);

        if(rout.getDepartureStation() == null || rout.getDestinationStation() == null){
            req.setAttribute("nullStation",true);
            LOG.trace("Set the request attribute: nullStation --> " + true);

            LOG.trace("Forward PAGE_SEARCH_ROUT_RESULT --> " + Path.PAGE_MAIN);
            LOG.debug("Controller finished, now go to forward address --> " + Path.PAGE_MAIN);
            getServletContext().getRequestDispatcher(Path.PAGE_MAIN).forward(req,resp);
            return;
        }

        List<Trip> listTrip = null;
        try {
            listTrip = dbManager.findTripByRout(rout);
            LOG.trace("Found in DB: listTrip --> " + listTrip);
        } catch (DBException e) {
            e.printStackTrace();
        }

        req.setAttribute("list_trip", listTrip);
        LOG.trace("Set the request attribute: list_trip --> " + listTrip);

        if(listTrip.isEmpty()){
            req.setAttribute("listIsEmpty", true);
            LOG.trace("Set the request attribute: listIsEmpty --> " + true);
        }

        LOG.trace("Forward PAGE_SEARCH_ROUT_RESULT --> " + Path.PAGE_LOGIN);
        LOG.debug("Controller finished, now go to forward address --> " + Path.PAGE_SEARCH_ROUT_RESULT);
        getServletContext().getRequestDispatcher(Path.PAGE_SEARCH_ROUT_RESULT).forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
