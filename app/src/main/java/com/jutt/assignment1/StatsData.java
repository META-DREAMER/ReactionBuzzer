package com.jutt.assignment1;

import android.os.Parcel;
import android.os.Parcelable;

import java.security.InvalidParameterException;
import java.util.ArrayList;

/**
 * Created by hammadjutt on 2015-10-03.
 */
public class StatsData implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(this.reactions);
        parcel.writeTypedList(buzzers);

    }

    public static final Parcelable.Creator<StatsData> CREATOR = new Parcelable.Creator<StatsData>() {
        public StatsData createFromParcel(Parcel in) {
            return new StatsData(in);
        }

        public StatsData[] newArray(int size) {
            return new StatsData[size];
        }
    };

    private StatsData(Parcel in) {
        this.reactions = in.readArrayList(Integer.class.getClassLoader());
        in.readTypedList(this.buzzers, BuzzerResult.CREATOR);
    }

}
