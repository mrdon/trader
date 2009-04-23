package org.twdata.trader.command;

import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.util.Set;

/**
 *
 */
public interface Command
{
    int getTurnCost();

    CommandErrors validate();
    CommandResponse execute();
}
