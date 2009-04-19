package org.twdata.trader;

import org.twdata.trader.guice.SimpleContainer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

/**
 *
 */
public class Main
{
    public static void main(String[] appArgs) throws IOException
    {
        SimpleContainer container = new SimpleContainer();
        Session ses = container.getSessionFactory().create("mrdon");
        /*
        System.out.println("Welcome to trader\n");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String line;
        do {
            line = reader.readLine();
            String[] args = line.split(" ");
            String cmd = args[0];

            Map<String,String> params = new HashMap<String,String>();
            if (args.length > 1)
            {
                for (int x=1; x<args.length; x++) {
                    String[] parts = args[x].split(":");
                    if (parts.length != 2) throw new IllegalArgumentException("Missing parameter name: " + args[x]);
                    params.put(parts[0], parts[1]);
                }
            }
            ses.executeCommand(cmd, params);
        }
        while (!"quit".equals(line));

        */
        ses.executeCommand("visitMarket", Collections.<String, Object> emptyMap());
        ses.executeCommand("buyCommodity", new HashMap<String,Object>(){{put("commodity", "Food"); put("quantity", 3);}});
        ses.executeCommand("leaveMarket", Collections.<String, Object> emptyMap());
        ses.executeCommand("move", new HashMap<String,Object>(){{put("toCity", "Justa");}});
        ses.executeCommand("visitMarket", Collections.<String, Object> emptyMap());
        ses.executeCommand("buyCommodity", new HashMap<String,Object>(){{put("commodity", "Beer"); put("quantity", 5);}});
        ses.executeCommand("buyCommodity", new HashMap<String,Object>(){{put("commodity", "Food"); put("quantity", 1);}});
        ses.executeCommand("leaveMarket", Collections.<String, Object> emptyMap());
        ses.executeCommand("move", new HashMap<String,Object>(){{put("toCity", "Sol");}});
        ses.executeCommand("visitMarket", Collections.<String, Object> emptyMap());
        //ses.executeCommand("buyCommodity", new HashMap<String,Object>(){{put("commodity", "Food"); put("quantity", 3);}});
        ses.executeCommand("sellCommodity", new HashMap<String,Object>(){{put("commodity", "Food"); put("quantity", 4);}});
        ses.executeCommand("buyCommodity", new HashMap<String,Object>(){{put("commodity", "Food"); put("quantity", 3);}});
        ses.executeCommand("leaveMarket", Collections.<String, Object> emptyMap());
        ses.executeCommand("quit", Collections.<String, Object> emptyMap());
    }

}
