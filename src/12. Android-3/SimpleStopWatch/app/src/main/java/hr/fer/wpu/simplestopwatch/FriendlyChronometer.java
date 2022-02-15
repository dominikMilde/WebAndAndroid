package hr.fer.wpu.simplestopwatch;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.widget.Chronometer;

public class FriendlyChronometer extends Chronometer {

    private boolean paused = false;
    private boolean stopped = true;
    private long timeWhenStopped = 0;

    public FriendlyChronometer(Context context) {
        super(context);
    }

    public FriendlyChronometer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public FriendlyChronometer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void play() {
        if (paused) {
            setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
            paused = false;
        }
        if (stopped) {
            setBase(SystemClock.elapsedRealtime());
            stopped = false;
        }
        start();
        stopped = false;
    }

    public void pause() {
        if (paused) {
            setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
            start();
            paused = false;
        } else {
            timeWhenStopped = getBase() - SystemClock.elapsedRealtime();
            stop();
            paused = true;
        }
    }

    public boolean isPaused() {
        return paused;
    }

    public void reset() {
        setBase(SystemClock.elapsedRealtime());
    }

    @Override
    public void stop() {
        super.stop();
        stopped = true;
    }
}