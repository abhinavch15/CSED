package com.sheild.abhinavchinta.csed;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sheild.abhinavchinta.csed.ViewUploadsActivity;
import com.sheild.abhinavchinta.csed.models.Strings;
import com.sheild.abhinavchinta.csed.models.Test;
import com.sheild.abhinavchinta.csed.models.Upload;



        import android.Manifest;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.net.Uri;
        import android.os.Build;
        import android.os.Bundle;
        import android.provider.Settings;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.support.v4.content.ContextCompat;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ProgressBar;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.abhinavchinta.csed.R;
        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.storage.FirebaseStorage;
        import com.google.firebase.storage.OnProgressListener;
        import com.google.firebase.storage.StorageReference;
        import com.google.firebase.storage.UploadTask;
        import com.sheild.abhinavchinta.csed.models.Strings;
        import com.sheild.abhinavchinta.csed.models.Upload;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
        import static android.content.ContentValues.TAG;


public class BlankFragment extends Fragment {

    private TextView textView;
    TextView textViewStatus;
    EditText editTextFilename;
    ProgressBar progressBar;
    final static int PICK_PDF_CODE = 2342;
    private static final int PICK_IMAGE_REQUEST = 234;
    static int type;

    //the firebase objects for storage and database
    StorageReference mStorageReference;
    DatabaseReference mDatabaseReference;
    public BlankFragment() {
        // Required empty public constructor

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        textView = (TextView)getView().findViewById(R.id.text);
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview =  inflater.inflate(R.layout.upload_layout, container, false);
        // Inflate the layout for this fragment

        //getting firebase objects
        mStorageReference = FirebaseStorage.getInstance().getReference();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Strings.DATABASE_PATH_UPLOADS);

        //getting the views
        textViewStatus = (TextView)rootview.findViewById(R.id.textViewStatus);
        editTextFilename = (EditText)rootview.findViewById(R.id.editTextFileName);
        progressBar = (ProgressBar)rootview.findViewById(R.id.progressbar);

        //attaching listeners to views
        Button button = (Button) rootview.findViewById(R.id.buttonUploadFile);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextFilename.getText().length()==0){Toast.makeText(getContext(),"file name cannot be blank",Toast.LENGTH_SHORT).show();  }
                else {
                    Log.i(TAG, "onClick: hi");
                    type = 0;
                    getPDF(0);
                }
            }
        });
        Button buttonn = (Button) rootview.findViewById(R.id.buttonUploadFileimg);
        buttonn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextFilename.getText().length()==0){Toast.makeText(getContext(),"file name cannot be blank",Toast.LENGTH_SHORT).show(); }
                else {
                    Log.i(TAG, "onClick: hi");
                    type=1;
                    getPDF(1);
                }
            }
        });

        TextView button1 = (TextView)rootview.findViewById(R.id.textViewUploads);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ViewUploadsActivity.class));
            }
        });
        return rootview;
    }

    private void getIMG() {

    }

    private void getPDF(int type) {
        //for greater than lolipop versions we need the permissions asked on runtime
        //so if the permission is not available user will go to the screen to allow storage permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getContext().getPackageName()));
            startActivity(intent);
            return;
        }

        //creating an intent for file chooser
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        if (type==0){
            intent.setType("application/pdf");
            startActivityForResult(Intent.createChooser(intent, "Select PDF"), PICK_PDF_CODE);
        }
        else {
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //when the user choses the file
        if ((requestCode == PICK_PDF_CODE||requestCode == PICK_IMAGE_REQUEST) && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //if a file is selected
            if (data.getData() != null) {
                //uploading the file
                uploadFile(data.getData());
            }else{
                //Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show();
            }
        }
    }


    //this method is uploading the file
    //the code is same as the previous tutorial
    //so we are not explaining it
    private void uploadFile(Uri data) {
        progressBar.setVisibility(View.VISIBLE);
        StorageReference sRef = mStorageReference.child(Strings.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + ".pdf");
        sRef.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressBar.setVisibility(View.GONE);
                        textViewStatus.setText("File Uploaded Successfully");

                        Upload upload = new Upload(editTextFilename.getText().toString(), Test.getName(),new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date()),taskSnapshot.getDownloadUrl().toString(),type);
                        mDatabaseReference.child(mDatabaseReference.push().getKey()).setValue(upload);
                        editTextFilename.setText("");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        textViewStatus.setText((int) progress + "% Uploading...");
                    }
                });
    }
}
