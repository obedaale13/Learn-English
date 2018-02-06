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

public class SignUpActivity extends AppCompatActivity {

    Button btnSignUp;
    EditText u, p, p2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnSignUp = (Button) findViewById(R.id.signUp);
        u = (EditText) findViewById(R.id.username);
        p = (EditText) findViewById(R.id.password);
        p2 = (EditText) findViewById(R.id.password2);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = u.getText().toString();
                String pass1 = p.getText().toString();
                String pass2 = p2.getText().toString();

                if(pass1.equals(pass2) && userName.length() > 0 && pass1.length() > 0){
                    SharedPreferences sharedPreferences = getSharedPreferences("LEARN_ENGLISH", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", userName);
                    editor.putString("password", pass1);
                    editor.commit();
                    Intent loginIntent = new Intent(SignUpActivity.this, LoginActivity.class);
                    Toast.makeText(SignUpActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                    startActivity(loginIntent);
                }else if(pass1.length() == 0 || pass2.length() == 0 || userName.length() == 0)
                    Toast.makeText(SignUpActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                else if(!pass1.equals(pass2)){
                    Toast.makeText(SignUpActivity.this, "Password did not match", Toast.LENGTH_SHORT).show();
                    p.setText("");
                    p2.setText("");
                }
            }
        });

    }
}
