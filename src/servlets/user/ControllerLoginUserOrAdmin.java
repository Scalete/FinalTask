package servlets.user;

import db.DBManager;
import db.entity.User;
import org.apache.log4j.Logger;
import path.Path;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Login user or admin controller.
 *
 * @author M.Palaguta
 *
 */
@WebServlet("/login")
public class ControllerLoginUserOrAdmin extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(ControllerLoginUserOrAdmin.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(Path.PAGE_LOGIN).forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("Controller starts");

        req.setCharacterEncoding("UTF-8");
        final String adminLogin = ResourceBundle.getBundle("admin").getString("admin.login");
        final String adminPassword = ResourceBundle.getBundle("admin").getString("admin.password");

        final String enterMail = req.getParameter("mail").trim();
        LOG.trace("Request parameter: mail --> " + enterMail);
        final String enterPassword = req.getParameter("password").trim();
        LOG.trace("Request parameter: password --> " + enterPassword);

        try {
            req.setAttribute("error_mail", false);
            LOG.trace("Set the request attribute: error_mail --> " + false);

            DBManager dbManager = DBManager.getInstance();
            HttpSession session = req.getSession();
            User currentUser = dbManager.findUserByPasswordAndMail(enterMail, enterPassword);
            LOG.trace("Found in DB: currentUser --> " + currentUser);

            if(enterMail.equals(adminLogin) && enterPassword.equals(adminPassword)){
                LOG.trace("Forward address --> " + Path.CONTROLLER_LOAD_ADMIN_TABLES);
                LOG.debug("Controller finished, now go to forward address --> " + Path.CONTROLLER_LOAD_ADMIN_TABLES);
                getServletContext().getRequestDispatcher(Path.CONTROLLER_LOAD_ADMIN_TABLES).forward(req, resp);
            }else if(currentUser != null) {
                session.setAttribute("active_user", currentUser);
                LOG.trace("Set the session attribute: user --> " + currentUser);
                session.setAttribute("mail", enterMail);
                LOG.trace("Set the session attribute: mail --> " + enterMail);
                session.setAttribute("password", enterPassword);
                LOG.trace("Set the session attribute: password --> " + enterPassword);

                LOG.trace("Forward address --> " + Path.PAGE_MAIN);
                LOG.debug("Controller finished, now go to forward address --> " + Path.PAGE_MAIN);
                getServletContext().getRequestDispatcher(Path.PAGE_MAIN).forward(req, resp);
            }else {
                req.setAttribute("error_mail", true);
                LOG.trace("Set the request attribute: error_mail --> " + true);

                LOG.trace("Forward address --> " + Path.PAGE_LOGIN);
                LOG.debug("Controller finished, now go to forward address --> " + Path.PAGE_LOGIN);
                getServletContext().getRequestDispatcher(Path.PAGE_LOGIN).forward(req, resp);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

}
