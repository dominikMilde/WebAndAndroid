package hr.fer.wpu.usersapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.gson.Gson;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private Gson gson = new Gson();
    private static final int REQUEST_CODE_NEW = 1;
    private static final int REQUEST_CODE_EDIT = 2;

    //spinner
    private Spinner spinner;
    List<String> uuids = new LinkedList<>();
    List<UserData> userDatas = new LinkedList<>();
    private int spinnerPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get the shared preferences
        prefs = getSharedPreferences("users", Activity.MODE_PRIVATE);
        if (prefs.getAll().size() == 0) {
            //fill the map using factory
            SharedPreferences.Editor editor = prefs.edit();
            for (Map.Entry<String, UserData> entry : UserDataFactory.getData().entrySet()) {
                //store userdata as json
                editor.putString(entry.getKey(), gson.toJson(entry.getValue()));
            }
            editor.apply();
        }

        LinearLayout verticalLayout = new LinearLayout(this);
        verticalLayout.setOrientation(LinearLayout.VERTICAL);

        Button addButton = new Button(this);
        addButton.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addButton.setText("Dodaj novog");
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditUserActivity.class);
                startActivityForResult(intent, REQUEST_CODE_NEW);

            }
        });
        verticalLayout.addView(addButton);

        LinearLayout horizontalLayout = new LinearLayout(this);

        spinner = new Spinner(this, Spinner.MODE_DIALOG);
        spinner.setPrompt("Odaberi korisnika");
        updateSpinner(null);

        //save spinner position change
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinnerPosition = 0;
            }
        });

        horizontalLayout.addView(spinner);

        Button editButton = new Button(this);
        editButton.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        editButton.setText("Promijeni odabranog");
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditUserActivity.class);
                intent.putExtra("userData", userDatas.get((int) spinner.getSelectedItemId()));
                intent.putExtra("userUuid", uuids.get((int) spinner.getSelectedItemId()));
                startActivityForResult(intent, REQUEST_CODE_EDIT);
            }
        });
        horizontalLayout.addView(editButton);
        verticalLayout.addView(horizontalLayout);

        this.setContentView(verticalLayout);
    }

    private void updateSpinner(String uuid) {
        if (uuid == null) {
            //fill uuids and userdatas lists
            for (Map.Entry<String, ?> entry : prefs.getAll().entrySet()) {
                uuids.add(entry.getKey());
                //load userdata from json
                userDatas.add(gson.fromJson(((String) entry.getValue()), UserData.class));
            }
        } else {
            //update uuids and userdatas lists
            int position = uuids.indexOf(uuid);
            if (position != -1) {
                //editing a user
                userDatas.set(position, gson.fromJson(((String) prefs.getString(uuid, null)), UserData.class));
            } else {
                //adding a user
                uuids.add(uuid);
                userDatas.add(gson.fromJson(((String) prefs.getString(uuid, null)), UserData.class));
            }
        }

        ArrayAdapter<UserData> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, userDatas);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setSelection(spinnerPosition);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_NEW) {
            //make sure the request was successful
            if (resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    //add new user to the map
                    UserData userData = (UserData) extras.getSerializable("userData");
                    //add new user data
                    String uuid = UUID.randomUUID().toString();
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString(uuid, gson.toJson(userData));
                    editor.apply();
                    updateSpinner(uuid);
                }
            }
        }

        if (requestCode == REQUEST_CODE_EDIT) {
            //make sure the request was successful
            if (resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    //add new user to the map
                    UserData userData = (UserData) extras.getSerializable("userData");
                    String uuid = extras.getString("userUuid");
                    //update stored user data
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString(uuid, gson.toJson(userData));
                    editor.apply();
                    updateSpinner(uuid);
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle instanceState) {
        super.onSaveInstanceState(instanceState);

        instanceState.putInt("spinnerPosition", spinnerPosition);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        spinnerPosition = savedInstanceState.getInt("spinnerPosition", -1);
        if (spinnerPosition != -1) {
            spinner.setSelection(spinnerPosition);
        } else {
            spinnerPosition = 0;
        }
    }
}
