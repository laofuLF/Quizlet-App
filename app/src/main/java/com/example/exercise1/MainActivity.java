package com.example.exercise1;


import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.nfc.Tag;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.Intent;

import org.w3c.dom.Text;

import java.util.Locale;

public class MainActivity extends Activity {
    private TimerService timer;
    private boolean bound = false;
    public static boolean showingResult = false;

    private int count = 2;
    public static boolean pass1 = false;
    private int seconds = 0;
    private int seconds2 = 0;
    public static Boolean running = false;
    public static Boolean running2 = false;

    private Boolean onTimer1;
    private Boolean onTimer2;
    private Boolean onButton;
    private String TIMERSETTING1 = "useTimer1";
    private String TIMERSETTING2 = "useTimer2";
    private String BUTTONSETTING = "useImage";
    private String SETTIME1 = "setTime1";
    private String SETTIME2 = "setTime2";
    public static int time1 = -1;
    public static int time2 = -1;

    private TextView timer2;
    private TextView timer1;

    private static final String TAG = "MainActivity";

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

            timer.runTimer(time1);
            timer.setListener(listener);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setting.questionNumber = 1;
        Intent intent = new Intent(this, TimerService.class);

//        onTimer1 = intent.getBooleanExtra(TIMERSETTING1, false);
//        onTimer2 = intent.getBooleanExtra(TIMERSETTING2, false);
//        time1 = intent.getIntExtra(SETTIME1, -1);
//        time2 = intent.getIntExtra(SETTIME2, -1);
//        onButton = intent.getBooleanExtra(BUTTONSETTING, false);
//        Intent intent6 = new Intent(this, TimerService.class);
//        bindService(intent6, connection, Context.BIND_AUTO_CREATE);

        start(intent);

//        TextView timer1 = (TextView) findViewById(R.id.q1timer1);
//        TextView timer2 = (TextView) findViewById(R.id.q1timer2);
//        if (savedInstanceState != null) {
//            seconds = savedInstanceState.getInt("seconds");
//            seconds2 = savedInstanceState.getInt("seconds2");
//            running = savedInstanceState.getBoolean("running");
//        }
//        if (onTimer1){
//            displayTime();
//        }else {
//            timer1.setVisibility(View.INVISIBLE);
//            timer2.setVisibility(View.INVISIBLE);
//        }

    }

    public void onClickQuestion1(View view){
        Intent intent1 = new Intent(this, DisplayResult.class);
        Intent intent2 = new Intent(this, question2.class);
        TextView text = (TextView) findViewById(R.id.result1);
        Spinner answer1 = (Spinner) findViewById(R.id.answer1);
        Spinner answer12 = (Spinner) findViewById(R.id.answer12);
        String answerQ1 = String.valueOf(answer1.getSelectedItem());
        String answerQ12 = String.valueOf(answer12.getSelectedItem());
        String result;
        if (answerQ1.equals("Python") && answerQ12.equals("Rainy")){
            result = "correct";
            pass1 = true;
            running = false;
//            stop();
            text.setText(result);
            intent2.putExtra(TIMERSETTING2, onTimer2);
            intent2.putExtra(BUTTONSETTING, onButton);
//            intent2.putExtra(SETTIME2, time2);
            startActivity(intent2);

        }else{
            result = "wrong!";
            count --;
            text.setText(result + ", you have" + count + "chance left");
        }

        if (count == 0){
            pass1 = false;
            running = false;
//            stop();
            intent1.putExtra("message", pass1);
            startActivity(intent1);
        }

    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        running = true;
//    }



    public void onSaveInstanceState(Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
        saveInstanceState.putInt("seconds", seconds);
        saveInstanceState.putInt("seconds2", seconds2);
        saveInstanceState.putBoolean("running", running);
    }


    private void displayTime() {
        final TextView timeView1 = (TextView) findViewById(R.id.q1timer1);
        final TextView timeView2 = (TextView) findViewById(R.id.q1timer2);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (bound && timer != null) {
                    seconds = timer.getSeconds11();
                    seconds2 = timer.getSeconds12();
                    int hours1 = seconds/3600;
                    int minutes1 = (seconds%3600)/60;
                    int secs1 = seconds%60;
                    String time1 = String.format(Locale.getDefault(), "%d:%02d:%02d", hours1, minutes1, secs1);

                    int hours2 = seconds2/3600;
                    int minutes2 = (seconds2%3600)/60;
                    int secs2 = seconds2%60;
                    String time2 = String.format(Locale.getDefault(), "%d:%02d:%02d", hours2, minutes2, secs2);


                    timeView1.setText(time1);
                    timeView2.setText(time2);

                }
                handler.postDelayed(this, 1000);
//                if (running == false) {
//                    stop();
//                    stopService(intent);
//                }
            }
        });
    }



//    @Override
//    protected void onStart() {
//        super.onStart();
//        Intent intent = new Intent(this, TimerService.class);
//        bindService(intent, connection, Context.BIND_AUTO_CREATE);
//    }

    public void start(Intent intent) {


        Intent intent1 = getIntent();
        time1 = intent1.getIntExtra(SETTIME1, -1);
        time2 = intent1.getIntExtra(SETTIME2, -1);

        if (!bound) {
            bindService(intent, connection, Context.BIND_AUTO_CREATE);
        }else {
            timer.setListener(listener);
            timer.runTimer(time1);
        }

        onButton = intent1.getBooleanExtra(BUTTONSETTING, false);
        onTimer1 = intent1.getBooleanExtra(TIMERSETTING1, false);
        onTimer2 = intent1.getBooleanExtra(TIMERSETTING2, false);
        timer1 = (TextView) findViewById(R.id.q1timer1);
        timer2 = (TextView) findViewById(R.id.q1timer2);
        ImageButton imageButton = (ImageButton) findViewById(R.id.submit1Image);
        Button button = (Button) findViewById(R.id.submit1);
        if (onButton) {
            button.setVisibility(View.GONE);
        }else {
            imageButton.setVisibility(View.GONE);
        }

//        if (onTimer1) {
//            displayTime(intent);
//        }else{
//            displayTime(intent);
//            timer1.setVisibility(View.INVISIBLE);
//            timer2.setVisibility(View.INVISIBLE);
//        }


    }


    public void stop() {
        if(bound) {
            unbindService(connection);
            running = false;
            bound = false;
        }
    }


//    @Override
//    protected void onStop() {
//        super.onStop();
//        Log.d("Weihuan Fu", "onStop!!!!!");
//        if(bound) {
//            unbindService(connection);
//            bound = false;
//        }
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");

        if(bound) {
            unbindService(connection);
            bound = false;
        }
    }

    public void onPause() {
        super.onPause();
        running2 = false;
    }


    public void onResume() {
        super.onResume();
        running2 = true;

        if (onTimer1) {
            displayTime();
        }else{
            displayTime();
            timer1.setVisibility(View.INVISIBLE);
            timer2.setVisibility(View.INVISIBLE);
        }
    }


}
