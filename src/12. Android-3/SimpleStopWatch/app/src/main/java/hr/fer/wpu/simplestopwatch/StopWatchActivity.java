package hr.fer.wpu.simplestopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.LinearLayout;

public class StopWatchActivity extends AppCompatActivity {

    private FriendlyChronometer stopWatch;
    private final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.5f);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout verticalLayout = new LinearLayout(this);
        verticalLayout.setOrientation(LinearLayout.VERTICAL);
        stopWatch = new FriendlyChronometer(this);
        stopWatch.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 60);
        stopWatch.setGravity(Gravity.CENTER_HORIZONTAL);
        verticalLayout.addView(stopWatch);

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
                stopWatch.play();
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
                stopWatch.pause();
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
                stopWatch.stop();
            }
        });
        horizontalLayout.addView(stopButton);

        verticalLayout.addView(horizontalLayout);
        setContentView(verticalLayout);
    }
}