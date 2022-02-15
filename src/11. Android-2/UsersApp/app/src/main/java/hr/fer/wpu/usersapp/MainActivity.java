package hr.fer.wpu.usersapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private Map<String, UserData> userDataMap = UserDataFactory.getData();
    private static final int REQUEST_CODE_NEW = 1;
    private static final int REQUEST_CODE_EDIT= 2;

    //spinner
    private Spinner spinner;
    List<String> uuids = new LinkedList<>();
    List<UserData> userDatas = new LinkedList<>();

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
                startActivityForResult(intent,REQUEST_CODE_NEW);
            }
        });
        verticalLayout.addView(addButton);

        LinearLayout horizontalLayout = new LinearLayout(this);

        spinner = new Spinner(this, Spinner.MODE_DIALOG);
        spinner.setPrompt("Odaberi korisnika");
        updateSpinner(null);
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
                startActivityForResult(intent,REQUEST_CODE_EDIT);
            }
        });
        horizontalLayout.addView(editButton);
        verticalLayout.addView(horizontalLayout);

        this.setContentView(verticalLayout);
    }

    private void updateSpinner(String uuid) {
        if (uuid == null) {
            //fill uuids and userdatas lists
            for(Map.Entry<String, UserData> entry:userDataMap.entrySet()) {
                uuids.add(entry.getKey());
                userDatas.add(entry.getValue());
            };
        }
        else {
            //update uuids and userdatas lists
            int position = uuids.indexOf(uuid);
            if (position != -1) {
                //editing a user
                userDatas.set(position, userDataMap.get(uuid));
            } else {
                //adding a user
                uuids.add(uuid);
                userDatas.add(userDataMap.get(uuid));
            }
        }

        ArrayAdapter<UserData> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, userDatas);
        spinner.setAdapter(spinnerArrayAdapter);
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
                    userDataMap.put(uuid, userData);
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
                    String userUuid = extras.getString("userUuid");
                    //update stored user data
                    userDataMap.put(userUuid, userData);
                    updateSpinner(userUuid);
                }
            }
        }
    }
}
