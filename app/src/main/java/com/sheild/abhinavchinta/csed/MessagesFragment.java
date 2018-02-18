package com.sheild.abhinavchinta.csed;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abhinavchinta.csed.R;
import com.sheild.abhinavchinta.csed.models.Message;
import com.sheild.abhinavchinta.csed.models.Test;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class MessagesFragment extends Fragment {

    private DatabaseReference mRestaurantReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<Message> listdataa= new ArrayList<>();
    private FirebaseDatabase database;
    private DatabaseReference DBR;

    public MessagesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //listdataa= Test
        View rootview =  inflater.inflate(R.layout.fragment_messages, container, false);
        database = FirebaseDatabase.getInstance();
        adapter = new MyAdapter(listdataa);
        recyclerView= (RecyclerView)rootview.findViewById(R.id.recyclerview1);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager LM = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(LM);



        //recyclerView.setAdapter(adapter);

        return rootview;
    }


    public class MyAdapter extends RecyclerView.Adapter<MessagesFragment.MyAdapter.MyViewHolder>{

        private List<Message> listarray = new ArrayList<>();

        public MyAdapter(List<Message> list){
            this.listarray=Test.getListdata();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message,parent,false);
            return new MessagesFragment.MyAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MessagesFragment.MyAdapter.MyViewHolder holder, int position) {
            Message message = listarray.get(position);
            holder.textViewdate.setText(message.getDate());
            holder.textViewmessage.setText(message.getMessage());
            holder.textViewname.setText(message.getName());
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{

            TextView textViewmessage;
            TextView textViewname;
            TextView textViewdate;
            public MyViewHolder(View itemView) {
                super(itemView);
                textViewmessage = (TextView)itemView.findViewById(R.id.message_content);
                textViewname = (TextView)itemView.findViewById(R.id.message_name);
                textViewdate = (TextView)itemView.findViewById(R.id.message_date_time);
            }
        }

        @Override
        public int getItemCount() {
            return listarray.size();
        }
    }


    }






