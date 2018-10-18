package com.sheild.abhinavchinta.csed;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abhinavchinta.csed.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.sheild.abhinavchinta.csed.models.Member;
import com.sheild.abhinavchinta.csed.models.Strings;
import com.sheild.abhinavchinta.csed.models.Test;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    FirebaseUser firebaseUser;
    private DatabaseReference mRestaurantReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;
    private RecyclerView recyclerView;
    private HomeFragment.MyAdapter adapter;
    private List<Member> listdataa= new ArrayList<>();
    private FirebaseDatabase database;
    private DatabaseReference DBRmembers;
    private SwipeRefreshLayout swipeRefreshLayout;
    EditText sbar;

    RecyclerView rview;



    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview =  inflater.inflate(R.layout.fragment_home, container, false);
        database = FirebaseDatabase.getInstance();
        adapter = new HomeFragment.MyAdapter(listdataa);
        recyclerView= (RecyclerView)rootview.findViewById(R.id.recyclerview2);
        recyclerView.setAdapter(adapter);
        TextView txvname = (TextView)rootview.findViewById(R.id.name);
        TextView department = (TextView)rootview.findViewById(R.id.department);
        sbar=rootview.findViewById(R.id.sbar);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        rview=rootview.findViewById(R.id.rview);

        txvname.setText(Test.getName());
        department.setText(Test.getDepartment());

        swipeRefreshLayout = (SwipeRefreshLayout)rootview.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Test.listmembers.clear();
                DBRmembers = database.getReference("member");
                DBRmembers.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Member member= dataSnapshot.getValue(Member.class);
                        Test.listmembers.add(member);
                        adapter = new HomeFragment.MyAdapter(listdataa);
                        recyclerView.setAdapter(adapter);
                        swipeRefreshLayout.setRefreshing(false);
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
        });
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager LM = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(LM);
        sbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty())
                {
                    setAdapter(editable.toString());
                }

            }
        });
        return rootview;
    }

    private void setAdapter(final String searchedstring) {
        DBRmembers.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    String uid=snapshot.getKey();
                    String full_name = snapshot.child("full_name").getValue(String.class);
                    String department = snapshot.child("department").getValue(String.class);
                    if(full_name.contains(searchedstring)){

                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public class MyAdapter extends RecyclerView.Adapter<HomeFragment.MyAdapter.MyViewHolder>{

        private List<Member> listarray = new ArrayList<>();
        private List<Member> listarrayfull;


        public MyAdapter(List<Member> list){
            this.listarray= Test.getListmembers();
        }

        @Override
        public HomeFragment.MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member,parent,false);
            return new HomeFragment.MyAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(HomeFragment.MyAdapter.MyViewHolder holder, int position) {
            final Member member = listarray.get(position);
            holder.textViewdate.setText(Strings.getDepartmentName(member.getDepartment()));
            holder.textViewname.setText(member.getName());
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(getContext(),""+member.getPhoneNumber(),Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+member.getPhoneNumber())));
                }
            });
            //holder.textViewname.setText(member.getName());
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{

            TextView textViewmessage;
            TextView textViewname;
            TextView textViewdate;
            LinearLayout linearLayout;
            public MyViewHolder(View itemView) {
                super(itemView);
                //textViewmessage = (TextView)itemView.findViewById(R.id.message_content);
                textViewname = (TextView)itemView.findViewById(R.id.member_name);
                textViewdate = (TextView)itemView.findViewById(R.id.member_dept);
                linearLayout = (LinearLayout)itemView.findViewById(R.id.linearlayout1);
                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return listarray.size();
        }
    }

}
