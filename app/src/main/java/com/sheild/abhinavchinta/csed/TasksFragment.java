package com.sheild.abhinavchinta.csed;


import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.sheild.abhinavchinta.csed.models.Member;
import com.sheild.abhinavchinta.csed.models.Task;
import com.sheild.abhinavchinta.csed.models.Test;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;


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
    private Button button;
    private FirebaseDatabase db;
    private DatabaseReference dbr;

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

        button = (Button)rootview.findViewById(R.id.taskbutton);
        if (Test.IsAdmin==0){button.setVisibility(View.GONE);}
        final CharSequence[] items = {" Editorial and Blog "," Events, UR and Strategies "," Expansion "," General Secretary" ," Human Resources "," Marketing "," Public Relations "," Startups "," Technical and Design"};
        final List<Integer> seletedItems=new ArrayList<Integer>();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = new AlertDialog.Builder(getContext())
                        .setTitle("Please select the departments you work for")
                        .setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
                                if (isChecked) {
                                    // If the user checked the item, add it to the selected items
                                    seletedItems.add(indexSelected);
                                } else if (seletedItems.contains(indexSelected)) {
                                    // Else, if the item is already in the array, remove it
                                    seletedItems.remove(Integer.valueOf(indexSelected));
                                }
                            }
                        }).setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                //  Your code when user clicked on OK
                                //  You can write the code  to save the selected item here
                                for (int a :seletedItems) {
                                    Log.i(TAG, "values are "+a );
                                }

                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                                alertDialog.setMessage("Enter Task");

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
                                                for (int a :seletedItems) {
                                                    Task newtask = new Task();
                                                    newtask.setDepartmentCode(String.valueOf(a));
                                                    newtask.setMessage(message);
                                                    newtask.setDate("DATE");

                                                    db= FirebaseDatabase.getInstance();
                                                    dbr = db.getReference().child("task");
                                                    String id  = dbr.push().getKey();
                                                    dbr.child(id).setValue(newtask);
                                                }
                                                seletedItems.clear();
                                            }
                                        });

                                alertDialog.setNegativeButton("CANCEL",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                                seletedItems.clear();
                                            }
                                        });

                                alertDialog.show();
                            }

                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                seletedItems.clear();
                            }
                        }).create();
                dialog.show();
            }
        });

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
