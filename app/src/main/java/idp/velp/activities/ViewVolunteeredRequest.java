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
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;

import idp.velp.R;
import idp.velp.util.CircleTransform;

public class ViewVolunteeredRequest extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private boolean task6a;
    private boolean task6b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_volunteered_request);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("View Request");

        //retrieve shared preferences
        sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);

        task6a = sharedPreferences.getBoolean("task6a", false);
        task6b = sharedPreferences.getBoolean("task6b", false);

        Button toggleBtn = (Button) findViewById(R.id.toggle_btn);
        if (task6a) {
            toggleBtn.setText("End Request");
        }


        ImageView elderlyPic = (ImageView) findViewById(R.id.elderly_pic);
        Picasso.with(this).load(R.drawable.elderly).transform(new CircleTransform()).into(elderlyPic);
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

    public void goToElderlyProfile(View view) {
        Intent intent = new Intent(this, ElderlyProfile.class);
        startActivity(intent);
    }

    public void toggleStatus(View view) {
        if (!task6a) {
            Toast.makeText(this, "Request started!", Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("task6a", true);
            editor.commit();
            Intent intent = new Intent(this, Home.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else {
            View customView = getLayoutInflater().inflate(R.layout.dialog_feedback, null);
            ImageView elderlyPic = (ImageView) customView.findViewById(R.id.elderly_pic);
            Picasso.with(this).load(R.drawable.elderly).transform(new CircleTransform()).into(elderlyPic);
            EditText dialogComments = (EditText) customView.findViewById(R.id.dialog_comments);
            dialogComments.setMaxLines(20);
            dialogComments.setHorizontallyScrolling(false);
            dialogComments.setImeOptions(EditorInfo.IME_ACTION_DONE);
            MaterialDialog dialog = new MaterialDialog.Builder(ViewVolunteeredRequest.this)
                    .customView(customView, true)
                    .title("Feedback")
                    .positiveText("Submit")
                    .negativeText("Cancel")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(MaterialDialog dialog, DialogAction which) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("task6b", true);
                            editor.commit();
                            Toast.makeText(ViewVolunteeredRequest.this, "Request ended", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ViewVolunteeredRequest.this, Home.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .show();
        }

    }
}
