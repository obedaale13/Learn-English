package com.example.admin.learnenglish;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Button btnSignIn;
    EditText u, p;
    TextView register;
    SharedPreferences sharedPreferences;
    String uname, passw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        u = (EditText) findViewById(R.id.username);
        p = (EditText) findViewById(R.id.password);
        register = (TextView) findViewById(R.id.register);
        sharedPreferences = getSharedPreferences("LEARN_ENGLISH", MODE_PRIVATE);

        uname = sharedPreferences.getString("username", "");
        passw  =sharedPreferences.getString("password", "");


        if(uname.equals("") || passw.equals(""))
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));

        btnSignIn = (Button) findViewById(R.id.signIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String u1 = u.getText().toString();
                String p1 = p.getText().toString();
                if(u1.length() > 0 && p1.length() > 0){

                    SharedPreferences.Editor editor = sharedPreferences.edit();


                    if(u1.equals(uname) && p1.equals(passw)){
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }else{
                        Toast.makeText(getApplicationContext(), "Incorrect username or password", Toast.LENGTH_SHORT).show();
                        p.setText("");
                        p.setHint("password");
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_LONG).show();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }
}
