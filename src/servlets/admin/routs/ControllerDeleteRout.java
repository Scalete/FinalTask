package servlets.admin.routs;

import db.DBManager;
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
 * Delete rout controller.
 *
 * @author M.Palaguta
 *
 */
@WebServlet("/delete_rout")
public class ControllerDeleteRout extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(ControllerDeleteRout.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOG.debug("Controller starts");

        int id = Integer.parseInt(req.getParameter("id"));
        LOG.trace("Request parameter: id --> " + id);

        DBManager dbManager = DBManager.getInstance();
        try {
            dbManager.deleteRout(id);
        } catch (DBException e) {
            e.printStackTrace();
        }

        LOG.trace("Forward address --> " + Path.CONTROLLER_LOAD_ADMIN_TABLES);
        LOG.debug("Controller finished, now go to forward address --> " + Path.CONTROLLER_LOAD_ADMIN_TABLES);
        resp.sendRedirect(req.getContextPath() + Path.CONTROLLER_LOAD_ADMIN_TABLES);
    }
}
