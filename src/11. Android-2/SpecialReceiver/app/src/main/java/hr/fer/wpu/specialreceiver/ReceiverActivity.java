package hr.fer.wpu.specialreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ReceiverActivity extends AppCompatActivity {

    private static final String SPECIAL_EVENT = "hr.fer.wpu.SPECIAL_EVENT";
    private IntentFilter filter = new IntentFilter(SPECIAL_EVENT);
    private SpecialReceiver receiver = new SpecialReceiver();
    private LinearLayout layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView scrollView = new ScrollView(this);
        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(layout);
        this.setContentView(scrollView);
    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private class SpecialReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();

            if (extras != null) {
                String message = extras.getString("message");

                TextView lastReceive = new TextView(ReceiverActivity.this);
                lastReceive.setText("Received message: " + message);
                layout.addView(lastReceive);
            }
        }
    }
}
