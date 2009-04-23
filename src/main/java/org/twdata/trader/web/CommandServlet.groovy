package org.twdata.trader.web

import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.mortbay.util.ajax.JSON
import org.twdata.trader.Session

/**
 * 
 */

public class CommandServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
    {
        Session ses = req.getSession().getAttribute("session");
        String cmd = req.getParameter("cmd");
        if (cmd == null) {
            res.sendError(500, "cmd parameter is required");
        }
        String params = req.getParameter("params");
        if (params == null) {
            res.sendError(500, "params parameter is required"); 
        }


        Map<String,Object> obj = JSON.parse(params);
        System.out.println("Executing :"+cmd+ " params: " + obj);
        ses.executeCommand(cmd, obj);
    }

    def toJSON( obj ) {
        if( ! obj ) return "";
        if( obj instanceof Map ) {
            return "{" + obj.keySet().inject("") { accu, k ->
                (accu.length() == 0? "": "${accu},\\n") +
                "'${k}': ${toJSON(obj[k])}"
            } + "\\n}"
        }
        if( obj instanceof List || obj instanceof Set ) {
          return "[" + obj.inject("") { accu, item ->  
               (accu.length() == 0? "": "${accu},\\n") +
               toJSON(item)
           } + "]"
       }
       return "’${obj}’"
   }


}