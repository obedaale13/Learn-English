package com.example.admin.learnenglish;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LearnEnglish extends AppCompatActivity {

    Button btnSU, btnSI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_english);

        btnSU = (Button)findViewById(R.id.btnSignUp);
        btnSI = (Button)findViewById(R.id.btnSignIn);

        btnSI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LearnEnglish.this, LoginActivity.class));
            }
        });

        btnSU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LearnEnglish.this, SignUpActivity.class));
            }
        });
    }
}
