package org.twdata.trader.command
/**
 * 
 */

public class CommandException extends RuntimeException {

    public CommandException()
    {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public CommandException(String message)
    {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public CommandException(String message, Throwable cause)
    {
        super(message, cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public CommandException(Throwable cause)
    {
        super(cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

}