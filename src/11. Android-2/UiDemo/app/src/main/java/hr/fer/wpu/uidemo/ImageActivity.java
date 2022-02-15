package hr.fer.wpu.uidemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class ImageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.fer_logo_1);
        this.setContentView(imageView);

    }
}
