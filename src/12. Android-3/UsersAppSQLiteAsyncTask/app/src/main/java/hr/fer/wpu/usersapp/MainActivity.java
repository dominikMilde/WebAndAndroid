package hr.fer.wpu.usersapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import java.util.List;


import hr.fer.wpu.usersapp.sqlite.DatabaseSingleton;
import hr.fer.wpu.usersapp.sqlite.UserDataDatabase;
import hr.fer.wpu.usersapp.sqlite.UserData;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_NEW = 1;
    private static final int REQUEST_CODE_EDIT = 2;

    //spinner
    private Spinner spinner;
    private Integer spinnerPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        editButton.setText("Promijeni");
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditUserActivity.class);
                intent.putExtra("userData", ((UserData) spinner.getSelectedItem()));
                startActivityForResult(intent, REQUEST_CODE_EDIT);
            }
        });
        horizontalLayout.addView(editButton);

        Button deleteButton = new Button(this);
        deleteButton.setText("Izbriši");
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String uuid = ((UserData) spinner.getSelectedItem()).getUuid();
                //delete the selected user
                new AsyncTask<Void, Void, List<UserData>>() {
                    @Override
                    protected List<UserData> doInBackground(Void... ignored) {
                        UserDataDatabase db = DatabaseSingleton.getInstance(MainActivity.this).getDB();
                        db.userDataDao().deleteByUuid(uuid);
                        return db.userDataDao().getAll();
                    }

                    @Override
                    protected void onPostExecute(List<UserData> userDatas) {
                        updateSpinner(userDatas);
                    }
                }.execute();
            }
        });
        horizontalLayout.addView(deleteButton);

        verticalLayout.addView(horizontalLayout);
        this.setContentView(verticalLayout);

        //update spinner
        new AsyncTask<Void, Void, List<UserData>>() {
            @Override
            protected List<UserData> doInBackground(Void... ignored) {
                UserDataDatabase db = DatabaseSingleton.getInstance(MainActivity.this).getDB();
                return db.userDataDao().getAll();
            }

            @Override
            protected void onPostExecute(List<UserData> userDatas) {
                updateSpinner(userDatas);
            }
        }.execute();
    }

    private void updateSpinner(List<UserData> userDatas) {
        ArrayAdapter<UserData> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, userDatas);
        spinner.setAdapter(spinnerArrayAdapter);
        //set the spinner position
        if (spinnerPosition < userDatas.size()) {
            spinner.setSelection(spinnerPosition);
        } else {
            spinner.setSelection(userDatas.size() - 1);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_NEW) {
            //make sure the request was successful
            if (resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    final UserData userData = (UserData) extras.getSerializable("userData");
                    //add a new user
                    new AsyncTask<Void, Void, List<UserData>>() {
                        @Override
                        protected List<UserData> doInBackground(Void... ignored) {
                            UserDataDatabase db = DatabaseSingleton.getInstance(MainActivity.this).getDB();
                            db.userDataDao().insert(userData);
                            return db.userDataDao().getAll();
                        }

                        @Override
                        protected void onPostExecute(List<UserData> db) {
                            updateSpinner(db);
                        }
                    }.execute();
                }
            }
        }

        if (requestCode == REQUEST_CODE_EDIT) {
            //make sure the request was successful
            if (resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    final UserData userData = (UserData) extras.getSerializable("userData");
                    //update the stored user
                    new AsyncTask<Void, Void, List<UserData>>() {
                        @Override
                        protected List<UserData> doInBackground(Void... ignored) {
                            UserDataDatabase db = DatabaseSingleton.getInstance(MainActivity.this).getDB();
                            db.userDataDao().deleteByUuid(userData.getUuid());
                            db.userDataDao().insert(userData);
                            return db.userDataDao().getAll();
                        }

                        @Override
                        protected void onPostExecute(List<UserData> userDatas) {
                            updateSpinner(userDatas);
                        }
                    }.execute();
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
        UserDataDatabase db = DatabaseSingleton.getInstance(this).getDB();
        if (db != null) {
            if (db.isOpen()) {
                db.close();
            }
        }
    }
}
