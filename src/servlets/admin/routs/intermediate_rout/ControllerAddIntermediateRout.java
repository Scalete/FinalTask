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
 * Add intermediate station controller.
 *
 * @author M.Palaguta
 *
 */
@WebServlet("/add_intermediate_station")
public class ControllerAddIntermediateRout extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(ControllerAddIntermediateRout.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.trace("Forward address --> " + Path.PAGE_ADD_INTERMEDIATE_STATION);
        LOG.debug("Controller finished, now go to forward address --> " + Path.PAGE_ADD_INTERMEDIATE_STATION);
        getServletContext().getRequestDispatcher(Path.PAGE_ADD_INTERMEDIATE_STATION).forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("Controller starts");

        req.setCharacterEncoding("UTF-8");
        final DBManager dbManager = DBManager.getInstance();
        final String enterIntermediateStation = req.getParameter("intermediate_station").trim();
        LOG.trace("Request parameter: intermediate_station --> " + enterIntermediateStation);
        final int idRout = ((req.getParameter("id_rout")) != null)?Integer.parseInt(req.getParameter("id_rout")):
                Integer.parseInt(req.getParameter("id_rout"));
        LOG.trace("Request parameter: id_rout --> " + idRout);
        final String enterDepartureDate = req.getParameter("departure_date").trim().replace("T", " ");
        LOG.trace("Request parameter: departure_date --> " + enterDepartureDate);
        final String enterDestinationDate = req.getParameter("destination_date").trim().replace("T", " ");
        LOG.trace("Request parameter: destination_date --> " + enterDestinationDate);
        final String enterStopTime = req.getParameter("stop_time");
        LOG.trace("Request parameter: stop_time --> " + enterStopTime);

        req.setAttribute("error_input", false);
        LOG.trace("Set the request attribute: error_input --> " + false);
        req.setAttribute("success_input", false);
        LOG.trace("Set the request attribute: success_input --> " + false);

        IntermediateStation station = new IntermediateStation();

        try {
            station.setStation(dbManager.findStationByName(enterIntermediateStation));
            station.setOrder(dbManager.calculateIntermediateStationOrder(idRout));
            station.setRout(dbManager.findRoutById(idRout));
        } catch (DBException e) {
            e.printStackTrace();
        }
        station.setDepartureTime(enterDepartureDate);
        station.setDestinationTime(enterDestinationDate);
        station.setStopTime(enterStopTime);

        try {
            dbManager.addIntermediateStation(station);
        } catch (DBException e) {
            e.printStackTrace();
            req.setAttribute("error_input", true);
            LOG.trace("Set the request attribute: error_input --> " + true);
            doGet(req, resp);
            return;
        }

        req.setAttribute("success_input", true);
        LOG.trace("Set the request attribute: success_input --> " + true);
        doGet(req, resp);
    }

}
