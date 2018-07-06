package com.ravindra.siit.chinkara.DataObj;

public class UserData {
    String name;
    String fName;
    String dob;
    String address;
    String mobile;

    public UserData(String name, String fName, String dob, String address, String mobile) {
        this.name = name;
        this.fName = fName;
        this.dob = dob;
        this.address = address;
        this.mobile = mobile;
    }

    public UserData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
