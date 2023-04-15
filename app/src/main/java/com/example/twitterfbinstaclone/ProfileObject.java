package com.example.twitterfbinstaclone;

import java.util.ArrayList;

public class ProfileObject
{
    private final String name;
    private final ArrayList<String> followers;
    private final String email;

    ProfileObject(String name,ArrayList<String> followers,String email)
    {
        this.name=name;
        this.email=email;
        this.followers=followers;
    }

    public String getName()
    {
        return name;
    }

    public ArrayList<String> getFollowers()
    {
        return followers;
    }

    public String getEmail() {
        return email;
    }

}
