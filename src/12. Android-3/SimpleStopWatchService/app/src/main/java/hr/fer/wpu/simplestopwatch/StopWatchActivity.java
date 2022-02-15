package hr.fer.wpu.simplestopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StopWatchActivity extends AppCompatActivity {

    private final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.5f);
    private TextView textView;
    private StopWatchService serviceReference;

    // handles the connection between the service and activity
    private ServiceConnection serviceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // called when the connection is made
            serviceReference = ((StopWatchService.SWSBinder) service)
                    .getService();

            // ensure the service is not in the foreground when bound
            serviceReference.background();

            SimpleStopWatch stopWatch = serviceReference.getStopWatch();

            // set elapsed time as shown time
            StopWatchActivity.this.runOnUiThread(new UpdateShownTime(stopWatch
                    .getElapsedTimeSec()));

            // set tick listener
            stopWatch.setTickListener(new SimpleStopWatch.OnTickListener() {
                @Override
                public void onTick(long elapsedTime) {
                    // update view
                    StopWatchActivity.this.runOnUiThread(new UpdateShownTime(elapsedTime));
                }
            });
        }

        public void onServiceDisconnected(ComponentName className) {
            // received when the service unexpectedly disconnects
            serviceReference = null;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // stopwatch service is a started service
        Intent startIntent = new Intent(StopWatchActivity.this, StopWatchService.class);
        startService(startIntent);

        // stopwatch service is a bounded service
        Intent bindIntent = new Intent(StopWatchActivity.this, StopWatchService.class);
        bindService(bindIntent, serviceConnection, Context.BIND_AUTO_CREATE);

        LinearLayout verticalLayout = new LinearLayout(this);
        verticalLayout.setOrientation(LinearLayout.VERTICAL);
        textView = new TextView(this);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 60);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        verticalLayout.addView(textView);

        LinearLayout horizontalLayout = new LinearLayout(this);
        horizontalLayout.setGravity(Gravity.CENTER_HORIZONTAL);

        //define buttons
        Button playButton = new Button(this);
        playButton.setBackgroundColor(Color.TRANSPARENT);
        Drawable top = getDrawable(R.drawable.play);
        playButton.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
        playButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                serviceReference.getStopWatch().startIt();
            }
        });
        horizontalLayout.addView(playButton);

        //define buttons
        Button pauseButton = new Button(this);
        pauseButton.setBackgroundColor(Color.TRANSPARENT);
        top = getDrawable(R.drawable.pause);
        pauseButton.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                serviceReference.getStopWatch().pauseIt();
            }
        });
        horizontalLayout.addView(pauseButton);

        //define buttons
        Button stopButton = new Button(this);
        stopButton.setBackgroundColor(Color.TRANSPARENT);
        top = getDrawable(R.drawable.stop);
        stopButton.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
        stopButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.startAnimation(buttonClick);

                serviceReference.getStopWatch().stopIt();
                serviceReference.getStopWatch().resetIt();

                // reset shown time
                UpdateShownTime updateTextView = new UpdateShownTime(0L);
                StopWatchActivity.this.runOnUiThread(updateTextView);
            }
        });
        horizontalLayout.addView(stopButton);

        verticalLayout.addView(horizontalLayout);
        setContentView(verticalLayout);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // stop service if stopwatch is not running
        if (serviceReference != null
                && !serviceReference.getStopWatch().isRunning()) {
            Intent stopIntent = new Intent(StopWatchActivity.this,
                    StopWatchService.class);
            stopService(stopIntent);
        } else {
            serviceReference.foreground();
        }

        // explicitly unbind service
        this.unbindService(serviceConnection);
    }

    private class UpdateShownTime implements Runnable {

        private long elapsedTime;

        public UpdateShownTime(long elapsedTime) {
            super();
            this.elapsedTime = elapsedTime;
        }

        public void run() {
            textView.setText(formatIntoHHMMSS(elapsedTime));
        }

        private String formatIntoHHMMSS(long secs) {

            long hours = secs / 3600, remainder = secs % 3600, minutes = remainder / 60, seconds = remainder % 60;

            return ((hours < 10 ? "0" : "") + hours + ":"
                    + (minutes < 10 ? "0" : "") + minutes + ":"
                    + (seconds < 10 ? "0" : "") + seconds);
        }
    }
}