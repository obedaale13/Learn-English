package com.example.admin.learnenglish;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zookey.universalpreferences.UniversalPreferences;

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
                boolean isPremium = UniversalPreferences.getInstance().get( "isPremium", false );
                if(isPremium){
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

    @Override
    public void onBackPressed() {
        finish();
    }
}
