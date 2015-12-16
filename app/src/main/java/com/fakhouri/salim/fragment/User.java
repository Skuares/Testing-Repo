package com.fakhouri.salim.fragment;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by salim on 12/16/2015.
 */
public class User {

    private String firstName;
    private String lastName;
    private String username;
    private String email;

    private int age;
    private Bitmap userImage;


    // constructor without image
    public User(String firstName,String lastName, String username, String email, int age){

        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.age = age;

        // if this contsructor is used, use placeholder image
        this.userImage = BitmapFactory.decodeResource(Resources.getSystem(),R.drawable.placeholderuser);

    }

    // constructor with image
    public User(String firstName,String lastName, String username, String email, int age,Bitmap userImage){

        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.age = age;

        this.userImage = userImage;


    }


    // create getters

    public String getFirstName(){

        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getUsername(){
        return username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Bitmap getUserImage() {
        return userImage;
    }

    public void setUserImage(Bitmap userImage) {
        this.userImage = userImage;
    }
}
