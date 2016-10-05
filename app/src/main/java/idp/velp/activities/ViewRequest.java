package idp.velp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import idp.velp.R;
import idp.velp.util.CircleTransform;

public class ViewRequest extends AppCompatActivity {
    private SharedPreferences sharedPreferences;

    private boolean task4;
    private boolean task5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("View Request");

        //retrieve shared preferences
        sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);

        ImageView elderlyPic = (ImageView) findViewById(R.id.elderly_pic);
        Picasso.with(this).load(R.drawable.elderly).transform(new CircleTransform()).into(elderlyPic);

        task4 = sharedPreferences.getBoolean("task4", false);
        task5 = sharedPreferences.getBoolean("task5", false);

        TextView task4EmptyState = (TextView) findViewById(R.id.task4_empty_state);
        LinearLayout task4SuccessState = (LinearLayout) findViewById(R.id.task4_success_state);
        CardView task4SuccessStateBtn = (CardView) findViewById(R.id.task4_success_state_btn);
        TextView requestInfoTitle = (TextView) findViewById(R.id.request_info_title);

        ImageView volunteerPic = (ImageView) findViewById(R.id.volunteer_pic);
        if (task4) {
            task4EmptyState.setVisibility(View.GONE);
            task4SuccessState.setVisibility(View.VISIBLE);
            task4SuccessStateBtn.setVisibility(View.VISIBLE);
            Picasso.with(this).load(R.drawable.volunteer).transform(new CircleTransform()).into(volunteerPic);
        }

        if (task5) {
            requestInfoTitle.setText("Approved Volunteers");
            task4SuccessStateBtn.setVisibility(View.GONE);
        }
    }

    public void goToElderlyProfile(View view) {
        Intent intent = new Intent(this, ElderlyProfile.class);
        startActivity(intent);
    }

    public void goToVolunteerProfile(View view) {
        Intent intent = new Intent(this, VolunteerProfile.class);
        startActivity(intent);
    }

    public void approve(View view) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("task5", true);
        editor.commit();

        Intent intent = new Intent(this, Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
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
