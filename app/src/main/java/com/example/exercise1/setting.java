package com.example.exercise1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;


public class setting extends AppCompatActivity {
    private Boolean onTimer1 = false;
    private Boolean onTimer2 = false;
    private Boolean onButton = false;
    private String TIMERSETTING1 = "useTimer1";
    private String TIMERSETTING2 = "useTimer2";
    private String SETTIME1 = "setTime1";
    private String SETTIME2 = "setTime2";
    private String BUTTONSETTING = "useImage";
    private int time1;
    private int time2;
    public static int questionNumber = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

    }


    public void onClickTimer1(View view){
        onTimer1 = ((Switch) view).isChecked();

    }

    public void onClickTimer2(View view) {
        onTimer2 = ((Switch) view).isChecked();
    }


    public void onCLickButton(View view) {
        onButton = ((Switch) view).isChecked();
    }


    public void onClickStart(View view) {
        Intent intent = new Intent(this, MainActivity.class);

        EditText timeView1 = (EditText)findViewById(R.id.setTime1);
        EditText timeView2 = (EditText)findViewById(R.id.setTime2);
        try {
            time1 = Integer.parseInt(timeView1.getText().toString());
        } catch (NumberFormatException e){
            time1 = -1;
        }

        try {
            time2 = Integer.parseInt(timeView2.getText().toString());
        } catch (NumberFormatException e){
            time2 = -1;
        }

        TimerService.seconds11 = 0;
        TimerService.seconds12 = 0;
        TimerService.seconds21 = 0;
        TimerService.seconds22 = 0;
        intent.putExtra(TIMERSETTING1, onTimer1);
        intent.putExtra(TIMERSETTING2, onTimer2);
        intent.putExtra(SETTIME1, time1);
        intent.putExtra(SETTIME2, time2);
        intent.putExtra(BUTTONSETTING, onButton);
        startActivity(intent);
        questionNumber = 1;


//        Intent intent3 = new Intent(this, TimingService.class);
//        intent3.putExtra(TimingService.EXTRA_MESSAGE, time1);
//        startService(intent3);

    }
}
