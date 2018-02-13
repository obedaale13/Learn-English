package com.example.admin.learnenglish;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class GrammarActivity extends AppCompatActivity {

    private TextView grTitle, grContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammar);

        grTitle  = (TextView) findViewById(R.id.grammarTitle);
        grContent = (TextView) findViewById(R.id.contents);


        final String section = getIntent().getExtras().getString("section", "g");
        final String level =  getIntent().getExtras().getString("level", "0");
        final int id = getIntent().getExtras().getInt("id", 1);
        String idStr = (id > 9) ? id+ "" : "0" + id;
        readAndStore(section + level + idStr);
    }



    private void readAndStore(String filename){
        String data = "";
        InputStream is = getResources().openRawResource(getResources().getIdentifier(filename, "raw",  getPackageName()));
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuffer buf = new StringBuffer();

        if(is != null){
            try{
                if((data = reader.readLine()) != null)
                    grTitle.setText(data);
                while((data = reader.readLine()) != null){
                    buf.append(data + "\n");
                }
                grContent.setText(buf);
            }catch(Exception e){

            }
        }
    }
}
