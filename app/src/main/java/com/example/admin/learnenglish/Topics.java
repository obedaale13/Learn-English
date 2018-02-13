package com.example.admin.learnenglish;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Topics extends AppCompatActivity {

    String[] grams1 = {"1. Singular and Plural Nouns ", "2. Count Nouns vs. Non-Count Nouns ", "3. Possessive Nouns ", "4. Pronouns ", "5. 'Be' Verbs ", "6. Action Verbs ", "7. Adjectives ", "8. Comparative and Superlative Adjectives ", "9. Adverbs ", "10. Simple Tense ", "11. Progressive and Perfect Tense ", "12. Perfect Progressive Tense ", "13. Irregular Verbs ", "14. Gerunds ", "15. Infinitives 1 ", "16. Infinitives 2 ", "17. Active Voice and Passive Voice ", "18. Indicative, Imperative, Subjunctive Mood ", "19. Auxiliary Verbs - 'Be,' 'Do,' 'Have' ", "20. Auxiliary Verbs - 'Will/Would,' 'Shall/Should' "};
    String[] grams2 = {"1. Past Continuous Use", "2. Present Perfect Use", "3. 'For', 'Since' and 'Ago'", "4. Modal Verbs", "5. 'Will' or 'Going To'", "6. \"Have To\" and \"Must\"", "7. Present Perfect Continuous Structure", "8. Present Perfect Continuous Use", "9. Second Conditional", "10. Passive Overview", "11. Passive Present", "12. Passive Past", "13. Get Passive", "14. Past Perfect Structure", "15. Past Perfect Use", "16. 'Make', 'Let' and 'Allow'", "17. Used To", "18. Gerund Or Infinitive", "19. Needn't", "20. Subject And Object Questions", "21. Relative Pronouns and Relative Clauses", "22. \"Can\" And \"Be Able\""};
    String[] grams3 = {"1. Reporting people's words and thoughts", "2. Reporting statements (1): that-clauses", "3. Reporting statements (2): verb tense in that-clauses", "4. Reporting statements (3): verb tense in the reporting clause; say and tell; etc. ", "5. Reporting offers, suggestions, orders, intentions, etc.", "6. Should in that-clauses", "7. Modal verbs in reporting IV", "8. Countable and uncountable nouns", "9 Agreement between subject and verb (1)", "10. Agreement between subject and verb (2)", "11. The possessive form of nouns (Jane's mother)", "12. Compound nouns (1)", "13. Compound nouns (2)", "14. A/an and one", "15. The and a/an (1):'the only one'", "16. The and a/an (2): 'things already known', etc.", "17. Some and zero article with plural and uncountable nouns ", "18. The, zero article and a/an: 'things in general'", "19. People and places", "20. Holidays, times of the day, meals, etc."};
    String[] vocab1 = {"English Vocabulary Lessons", "Prefixes using pre-, re-, and sub-", "Some root words + ~cide", "Unicycle, Bicycle, Tricycle, and more", "Caring Vocabulary", "Professional Relationship Vocabulary", "Marry/ Get Married/ Be Married", "Using Die, Died and Dead", "American Words/ British Words", "Languages and Nationalities", "Words with Bulb and Knob", "Go, do, or play sports?", "Root Words from Tele and Phone"};
    String[] vocab2 = {"Beauty Salons","Cooking Instructions in English and what they mean","Do vs. Make","Earth Day","Feelings and Emotions","Halloween","Health Problems","Hotels","Hotel Dialogues","Kitchen","Office Equipment","Opinions","Personality Types","Saint Patrick's Day","Sounds Animals Make","Sports","Street vs. Road","Thanksgiving Day","Universe & Space Exploration","United Kingdom vs. Great Britain","Valentine's Day","Weather"};
    String[] vocab3 = {"Australia","Christmas Carol - 12 Days of Christmas","Christmas Traditions","Collective Nouns","Construction Sites","Endangered Species","England","Environment","Idioms - Weather","IELTS Academic Writing Task 1 - Describing Trends","New Zealand","Nursery Rhymes","Parts of a Car","Phrasal Verbs with GET","Phrasal Verbs with LOOK","Phrasal Verbs with MAKE","Phrasal Verbs with PUT","Phrasal Verbs with TAKE","TOEFL iBT Listening - Student Conversations ","United States ","Workshop Tools"};
    String[] audios = {"01 (50 Famous Stories Retold)", "02 (50 Famous Stories Retold)", "03 (50 Famous Stories Retold)", "04 (50 Famous Stories Retold)", "05 (50 Famous Stories Retold)"};

    private ListView listView;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_topics);
        listView = (ListView) findViewById(R.id.listOfTopics);
        final ArrayList<String> list = new ArrayList<>();
        final String section = getIntent().getExtras().getString("section", "g");
        final String level =  getIntent().getExtras().getString("level", "0");

        if(section.equals("g") && level.equals("0"))
            for(int i = 0; i < grams1.length; i++)
                list.add(grams1[i]);

        else if(section.equals("g") && level.equals("1"))
            for(int i = 0; i < grams2.length; i++)
                list.add(grams2[i]);

        else if(section.equals("g") && level.equals("2"))
            for(int i = 0; i < grams3.length; i++)
                list.add(grams3[i]);

        else if(section.equals("v") && level.equals("0"))
            for(int i = 0; i < vocab1.length; i++)
                list.add(vocab1[i]);
        else if(section.equals("v") && level.equals("1"))
            for(int i = 0; i < vocab2.length; i++)
                list.add(vocab2[i]);
        else if(section.equals("v") && level.equals("2"))
            for(int i = 0; i < vocab3.length; i++)
                list.add(vocab3[i]);

        else if(section.equals("a")){
            for(int i = 0; i < audios.length; i++)
                list.add(audios[i]);
        }
        else
            startActivity(new Intent(getApplicationContext(), EmptyActivity.class));

        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                Intent i;
                if(section.equals("g") || section.equals("v") || section.equals("p")){
                    i = new Intent(Topics.this, GrammarActivity.class);

                }else{
                    i = new Intent(Topics.this, AudioActivity.class);
                }
                i.putExtra("id", position+1);
                i.putExtra("section", section);
                i.putExtra("level", level);
                startActivity(i);

            }

        });

    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }
}


