package idp.velp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;

import idp.velp.R;
import idp.velp.util.CircleTransform;

public class ViewVolunteeredRecurringRequest extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private TextView requestDate;
    private TextView requestTime;
    private boolean task11b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_volunteered_recurring_request);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("View Request");

        //retrieve shared preferences
        sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        task11b = sharedPreferences.getBoolean("task11b", false);

        requestDate = (TextView) findViewById(R.id.request_date);
        requestTime = (TextView) findViewById(R.id.request_time);

        ImageView elderlyPic = (ImageView) findViewById(R.id.elderly_pic);
        Picasso.with(this).load(R.drawable.elderly).transform(new CircleTransform()).into(elderlyPic);
    }

    public void goToElderlyProfile(View view) {
        Intent intent = new Intent(this, ElderlyProfile.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_repeat, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_repeat:
                String[] options = {"28 Oct 2016", "28 Nov 2016", "28 Dec 2016"};

                new MaterialDialog.Builder(this)
                        .items(options)
                        .title("Select date to view")
                        .widgetColor(ContextCompat.getColor(this, R.color.colorPrimary))
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                requestDate.setText(text.toString());
                                if (task11b && text.equals("28 Nov 2016")) {
                                    requestTime.setText("1:00pm - 3:00pm");
                                } else {
                                    requestTime.setText("2:00pm - 4:00pm");
                                }
                            }
                        })
                        .show();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_settings:
                return true;
            case R.id.action_logout:
                Intent intent2 = new Intent(this, Login.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent2);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
