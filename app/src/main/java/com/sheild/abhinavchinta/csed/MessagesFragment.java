package com.sheild.abhinavchinta.csed;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abhinavchinta.csed.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.sheild.abhinavchinta.csed.models.Message;
import com.sheild.abhinavchinta.csed.models.Test;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;
import static com.sheild.abhinavchinta.csed.SplashActivity.listdata1;



public class MessagesFragment extends Fragment implements SplashActivity.CallBack {



    private DatabaseReference mRestaurantReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;
    public static RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<Message> listdataa= new ArrayList<>();
    private FirebaseDatabase database;
    private DatabaseReference DBR;
    private Button button;
    private FirebaseDatabase db;
    private DatabaseReference dbr;
    private SwipeRefreshLayout swipeRefreshLayout;
    private DatabaseReference DBRmessages;

    public MessagesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //listdataa= Test
        View rootview =  inflater.inflate(R.layout.fragment_messages, container, false);
        database = FirebaseDatabase.getInstance();
        adapter = new MyAdapter(listdata1);
        recyclerView= (RecyclerView)rootview.findViewById(R.id.recyclerview1);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager LM = new LinearLayoutManager(getContext());
        //LM.setReverseLayout(true);
        //LM.setStackFromEnd(true);
        //recyclerView.smoothScrollToPosition(listdata1.size());
        recyclerView.setLayoutManager(LM);

        SplashActivity.callback = MessagesFragment.this;

        swipeRefreshLayout = (SwipeRefreshLayout)rootview.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        button = (Button)rootview.findViewById(R.id.messagebutton);
        //if (Test.IsAdmin==0){button.setVisibility(View.GONE);}
        button.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                                alertDialog.setMessage("Enter Message");

                                final EditText input = new EditText(getContext());
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT);
                                input.setLayoutParams(lp);
                                alertDialog.setView(input);

                                alertDialog.setPositiveButton("SUBMIT",
                                        new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int which) {
                                                String message = input.getText().toString();
                                                Message newmessage = new Message(message,Test.name,new SimpleDateFormat("dd/MM/yy", Locale.getDefault()).format(new Date()));
//                                                newmessage.setMessage(message);
//                                                newmessage.setName(Test.name);
//                                                newmessage.setDate(new SimpleDateFormat("dd/MM/yy", Locale.getDefault()).format(new Date()));
                                                db= FirebaseDatabase.getInstance();
                                                dbr = db.getReference().child("messages");
                                                String id  = dbr.push().getKey();
                                                dbr.child(id).setValue(newmessage);
                                            }
                                        });

                                alertDialog.setNegativeButton("CANCEL",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                                alertDialog.show();

                                      }
                                  });


                //recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(listdata1.size()-1);
        return rootview;
    }

    private void refresh()
    {
        DBRmessages = database.getReference("messages");
        listdata1.clear();
        DBRmessages.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //updateList(dataSnapshot);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(),"Check your internet connection",Toast.LENGTH_LONG).show();
            }
        });
        DBRmessages.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.i(TAG, "onChildAdded: herrr");
                updateList(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {updateList(dataSnapshot);
                Log.i(TAG, "onChildChanged: ");}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {updateList(dataSnapshot);
                Log.i(TAG, "onChildRemoved: ");}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {updateList(dataSnapshot);
                Log.i(TAG, "onChildMoved: ");}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    public void updateList(DataSnapshot dataSnapshot)
    {
        Message message= dataSnapshot.getValue(Message.class);
        listdata1.add(message);
    }

    @Override
    public void methodToCallBack() {
        recyclerView.smoothScrollToPosition(listdata1.size()-1);
    }

    public class MyAdapter extends RecyclerView.Adapter<MessagesFragment.MyAdapter.MyViewHolder>{

        private List<Message> listarray = new ArrayList<>();

        public MyAdapter(List<Message> list){
            //this.listarray.clear();
            this.listarray= list;
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






