package com.yuyang.fitsystemwindowstestdrawer.googleWidget.dataBinding;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * DataBinding绑定的数据
 */

public class UserBean extends BaseObservable {
    public String firstName;
    public String lastName;
    public boolean isAdult;

    public UserBean(String firstName, String lastName, boolean isAdult) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isAdult = isAdult;
    }

    @Bindable
    public String getFirstName() {
        return firstName;
    }

    @Bindable
    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public void setAdult(boolean adult) {
        isAdult = adult;
    }
}
