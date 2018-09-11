package com.sheild.abhinavchinta.csed;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.abhinavchinta.csed.R;
import com.sheild.abhinavchinta.csed.models.Member;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RegistrationActivity extends AppCompatActivity {

    private EditText etname, etemail, etpassword, etpasswordconfirm, etphoneno, etregno, etroomno;
    ProgressBar spinnerr;
    private FirebaseAuth auth;
    private Button btnSignIn;
    private FirebaseDatabase db;
    private DatabaseReference dbr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);



        auth = FirebaseAuth.getInstance();

        etname = (EditText) findViewById(R.id.nameip);
        etemail = (EditText) findViewById(R.id.email);
        etpassword = (EditText) findViewById(R.id.password1);
        etpasswordconfirm = (EditText) findViewById(R.id.password2);
        etphoneno = (EditText) findViewById(R.id.phoneno);
        etregno = (EditText) findViewById(R.id.regno);
        etroomno = (EditText) findViewById(R.id.roomno);
        spinnerr = (ProgressBar) findViewById(R.id.overlay);
        spinnerr.setVisibility(View.INVISIBLE);


        btnSignIn = (Button)findViewById(R.id.signinbutton);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = etemail.getText().toString().trim();
                final String password = etpassword.getText().toString().trim();
                final String name = etname.getText().toString().trim();
                final long phno;// = Long.parseLong(etphoneno.getText().toString());
                final String regno = etregno.getText().toString().trim();
                final String roomno = etroomno.getText().toString().trim();


                if("".equals(etname.getText().toString())) {
                    etname.setError("Field cannot be empty");
                    etname.requestFocus();
                    return;
                }
                if("".equals(etemail.getText().toString())) {
                    etemail.setError("Field cannot be empty");
                    etemail.requestFocus();
                    return;
                }
                if("".equals(etpassword.getText().toString())) {
                    etpassword.setError("Field cannot be empty");
                    etpassword.requestFocus();
                    return;
                }
                if("".equals(etphoneno.getText().toString())) {
                    etphoneno.setError("Field cannot be empty");
                    etphoneno.requestFocus();
                    return;
                }
                phno = Long.parseLong(etphoneno.getText().toString());

                if("".equals(etregno.getText().toString())) {
                    etregno.setError("Field cannot be empty");
                    etregno.requestFocus();
                    return;
                }
                if("".equals(etroomno.getText().toString())) {
                    etroomno.setError("Field cannot be empty");
                    etroomno.requestFocus();
                    return;
                }
                if(etphoneno.getText().toString().length()!=10) {
                    etphoneno.setError("Please enter a valid mobile number");
                    etphoneno.requestFocus();
                    return;
                }
                if (!etpassword.getText().toString().equals(etpasswordconfirm.getText().toString())){
                    etpasswordconfirm.setError("Passwords do not match");
                    etpasswordconfirm.requestFocus();
                    return;
                }



                String passwordconfirn = etpasswordconfirm.getText().toString().trim();
                //if (password.equals(password)){

                    final CharSequence[] items = {" Editorial and Blog "," Events, UR and Strategies "," Expansion "," General Secretary" ," Human Resources "," Marketing "," Public Relations "," Startups "," Technical and Design"};
                    final ArrayList seletedItems=new ArrayList();

                    AlertDialog dialog = new AlertDialog.Builder(RegistrationActivity.this)
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
                                    btnSignIn.setVisibility(View.INVISIBLE);
                                    spinnerr.setVisibility(View.VISIBLE);


                                    auth.createUserWithEmailAndPassword(email, password)
                                            .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    Toast.makeText(RegistrationActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                                    //progressBar.setVisibility(View.GONE);
                                                    // If sign in fails, display a message to the user. If sign in succeeds
                                                    // the auth state listener will be notified and logic to handle the
                                                    // signed in user can be handled in the listener.
                                                    if (!task.isSuccessful()) {
                                                        //Toast.makeText(RegistrationActivity.this, "Authentication failed." + task.getException(),
                                                                //Toast.LENGTH_SHORT).show();
                                                        try {
                                                            throw task.getException();
                                                        } catch(FirebaseAuthWeakPasswordException e) {
                                                            etpassword.setError("Password must have at least 6 digits");
                                                            etpassword.requestFocus();

                                                        } catch(FirebaseAuthInvalidCredentialsException e) {
                                                            etemail.setError("Invalid email");
                                                            etemail.requestFocus();

                                                        } catch(FirebaseAuthUserCollisionException e) {
                                                            etemail.setError("Email already exists");
                                                            etemail.requestFocus();
                                                        } catch(Exception e) {

                                                            //Log.e(TAG, e.getMessage());
                                                        }
                                                        btnSignIn.setVisibility(View.VISIBLE);
                                                        spinnerr.setVisibility(View.INVISIBLE);



                                                    } else {
                                                        //auth.signOut();
                                                        //startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                                                        //Toast.makeText(RegistrationActivity.this, "Authentication success." + task.getException(),Toast.LENGTH_LONG).show();

                                                        Member member = new Member(1,"#"+seletedItems.get(0).toString(),"Core committee member",email,0,name,phno,regno);
                                                        db= FirebaseDatabase.getInstance();
                                                        dbr = db.getReference().child("member").child(auth.getUid());
                                                        dbr.setValue(member);

                                                        /*DatabaseReference Db1=FirebaseDatabase.getInstance().getReference();
                                                        DatabaseReference Users = Db1.child("Users").child(auth.getCurrentUser().getUid());
                                                        Users.setValue(member);
                                                        auth.signOut();*/


                                                        finish();
                                                    }
                                                }
                                            });
                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            }).create();
                    dialog.show();
                }



        });
    }
}
