package com.example.admin.learnenglish;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.zookey.universalpreferences.UniversalPreferences;

public class PremiumActivity extends AppCompatActivity {

    private BootstrapEditText editText;
    private BootstrapButton btnActivate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_premium );

        editText = (BootstrapEditText) findViewById( R.id.etCardNumber);
        btnActivate = (BootstrapButton) findViewById( R.id.btnActivate );

        btnActivate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().length() == 16 ){
                    UniversalPreferences.getInstance().put( "isPremium", true );
                    Toast.makeText( PremiumActivity.this, "Activated Successfully", Toast.LENGTH_SHORT ).show();
                    startActivity( new Intent( PremiumActivity.this, MainActivity.class ) );
                }
            }
        } );

    }
}
