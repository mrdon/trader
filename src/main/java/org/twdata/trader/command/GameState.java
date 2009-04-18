package org.twdata.trader.command;

public enum GameState
{
    END,
    IN_CITY,
    IN_MARKET;

    GameState check(final GameState newGameState)
    {
        return newGameState;
    }

    void illegalState(final GameState newGameState)
    {
        throw new IllegalStateException("Cannot go from GameState: " + this + " to: " + newGameState);
    }
}