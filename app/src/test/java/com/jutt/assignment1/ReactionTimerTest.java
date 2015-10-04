//package com.jutt.assignment1;

import org.junit.Test;
import com.jutt.assignment1.ReactionTimer;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by hammadjutt on 2015-10-02.
 */

public class ReactionTimerTest {
    @Test
    public void TestTimer () {
        ReactionTimer timer = new ReactionTimer();
        assertThat(timer.delayPassed(), is(false));

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertThat(timer.getReaction() >= 0, is(true));
        assertThat(timer.delayPassed(), is(true));
        timer.resetTimer();
        assertThat(timer.getReaction() >= 0, is(false));
        assertThat(timer.delayPassed(), is(false));
    }

}
