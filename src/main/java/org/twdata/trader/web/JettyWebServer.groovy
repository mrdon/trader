package org.twdata.trader.web

import org.mortbay.jetty.Server
import org.mortbay.jetty.servlet.Context
import org.mortbay.jetty.servlet.ServletHolder
import org.twdata.trader.SessionFactory

/**
 * 
 */

public class JettyWebServer {

    SessionFactory sessionFactory;

    def JettyWebServer(SessionFactory fac)
    {
        sessionFactory = fac;
        Server server = new Server(8080);
        Context root = new Context(server,"/",Context.SESSIONS);
        root.addServlet(new ServletHolder(new CommandServlet()), "/command");
        root.addServlet(new ServletHolder(new SessionServlet(sessionFactory)), "/session");
        server.start();
    }
}