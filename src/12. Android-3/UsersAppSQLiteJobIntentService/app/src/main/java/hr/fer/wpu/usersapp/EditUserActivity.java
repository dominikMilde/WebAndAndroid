package hr.fer.wpu.usersapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import hr.fer.wpu.usersapp.sqlite.UserData;

public class EditUserActivity extends Activity {

    private final String[] CITIES = {"Zagreb", "Osijek", "Rijeka", "Split", "Dubrovnik", "Zadar", "Šibenik", "Pula"};
    private String uuid;
    private EditText etFirstName;
    private EditText etLastName;
    private CheckBox cbEmail;
    private RadioButton rbWeekly;
    private RadioButton rbDaily;
    private RadioButton rbMonthly;
    private EditText etStreet;
    private Spinner spinner;
    private EditText etPostalCode;
    private RadioGroup rgEmails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView sv = new ScrollView(this);//to be sure that it can be displayed on small screens
        LinearLayout llMain = new LinearLayout(this);
        llMain.setOrientation(LinearLayout.VERTICAL);
        sv.addView(llMain);

        // row 0
        etFirstName = new EditText(this);
        etFirstName.setHint("Upišite ime");
        llMain.addView(etFirstName);

        // row 1
        etLastName = new EditText(this);
        etLastName.setHint("Upišite prezime");
        llMain.addView(etLastName);

        // row 2
        cbEmail = new CheckBox(this);
        cbEmail.setText("Želite li primati mailove?");
        cbEmail.setChecked(true);
        llMain.addView(cbEmail);

        // row 3
        LinearLayout emailsLayout = new LinearLayout(this);
        llMain.addView(emailsLayout);

        TextView twEmails = new TextView(this);
        twEmails.setText("Kako želite primati e-mailove?");
        twEmails.setGravity(Gravity.CENTER_VERTICAL);
        twEmails.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        emailsLayout.addView(twEmails);

        rgEmails = new RadioGroup(this);
        rgEmails.setOrientation(RadioGroup.VERTICAL);
        rbWeekly = new RadioButton(this);
        rbWeekly.setText("Tjedno");
        rgEmails.addView(rbWeekly);
        rbDaily = new RadioButton(this);
        rbDaily.setText("Dnevno");
        rgEmails.addView(rbDaily);
        rbMonthly = new RadioButton(this);
        rbMonthly.setText("Mjesečno");
        rgEmails.addView(rbMonthly);
        emailsLayout.addView(rgEmails);

        // row 4
        etStreet = new EditText(this);
        etStreet.setHint("Upišite ulicu i broj");
        llMain.addView(etStreet);

        // row 5
        LinearLayout llCity = new LinearLayout(this);
        llMain.addView(llCity);

        TextView twCity = new TextView(this);
        twCity.setText("Odaberite grad");
        llCity.addView(twCity);

        List<String> cities = Arrays.asList(CITIES);
        spinner = new Spinner(this);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cities);
        spinner.setAdapter(spinnerArrayAdapter);
        llCity.addView(spinner);

        // row 6
        etPostalCode = new EditText(this);
        etPostalCode.setInputType(InputType.TYPE_CLASS_NUMBER); //only numbers
        etPostalCode.setHint("Upišite poštanski broj");
        llMain.addView(etPostalCode);

        // row 7
        LinearLayout llButtons = new LinearLayout(this);
        llButtons.setGravity(Gravity.CENTER_HORIZONTAL);
        llMain.addView(llButtons);

        Button bOK = new Button(this);
        bOK.setText("U redu");
        bOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserData userData = getUserData();

                Intent data = new Intent();
                //add uuid for new users
                if (userData.getUuid() == null) {
                    userData.setUuid(UUID.randomUUID().toString());
                }
                data.putExtra("userData", userData);
                setResult(Activity.RESULT_OK, data);
                finish();
            }
        });
        llButtons.addView(bOK);

        Button bCancel = new Button(this);
        bCancel.setText("Odustani");
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
        llButtons.addView(bCancel);

        //get extras
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            UserData userData = (UserData) extras.getSerializable("userData");
            setUserData(userData);
        }

        this.setContentView(sv);
    }

    public UserData getUserData() {
        UserData data = new UserData();
        data.setFirstName(etFirstName.getText().toString());
        data.setLastName(etLastName.getText().toString());
        data.setReceiveEmails(cbEmail.isChecked());
        if (rbDaily.isChecked()) {
            data.setEmailType("DAILY");
        } else if (rbMonthly.isChecked()) {
            data.setEmailType("MONTHLY");
        } else {
            data.setEmailType("WEEKLY");
        }
        data.setStreet(etStreet.getText().toString());
        if (etPostalCode.getText().length() == 0) {
            data.setPostalCode(0);
        } else {
            data.setPostalCode((Integer.parseInt(etPostalCode.getText().toString())));
        }
        data.setCity((String) spinner.getSelectedItem());
        data.setUuid(this.uuid);
        return data;
    }

    public void setUserData(UserData userData) {
        etFirstName.setText(userData.getFirstName());
        etLastName.setText(userData.getLastName());
        cbEmail.setChecked(userData.isReceiveEmails());
        if (userData.getEmailType() != null) {
            switch (userData.getEmailType()) {
                case "DAILY":
                    rbDaily.setChecked(true);
                    break;
                case "WEEKLY":
                    rbWeekly.setChecked(true);
                    break;
                case "MONTHLY":
                    rbMonthly.setChecked(true);
                    break;
                default:
                    throw new RuntimeException(
                            "UserData.EmailType is not recognized: type="
                                    + userData.getEmailType());
            }
        } else {
            rgEmails.clearCheck();
        }

        etStreet.setText(userData.getStreet());
        etPostalCode.setText(Integer.toString(userData.getPostalCode()));
        spinner.setSelection(Arrays.asList(CITIES).indexOf(userData.getCity()));
        this.uuid = userData.getUuid();
    }
}
