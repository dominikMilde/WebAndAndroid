package hr.fer.wpu.usersapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import hr.fer.wpu.usersapp.sqlite.UserDataDatabase;
import hr.fer.wpu.usersapp.sqlite.UserData;
import hr.fer.wpu.usersapp.sqlite.UserDataFactory;

public class MainActivity extends AppCompatActivity {

    private UserDataDatabase db;
    private static final int REQUEST_CODE_NEW = 1;
    private static final int REQUEST_CODE_EDIT = 2;

    //spinner
    private Spinner spinner;
    private int spinnerPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //build the database - on main thread?!
        if (db == null) {
            db = Room.databaseBuilder(getApplicationContext(), UserDataDatabase.class, "userdatas").allowMainThreadQueries().build();
            //db.userDataDao().deleteAll(); //delete database if necessary
        }

        //fill the database if empty
        if (db.userDataDao().getAll().size() == 0) {
                db.userDataDao().insertAll(UserDataFactory.getData());
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
        updateSpinner();

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
                intent.putExtra("userData", ((UserData) spinner.getSelectedItem()));
                startActivityForResult(intent, REQUEST_CODE_EDIT);
            }
        });
        horizontalLayout.addView(editButton);
        verticalLayout.addView(horizontalLayout);

        this.setContentView(verticalLayout);
    }

    private void updateSpinner() {
        ArrayAdapter<UserData> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, db.userDataDao().getAll());
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
                    UserData userData = (UserData) extras.getSerializable("userData");
                    //add new user data
                    db.userDataDao().insert(userData);
                    updateSpinner();
                }
            }
        }

        if (requestCode == REQUEST_CODE_EDIT) {
            //make sure the request was successful
            if (resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    UserData userData = (UserData) extras.getSerializable("userData");
                    //update stored user data
                    db.userDataDao().deleteByUuid(userData.getUuid());
                    db.userDataDao().insert(userData);
                    updateSpinner();
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (db != null) {
            if (db.isOpen()) {
                db.close();
            }
            db = null;
        }
    }
}
