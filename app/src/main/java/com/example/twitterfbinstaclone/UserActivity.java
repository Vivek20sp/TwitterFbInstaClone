package com.example.twitterfbinstaclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



import java.util.ArrayList;
import java.util.HashMap;

public class UserActivity extends AppCompatActivity implements MyRecyclerViewAdaptor.ItemClickListenerInterface
{
    public static String Tag="UserActivity.java";
    String email;
    String currentName;
    MyRecyclerViewAdaptor myRecyclerViewAdaptor;
    ArrayList<ProfileObject> mUserArrayList;
    RecyclerView recyclerViewUsers;
    ArrayList<String> followers;
    ArrayList<String> CurrentFollowers;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        recyclerViewUsers=findViewById(R.id.usre_recyclerView);
        fillData();
        Intent intent=new Intent();
        intent=getIntent();
        email=intent.getStringExtra("email");

    }
    public void fillData()
    {
        FirebaseDatabase.getInstance("https://twitterfbinstaclone-b4720-default-rtdb.firebaseio.com/").getReference().child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                mUserArrayList=new ArrayList<>();
                for (DataSnapshot childSnapshot:snapshot.getChildren())
                {
                    HashMap<String,String> value=(HashMap<String,String>) childSnapshot.getValue();

                    assert value != null;
                    String name=value.get("Name");
                    Log.i(Tag,"Name"+name);
                    Object IsFollowing=value.get("IsFollowing");
                    followers=new ArrayList<>();
                    followers = (ArrayList) IsFollowing;
                    Log.i(Tag,"followers"+ followers);

                    assert name != null;
                    String NameLowerCase= name.toLowerCase();
                    if(email.contains(NameLowerCase))
                    {
                        CurrentFollowers = followers;
                        currentName=name;
                    }

                    mUserArrayList.add(new ProfileObject(name,followers,email));
                }
                recyclerViewUsers.setLayoutManager(new LinearLayoutManager(UserActivity.this));
                myRecyclerViewAdaptor=new MyRecyclerViewAdaptor(UserActivity.this,mUserArrayList,UserActivity.this,CurrentFollowers);
                recyclerViewUsers.setAdapter(myRecyclerViewAdaptor);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }

    @Override
    public void onItemClick(boolean isChecked, int position)
    {
        if(isChecked)
        {
            CurrentFollowers.add(myRecyclerViewAdaptor.getName(position));
            FirebaseDatabase.getInstance("https://twitterfbinstaclone-b4720-default-rtdb.firebaseio.com/").getReference().child("User").child(currentName).child("IsFollowing").setValue(CurrentFollowers);
        }
        else
        {
            CurrentFollowers.remove(myRecyclerViewAdaptor.getName(position));
            FirebaseDatabase.getInstance("https://twitterfbinstaclone-b4720-default-rtdb.firebaseio.com/").getReference().child("User").child(currentName).child("IsFollowing").setValue(CurrentFollowers);
        }
    }
}