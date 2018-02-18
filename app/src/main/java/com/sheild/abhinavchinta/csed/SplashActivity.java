package com.sheild.abhinavchinta.csed;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.abhinavchinta.csed.R;
import com.sheild.abhinavchinta.csed.models.Member;
import com.sheild.abhinavchinta.csed.models.Message;
import com.sheild.abhinavchinta.csed.models.Strings;
import com.sheild.abhinavchinta.csed.models.Task;
import com.sheild.abhinavchinta.csed.models.Test;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private List<Message> listdata1= new ArrayList<>();
    private List<Member> listdata2= new ArrayList<>();
    private List<Task> listdata3= new ArrayList<>();
    private FirebaseDatabase database;
    private DatabaseReference DBRmessages, DBRmembers, DBRtasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        database = FirebaseDatabase.getInstance();

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {

        GetMembers();
        GetMessages();
        GetTasks();
        }
        else {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        }

    }

    void GetMessages(){

            DBRmessages = database.getReference("messages");
            listdata1.clear();
            DBRmessages.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Test.listdata=listdata1;
                    /*startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();*/
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            DBRmessages.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Message message= dataSnapshot.getValue(Message.class);
                    listdata1.add(message);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {}

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });
    }

    void GetMembers(){


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        //String name = user.getDisplayName();

        //Test.name=uid;

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query = reference.child("member").child(auth.getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        // do something with the individual "issues"
                        Member mymember= dataSnapshot.getValue(Member.class);
                        Test.name=mymember.getName();
                        Test.department= Strings.getDepartmentName(mymember.getDepartment());

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        DBRmembers = database.getReference("member");

        listdata2.clear();
        DBRmembers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Test.listmembers=listdata2;
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        DBRmembers.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Member member= dataSnapshot.getValue(Member.class);
                listdata2.add(member);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }

    void GetTasks(){

        DBRtasks = database.getReference("task");
        listdata3.clear();
        DBRtasks.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Test.listtasks=listdata3;
//                startActivity(new Intent(SplashActivity.this, MainActivity.class));
//                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        DBRtasks.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Task task= dataSnapshot.getValue(Task.class);
                listdata3.add(task);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }
}