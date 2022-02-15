package hr.fer.wpu.uidemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class SpinnerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ArrayList<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("apple");
        spinnerArray.add("pear");
        spinnerArray.add("plum");
        Spinner spinner = new Spinner(this, Spinner.MODE_DIALOG);
        spinner.setPrompt("Choose your favorite fruit");
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SpinnerActivity.this, "Position " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(SpinnerActivity.this, "Nothing ", Toast.LENGTH_SHORT).show();
            }
        });
        this.setContentView(spinner);


    }
}
