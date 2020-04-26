package servlets.user.account;

import db.DBManager;
import db.entity.IntermediateStation;
import db.entity.Rout;
import db.entity.Ticket;
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
import java.util.List;

/**
 * Load user account controller.
 *
 * @author M.Palaguta
 *
 */
@WebServlet("/load_account")
public class ControllerLoadUserAccount extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(ControllerLoadUserAccount.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("Controller starts");

        DBManager dbManager = DBManager.getInstance();
        User user = (User) req.getSession().getAttribute("active_user");
        List<Ticket> tickets = null;
        try {
            tickets = dbManager.findAllTicketsInUser(user);
            LOG.trace("Found in DB: tickets --> " + tickets);
        } catch (DBException e) {
            e.printStackTrace();
        }

        req.setAttribute("user", user);
        LOG.trace("Set the request attribute: user --> " + user);
        req.setAttribute("tickets", tickets);
        LOG.trace("Set the request attribute: tickets --> " + tickets);

        LOG.trace("Forward address --> " + Path.PAGE_ACCOUNT);
        LOG.debug("Controller finished, now go to forward address --> " + Path.PAGE_ACCOUNT);
        getServletContext().getRequestDispatcher(Path.PAGE_ACCOUNT).forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

}
