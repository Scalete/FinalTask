package servlets.admin.routs;

import db.DBManager;
import db.entity.Rout;
import db.entity.Station;
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
 * Edit rout controller.
 *
 * @author M.Palaguta
 *
 */
@WebServlet("/edit_rout")
public class ControllerEditRout extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(ControllerEditRout.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("Controller starts");

        DBManager dbManager = DBManager.getInstance();
        Rout rout = null;
        try {
            rout = dbManager.findRoutById(Integer.parseInt(req.getParameter("rout_id")));
            LOG.trace("Found in DB: rout --> " + rout);
        } catch (DBException e) {
            e.printStackTrace();
        }

        req.setAttribute("rout", rout);
        LOG.trace("Set the request attribute: rout --> " + rout);

        LOG.trace("Forward address --> " + Path.PAGE_EDIT_ROUT);
        LOG.debug("Controller finished, now go to forward address --> " + Path.PAGE_EDIT_ROUT);
        getServletContext().getRequestDispatcher(Path.PAGE_EDIT_ROUT).forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("Controller starts");

        req.setCharacterEncoding("UTF-8");
        final DBManager dbManager = DBManager.getInstance();
        Rout rout = new Rout();

        final int id = Integer.parseInt(req.getParameter("rout_id").trim());
        final String enterDepartureStation = req.getParameter("departure_station").trim();
        LOG.trace("Request parameter: departure_station --> " + enterDepartureStation);
        final String enterDestinationStation = req.getParameter("destination_station").trim();
        LOG.trace("Request parameter: destination_station --> " + enterDestinationStation);
        final String enterDepartureDate = req.getParameter("departure_date").trim().replace("T", " ");
        LOG.trace("Request parameter: departure_date --> " + enterDepartureDate);
        final String enterDestinationDate = req.getParameter("destination_date").trim().replace("T", " ");
        LOG.trace("Request parameter: destination_date --> " + enterDestinationDate);

        req.setAttribute("error_input",false);
        LOG.trace("Set the request attribute: error_input --> " + false);
        req.setAttribute("isDuplicate", false);
        LOG.trace("Set the request attribute: isDuplicate --> " + false);

        rout.setId(id);
        try {
            rout.setDepartureStation(dbManager.findStationByName(enterDepartureStation));
            rout.setDestinationStation(dbManager.findStationByName(enterDestinationStation));
        } catch (DBException e) {
            e.printStackTrace();
        }
        rout.setDepartureDateTime(enterDepartureDate);
        rout.setDestinationDateTime(enterDestinationDate);

        try {
            if(dbManager.isDuplicateRout(rout)){
                req.setAttribute("isDuplicate", true);
                LOG.trace("Set the request attribute: isDuplicate --> " + true);
                doGet(req,resp);
            }else {
                try {
                    dbManager.editRout(rout);
                } catch (DBException e) {
                    req.setAttribute("error_input", true);
                    LOG.trace("Set the request attribute: error_input --> " + true);
                    doGet(req,resp);
                    return;
                }
            }
        } catch (DBException e) {
            e.printStackTrace();
        }

        LOG.trace("Forward address --> " + Path.CONTROLLER_LOAD_ADMIN_TABLES);
        LOG.debug("Controller finished, now go to forward address --> " + Path.CONTROLLER_LOAD_ADMIN_TABLES);
        resp.sendRedirect(Path.CONTROLLER_LOAD_ADMIN_TABLES);
    }
}
