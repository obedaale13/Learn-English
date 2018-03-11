package com.example.admin.learnenglish;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.firebase.auth.FirebaseAuth;
import com.zookey.universalpreferences.UniversalPreferences;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private Button btnGrammar, btnVocabulary, btnPractise, btnAudioBooks, btnPremium, btnEssay;
    private static final String[] menu = {"A1 - A2", "B1 - B2", "C1 - C2"};
    private static final String dialogMsg = "Please choose level";
    private static final String[] section = {"g", "v", "p"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGrammar = (Button) findViewById(R.id.btnGrammar);
        btnVocabulary = (Button) findViewById(R.id.btnVocabulary);
        btnPractise = (Button) findViewById(R.id.btnPractise);
        btnAudioBooks = (Button) findViewById(R.id.btnAudioBooks);
        btnPremium = (Button) findViewById(R.id.btnPremium);
        btnEssay = (Button) findViewById( R.id.btnEssay );
        context = getApplicationContext();

        btnGrammar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog(dialogMsg, menu, section[0]);
            }
        });

        btnVocabulary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog(dialogMsg, menu, section[1]);
            }
        });

        btnPractise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog(dialogMsg, menu, section[2]);
            }
        });

        btnAudioBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Topics.class);
                i.putExtra("section", "a");
                startActivity(i);
            }
        });

        btnEssay.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isActivated = UniversalPreferences.getInstance().get( "isActivated", false );
                if(isActivated){
                    startActivity( new Intent( MainActivity.this, EssayActivity.class ) );
                }else{
                    Toast.makeText( context, "Activate your account please", Toast.LENGTH_SHORT ).show();
                }
            }
        } );

        btnPremium.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent( MainActivity.this, PremiumActivity.class ) );
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Sign Out")
                        .setMessage("Are you sure you want to sign out from Google account?\n" +
                                "Note: all data of the application will be deleted and you can't undo this action.")
                        .setIcon( R.drawable.warning )
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth.getInstance().signOut();
                                deleteAppData();
                                UniversalPreferences.getInstance().clear();
                            }
                        }).setNegativeButton("No", null).show();

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createDialog(String title, String menu[], final String section) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(title)
                .setItems(menu, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent;
                        if(section.equals("p")){
                            intent = new Intent(context, PractiseActivity.class);
                        }else{
                            intent = new Intent(context, Topics.class);
                            intent.putExtra("section", section);
                        }
                        intent.putExtra("level", "" + which);
                        startActivity(intent);
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteAppData() {
        try {
            // clearing app data
            String packageName = getApplicationContext().getPackageName();
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("pm clear "+packageName);

        } catch (Exception e) {
            e.printStackTrace();
        } }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setIcon( R.drawable.exit )
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("No", null).show();
    }
}
