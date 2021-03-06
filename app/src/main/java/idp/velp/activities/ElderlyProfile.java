package idp.velp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import idp.velp.R;
import idp.velp.util.CircleTransform;

public class ElderlyProfile extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private boolean task4;
    private boolean volunteer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elderly_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tan Siew Mei");

        //retrieve shared preferences
        sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);

        task4 = sharedPreferences.getBoolean("task4", false);
        volunteer = sharedPreferences.getBoolean("volunteer", false);

        ImageView elderlyPic = (ImageView) findViewById(R.id.elderly_pic);
        Picasso.with(this).load(R.drawable.elderly).transform(new CircleTransform()).into(elderlyPic);

        TextView elderlyNRIC = (TextView) findViewById(R.id.elderly_nric);
        TextView elderlyNRICLabel = (TextView) findViewById(R.id.elderly_nric_label);
        TextView elderlyDOB = (TextView) findViewById(R.id.elderly_dob);
        TextView elderlyDOBLabel = (TextView) findViewById(R.id.elderly_dob_label);
        View elderlyDOBDivider = findViewById(R.id.elderly_dob_divider);

        if (volunteer && task4) {
            elderlyNRIC.setText("77 years old");
            elderlyNRICLabel.setText("Age");

            elderlyDOB.setVisibility(View.GONE);
            elderlyDOBLabel.setVisibility(View.GONE);
            elderlyDOBDivider.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        MenuItem menuItem = menu.findItem(R.id.action_profile);
        menuItem.setVisible(false);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
