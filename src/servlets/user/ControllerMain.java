package servlets.user;

import org.apache.log4j.Logger;
import path.Path;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Main controller.
 *
 * @author M.Palaguta
 *
 */
@WebServlet("/main")
public class ControllerMain extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(ControllerMain.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOG.debug("Controller starts");
        boolean exit = Boolean.parseBoolean(req.getParameter("exit"));
        LOG.trace("Request parameter: exit --> " + exit);
        if(exit){
            HttpSession session = req.getSession();
            session.invalidate();
        }

        LOG.trace("Forward address --> " + Path.PAGE_MAIN);
        LOG.debug("Controller finished, now go to forward address --> " + Path.PAGE_MAIN);
        resp.sendRedirect(req.getContextPath() + Path.PAGE_MAIN);
    }
}