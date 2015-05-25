package com.homeassignment.softunitrainingdemo.Models;

/**
 * Created by Bon on 5/19/2015.
 */
public class University {
    public University(int id, String address, String name) {
        this.id = id;
        Address = address;
        Name = name;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public  int id;
    public String Name;
    public String Address;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }


}
