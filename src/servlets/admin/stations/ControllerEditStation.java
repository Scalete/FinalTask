package servlets.admin.stations;

import db.DBManager;
import db.entity.Station;
import db.entity.User;
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
 * Edit station controller.
 *
 * @author M.Palaguta
 *
 */
@WebServlet("/edit_station")
public class ControllerEditStation extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(ControllerEditStation.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("Controller starts");

        DBManager dbManager = DBManager.getInstance();
        Station station = null;
        try {
            station = dbManager.findStationById(Integer.parseInt(req.getParameter("station_id")));
            LOG.trace("Found in DB: station --> " + station);
        } catch (DBException e) {
            e.printStackTrace();
        }

        req.setAttribute("station", station);
        LOG.trace("Set the request attribute: station --> " + station);

        LOG.trace("Forward address --> " + Path.PAGE_EDIT_STATION);
        LOG.debug("Controller finished, now go to forward address --> " + Path.PAGE_EDIT_STATION);
        getServletContext().getRequestDispatcher(Path.PAGE_EDIT_STATION).forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("Controller starts");
        req.setCharacterEncoding("UTF-8");
        final DBManager dbManager = DBManager.getInstance();

        final int id = Integer.parseInt(req.getParameter("station_id").trim());
        LOG.trace("Request parameter: station_id --> " + id);
        final String enterNewStation = req.getParameter("station").trim();
        LOG.trace("Request parameter: station --> " + enterNewStation);

        req.setAttribute("error_input",false);
        LOG.trace("Set the request attribute: error_input --> " + false);

        Station station = new Station(id, enterNewStation);

        try {
            dbManager.editStation(station);
            LOG.trace("Edit station in DB: station --> " + station);
        } catch (DBException e) {
            req.setAttribute("error_input", true);
            LOG.trace("Set the request attribute: error_input --> " + true);
            doGet(req,resp);
            return;
        }

        LOG.trace("Forward address --> " + Path.CONTROLLER_LOAD_ADMIN_TABLES);
        LOG.debug("Controller finished, now go to forward address --> " + Path.CONTROLLER_LOAD_ADMIN_TABLES);
        resp.sendRedirect(Path.CONTROLLER_LOAD_ADMIN_TABLES);
    }
}
