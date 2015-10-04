package com.jutt.assignment1;

import java.security.InvalidParameterException;
import java.util.ArrayList;

/**
 * Created by hammadjutt on 2015-10-03.
 */
public class StatsData {
    private ArrayList<Integer> reactions;
    private ArrayList<BuzzerResult> buzzers;

    public StatsData () {
        reactions = new ArrayList<>();
        buzzers = new ArrayList<>();
    }

    public ArrayList<Integer> getReactions () {
        return reactions;
    }

    public ArrayList<BuzzerResult> getBuzzers () {
        return buzzers;
    }

    public void addReaction(Integer t) {
        if(t >= 0)
            reactions.add(t);
        else
            throw new InvalidParameterException("Time must be positive!");
    }
    public void addBuzzer(BuzzerResult br) {
        buzzers.add(br);
    }

    public void clearStats() {
        reactions.clear();
        buzzers.clear();
    }

    //Loading and saving taken from https://github.com/joshua2ua/lonelyTwitter

}
