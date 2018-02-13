package com.example.admin.learnenglish;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PractiseActivity extends AppCompatActivity {

    private static String[][] q = {{"","","","",""},{"","","","",""},{"","","","",""},{"","","","",""},{"","","","",""},{"","","","",""},{"","","","",""},{"","","","",""},{"","","","",""},{"","","","",""}};
    private char[] answers = {'D', 'A', 'D', 'C', 'D', 'A', 'B', 'C', 'B', 'D', 'A', 'B', 'B', 'C', 'B', 'D', 'D', 'B', 'A', 'B', 'D', 'C', 'B', 'D', 'C', 'C', 'A', 'B', 'A', 'D', 'C', 'C', 'B', 'C', 'A', 'D', 'D', 'C', 'C', 'B', 'D', 'A', 'A', 'D', 'C', 'C', 'C', 'B', 'A', 'B', 'B', 'D', 'B', 'B', 'D', 'A', 'D', 'C', 'B', 'A', 'C', 'A', 'A', 'B', 'A', 'B', 'C', 'B', 'A', 'C', 'A', 'B', 'D', 'B', 'D', 'A', 'C', 'B', 'D', 'B', 'C', 'A', 'B', 'C', 'D', 'D', 'A', 'A', 'C', 'B', 'C', 'B', 'A', 'A', 'D', 'C', 'A', 'B', 'B', 'B', 'D', 'D', 'A', 'C', 'D', 'C', 'C', 'B', 'D', 'D', 'C', 'C', 'A', 'C', 'C', 'D', 'D', 'D', 'D', 'A', 'C', 'D', 'D', 'A', 'C', 'D', 'B', 'C', 'D', 'C', 'A', 'D', 'D', 'C', 'B', 'D', 'D', 'D', 'B', 'C', 'C', 'B', 'B', 'B', 'A', 'A', 'B', 'C', 'B', 'B', 'B', 'D', 'B', 'C', 'D', 'B', 'D', 'B', 'D', 'B', 'D', 'C', 'A', 'A', 'B', 'D', 'A', 'D', 'C', 'C', 'C', 'C', 'B', 'C', 'B', 'A', 'D', 'C', 'A', 'A', 'A', 'D', 'B', 'B', 'D', 'B', 'B', 'C', 'B', 'A', 'C', 'C', 'D', 'C', 'B', 'B', 'A', 'D', 'A', 'B'};
    private TextView qTxt;
    private RadioButton radA, radB, radC, radD;
    private RadioGroup radioGroup;
    private Button btnNext;
    private int count;
    private int corrects;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practise);

        qTxt = (TextView) findViewById(R.id.questionTxt);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radA = (RadioButton) findViewById(R.id.keyA);
        radB = (RadioButton) findViewById(R.id.keyB);
        radC = (RadioButton) findViewById(R.id.keyC);
        radD = (RadioButton) findViewById(R.id.keyD);
        btnNext = (Button) findViewById(R.id.btnNext);
        int level = Integer.parseInt(getIntent().getExtras().getString("level", "0"));
        count = 0;
        corrects = 0;

        final List<Integer> list = new ArrayList<Integer>();
        for(int i = 0; i < 67; i++)
            list.add(i);
        Collections.shuffle(list);
        final int[] chosen = new int[10];
        for(int i = 0; i < chosen.length; i++) {
            chosen[i] = 67 * level + list.get(i);
            Log.e("CHOSEN " , chosen[i]+"");
        }
        readQuestions(chosen, "practise");

        fillQuestion(0);


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            int ids = radioGroup.getCheckedRadioButtonId();
            char ans = ' ';
            switch (ids){
                case R.id.keyA: ans = radA.getText().toString().charAt(0); break;
                case R.id.keyB: ans = radB.getText().toString().charAt(0); break;
                case R.id.keyC: ans = radC.getText().toString().charAt(0); break;
                case R.id.keyD: ans = radD.getText().toString().charAt(0); break;
                default: break;
            }
            if(ans == answers[chosen[count]]){
                corrects++;
                Toast.makeText(PractiseActivity.this, "CORRECT", Toast.LENGTH_SHORT).show();
            }
            Log.i("STATUS : ", ans + " ? " + q[count][1].charAt(0));
            count++;
            if(count < 10){
                fillQuestion(count);
            }
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(PractiseActivity.this);
                builder.setTitle("Quiz Results")
                        .setMessage("Your result : " + corrects + "/" + count)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                count = 0;
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }
                        })
                        .setCancelable(false);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            }
        });
    }


    private void fillQuestion(int i){
        qTxt.setText((i+1) + ". " + q[i][0]);
        radA.setText(q[i][1]);
        radB.setText(q[i][2]);
        radC.setText(q[i][3]);
        radD.setText(q[i][4]);

    }

    private void readQuestions(int[] arr, String filename) {
        String questions[][] = new String[201][5];
        String data = "";
        InputStream is = getResources().openRawResource(getResources().getIdentifier(filename, "raw",  getPackageName()));
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuffer buf = new StringBuffer();

        if(is != null){
            try{
                int i = 0; int j = 0;
                while((data = reader.readLine()) != null){
                    questions[i][j] = data;
                    j++;
                    if(j == 5){
                        j = 0;
                        i++;
                    }
                }
                Log.e("LENGTH" , ""+i);

            }catch(Exception e){
                e.printStackTrace();
            }
        }

        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 5; j++)
                q[i][j] = questions[arr[i]][j];
        }
    }
}