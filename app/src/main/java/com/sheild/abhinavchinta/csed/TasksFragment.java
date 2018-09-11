package com.sheild.abhinavchinta.csed;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.abhinavchinta.csed.R;
import com.sheild.abhinavchinta.csed.models.Task;
import com.sheild.abhinavchinta.csed.models.Test;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TasksFragment extends Fragment {

    private DatabaseReference mRestaurantReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;
    private RecyclerView recyclerView;
    private TasksFragment.MyAdapter adapter;
    private List<Task> listdataa= new ArrayList<>();
    private FirebaseDatabase database;
    private DatabaseReference DBR;


    public TasksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview =  inflater.inflate(R.layout.fragment_tasks, container, false);
        database = FirebaseDatabase.getInstance();
        adapter = new TasksFragment.MyAdapter(listdataa);
        recyclerView= (RecyclerView)rootview.findViewById(R.id.recyclerview3);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager LM = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(LM);

        return rootview;
    }

    public class MyAdapter extends RecyclerView.Adapter<TasksFragment.MyAdapter.MyViewHolder>{

        private List<Task> listarray = new ArrayList<>();

        public MyAdapter(List<Task> list){
            this.listarray= Test.getListtasks();
        }

        @Override
        public TasksFragment.MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task,parent,false);
            return new TasksFragment.MyAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(TasksFragment.MyAdapter.MyViewHolder holder, int position) {
            Task task = listarray.get(position);
            holder.textViewdate.setText(task.getDate());
            holder.textViewname.setText(task.getName());
            holder.textViewmessage.setText(task.getMessage());
            holder.textViewdeadline.setText(task.getDeadline());
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{

            TextView textViewmessage;
            TextView textViewname;
            TextView textViewdate;
            TextView textViewdeadline;
            LinearLayout linearLayout;

            public MyViewHolder(View itemView) {
                super(itemView);
                textViewmessage = (TextView)itemView.findViewById(R.id.task_message);
                textViewname = (TextView)itemView.findViewById(R.id.task_name);
                textViewdate = (TextView)itemView.findViewById(R.id.task_date);
                textViewdeadline = (TextView)itemView.findViewById(R.id.task_deadline);
                linearLayout = (LinearLayout)itemView.findViewById(R.id.linearlayout);
                final View view1 = (View)itemView.findViewById(R.id.status);
                view1.setTag(0);
//                linearLayout.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if (view1.getTag().equals(0)){
//                        view1.setBackgroundColor(Color.parseColor("#20B41D"));
//                        view1.setTag(1);}
//                        else {
//                            view1.setBackgroundColor(Color.parseColor("#B83232"));
//                            view1.setTag(0);
//                        }
//                    }
//                });
            }


        }

        @Override
        public int getItemCount() {
            return listarray.size();
        }
    }

}
