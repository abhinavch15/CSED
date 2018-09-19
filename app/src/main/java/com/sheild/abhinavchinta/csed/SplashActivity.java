package com.sheild.abhinavchinta.csed;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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

import static android.content.ContentValues.TAG;

public class SplashActivity extends AppCompatActivity {

    public interface CallBack {                   //declare an interface with the callback methods, so you can use on more than one class and just refer to the interface
        void methodToCallBack();
    }

    public static CallBack callback;

    private FirebaseAuth auth;
    public static List<Message> listdata1= new ArrayList<>();
    public static List<Member> listdata2= new ArrayList<>();
    public static List<Task> listdata3= new ArrayList<>();
    private static FirebaseDatabase database;
    private static DatabaseReference DBRmessages;
    private DatabaseReference DBRmembers;
    private DatabaseReference DBRtasks;
    private int count;
    public static int day;
    public static int month;
    public static int year;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = (c.get(Calendar.MONTH)+1);
        day = c.get(Calendar.DAY_OF_MONTH);

        database = FirebaseDatabase.getInstance();

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            count=0;
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
                    count++;
                    if (count==3) {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    }
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
                    Log.i("TAG", "onChildAdded: here1 ");
                    if (callback!=null)
                    {
                        Log.i("TAG", "onChildAdded: here2 ");
                        callback.methodToCallBack();
                    }

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
                        try {
                            Test.department2 = Strings.getDepartmentName(mymember.getDepartment2());
                            Test.department3 = Strings.getDepartmentName(mymember.getDepartment3());
                            Test.departmentcode2 = mymember.getDepartment2();
                            Test.departmentcode3 = mymember.getDepartment3();
                        }
                        catch (Exception a){}

                        Test.departmentcode = mymember.getDepartment();
                        Test.IsAdmin = mymember.getIsAdmin();

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
                count++;
                if (count==3) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
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
                count++;
                if (count==3) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        DBRtasks.addChildEventListener(new ChildEventListener() {
            public static final String TAG = "";

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Task task= dataSnapshot.getValue(Task.class);
                Log.i(TAG, "comparingggg "+task.getDepartmentCode()+"and"+Test.getDepartmentcode());
                if (((task.getDepartmentCode().equals(Test.getDepartmentcode()))||(task.getDepartmentCode().equals(Test.getDepartmentcode2()))||(task.getDepartmentCode().equals(Test.getDepartmentcode3())))&&((task.getDay()>=day&&task.getMonth()==month&&task.getYear()==year)||(task.getMonth()>month&&task.getYear()==year)||(task.getYear()>year))) {
                    listdata3.add(task);
                    Log.i(TAG, "Current: "+day+"/"+month+"/"+year);
                    Log.i(TAG, "From database: "+task.getDay()+"/"+task.getMonth()+"/"+task.getYear());
                }
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
