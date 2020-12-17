package com.example.yaatris;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;

import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;
import java.io.IOException;
import java.util.UUID;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener  {

    //defining view objects
    EditText name;
    EditText phone;
    EditText editTextEmail;
    EditText editTextPassword, confirmPassword;
    Button buttonSignup;
    ProgressDialog progressDialog;
    String Name,Email,Password, Phone, RegisterConfirmPassword;
    DatabaseReference mdatabase;
    FirebaseAuth mAuth ;

    // views for button
    private Button btnSelect, btnUpload;

    // view for image view
    private ImageView imageView;

    // Uri indicates, where the image will be picked from
    private Uri filePath;

    // request code
    private final int PICK_IMAGE_REQUEST = 22;

    // instance for firebase storage and StorageReference
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        //initializing views
        name = (EditText)findViewById(R.id.editTextTextPersonName);
        phone = (EditText)findViewById(R.id.editTextPhone);
        editTextEmail = (EditText)findViewById(R.id.editTextTextEmailAddress);
        editTextPassword = (EditText)findViewById(R.id.signupPassword);
        buttonSignup = (Button)findViewById(R.id.buttonSignup);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);

        //attaching listener to button
        buttonSignup.setOnClickListener(this);

        //authentication
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        mdatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        //image upload
        btnSelect = findViewById(R.id.btnChoose);
        btnUpload = findViewById(R.id.btnChooseImage);
        imageView = findViewById(R.id.imgView);

        // get the Firebase  storage reference
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SelectImage();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                uploadImage();
            }
        });
    }



    private void SelectImage()
    {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data)
    {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                imageView.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    private void uploadImage()
    {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            final ProgressDialog progressDialog2
                    = new ProgressDialog(this);
            progressDialog2.setTitle("Uploading...");
            progressDialog2.show();

            // Defining the child of storageReference
            StorageReference ref
                    = storageReference
                    .child(
                            "images/"
                                    + UUID.randomUUID().toString());

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {

                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog2.dismiss();
                                    Toast
                                            .makeText(RegisterUser.this,
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog2.dismiss();
                            Toast
                                    .makeText(RegisterUser.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog2.setMessage(
                                            "Uploaded "
                                                    + (int)progress + "%");
                                }
                            });
        }
    }


    @Override
    public void onClick(View view) {
        //calling register method on click
        registerUser();
    }

    private void registerUser() {

        //getting details from edit texts
        Name = name.getText().toString().trim();
        Phone = phone.getText().toString().trim();
        Email = editTextEmail.getText().toString().trim();
        Password = editTextPassword.getText().toString().trim();
        RegisterConfirmPassword = confirmPassword.getText().toString().trim();


        //checking if email and passwords are empty
        if (TextUtils.isEmpty(Name)) {
            name.setError(getString(R.string.enterName));
            name.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(Phone)) {
            phone.setError("Enter phone number");
            phone.requestFocus();
            return;
        }


        if (Phone.length()<10){
            phone.setError("Phone no must be 10 of characters");
            phone.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(Email)) {
            editTextEmail.setError("Please enter email");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            editTextEmail.setError(getString(R.string.input_error_email_invalid));
            editTextEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(Password)) {
            editTextPassword.setError("Enter password");
            editTextPassword.requestFocus();
            return;
        }

        if (Password.length()<6){
            editTextPassword.setError("Password must be longer than 6 characters");
            editTextPassword.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(RegisterConfirmPassword)) {
            confirmPassword.setError("Enter confirmation password");
            confirmPassword.requestFocus();
            return;
        }

        if (!RegisterConfirmPassword.equals(Password))
        {
            confirmPassword.setError("Passwords do not match");
            confirmPassword.requestFocus();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        //creating a new user
        mAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(Name, Phone, Email, new Date().getTime());
                            Toast.makeText(RegisterUser.this, "User created", Toast.LENGTH_SHORT).show();
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressDialog.dismiss();
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterUser.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                    } else {
                                        //display a failure message
                                        Toast.makeText(RegisterUser.this, "Check the credentials again", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(RegisterUser.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
                            Log.d("taskjagdish" , String.valueOf(task));
                        }
                    }
                });
    }
}



//checking if success
//                        if (task.isSuccessful()) {
//                            //display some message here
//
//                            User user = new User(Name, Phone, Email, new Date().getTime());                           }
//
//                            progressDialog.dismiss();
//                            Toast.makeText(RegisterUser.this, "Successfully registered", Toast.LENGTH_LONG).show();
//                        } else {
//                            //display some message here
//                            Toast.makeText(RegisterUser.this, "Registration Error", Toast.LENGTH_LONG).show();
//                        }