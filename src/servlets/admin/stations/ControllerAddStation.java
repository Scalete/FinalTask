package servlets.admin.stations;

import db.DBManager;
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
 * Add station controller.
 *
 * @author M.Palaguta
 *
 */
@WebServlet("/add_station")
public class ControllerAddStation extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(ControllerAddStation.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(Path.PAGE_ADD_STATION).forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("Controller starts");

        req.setCharacterEncoding("UTF-8");
        final DBManager dbManager = DBManager.getInstance();
        final String enterNewStation = req.getParameter("station").trim();
        LOG.trace("Request parameter: station --> " + enterNewStation);

        req.setAttribute("error_input",false);
        LOG.trace("Set the request attribute: error_input --> " + false);
        req.setAttribute("success_input", false);
        LOG.trace("Set the request attribute: success_input --> " + false);

        Station station = new Station(enterNewStation);

        try {
            dbManager.addStation(station);
        } catch (DBException e) {
            req.setAttribute("error_input", true);
            LOG.trace("Set the request attribute: error_input --> " + true);

            LOG.trace("Forward address --> " + Path.PAGE_ADD_STATION);
            LOG.debug("Controller finished, now go to forward address --> " + Path.PAGE_ADD_STATION);
            doGet(req,resp);
            return;
        }

        req.setAttribute("success_input", true);
        LOG.trace("Set the request attribute: success_input --> " + true);

        LOG.trace("Forward address --> " + Path.PAGE_ADD_STATION);
        LOG.debug("Controller finished, now go to forward address --> " + Path.PAGE_ADD_STATION);
        doGet(req,resp);
    }
}
