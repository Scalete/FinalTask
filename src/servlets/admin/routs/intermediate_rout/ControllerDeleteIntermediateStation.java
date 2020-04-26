package servlets.admin.routs.intermediate_rout;

import db.DBManager;
import db.entity.IntermediateStation;
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
 * Delete intermediate station controller.
 *
 * @author M.Palaguta
 *
 */
@WebServlet("/delete_intermediate_station")
public class ControllerDeleteIntermediateStation extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(ControllerDeleteIntermediateStation.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("Controller starts");

        DBManager dbManager = DBManager.getInstance();
        IntermediateStation station = null;
        try {
            station = dbManager.findIntermediateStationById(Integer.parseInt(req.getParameter("id_station")));
            LOG.trace("Found in DB: station --> " + station);
            dbManager.deleteIntermediateStation(station.getId());
        } catch (DBException e) {
            e.printStackTrace();
        }

        req.setAttribute("id", station.getRout().getId());
        LOG.trace("Set the request attribute: id --> " + station.getRout().getId());

        LOG.trace("Forward address --> " + Path.CONTROLLER_LOAD_ROUT_INFO);
        LOG.debug("Controller finished, now go to forward address --> " + Path.CONTROLLER_LOAD_ROUT_INFO);
        getServletContext().getRequestDispatcher(Path.CONTROLLER_LOAD_ROUT_INFO).forward(req, resp);
    }
}
