package servlets.user.account;

import db.DBManager;
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
import java.sql.SQLException;
import java.util.List;

/**
 * Edit user account controller.
 *
 * @author M.Palaguta
 *
 */
@WebServlet("/edit_account")
public class ControllerEditUserAccount extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(ControllerEditUserAccount.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("Controller starts");

        User user = (User) req.getSession().getAttribute("active_user");
        LOG.trace("Get from session attribute: active_user --> " + user);

        req.setAttribute("user", user);
        LOG.trace("Set the request attribute: user --> " + user);

        LOG.trace("Forward address --> " + Path.PAGE_EDIT_ACCOUNT);
        LOG.debug("Controller finished, now go to forward address --> " + Path.PAGE_EDIT_ACCOUNT);
        getServletContext().getRequestDispatcher(Path.PAGE_EDIT_ACCOUNT).forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("Controller starts");

        req.setCharacterEncoding("UTF-8");
        DBManager dbManager = DBManager.getInstance();

        User user = new User();
        user.setId(Integer.parseInt(req.getParameter("user_id").trim()));
        user.setFirstName(req.getParameter("first_name").trim());
        user.setLastName(req.getParameter("last_name").trim());
        user.setTelephone(req.getParameter("tel").trim());
        user.setEmail(req.getParameter("email").trim());

        try {
            dbManager.editUserAccount(user);
            User new_user = dbManager.findUserById(user.getId());
            req.getSession().setAttribute("active_user", new_user);
            LOG.trace("Update session attribute: active_user --> " + new_user);
        } catch (DBException e) {
            e.printStackTrace();
        }

        LOG.trace("Forward address --> " + Path.CONTROLLER_LOAD_ACCOUNT);
        LOG.debug("Controller finished, now go to forward address --> " + Path.CONTROLLER_LOAD_ACCOUNT);
        getServletContext().getRequestDispatcher(Path.CONTROLLER_LOAD_ACCOUNT).forward(req,resp);
    }
}
