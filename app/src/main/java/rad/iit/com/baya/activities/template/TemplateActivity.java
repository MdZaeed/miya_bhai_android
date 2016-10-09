package rad.iit.com.baya.activities.template;

/**
 * Created by iit on 8/16/2016.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import java.util.Locale;

import rad.iit.com.baya.R;
import rad.iit.com.baya.utils.CustomToast;

public abstract class TemplateActivity extends AppCompatActivity {

    public static int language = 0;
    public static int languageChanged=0;

    protected TextView toolbarTitle;
    protected CustomToast customToast = new CustomToast(this);

    public abstract void initView();

    public abstract void loadData();

    public abstract void initializeViewByData();

    public abstract void listenView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (language == 0) {
            changeLanguage("BN");
        } else {
            changeLanguage("EN");
        }


        new ViewLoadingTask().execute();
    }

    public void changeLanguage(String language_code) {
        Resources res = this.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.locale = new Locale(language_code.toLowerCase());
        res.updateConfiguration(conf, dm);
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
            case R.id.btn_bangla:
                if(language!=0) {
                    language = 0;
                    restartActivity();
                }
                return true;
            case R.id.btn_english:
                if (language!=1) {
                    language = 1;
                    restartActivity();
                }
                return true;
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

    private void restartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Locale current = getResources().getConfiguration().locale;
        if(current.getDisplayLanguage().equals("bn") && language==1)
        {
            restartActivity();
        }else if(current.getDisplayLanguage().equals("en") && language==0)
        {
            restartActivity();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Locale current = getResources().getConfiguration().locale;
        if(current.getDisplayLanguage().equals("bn") && language==1)
        {
            restartActivity();
        }else if(current.getDisplayLanguage().equals("en") && language==0)
        {
            restartActivity();
        }
    }
}
