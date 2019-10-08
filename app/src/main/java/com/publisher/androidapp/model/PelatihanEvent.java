package com.publisher.androidapp.model;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import com.applandeo.materialcalendarview.EventDay;
import com.publisher.androidapp.R;

import java.util.Calendar;

public class PelatihanEvent extends EventDay implements Parcelable {
    private String mNote;
    private int imageResource;
    public PelatihanEvent(Calendar day, Drawable drawable) {
        super(day, drawable);
        mNote = "";
        this.imageResource = imageResource;
    }
    String getNote() {
        return mNote;
    }
    private PelatihanEvent(Parcel in) {
        super((Calendar) in.readSerializable(), in.readInt());
        mNote = in.readString();
    }
    public static final Creator<PelatihanEvent> CREATOR = new Creator<PelatihanEvent>() {
        @Override
        public PelatihanEvent createFromParcel(Parcel in) {
            return new PelatihanEvent(in);
        }
        @Override
        public PelatihanEvent[] newArray(int size) {
            return new PelatihanEvent[size];
        }
    };
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeSerializable(getCalendar());
        parcel.writeInt(imageResource);
        parcel.writeString(mNote);
    }


    @Override
    public int describeContents() {
        return 0;
    }
}