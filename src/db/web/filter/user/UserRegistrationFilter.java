package db.web.filter.user;

import db.DBManager;
import exeption.DBException;
import org.apache.log4j.Logger;
import path.Path;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User registration filter.
 *
 * @author M.Palaguta
 *
 */
public class UserRegistrationFilter implements Filter {

    private static final org.apache.log4j.Logger LOG = Logger.getLogger(UserRegistrationFilter.class);
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
        filterConfig.getServletContext().getRequestDispatcher(Path.PAGE_REGISTRATION).forward(servletRequest, servletResponse);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOG.debug("Filter starts");

        servletRequest.setCharacterEncoding("UTF-8");
        DBManager dbManager = DBManager.getInstance();

        final String enterEmail = servletRequest.getParameter("email").trim();
        LOG.trace("Request parameter: email --> " + enterEmail);
        final String enterLastName = servletRequest.getParameter("last_name").trim();
        LOG.trace("Request parameter: last_name --> " + enterLastName);
        final String enterFirstName = servletRequest.getParameter("first_name").trim();
        LOG.trace("Request parameter: first_name --> " + enterFirstName);
        final String enterTel = servletRequest.getParameter("tel").trim();
        LOG.trace("Request parameter: tel --> " + enterTel);
        final String enterPassword = servletRequest.getParameter("password").trim();
        LOG.trace("Request parameter: password --> " + enterPassword);

        int countError = 0;

        servletRequest.setAttribute("shortName", false);
        LOG.trace("Set the request attribute: shortName --> " + false);
        servletRequest.setAttribute("shortPassword",false);
        LOG.trace("Set the request attribute: shortPassword --> " + false);
        servletRequest.setAttribute("errorTel",false);
        LOG.trace("Set the request attribute: errorTel --> " + false);
        servletRequest.setAttribute("user_is_alone", false);
        LOG.trace("Set the request attribute: user_is_alone --> " + false);
        servletRequest.setAttribute("empty_email", false);
        LOG.trace("Set the request attribute: empty_email --> " + false);

        Pattern pattern = Pattern.compile("\\+\\d{12}");
        Matcher matcher = pattern.matcher(enterTel);

        if (!matcher.matches()) {
            servletRequest.setAttribute("errorTel",true);
            LOG.trace("Set the request attribute: errorTel --> " + true);
            countError++;
        }

        if(enterFirstName.length() < 2 || enterLastName.length() < 2){
            servletRequest.setAttribute("shortName", true);
            LOG.trace("Set the request attribute: shortName --> " + true);
            countError++;
        }

        if(enterEmail.length() <= 0){
            servletRequest.setAttribute("empty_email", true);
            LOG.trace("Set the request attribute: empty_email --> " + true);
            countError++;
        }

        if(enterPassword.length() < 8){
            servletRequest.setAttribute("shortPassword",true);
            LOG.trace("Set the request attribute: shortPassword --> " + true);
            countError++;
        }

        try {
            if(dbManager.checkUserDuplicate(enterEmail)){
                servletRequest.setAttribute("user_is_alone", true);
                LOG.trace("Set the request attribute: user_is_alone --> " + true);
                countError++;
            }
        } catch (DBException e) {
            e.printStackTrace();
        }

        if(countError > 0){
            doUpdate(servletRequest,servletResponse);
            return;
        }

        LOG.debug("Filter finished");
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
