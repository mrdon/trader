package org.twdata.trader;

import org.twdata.trader.guice.SimpleContainer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Main
{
    public static void main(String[] appArgs) throws IOException
    {
        SimpleContainer container = new SimpleContainer();
        Session ses = container.getSessionFactory().create("mrdon");

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

        /*
        ses.visitMarket();
        ses.buyCommodity("Food", 3);
        ses.leaveMarket();
        ses.move("Justa");
        ses.visitMarket();
        ses.buyCommodity("Beer", 5);
        ses.buyCommodity("Food", 1);
        ses.leaveMarket();
        ses.move("Brennnat");
        ses.visitMarket();
        ses.buyCommodity("Food", 3);
        ses.sellCommodity("Food", 4);
        ses.buyCommodity("Food", 3);
        ses.leaveMarket();
        ses.quit();
        */
    }

}
