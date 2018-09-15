package com.sheild.abhinavchinta.csed;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.abhinavchinta.csed.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sheild.abhinavchinta.csed.models.Message;
import com.sheild.abhinavchinta.csed.models.Strings;
import com.sheild.abhinavchinta.csed.models.Test;
import com.sheild.abhinavchinta.csed.models.Upload;

import java.util.ArrayList;
import java.util.List;

public class ViewUploadsActivity extends AppCompatActivity {
    //the listview
    ListView listView;

    //database reference to get uploads data
    DatabaseReference mDatabaseReference;

    //list to store uploads data
    List<Upload> uploadList;
    private RecyclerView recyclerView;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_uploads);

        uploadList = new ArrayList<>();
        //recyclerView = (RecyclerView) findViewById(R.id.listVi


        //getting the database reference
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Strings.DATABASE_PATH_UPLOADS);

        //retrieving upload data from firebase database
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    uploadList.add(upload);
                }

                String[] uploads = new String[uploadList.size()];

                for (int i = 0; i < uploads.length; i++) {
                    uploads[i] = uploadList.get(i).getName();
                }

                //displaying it to list
                //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, uploads);
                //View header = (View)getLayoutInflater().inflate(R.layout.listview_header_row, null);
                //listView.addHeaderView(header);
                //MyAdapter adapter = new MyAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, uploadList);
                //listView1.setAdapter(adapter);
                //listView.setAdapter(adapter);


                adapter = new MyAdapter(uploadList);
                recyclerView= (RecyclerView)findViewById(R.id.listViewr);
                recyclerView.setAdapter(adapter);
                RecyclerView.LayoutManager LM = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(LM);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public class MyAdapter extends RecyclerView.Adapter<ViewUploadsActivity.MyAdapter.MyViewHolder>{

        private List<Upload> listarray = new ArrayList<>();
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemPosition = recyclerView.getChildLayoutPosition(view);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Upload upload = uploadList.get(itemPosition);
                intent.setData(Uri.parse(upload.getUrl()));
                startActivity(intent);
            }
        };


        public MyAdapter(List<Upload> list){
            this.listarray= list;
        }

        @Override
        public ViewUploadsActivity.MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message,parent,false);
            view.setOnClickListener(mOnClickListener);
            return new MyAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            Upload upload = listarray.get(position);
            holder.textViewdate.setText(upload.getName());
            holder.textViewmessage.setText(upload.getName());
            holder.textViewname.setText(upload.getName());
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