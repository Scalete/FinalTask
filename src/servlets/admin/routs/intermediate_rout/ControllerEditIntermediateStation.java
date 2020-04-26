package servlets.admin.routs.intermediate_rout;

import db.DBManager;
import db.entity.IntermediateStation;
import db.entity.Rout;
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
import java.sql.SQLException;

/**
 * Edit intermediate station controller.
 *
 * @author M.Palaguta
 *
 */
@WebServlet("/edit_intermediate_station")
public class ControllerEditIntermediateStation extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(ControllerEditIntermediateStation.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("Controller starts");

        DBManager dbManager = DBManager.getInstance();
        IntermediateStation station = null;
        try {
            station = dbManager.findIntermediateStationById(Integer.parseInt(req.getParameter("id_station")));
            LOG.trace("Found in DB: station --> " + station);
        } catch (DBException e) {
            e.printStackTrace();
        }

        req.setAttribute("intermediate_station", station);
        LOG.trace("Set the request attribute: intermediate_station --> " + station);

        LOG.trace("Forward address --> " + Path.PAGE_EDIT_INTERMEDIATE_STATION);
        LOG.debug("Controller finished, now go to forward address --> " + Path.PAGE_EDIT_INTERMEDIATE_STATION);
        getServletContext().getRequestDispatcher(Path.PAGE_EDIT_INTERMEDIATE_STATION).forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("Controller starts");

        req.setCharacterEncoding("UTF-8");
        final DBManager dbManager = DBManager.getInstance();
        IntermediateStation station = new IntermediateStation();

        station.setId(Integer.parseInt(req.getParameter("id_station")));
        try {
            station.setStation(dbManager.findStationByName(req.getParameter("intermediate_station")));
            station.setRout(dbManager.findRoutById(Integer.parseInt(req.getParameter("id_rout"))));
        } catch (DBException e) {
            e.printStackTrace();
        }
        station.setDestinationTime(req.getParameter("destination_date").replace("T", " "));
        station.setDepartureTime(req.getParameter("departure_date").replace("T", " "));
        station.setStopTime(req.getParameter("stop_time").replace("T", " "));
        station.setOrder(Integer.parseInt(req.getParameter("order")));

        req.setAttribute("error_input",false);
        LOG.trace("Set the request attribute: error_input --> " + false);

        try {
            dbManager.editIntermediateStation(station);
        } catch (DBException e) {
            req.setAttribute("error_input", true);
            LOG.trace("Set the request attribute: error_input --> " + true);
            doGet(req, resp);
            return;
        }

        req.setAttribute("id", station.getRout().getId());
        LOG.trace("Set the request attribute: id --> " + station.getRout().getId());

        LOG.trace("Forward address --> " + Path.CONTROLLER_LOAD_ROUT_INFO);
        LOG.debug("Controller finished, now go to forward address --> " + Path.CONTROLLER_LOAD_ROUT_INFO);
        getServletContext().getRequestDispatcher(Path.CONTROLLER_LOAD_ROUT_INFO).forward(req,resp);
    }
}
