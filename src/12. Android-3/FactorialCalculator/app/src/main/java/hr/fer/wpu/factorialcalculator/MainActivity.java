package hr.fer.wpu.factorialcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private EditText editText;
    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setHint("Enter a number");
        editText.setLayoutParams(layoutParams);
        layout.addView(editText);

        progressBar = new ProgressBar(this,null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setVisibility(View.INVISIBLE);
        editText.setLayoutParams(layoutParams);
        layout.addView(progressBar);

        button = new Button(this);
        button.setText("Calculate Factorial");
        editText.setLayoutParams(layoutParams);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CalculateFactorialTask().execute(Short.parseShort(editText.getText().toString()));
            }
        });
        layout.addView(button);

        textView = new TextView(this);
        textView.setLayoutParams(layoutParams);
        layout.addView(textView);

        setContentView(layout);
    }

    private class CalculateFactorialTask extends
            AsyncTask<Short, Integer, Long> {

        @Override
        protected void onPreExecute() {
            // disable clicking on button
            button.setClickable(false);

            //reset and show progress bar
            progressBar.setProgress(0);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Long doInBackground(Short... params) {
            short n = params[0];
            long result = 1L;

            for (short i = 1; i <= n; i++) {
                if (Long.MAX_VALUE / i < result) {
                    //skip if overflow
                    result = 0L;
                    break;
                }
                result = result * i;

                //slow down the thread
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //publish progress update
                publishProgress((int) Math.round((i * 100) / n));
            }

            return result;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            //update progress bar
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Long result) {
            // show result
            textView.setText(String.format("Result: %d! = %d", Integer.parseInt(editText.getText().toString()), result));

            // enable clicking on button
            button.setClickable(true);

            //hide progress bar
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}