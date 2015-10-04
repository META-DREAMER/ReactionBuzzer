package com.jutt.assignment1;

import java.security.InvalidParameterException;

/**
 * Created by hammadjutt on 2015-10-03.
 */
public class BuzzerResult {
    protected Player winner;
    protected Integer numPlayers;

    public BuzzerResult(Player winner, Integer numPlayers) {
        if(numPlayers > 4 || numPlayers < 2)
            throw new InvalidParameterException("Buzzer Result must have 2-4 players");
        else if(winner.getNumber() > 4 || winner.getNumber() < 1)
            throw new InvalidParameterException("Invalid Player number");
        else {
            this.winner = winner;
            this.numPlayers = numPlayers;
        }
    }

    public Player getWinner() {
        return this.winner;
    }

    public Integer getNumPlayers () {
        return this.numPlayers;
    }

}