package servlets.user.ticket;

import db.DBManager;
import db.entity.*;
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
 * Form ticket controller.
 *
 * @author M.Palaguta
 *
 */
@WebServlet("/form_ticket")
public class ControllerFormTicket extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(ControllerFormTicket.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("Controller starts");
        DBManager dbManager = DBManager.getInstance();
        Trip trip = null;
        Carriage carriage = null;
        try {
            trip = dbManager.findTripById(Integer.parseInt(req.getParameter("id_trip")));
            LOG.trace("Found in DB: trip --> " + trip);
            carriage = dbManager.findCarriageById(Integer.parseInt(req.getParameter("id_carriage")));
            LOG.trace("Found in DB: carriage --> " + carriage);
        } catch (DBException e) {
            e.printStackTrace();
        }

        User user = (User)req.getSession().getAttribute("active_user");
        LOG.trace("Found in session: user --> " + user);

        Ticket ticket = new Ticket(user, trip, carriage);

        req.setAttribute("ticket", ticket);
        LOG.trace("Set the request attribute: ticket --> " + ticket);

        LOG.trace("Forward address --> " + Path.PAGE_TICKET);
        LOG.debug("Controller finished, now go to forward address --> " + Path.PAGE_TICKET);
        getServletContext().getRequestDispatcher(Path.PAGE_TICKET).forward(req,resp);
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

}
