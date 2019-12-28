package com.example.exercise1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class DisplayResult extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "message";
    private String result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.showingResult = true;
        setContentView(R.layout.activity_display_result);
        Intent intent = getIntent();
        Boolean pass = intent.getBooleanExtra(EXTRA_MESSAGE, false);

        if (pass == false){
            QuizApplication.failedTimes++;
        }else{
            QuizApplication.successTimes++;
        }
        result = "You have passed this quiz " + QuizApplication.successTimes + " times \n You have failed this quiz " +
                QuizApplication.failedTimes + " times";
        TextView result_display = (TextView) findViewById(R.id.final_result);
        result_display.setText(result);
    }

    public void onClickShare(View view){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, result);
        String chooseTitle = getString(R.string.chooser);
        Intent chosenIntent = Intent.createChooser(intent, chooseTitle);
        startActivity(chosenIntent);
    }

    public void onClickRetake(View view){
        Intent intent = new Intent(this, setting.class);
        startActivity(intent);
        MainActivity.showingResult = false;
    }
}
