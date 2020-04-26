package servlets.user.ticket;

import db.DBManager;
import db.entity.Carriage;
import db.entity.Ticket;
import db.entity.Trip;
import db.entity.User;
import exeption.DBException;
import org.apache.log4j.Logger;
import path.Path;
import servlets.user.ControllerLoginUserOrAdmin;

import javax.print.attribute.UnmodifiableSetException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Buy ticket controller.
 *
 * @author M.Palaguta
 *
 */
@WebServlet("/buy_ticket")
public class ControllerBuyTicket extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(ControllerBuyTicket.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("Controller starts");
        DBManager dbManager = DBManager.getInstance();

        User user = null;
        Trip trip = null;
        Carriage carriage = null;
        try {
            user = dbManager.findUserById(Integer.parseInt(req.getParameter("user_id")));
            LOG.trace("Found in DB: currentUser --> " + user);
            trip = dbManager.findTripById(Integer.parseInt(req.getParameter("trip_id")));
            LOG.trace("Found in DB: currentUser --> " + trip);
            carriage = dbManager.findCarriageById(Integer.parseInt(req.getParameter("carriage_id")));
            LOG.trace("Found in DB: currentUser --> " + carriage);
        } catch (DBException e) {
            e.printStackTrace();
        }

        Ticket ticket = new Ticket(user, trip, carriage);

        req.setAttribute("not_enough_money", false);
        LOG.trace("Set the request attribute: not_enough_money --> " + false);

        if(!isUserMoneyEnough(user, trip)){
            req.setAttribute("not_enough_money", true);
            LOG.trace("Set the request attribute: not_enough_money --> " + true);
            req.setAttribute("ticket", ticket);
            LOG.trace("Set the request attribute: ticket --> " + ticket);

            LOG.trace("Forward address --> " + Path.PAGE_TICKET);
            LOG.debug("Controller finished, now go to forward address --> " + Path.PAGE_TICKET);
            getServletContext().getRequestDispatcher(Path.PAGE_TICKET).forward(req,resp);
            return;
        }

        try {
            buySeat(carriage, dbManager, user, trip);
            dbManager.addTicket(ticket);
            LOG.trace("Add in DB: ticket --> " + ticket);
            User newUser = dbManager.findUserById(Integer.parseInt(req.getParameter("user_id")));
            LOG.trace("Found in DB: newUser --> " + newUser);
            req.getSession().setAttribute("active_user", newUser);
            LOG.trace("Update the session attribute: active_user --> " + newUser);
        } catch (DBException e) {
            e.printStackTrace();
        }

        LOG.trace("Forward address --> " + Path.CONTROLLER_LOAD_ACCOUNT);
        LOG.debug("Controller finished, now go to forward address --> " + Path.CONTROLLER_LOAD_ACCOUNT);
        getServletContext().getRequestDispatcher(Path.CONTROLLER_LOAD_ACCOUNT).forward(req,resp);
    }

    private void buySeat(Carriage carriage, DBManager dbManager, User user, Trip trip) throws DBException {
        carriage.setNumSeats(carriage.getNumSeats() - 1);
        dbManager.buySeatInCarriage(carriage);
        LOG.trace("Buy seat in carriage: carriage --> " + carriage);
        dbManager.changeUserCount(user, trip.getPrice());
    }

    public boolean isUserMoneyEnough(User user, Trip trip){
        return user.getCount() >= trip.getPrice();
    }
}
