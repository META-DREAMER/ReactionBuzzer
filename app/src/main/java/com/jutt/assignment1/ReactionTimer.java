package com.jutt.assignment1;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by hammadjutt on 2015-10-02.
 */
public class ReactionTimer {

    private long delay;
    private long start;
    private static int max = 2000;
    private static int min = 10;
    private Random rand = new Random();

    public ReactionTimer() {
        resetTimer();
    }
    public ReactionTimer resetTimer () {
        delay = ((long) (rand.nextDouble() * (max - min))) + min;
        start = System.nanoTime();
        return this;
    }

    public Boolean delayPassed() {
        return getTime() >= delay;
    }

    public int getReaction () {
        long reaction = getTime() - delay;
        return (int) reaction;
    }

    public long getTime() {
        long end = System.nanoTime();
        return TimeUnit.MILLISECONDS.convert(end-start, TimeUnit.NANOSECONDS);
    }

}
