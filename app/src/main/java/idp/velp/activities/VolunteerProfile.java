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

import org.w3c.dom.Text;

import idp.velp.R;
import idp.velp.util.CircleTransform;

public class VolunteerProfile extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private boolean task4;
    private boolean task7;
    private boolean volunteer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Jerry Yan");

        //retrieve shared preferences
        sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);

        task4 = sharedPreferences.getBoolean("task4", false);
        task7 = sharedPreferences.getBoolean("task7", false);
        volunteer = sharedPreferences.getBoolean("volunteer", false);

        ImageView volunteerPic = (ImageView) findViewById(R.id.volunteer_pic);
        Picasso.with(this).load(R.drawable.volunteer).transform(new CircleTransform()).into(volunteerPic);

        TextView volunteerHeader = (TextView) findViewById(R.id.volunteer_header);
        TextView volunteerNRIC = (TextView) findViewById(R.id.volunteer_nric);
        TextView volunteerNRICLabel = (TextView) findViewById(R.id.volunteer_nric_label);
        TextView volunteerDOB = (TextView) findViewById(R.id.volunteer_dob);
        TextView volunteerDOBLabel = (TextView) findViewById(R.id.volunteer_dob_label);
        TextView volunteerHours = (TextView) findViewById(R.id.volunteer_hours);
        View volunteerDOBDivider = findViewById(R.id.volunteer_dob_divider);

        if (!volunteer && task4) {
            volunteerHeader.setText("Basic Information");
            volunteerNRIC.setText("22 years old");
            volunteerNRICLabel.setText("Age");

            volunteerDOB.setVisibility(View.GONE);
            volunteerDOBLabel.setVisibility(View.GONE);
            volunteerDOBDivider.setVisibility(View.GONE);
        }
        if (task7) {
            volunteerHours.setText("17.5");
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
