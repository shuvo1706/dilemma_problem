package com.example.user.dilemma_problem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {
    private EditText memail;
    private  EditText mpassword;
    private EditText mname;
    private EditText mgender;
    private  EditText mage;
    private EditText moccupation;
    private Button mbutton;
    private FirebaseAuth mauth;
    private ProgressDialog mprogress;
    private DatabaseReference mdatabase;
    private FirebaseAuth.AuthStateListener mauthlistener;
private  String name;
    private  String age;
    private  String occupation;
    private  String gender;
    private  String email;
    private  String password;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mauth=FirebaseAuth.getInstance();
        mname=(EditText)findViewById(R.id.nameid);
        moccupation=(EditText)findViewById(R.id.occupationid);
        mage=(EditText) findViewById(R.id.ageid);
        mpassword=(EditText)findViewById(R.id.editText6);
        mbutton=(Button)findViewById(R.id.signupid);
        mprogress=new ProgressDialog(this);
        mdatabase=FirebaseDatabase .getInstance().getReference().child("Users");
        mauthlistener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null)
                {
                    startActivity(new Intent(Registration.this,Signup.class));

                }
            }
        };
        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegister();
                //startsignup();
            }


        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        mauth.addAuthStateListener(mauthlistener);
    }

    private void startsignup() {
         String name=mname.getText().toString().trim();
         String age=mage.getText().toString().trim();
         String occupation=moccupation.getText().toString().trim();
         String email=memail.getText().toString().trim();
        String password=mpassword.getText().toString().trim();
        if(TextUtils.isEmpty(name)&&TextUtils.isEmpty(age)&&TextUtils.isEmpty(email)&&TextUtils.isEmpty(occupation)&&TextUtils.isEmpty(password))
        {
            Toast.makeText(Registration.this,"fields are empty",Toast.LENGTH_LONG);
        }
        mauth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(Registration.this,"sign in problem",Toast.LENGTH_LONG);


                }
            }
        });


    }

    private void startRegister() {
        name=mname.getText().toString().trim();

        gender=mgender.getText().toString().trim();
          age=mage.getText().toString().trim();
        occupation=moccupation.getText().toString().trim();
      email=memail.getText().toString().trim();
         password=mpassword.getText().toString().trim();

        if(!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(age)&&!TextUtils.isEmpty(gender)&&!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(occupation)&&!TextUtils.isEmpty(password))
        {
            mprogress.setMessage("signing up");
            mprogress.show();
            mauth.createUserWithEmailAndPassword(email, password)
                  .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                      @Override
                      public void onComplete(@NonNull Task<AuthResult> task) {
                          if (task.isSuccessful())
                          {
                              String user_id=mauth.getCurrentUser().getUid();
                              DatabaseReference current_user_db=mdatabase.child(user_id);
                              current_user_db.child("name").setValue(name);
                              current_user_db.child("age").setValue(age);
                              current_user_db.child("occupation").setValue(occupation);

                              current_user_db.child("Email").setValue(email);
                              mprogress.dismiss();
                              Intent my=new Intent(Registration.this,MainActivity.class);
                              my.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                              startActivity(my);
                          }


                      }
                  });
        }

    }
}
