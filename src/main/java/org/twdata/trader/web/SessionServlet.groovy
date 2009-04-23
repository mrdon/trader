package org.twdata.trader.web

import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.twdata.trader.SessionFactory
import org.twdata.trader.Session

/**
 * 
 */

public class SessionServlet extends HttpServlet {

    final private SessionFactory sessionFactory;

    SessionServlet(SessionFactory fac)
    {
        this.sessionFactory = fac;
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
    {
        String username = req.getParameter("username");
        Session ses = sessionFactory.create(username);
        req.getSession(true).setAttribute("session", ses);
    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse res)
    {
        req.getSession(true).removeAttribute("session"); 
    }


}