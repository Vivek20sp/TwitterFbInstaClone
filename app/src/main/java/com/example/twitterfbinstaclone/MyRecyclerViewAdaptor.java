package com.example.twitterfbinstaclone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyRecyclerViewAdaptor extends RecyclerView.Adapter<MyRecyclerViewAdaptor.ViewHolder>
{
    ArrayList<ProfileObject> mData;
    LayoutInflater layoutInflater;
    ItemClickListenerInterface mItemClickListenerInterface;
    ArrayList<String> mCurrentFollowers;


    MyRecyclerViewAdaptor(Context context, ArrayList<ProfileObject> data,ItemClickListenerInterface ItemClickListenerInterface,ArrayList<String> CurrentFollowers)
    {
        this.mCurrentFollowers=CurrentFollowers;
        this.mData=data;
        this.layoutInflater=LayoutInflater.from(context);
        mItemClickListenerInterface=ItemClickListenerInterface;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view=layoutInflater.inflate(R.layout.recycler_view_item,parent,false);
        return new ViewHolder(view);
    }
    public String getName(int position)
    {
        return mData.get(position).getName();
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        ProfileObject data=mData.get(position);
        String text=data.getName();
        holder.checkedTextView.setText(text);
        if(mCurrentFollowers!=null)
        {
            for(int i=0;i<mCurrentFollowers.size();i++)
            {
                if(data.getName().equalsIgnoreCase(mCurrentFollowers.get(i)))
                {
                    holder.checkedTextView.setChecked(true);
                    holder.checkedTextView.setCheckMarkDrawable(android.R.drawable.checkbox_on_background);
                }
            }
        }
    }

    @Override
    public int getItemCount()
    {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        CheckedTextView checkedTextView;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            checkedTextView=itemView.findViewById(R.id.textViewUser);
            checkedTextView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v)
        {
            if(checkedTextView.isChecked())
            {
                checkedTextView.setChecked(false);
                checkedTextView.setCheckMarkDrawable(android.R.drawable.checkbox_off_background);
                if(mItemClickListenerInterface!=null)
                {
                    mItemClickListenerInterface.onItemClick(false,getAdapterPosition());
                }
            }
            else
            {
                checkedTextView.setChecked(true);
                checkedTextView.setCheckMarkDrawable(android.R.drawable.checkbox_on_background);
                if(mItemClickListenerInterface!=null)
                {
                    mItemClickListenerInterface.onItemClick(true,getAdapterPosition());
                }
            }

        }
    }
    public interface ItemClickListenerInterface
    {
        void onItemClick(boolean isChecked ,int position);
    }
}
