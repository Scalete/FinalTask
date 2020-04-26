package servlets.user;

import db.DBManager;
import db.entity.User;
import exeption.DBException;
import org.apache.log4j.Logger;
import path.Path;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Registration user controller.
 *
 * @author M.Palaguta
 *
 */
@WebServlet("/login_registration_user")
public class ControllerRegistrationUser extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(ControllerRegistrationUser.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(Path.PAGE_REGISTRATION).forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOG.debug("Controller starts");

        req.setCharacterEncoding("UTF-8");
        final String enterEmail = req.getParameter("email").trim();
        LOG.trace("Request parameter: enterEmail --> " + enterEmail);
        final String enterFirstName = req.getParameter("first_name").trim();
        LOG.trace("Request parameter: enterFirstName --> " + enterFirstName);
        final String enterLastName = req.getParameter("last_name").trim();
        LOG.trace("Request parameter: enterLastName --> " + enterLastName);
        final String enterPassword = req.getParameter("password").trim();
        LOG.trace("Request parameter: enterPassword --> " + enterPassword);
        final String enterTel = req.getParameter("tel").trim();
        LOG.trace("Request parameter: enterTel --> " + enterTel);

        DBManager dbManager = DBManager.getInstance();

        User user = new User();
        user.setEmail(enterEmail);
        user.setPassword(enterPassword);
        user.setTelephone(enterTel);
        user.setFirstName(enterFirstName);
        user.setLastName(enterLastName);

        try {
            dbManager.addUser(user);
            LOG.trace("Add in DB: user --> " + user);
        } catch (DBException e) {
            e.printStackTrace();
        }

        LOG.trace("Forward address --> " + Path.PAGE_MAIN);
        LOG.debug("Controller finished, now go to forward address --> " + Path.PAGE_MAIN);
        resp.sendRedirect(req.getContextPath() + Path.PAGE_MAIN);
    }

}
