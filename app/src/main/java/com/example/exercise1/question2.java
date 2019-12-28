package com.example.exercise1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.BundleCompat;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Locale;

public class question2 extends AppCompatActivity {
    private int count = 2;
    private TimerService timer;
    private boolean bound = false;
    private Boolean pass = false;
    private int seconds = 0;
    private int seconds2 = 0;
    public static Boolean running = false;
    public static Boolean running2 = false;
    private Boolean onTimer2;
    private Boolean onButton;
    private String TIMERSETTING2 = "useTimer2";
    private String BUTTONSETTING = "useImage";
    public static int time2 = -1;
    private TextView timer1;
    private TextView timer2;
//    private String SETTIME2 = "setTime2";

    private TimerService.OnTimeOutListener listener = new TimerService.OnTimeOutListener() {
        @Override
        public void onTimerOut() {
            finish();
        }
    };


    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            TimerService.TimerBinder timerBinder =
                    (TimerService.TimerBinder) binder;
            timer = timerBinder.getTime();
            bound = true;

            timer.setListener(listener);
            timer.runTimer2(time2);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question2);
        setting.questionNumber = 2;
        time2 = MainActivity.time2;
        Intent intent = getIntent();
        onTimer2 = intent.getBooleanExtra(TIMERSETTING2, false);
        onButton = intent.getBooleanExtra(BUTTONSETTING, false);
        if (!bound) {
            bindService(intent, connection, Context.BIND_AUTO_CREATE);
        }else {
            timer.setListener(listener);
            timer.runTimer2(time2);
        }

        Intent intent6 = new Intent(this, TimerService.class);
        bindService(intent6, connection, Context.BIND_AUTO_CREATE);

        timer1 = (TextView) findViewById(R.id.q2timer1);
        timer2 = (TextView) findViewById(R.id.q2timer2);

        if (onTimer2) {
            displayTime();
        }else{
            displayTime();
            timer1.setVisibility(View.INVISIBLE);
            timer2.setVisibility(View.INVISIBLE);
        }
        ImageButton imageButton = (ImageButton) findViewById(R.id.submit2Image);
        Button button = (Button) findViewById(R.id.submit2);
        if (onButton) {
            button.setVisibility(View.GONE);
        }else {
            imageButton.setVisibility(View.GONE);
        }
    }

    public void onClickQuestion2(View view){
        Intent intent = new Intent(this, DisplayResult.class);
        EditText messageView = (EditText)findViewById(R.id.editText);
        String messageText = messageView.getText().toString();
        TextView text = (TextView) findViewById(R.id.textView3);

        if (messageText.toLowerCase().equals("seattle")){
            pass = true;
            running = false;
            intent.putExtra("message", pass);
            startActivity(intent);
            text.setText("correct!");
        }else{
            count --;
            text.setText("wrong! You have "+ count + "chance left");
        }

        if (count == 0){
            pass = false;
            running = false;
            intent.putExtra("message", pass);
            startActivity(intent);
        }
    }

    private void displayTime() {
        final TextView timeView1 = (TextView) findViewById(R.id.q2timer1);
        final TextView timeView2 = (TextView) findViewById(R.id.q2timer2);

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                if (bound && timer != null) {
                    seconds = timer.getSeconds21();
                    seconds2 = timer.getSeconds22();

                    int hours = seconds/3600;
                    int minutes = (seconds%3600)/60;
                    int secs = seconds%60;
                    String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);

                    int hours2 = seconds2/3600;
                    int minutes2 = (seconds2%3600)/60;
                    int secs2 = seconds2 % 60;
                    String time2 = String.format(Locale.getDefault(), "%d:%02d:%02d", hours2, minutes2, secs2);

                    timeView1.setText(time);
                    timeView2.setText(time2);

                }
//                if (running == false) {
//                    stop();
//                }

            }
        });
    }



    public void onSaveInstanceState(Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
        saveInstanceState.putInt("seconds", seconds);
        saveInstanceState.putInt("seconds2", seconds2);
        saveInstanceState.putBoolean("running", running);
    }

    public void onPause() {
        super.onPause();

        running2 = false;
    }


    public void onResume() {
        super.onResume();
        if (onTimer2) {
            displayTime();
        }else{
            displayTime();
            timer1.setVisibility(View.INVISIBLE);
            timer2.setVisibility(View.INVISIBLE);
        }
        running2 = true;
    }


//    public void stop() {
//        if(bound) {
//            unbindService(connection);
//            running = false;
//            bound = false;
//        }
//    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        if(bound) {
//            unbindService(connection);
//            bound = false;
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(bound) {
            unbindService(connection);
            bound = false;
        }
    }

}
