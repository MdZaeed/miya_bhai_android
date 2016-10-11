package rad.iit.com.baya.activities.template;

/**
 * Created by iit on 8/16/2016.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import rad.iit.com.baya.R;
import rad.iit.com.baya.activities.SettingsActivity;
import rad.iit.com.baya.data.constants.ApplicationConstants;
import rad.iit.com.baya.utils.CustomToast;

public abstract class TemplateActivity extends AppCompatActivity {

    protected TextView toolbarTitle;
    protected CustomToast customToast = new CustomToast(this);

    public abstract void initView();

    public abstract void loadData();

    public abstract void initializeViewByData();

    public abstract void listenView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getLanguage().equals(getResources().getString(R.string.bangla_string)))
        {
            setLanguageInApp(getResources().getString(R.string.bangla_string));
        } else if(getLanguage().equals(getResources().getString(R.string.english_string)))
        {
            setLanguageInApp(getResources().getString(R.string.english_string));
        }

        new ViewLoadingTask().execute();
    }

    public void saveLanguageData(String token) {
        SharedPreferences sharedPreferences = getSharedPreferences(ApplicationConstants.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ApplicationConstants.LANGUAGE, token);
        editor.apply();
    }

    public void setLanguageInApp(String language_code) {
        Resources res = this.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.locale = new Locale(language_code.toLowerCase());
        res.updateConfiguration(conf, dm);
    }

    public String getLanguage() {
        SharedPreferences sharedPreferences = getSharedPreferences(ApplicationConstants.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(ApplicationConstants.LANGUAGE,getString(R.string.bangla_string));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.general_menu, menu);

        return true;
    }


    class ViewLoadingTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog = new ProgressDialog(TemplateActivity.this);

        @Override
        protected void onPreExecute() {
            initView();
            progressDialog.setMessage("Loading");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            loadData();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            initializeViewByData();
            listenView();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.btn_settings:
                goToActivity(new SettingsActivity());
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void goToActivity(Activity activity) {
        Intent intent = new Intent(this, activity.getClass());
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        restartActivity();
    }

    private void restartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}
