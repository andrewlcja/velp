package idp.velp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;

import idp.velp.R;

public class ViewSearchRecurringRequest extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private TextView requestDate;
    private boolean task9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_search_recurring_request);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("View Request");

        //retrieve shared preferences
        sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);

        task9 = sharedPreferences.getBoolean("task9", false);

        requestDate = (TextView) findViewById(R.id.request_date);
        CardView volunteerBtn = (CardView) findViewById(R.id.volunteer_btn);

        if (task9) {
            volunteerBtn.setVisibility(View.GONE);
        }

    }


    public void volunteer(View view) {
        final String[] options = {"Jesper Tan", "Vic Chou", "Vanness Wu"};
        String[] dateOptions = {"28 Oct 2016", "28 Nov 2016", "28 Dec 2016"};

        new MaterialDialog.Builder(this)
                .title("Select Dates")
                .items(dateOptions)
                .positiveText("Next")
                .itemsCallbackMultiChoice(new Integer[]{}, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        return true;
                    }}
                )
                .negativeText("Cancel")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        new MaterialDialog.Builder(ViewSearchRecurringRequest.this)
                                .title("Send Invitation (0/1)")
                                .items(options)
                                .itemsCallbackMultiChoice(new Integer[]{}, new MaterialDialog.ListCallbackMultiChoice() {
                                    @Override
                                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                                        boolean allowSelection = which.length <= 1; // limit selection to 1, the new selection is included in the which array
                                        if (!allowSelection) {
                                            Toast.makeText(ViewSearchRecurringRequest.this, "Selection limit reached", Toast.LENGTH_SHORT).show();
                                        } else {
                                            if (which.length == 1) {
                                                dialog.setTitle("Send Invitation (1/1)");
                                            } else {
                                                dialog.setTitle("Send Invitation (0/1)");
                                            }
                                        }

                                        return allowSelection;
                                    }
                                })
                                .positiveText("Submit")
                                .negativeText("Cancel")
                                .neutralText("Skip")
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        new MaterialDialog.Builder(ViewSearchRecurringRequest.this)
                                                .title("Volunteer for request")
                                                .content("Are you sure you want to proceed?")
                                                .positiveText("Yes")
                                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                                    @Override
                                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                                        editor.putBoolean("task9", true);
                                                        editor.commit();
                                                        Intent intent = new Intent(ViewSearchRecurringRequest.this, Home.class);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                })
                                                .negativeText("Cancel")
                                                .show();
                                    }
                                })
                                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        new MaterialDialog.Builder(ViewSearchRecurringRequest.this)
                                                .title("Volunteer for request")
                                                .content("Are you sure you want to proceed?")
                                                .positiveText("Yes")
                                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                                    @Override
                                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                                        editor.putBoolean("task9", true);
                                                        editor.commit();
                                                        Intent intent = new Intent(ViewSearchRecurringRequest.this, Home.class);
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
                })
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
