package at.htlwels.mountaintracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "at.htlwels.MainActivity";
    private static final int  MENU_ITEM_ITEM1 = 1;

    Button button;

    private TextView textView_timer;
    private Button button_start_pause;
    private Button button_stop;

    Timer timer;
    TimerTask timerTask;
    Double time = 0.0;

    private boolean timerRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "Main Activity created");

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity();
            }
        });

        //Timer
        textView_timer = findViewById(R.id.text_view_timer);
        button_start_pause = findViewById(R.id.button_start_pause);
        button_stop = findViewById(R.id.button_stop);

        button_start_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPauseTimer();
            }
        });

        button_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopTimer();
            }
        });

        timer = new Timer();
    }

    private void startPauseTimer() {
        if(timerRunning == false) {
            timerRunning = true;
            button_start_pause.setText("Pause");

            startTimer();
        } else {
            timerRunning = false;
            button_start_pause.setText("Start");

            timerTask.cancel();
        }
    }

    private void stopTimer() {
        if (timerTask != null) {
            timerTask.cancel();
            time = 0.0;
            timerRunning = false;
            textView_timer.setText(formatTime(0,0,0));
        }
    }

    private void startTimer() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time++;
                        textView_timer.setText(getTimerText());
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    private String getTimerText() {
        int rounded = (int) Math.round(time);
        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hours = (rounded % 86400) / 3600;

        return formatTime(seconds, minutes, hours);
    }

    private String formatTime(int seconds, int minutes, int hours) {
        return String.format("%02d", hours) + " : " + String.format("%02d", minutes) + " : " + String.format("%02d", seconds);
    }

    public void openNewActivity(){
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //menu.add(Menu.NONE, MENU_ITEM_ITEM1, Menu.NONE, "Hello");
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_hello:
                Log.i(TAG, "Menu Item - Hello - clicked!");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}