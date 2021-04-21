package com.example.user.dilemma_problem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button login;
    private Button signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login= (Button)findViewById(R.id.loginid);
        signup= (Button)findViewById(R.id.signupid);
        signup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent register=new Intent(MainActivity.this,Registration.class);
                startActivity(register);
            }
        });


    }
}
