package org.twdata.trader.event.events

import org.twdata.trader.command.CommandResponse
import org.twdata.trader.Session

/**
 * 
 */

public class CommandExecutedEvent {
    Object command;
    CommandResponse response;
    Session session;
}