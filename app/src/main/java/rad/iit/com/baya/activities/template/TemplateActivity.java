package rad.iit.com.baya.activities.template;

/**
 * Created by iit on 8/16/2016.
 */
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import rad.iit.com.baya.R;
import rad.iit.com.baya.utils.CustomToast;

public abstract class TemplateActivity extends AppCompatActivity {

    protected CustomToast customToast = new CustomToast(this);

    public abstract void initView();
    public abstract void loadData();
    public abstract void initializeViewByData();
    public abstract void listenView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ViewLoadingTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


    class ViewLoadingTask extends AsyncTask<Void,Void,Void> {
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
            if (progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            initializeViewByData();
            listenView();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
