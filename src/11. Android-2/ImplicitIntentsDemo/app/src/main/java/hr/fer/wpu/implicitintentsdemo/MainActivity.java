package hr.fer.wpu.implicitintentsdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        Button buttonSearch = new Button(this);
        buttonSearch.setText("Search FER");
        buttonSearch.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.addView(buttonSearch);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //search manager app has to be installed, e.g. Google app
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, "FER");
                startActivity(intent);

                //only a web browser has to be installed
//                Uri uri = Uri.parse("http://www.google.com/#q=FER");
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                startActivity(intent);
            }
        });

        Button buttonShow = new Button(this);
        buttonShow.setText("Show Zagreb");
        buttonShow.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.addView(buttonShow);

        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:45.81,15.98"));
                startActivity(intent);
            }
        });

        Button buttonCall = new Button(this);
        buttonCall.setText("Call Dean");
        buttonCall.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.addView(buttonCall);

        buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:01/6129-642"));
                startActivity(intent);
            }
        });

        Button buttonOpen = new Button(this);
        buttonOpen.setText("Open PDF");
        buttonOpen.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.addView(buttonOpen);

        buttonOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse("content:///storage/emulated/0/Download/whitepaper.pdf"), "application/pdf");
                startActivity(intent);
            }
        });

        Button buttonSend = new Button(this);
        buttonSend.setText("Send email");
        buttonSend.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.addView(buttonSend);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_EMAIL, "kpripuzic@fer.hr");
                intent.putExtra(Intent.EXTRA_SUBJECT, "[wpu] test");
                startActivity(intent);
            }
        });

        setContentView(layout);


        Button buttonSMS = new Button(this);
        buttonSMS.setText("Send SMS");
        buttonSMS.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.addView(buttonSMS);

        buttonSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://contacts/people");
                Intent intent = new Intent(Intent.ACTION_PICK, Phone.CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });

        setContentView(layout);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            //a contact was picked
            Cursor cursor = null;
            String phoneNumber = "";

            try {
                String id = data.getData().getLastPathSegment();

                //get and parse the selected phone number from the content provider for contacts
                cursor = getContentResolver().query(Phone.CONTENT_URI, null, Phone._ID + " = " + id, null, null);
                if (cursor.moveToFirst()) {
                    phoneNumber = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
                    cursor.moveToNext();
                }

                cursor.close();

                //send sms to the selected phone number
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phoneNumber));
                intent.putExtra("sms_body", "some text to send");
                startActivity(intent);

            } catch (Exception e) {
                Toast.makeText(this, "No permission to access contacts", Toast.LENGTH_SHORT).show();
            }
        }
    }
}