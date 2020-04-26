package servlets.user.search;

import db.DBManager;
import db.entity.Carriage;
import db.entity.IntermediateStation;
import db.entity.Rout;
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
 * Trip info controller.
 *
 * @author M.Palaguta
 *
 */
@WebServlet("/load_trip_info")
public class ControllerTripInfo extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(ControllerTripInfo.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("Controller starts");

        DBManager dbManager = DBManager.getInstance();
        Trip trip = null;
        List<IntermediateStation> intermediateStations = null;
        List<Carriage> carriages = null;
        try {
            trip = dbManager.findTripById(Integer.parseInt(req.getParameter("id")));
            intermediateStations = dbManager.findAllIntermediateStationInRout(trip.getRout());
            LOG.trace("Found in DB: intermediateStations --> " + intermediateStations);
            carriages = dbManager.findAllCarriageInTrain(trip.getTrain());
            LOG.trace("Found in DB: carriages --> " + carriages);
        } catch (DBException e) {
            e.printStackTrace();
        }

        req.setAttribute("trip", trip);
        LOG.trace("Set the request attribute: trip --> " + trip);
        req.setAttribute("list_intermediate_station", intermediateStations);
        LOG.trace("Set the request attribute: list_intermediate_station --> " + intermediateStations);
        req.setAttribute("list_carriages", carriages);
        LOG.trace("Set the request attribute: list_carriages --> " + carriages);


        LOG.trace("Forward address --> " + Path.PAGE_TRIP_INFO);
        LOG.debug("Controller finished, now go to forward address --> " + Path.PAGE_TRIP_INFO);
        getServletContext().getRequestDispatcher(Path.PAGE_TRIP_INFO).forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
