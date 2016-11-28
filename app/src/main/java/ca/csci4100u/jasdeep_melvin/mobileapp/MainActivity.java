package ca.csci4100u.jasdeep_melvin.mobileapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button go_to_canvas_button;
    Button log_out_button;
    Button get_contacts;
    ListView list;
    LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        go_to_canvas_button = (Button)findViewById(R.id.open_canvas_button);
        go_to_canvas_button.setOnClickListener(MainActivity.this);

        log_out_button = (Button)findViewById(R.id.logout_button);
        log_out_button.setOnClickListener(MainActivity.this);

        get_contacts = (Button)findViewById(R.id.get_contact_button);
        get_contacts.setOnClickListener(MainActivity.this);

        ll = (LinearLayout) findViewById(R.id.landing_page_layout);
        list = (ListView) findViewById(R.id.contact_list_view);

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        this.startActivity(intent);
    }

    @Override
    public void onClick(android.view.View target){
        if(target.equals(go_to_canvas_button)) {
            Intent i = new Intent(target.getContext(), Draw.class);
            target.getContext().startActivity(i);
        } else if (target.equals(log_out_button)){
            this.finish();
            return;
        } else if (target.equals(get_contacts)) {
            LoadContactsAyscn lca = new LoadContactsAyscn();
            lca.execute();
        }
    }

    class LoadContactsAyscn extends AsyncTask<Void, Void, ArrayList<String>> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            pd = ProgressDialog.show(MainActivity.this, "Loading Contacts",
                    "Please Wait");
        }

        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            // TODO Auto-generated method stub
            ArrayList<String> contacts = new ArrayList<String>();

            Cursor c = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    null, null, null);
            while (c.moveToNext()) {

                String contactName = c
                        .getString(c
                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phNumber = c
                        .getString(c
                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                contacts.add(contactName + ":" + phNumber);

            }
            c.close();

            return contacts;
        }

        @Override
        protected void onPostExecute(ArrayList<String> contacts) {
            // TODO Auto-generated method stub
            super.onPostExecute(contacts);

            pd.cancel();

            ll.removeView(get_contacts);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getApplicationContext(), R.layout.list_item, contacts);

            list.setAdapter(adapter);

        }

    }
}



