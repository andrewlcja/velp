package idp.velp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.afollestad.materialdialogs.MaterialDialog;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import idp.velp.R;

public class Search extends AppCompatActivity {
    private SlidingUpPanelLayout searchSlidingLayout;
    private TextView searchFilterText;
    private ImageView searchFilterIcon;

    private TextView searchStartDate;
    private TextView searchEndDate;
    private TextView searchStartTime;
    private TextView searchEndTime;
    private TextView searchLocation;

    private CardView task3SuccessState;
    private LinearLayout task3EmptyState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Search");

        searchSlidingLayout = (SlidingUpPanelLayout) findViewById(R.id.search_sliding_layout);
        searchFilterText = (TextView) findViewById(R.id.search_filter_text);
        searchFilterIcon = (ImageView) findViewById(R.id.search_filter_icon);

        searchStartDate = (TextView) findViewById(R.id.search_start_date);
        searchEndDate = (TextView) findViewById(R.id.search_end_date);
        searchStartTime = (TextView) findViewById(R.id.search_start_time);
        searchEndTime = (TextView) findViewById(R.id.search_end_time);
        searchLocation = (TextView) findViewById(R.id.search_location);

        task3SuccessState = (CardView) findViewById(R.id.task3_success_state);
        task3EmptyState = (LinearLayout) findViewById(R.id.task3_empty_state);

        searchSlidingLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                if (slideOffset > 0) {
                    searchFilterText.setText("Hide search filters");
                    searchFilterIcon.animate().rotation(180).start();
                } else {
                    searchFilterText.setText("Show search filters");
                    searchFilterIcon.animate().rotation(0).start();

                    if (searchStartDate.getText().toString().equals("24 Oct 2016") && searchEndDate.getText().toString().equals("31 Oct 2016") && searchLocation.getText().toString().equals("Harbourfront")) {
                        task3EmptyState.setVisibility(View.GONE);
                        task3SuccessState.setVisibility(View.VISIBLE);
                    } else {
                        task3SuccessState.setVisibility(View.GONE);
                        task3EmptyState.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {

            }
        });

        searchSlidingLayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchSlidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });


    }

    public void showDatePicker(View view) {
        final View customView = view;
        Calendar cal = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog();
        datePickerDialog.setMinDate(cal);
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                //date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;

                //set date
                switch (customView.getId()) {
                    case R.id.search_start_date_layout:
                        searchStartDate.setText(dayOfMonth + " " + new DateFormatSymbols().getShortMonths()[monthOfYear] + " " + year);
                        break;
                    case R.id.search_end_date_layout:
                        searchEndDate.setText(dayOfMonth + " " + new DateFormatSymbols().getShortMonths()[monthOfYear] + " " + year);
                        break;
                }
            }
        });
        datePickerDialog.show(getFragmentManager(), "");
    }

    public void showTimePicker(View view) {
        final View customView = view;
        TimePickerDialog startTimePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                try {
                    //startTime = hourOfDay + ":" + minute;

                    //date formats
                    SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("h:mma");
                    Date time = sdf.parse(hourOfDay + ":" + minute);

                    switch (customView.getId()) {
                        case R.id.search_start_time_layout:
                            searchStartTime.setText(dateFormat.format(time).replace(".", ""));
                            break;
                        case R.id.search_end_time_layout:
                            searchEndTime.setText(dateFormat.format(time).replace(".", ""));
                            break;
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, 7, 0, false);

        //min start time is 7am, max start time is 11pm
        startTimePickerDialog.setMinTime(7, 0, 0);
        startTimePickerDialog.setMaxTime(23, 0, 0);

        //only allow interval of 30 mins
        startTimePickerDialog.setTimeInterval(1, 30);
        startTimePickerDialog.show(getFragmentManager(), "");
    }

    public void showLocationOptions(View view) {
        String[] options = {"Ang Mo Kio", "Bukit Batok", "Bukit Panjang", "Choa Chu Kang", "Clementi", "Harbourfront", "Kallang", "Pasir Ris", "Punggol", "Sengkang", "Serangoon", "Tampines", "Toa Payoh"};

        new MaterialDialog.Builder(this)
                .items(options)
                .widgetColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        searchLocation.setText(text.toString());
                    }
                })
                .show();
    }

    public void goToViewSearchRequest(View view) {
        Intent intent = new Intent(this, ViewSearchRequest.class);
        startActivity(intent);
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
                Intent intent3 = new Intent(this, Login.class);
                intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent3);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
