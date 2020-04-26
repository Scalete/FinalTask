package db.web.filter.user;

import db.DBManager;
import db.entity.User;
import exeption.DBException;
import org.apache.log4j.Logger;
import path.Path;
import servlets.user.ControllerLoginUserOrAdmin;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User edit account filter.
 *
 * @author M.Palaguta
 *
 */
public class UserEditAccountFilter implements Filter {

    private static final org.apache.log4j.Logger LOG = Logger.getLogger(UserEditAccountFilter.class);
    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void destroy() {
        filterConfig = null;
    }

    private void doUpdate(ServletRequest servletRequest, ServletResponse servletResponse)
            throws ServletException, IOException {
        LOG.debug("Filter finished");
        filterConfig.getServletContext().getRequestDispatcher(Path.PAGE_EDIT_ACCOUNT).forward(servletRequest, servletResponse);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOG.debug("Filter starts");

        servletRequest.setCharacterEncoding("UTF-8");
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        DBManager dbManager = DBManager.getInstance();

        if (req.getParameter("edit_account") != null){
            User user = (User) req.getSession().getAttribute("active_user");

            final String enterFirstName = servletRequest.getParameter("first_name").trim();
            LOG.trace("Request parameter: first_name --> " + enterFirstName);
            final String enterLastName = servletRequest.getParameter("last_name").trim();
            LOG.trace("Request parameter: last_name --> " + enterLastName);
            final String enterTel = servletRequest.getParameter("tel").trim();
            LOG.trace("Request parameter: tel --> " + enterTel);
            final String enterEmail = servletRequest.getParameter("email").trim();
            LOG.trace("Request parameter: email --> " + enterEmail);

            int countError = 0;

            servletRequest.setAttribute("shortName", false);
            LOG.trace("Set the request attribute: shortName --> " + false);
            servletRequest.setAttribute("errorTel",false);
            LOG.trace("Set the request attribute: errorTel --> " + false);
            servletRequest.setAttribute("user_is_alone", false);
            LOG.trace("Set the request attribute: user_is_alone --> " + false);
            servletRequest.setAttribute("user", user);
            LOG.trace("Set the request attribute: user --> " + user);
            servletRequest.setAttribute("empty_email", false);
            LOG.trace("Set the request attribute: empty_email --> " + false);

            if(enterEmail.length() <= 0){
                servletRequest.setAttribute("empty_email", true);
                LOG.trace("Set the request attribute: empty_email --> " + true);
                countError++;
            }

            if(enterFirstName.length() < 2 || enterLastName.length() < 2){
                servletRequest.setAttribute("shortName", true);
                LOG.trace("Set the request attribute: shortName --> " + true);
                countError++;
            }


            try {
                if(!user.getEmail().equals(enterEmail) && dbManager.checkUserDuplicate(enterEmail)){
                    servletRequest.setAttribute("user_is_alone", true);
                    LOG.trace("Set the request attribute: user_is_alone --> " + true);
                    countError++;
                }
            } catch (DBException e) {
                e.printStackTrace();
            }

            Pattern pattern = Pattern.compile("\\+\\d{12}");
            Matcher matcher = pattern.matcher(enterTel);

            if (!matcher.matches()) {
                servletRequest.setAttribute("errorTel",true);
                LOG.trace("Set the request attribute: errorTel --> " + true);
                countError++;
            }

            if(countError > 0){
                doUpdate(servletRequest,servletResponse);
                return;
            }
        }

        LOG.debug("Filter finished");
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
