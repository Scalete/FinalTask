package servlets.admin.routs;

import db.DBManager;
import db.entity.IntermediateStation;
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
 * Load route info controller.
 *
 * @author M.Palaguta
 *
 */
@WebServlet("/load_rout_info")
public class ControllerLoadRoutInfo extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(ControllerLoadRoutInfo.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("Controller starts");

        DBManager dbManager = DBManager.getInstance();
        Rout rout = null;
        List<IntermediateStation> listStation = null;
        try {
            rout = getRout(req, dbManager);
            LOG.trace("Found in DB: rout --> " + rout);
            listStation = dbManager.findAllIntermediateStationInRout(rout);
            LOG.trace("Found in DB: listStation --> " + listStation);
        } catch (DBException e) {
            e.printStackTrace();
        }

        req.setAttribute("rout", rout);
        LOG.trace("Set the request attribute: rout --> " + rout);
        req.setAttribute("list_intermediate_station", listStation);
        LOG.trace("Set the request attribute: list_intermediate_station --> " + listStation);

        LOG.trace("Forward address --> " + Path.PAGE_ROUT_INFO);
        LOG.debug("Controller finished, now go to forward address --> " + Path.PAGE_ROUT_INFO);
        getServletContext().getRequestDispatcher(Path.PAGE_ROUT_INFO).forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    private Rout getRout(HttpServletRequest req, DBManager dbManager) throws DBException {
        Rout rout;
        if(req.getAttribute("id") == null){
            rout = dbManager.findRoutById(Integer.parseInt(req.getParameter("id")));
        }else {
            rout = dbManager.findRoutById(Integer.parseInt(req.getAttribute("id").toString()));
            req.setAttribute("id", "");
        }

        return rout;
    }
}
