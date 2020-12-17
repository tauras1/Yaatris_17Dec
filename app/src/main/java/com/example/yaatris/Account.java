package com.example.yaatris;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import static com.example.yaatris.MainActivity.emailId;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Account#newInstance} factory method to
 * create an instance of this fragment.
 */

public class Account extends Fragment implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private EditText Email;
    private EditText Password;
    private Button LogInButton;
    private Button Settings;
    private Button a;
    private Button login;
    private ProgressBar pgsBar;
    private TextView e;
    private TextView p;
    private Button logout;



    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
    FirebaseUser mUser;
    String email, password;
    ProgressDialog dialog;
    public static final String userEmail="";
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    public Account() {
        // Required empty public constructor
    }

    public static Account newInstance() {
        Account fragment = new Account();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_account, container, false);
        pgsBar = (ProgressBar) v.findViewById(R.id.pBar);
        pgsBar.setVisibility(v.VISIBLE);
        logout = (Button) v.findViewById(R.id.btnLogout);
        logout.setVisibility(v.GONE);

        p = (TextView) v.findViewById(R.id.loginPassword);
        e = (TextView) v.findViewById(R.id.loginEmail);
        firebaseAuth = FirebaseAuth.getInstance();
        //initializing views
        Email = (EditText) v.findViewById(R.id.loginEmail);
        Password = (EditText) v.findViewById(R.id.loginPassword);
        LogInButton= (Button) v.findViewById(R.id.buttonLogin);


        a = (Button) v.findViewById(R.id.buttonAddAdventure);
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), AddAdventure.class);
                i.putExtra( "companyEmail", mAuth.getCurrentUser().getEmail());
                startActivity(i);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                e.setVisibility(View.VISIBLE);
                p.setVisibility(View.VISIBLE);
                login.setVisibility(View.VISIBLE);
                a.setVisibility(View.GONE);
                logout.setVisibility(View.GONE);
            }
        });


        login = (Button) v.findViewById(R.id.buttonLogin);
        login.setVisibility(View.GONE);
        e.setVisibility(View.GONE);
        p.setVisibility(View.GONE);
        a.setVisibility(View.GONE);

        dialog = new ProgressDialog(getActivity());

        mAuth = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
         confirmCompany();

        //attaching listener to button
//        LogInButton.setOnClickListener((View.OnClickListener) this);

        LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Calling EditText is empty or no method.
                userSign();
            }
        });


        ImageButton userReg = (ImageButton) v.findViewById(R.id.userImgRegBtn);
        userReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), RegisterUser.class);
                startActivity(in);
            }
        });

        ImageButton companyReg = (ImageButton) v.findViewById(R.id.compRegImgBtn);
        companyReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in2 = new Intent(getActivity(), RegisterCompany.class);
                startActivity(in2);
            }
        });

        ImageButton volReg = (ImageButton) v.findViewById(R.id.volRegImgBtn);
        volReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in3 = new Intent(getActivity(), RegisterVolunteer.class);
                startActivity(in3);
            }
        });
        return v;
    }

        public void confirmCompany(){
        final String loggedEmail;
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!= null) {
            loggedEmail = currentUser.getEmail();
            Query query = mDatabase.child("Companies").orderByChild("email").equalTo(loggedEmail);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getChildrenCount()>0) {
                        a.setVisibility(View.VISIBLE);
                        login.setVisibility(View.GONE);
                        e.setVisibility(View.GONE);
                        p.setVisibility(View.GONE);
                        logout.setVisibility(View.VISIBLE);
                    }else{
                        String lemail = loggedEmail;
                        Query query = mDatabase.child("Volunteers").orderByChild("Email").equalTo(lemail);
                        query.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.getChildrenCount()>0) {
                                    a.setVisibility(View.VISIBLE);
                                    login.setVisibility(View.GONE);
                                    e.setVisibility(View.GONE);
                                    p.setVisibility(View.GONE);
                                    logout.setVisibility(View.VISIBLE);

                                }else{

                                    logout.setVisibility(View.VISIBLE);
                                }
                                pgsBar.setVisibility(View.GONE);
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(getActivity(), "Database error", Toast.LENGTH_SHORT).show();
                                pgsBar.setVisibility(View.GONE);
                            }
                        });

                    }
                    pgsBar.setVisibility(View.GONE);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getActivity(), "Database error", Toast.LENGTH_SHORT).show();
                    pgsBar.setVisibility(View.GONE);
                }
            });
        }
        else{
            Toast.makeText(getActivity(), "Not logged in", Toast.LENGTH_SHORT).show();
            a.setVisibility(View.GONE);
            login.setVisibility(View.VISIBLE);
            e.setVisibility(View.VISIBLE);
            p.setVisibility(View.VISIBLE);
            pgsBar.setVisibility(View.GONE);
        }
    }

    private void userSign() {
        email = Email.getText().toString().trim();
        password = Password.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getActivity(), "Enter the correct Email", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(getActivity(), "Enter the correct password", Toast.LENGTH_SHORT).show();
            return;
        }
        dialog.setMessage("Logging in please wait...");
        dialog.setIndeterminate(true);
        dialog.show();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    dialog.dismiss();
                    Toast.makeText(getActivity(), "Login failed", Toast.LENGTH_SHORT).show();

                } else {
                    dialog.dismiss();
                    Toast.makeText(getActivity(), "Logged in", Toast.LENGTH_SHORT).show();
                    confirmCompany();
                    System.out.println("333333333333333333333333333333333333333333333333333333333333" +
                            "@#@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" +
                            "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"
                            + mAuth.getCurrentUser().getEmail()  + "asfjkjlsadj " +
                            "333333333333333333333333333333333333333333333333333333333333" +
                            "@#@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" +
                            "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
                    emailId = mAuth.getCurrentUser().getEmail();
                }
            }
        });

    }



    @Override
    public void onClick(View view) {
        //calling register method on click
    }

}