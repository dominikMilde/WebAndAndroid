package hr.fer.wpu.simplestopwatch;

public class SimpleStopWatch extends Thread {
    private long seconds = 0L;
    private boolean running = false;
    private boolean paused = true;

    public long getElapsedTimeSec() {
        return seconds;
    }

    public void run() {
        while (running) {
            try {
                Thread.sleep(1000);

                //tick if not paused
                if (!paused) {
                    seconds++;
                    if (tickListener != null) {
                        tickListener.onTick(seconds);
                    }
                }
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public void startIt() {
        if (!running) { //run in a new thread
            Thread thread = new Thread(this);
            thread.setPriority(Thread.MAX_PRIORITY);
            thread.start();
            running = true;
        }

        paused = false;
    }

    public void pauseIt() {
        if (!paused) {
            paused = true;
        } else if (seconds != 0L) {//start it if not stopped
            paused = false;
        }
    }

    public void stopIt() {
        paused = true;
        running = false;
    }

    public void resetIt() {
        seconds = 0L;
    }

    public boolean isRunning() {
        return running;
    }

    public static void main(String[] args) {
        SimpleStopWatch timer = new SimpleStopWatch();
        timer.setTickListener(timer.new SystemOutTickListener());
        timer.startIt();
    }

    private OnTickListener tickListener;

    public interface OnTickListener {
        void onTick(long elapsedTime);
    }

    public void setTickListener(OnTickListener tickListener) {
        this.tickListener = tickListener;
    }

    private class SystemOutTickListener implements OnTickListener {

        @Override
        public void onTick(long elapsedTime) {
            System.out.println(elapsedTime);
        }
    }
}