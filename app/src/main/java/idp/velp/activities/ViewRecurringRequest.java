package idp.velp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
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
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import idp.velp.R;
import idp.velp.util.CircleTransform;

public class ViewRecurringRequest extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private TextView requestDate;
    private TextView requestTime;

    private boolean task9;
    private boolean task10;
    private boolean task11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recurring_request);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("View Request");

        //retrieve shared preferences
        sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);

        task9 = sharedPreferences.getBoolean("task9", false);
        task10 = sharedPreferences.getBoolean("task10", false);
        task11 = sharedPreferences.getBoolean("task11", false);

        requestDate = (TextView) findViewById(R.id.request_date);

        ImageView elderlyPic = (ImageView) findViewById(R.id.elderly_pic);
        Picasso.with(this).load(R.drawable.elderly).transform(new CircleTransform()).into(elderlyPic);

        TextView task9EmptyState = (TextView) findViewById(R.id.task9_empty_state);
        LinearLayout task9SuccessState = (LinearLayout) findViewById(R.id.task9_success_state);
        CardView task9SuccessStateBtn = (CardView) findViewById(R.id.task9_success_state_btn);
        TextView requestInfoTitle = (TextView) findViewById(R.id.request_info_title);

        ImageView volunteerPic = (ImageView) findViewById(R.id.volunteer_pic);
        ImageView volunteerFriendPic = (ImageView) findViewById(R.id.volunteer_friend_pic);

        requestTime = (TextView) findViewById(R.id.request_time);

        Intent intent = getIntent();
        boolean fromEdit = intent.getBooleanExtra("fromEdit", false);

        if (task9) {
            task9EmptyState.setVisibility(View.GONE);
            task9SuccessState.setVisibility(View.VISIBLE);
            task9SuccessStateBtn.setVisibility(View.VISIBLE);
            Picasso.with(this).load(R.drawable.volunteer).transform(new CircleTransform()).into(volunteerPic);
            Picasso.with(this).load(R.drawable.volunteer_friend).transform(new CircleTransform()).into(volunteerFriendPic);
        }
        if (task10) {
            requestInfoTitle.setText("Approved Volunteers");
            task9SuccessStateBtn.setVisibility(View.GONE);
        }
        if (task11 && fromEdit) {
            requestDate.setText("28 Nov 2016");
            requestTime.setText("1:00pm - 3:00pm");
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

    public void goToFriendProfile(View view) {
        Intent intent = new Intent(this, FriendProfile.class);
        startActivity(intent);
    }

    public void editRequest(View view) {
        Intent intent = new Intent(this, EditRequest.class);
        startActivity(intent);
    }

    public void approve(View view) {
        String[] options = {"Jerry Yan", "Jesper Tan"};

        new MaterialDialog.Builder(this)
                .title("Approve Volunteers (0/2)")
                .items(options)
                .itemsCallbackMultiChoice(new Integer[]{}, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        boolean allowSelection = which.length <= 2; // limit selection to 1, the new selection is included in the which array
                        if (!allowSelection) {
                            Toast.makeText(ViewRecurringRequest.this, "Selection limit reached", Toast.LENGTH_SHORT).show();
                        } else {
                            if (which.length == 1) {
                                dialog.setTitle("Approve Volunteers (1/2)");
                            } else if (which.length == 2) {
                                dialog.setTitle("Approve Volunteers (2/2)");
                            } else {
                                dialog.setTitle("Approve Volunteers (0/2)");
                            }
                        }

                        return allowSelection;
                    }
                })
                .positiveText("Submit")
                .negativeText("Cancel")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        new MaterialDialog.Builder(ViewRecurringRequest.this)
                                .title("Approve Volunteers")
                                .content("Are you sure you want to proceed?")
                                .positiveText("Yes")
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putBoolean("task10", true);
                                        editor.commit();
                                        Intent intent = new Intent(ViewRecurringRequest.this, Home.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                                .negativeText("Cancel")
                                .show();
                    }
                })
                .alwaysCallMultiChoiceCallback() // the callback will always be called, to check if selection is still allowed
                .show();

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
                                if (task11 && text.equals("28 Nov 2016")) {
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

    @Override
    public void onBackPressed() {
        if (task11) {
            Intent intent2 = new Intent(this, Home.class);
            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent2);
            finish();
        } else {
            super.onBackPressed();
        }
    }
}
