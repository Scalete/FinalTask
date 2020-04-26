package servlets.admin;

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
import java.util.List;

/**
 * Load admin panel controller.
 *
 * @author M.Palaguta
 *
 */
@WebServlet("/loadAdminTables")
public class ControllerLoadAdminPanel extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(ControllerLoadAdminPanel.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("Controller starts");

        DBManager dbManager = DBManager.getInstance();
        List<Rout> listRout = null;
        List<Station> listStation = null;
        try {
            listRout = dbManager.findAllRouts();
            LOG.trace("Found in DB: listRout --> " + listRout);
            listStation = dbManager.findAllStation();
            LOG.trace("Found in DB: listStation --> " + listStation);
        } catch (DBException e) {
            e.printStackTrace();
        }

        req.setAttribute("list_rout", listRout);
        LOG.trace("Set the request attribute: list_rout --> " + listRout);
        req.setAttribute("list_station", listStation);
        LOG.trace("Set the request attribute: list_station --> " + listStation);

        LOG.trace("Forward address --> " + Path.PAGE_ADMIN);
        LOG.debug("Controller finished, now go to forward address --> " + Path.PAGE_ADMIN);
        getServletContext().getRequestDispatcher(Path.PAGE_ADMIN).forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
