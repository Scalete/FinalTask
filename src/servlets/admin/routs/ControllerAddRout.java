package servlets.admin.routs;

import db.DBManager;
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

/**
 * Add rout controller.
 *
 * @author M.Palaguta
 *
 */
@WebServlet("/add_rout")
public class ControllerAddRout extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(ControllerAddRout.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.trace("Forward address --> " + Path.PAGE_ADD_ROUT);
        LOG.debug("Controller finished, now go to forward address --> " + Path.PAGE_ADD_ROUT);
        getServletContext().getRequestDispatcher(Path.PAGE_ADD_ROUT).forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("Controller starts");

        req.setCharacterEncoding("UTF-8");
        final DBManager dbManager = DBManager.getInstance();
        final String enterDepartureStation = req.getParameter("departure_station").trim();
        LOG.trace("Request parameter: departure_station --> " + enterDepartureStation);
        final String enterDestinationStation = req.getParameter("destination_station").trim();
        LOG.trace("Request parameter: destination_station --> " + enterDestinationStation);

        String enterDepartureDate = req.getParameter("departure_date").trim().replace("T", " ");
        LOG.trace("Request parameter: departure_date --> " + enterDepartureDate);
        String enterDestinationDate = req.getParameter("destination_date").trim().replace("T", " ");
        LOG.trace("Request parameter: destination_date --> " + enterDestinationDate);

        req.setAttribute("error_input", false);
        LOG.trace("Set the request attribute: error_input --> " + false);
        req.setAttribute("success_input", false);
        LOG.trace("Set the request attribute: success_input --> " + false);

        Rout rout = new Rout();
        try {
            rout.setDepartureStation(dbManager.findStationByName(enterDepartureStation));
            rout.setDestinationStation(dbManager.findStationByName(enterDestinationStation));
        } catch (DBException e) {
            e.printStackTrace();
        }
        rout.setDepartureDateTime(enterDepartureDate);
        rout.setDestinationDateTime(enterDestinationDate);

        try {
            dbManager.addRout(rout);
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
