package com.jutt.assignment1;

import org.junit.Test;
import com.jutt.assignment1.ReactionTimer;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by hammadjutt on 2015-10-02.
 */

public class MainActivityTest {
    @Test
    public void TestDataReaction () {
        StatsData data = new StatsData();

        assertThat(data.getReactions().isEmpty(), is(true));
        data.addReaction(500);
        assertThat(data.getReactions().isEmpty(), is(false));
        assertThat(data.getReactions().get(0), is(500));
        data.addReaction(100);
        assertThat(data.getReactions().get(0), is(500));
        assertThat(data.getReactions().get(1), is(100));
        data.clearStats();
        assertThat(data.getReactions().isEmpty(), is(true));
        try {
            data.addReaction(-100);
        } catch (InvalidParameterException e) {
        }
        assertThat(data.getReactions().isEmpty(), is(true));
    }

    @Test
    public void TestDataBuzzer () {
        StatsData data = new StatsData();
        Player p1 = new Player(1);
        Player p2 = new Player(1);
        BuzzerResult br = new BuzzerResult(p1, 4);
        BuzzerResult br2 = new BuzzerResult(p2, 2);
        assertThat(data.getBuzzers().isEmpty(), is(true));

        data.addBuzzer(br);
        assertThat(data.getBuzzers().isEmpty(), is(false));
        assertThat(data.getBuzzers().get(0).getWinner(), is(p1));
        assertThat(data.getBuzzers().get(0).getNumPlayers(), is(4));

        data.addBuzzer(br2);
        assertThat(data.getBuzzers().get(1).getWinner(), is(p2));
        assertThat(data.getBuzzers().get(1).getWinner().playerNumber, is(p1.playerNumber));
        data.clearStats();
        assertThat(data.getBuzzers().isEmpty(), is(true));
    }

    @Test
    public void TestStatsCalc () {
        StatsData data = new StatsData();
        StatsCalc calc = new StatsCalc(data);

        for(int n = 2; n <= 4; n++) {
            for(int k = 1; k <= 4; k++) {
                int x = 0;
                while (x < 20) {
                    data.addBuzzer(new BuzzerResult(new Player(k), n));
                    x++;
                }
            }
        }
        ArrayList<Integer> results = new ArrayList<>();
        for(int n = 2; n <= 4; n++) {
            for(int k = 1; k <= 4; k++) {
                results.add(calc.countBuzzes(new Player(k), n));
            }
        }
        assertThat(results.size(), is(12));
        data.clearStats();
        assertThat(data.getBuzzers().size(), is(0));

        for(int n = 0; n <= 200; n++) {
            data.addReaction(n);
        }

        assertThat(calc.min(), is(0));
        assertThat(calc.min(10), is(191));
        assertThat(calc.min(100), is(101));
        assertThat(calc.max(), is(200));
        assertThat(calc.max(10), is(200));
        assertThat(calc.max(100), is(200));
        assertThat(calc.avg(), is(100));
        assertThat(calc.avg(10), is(195));
        assertThat(calc.avg(100), is(150));
        assertThat(calc.median(), is(100));
        assertThat(calc.median(10), is(195));
        assertThat(calc.median(100), is(150));
        data.clearStats();
        assertThat(calc.avg(), is(0));
        data.addReaction(41);
        data.addReaction(78);
        data.addReaction(73);
        data.addReaction(33);
        data.addReaction(0);
        data.addReaction(21);
        assertThat(calc.median(), is(37));
        data.addReaction(4);
        assertThat(calc.median(), is(33));





    }
}
