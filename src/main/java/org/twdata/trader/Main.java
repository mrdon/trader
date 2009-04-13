package org.twdata.trader;

import org.twdata.trader.model.Game;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

/**
 *
 */
public class Main
{
    public static void main(String[] appArgs) throws IOException
    {
        Session ses = new Session();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Class<Session> sessionClass = Session.class;
        String line;
        while (!"quit".equals(line = reader.readLine()))
        {
            String[] args = line.split(" ");
            Object[] cmdArgs = new Object[0];
            String cmd = args[0];
            if (args.length > 1)
            {
                cmdArgs = new Object[args.length - 1];
            }
            boolean found = false;
            for (Method m : sessionClass.getDeclaredMethods())
            {
                if (cmd.equals(m.getName()) && m.getParameterTypes().length == cmdArgs.length)
                {
                    for (int x=0; x<m.getParameterTypes().length; x++) {
                        Class type = m.getParameterTypes()[x];
                        if (type == int.class) {
                            cmdArgs[x] = Integer.parseInt(args[x + 1]);
                        } else if (type == String.class) {
                            cmdArgs[x] = args[x + 1];
                        } else {
                            throw new IllegalArgumentException("Unknown type: " + type);
                        }
                    }
                    try
                    {
                        m.invoke(ses, cmdArgs);
                    }
                    catch (IllegalAccessException e)
                    {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                    catch (InvocationTargetException e)
                    {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Command not found");
            }
        }
        /*
        ses.start("mrdon");
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
        */
    }

}
