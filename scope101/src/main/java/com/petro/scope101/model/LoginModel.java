package com.petro.scope101.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class LoginModel implements Parcelable, Serializable {
    private String email;
    private String password;

    public LoginModel(String email, String password){
        this.email = email;
        this.password = password;

    }

    protected LoginModel(Parcel in) {
        email = in.readString();
        password = in.readString();
    }

    public static final Creator<LoginModel> CREATOR = new Creator<LoginModel>() {
        @Override
        public LoginModel createFromParcel(Parcel in) {
            return new LoginModel(in);
        }

        @Override
        public LoginModel[] newArray(int size) {
            return new LoginModel[size];
        }
    };

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(password);
    }
}
