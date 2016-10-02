package rad.iit.com.baya.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import rad.iit.com.baya.R;
import rad.iit.com.baya.activities.template.TemplateActivity;
import rad.iit.com.baya.data.constants.ApplicationConstants;
import rad.iit.com.baya.datamodels.User;
import rad.iit.com.baya.utils.CustomToast;

public class SignUpActivity extends TemplateActivity implements View.OnClickListener {

    EditText userNameEditText;
    EditText passwordEditText;
    TextView loginButton;

    Button signUpButton;
    String userName, password;

    @Override
    public void initView() {
        setContentView(R.layout.activity_sign_up);

        userNameEditText = (EditText) findViewById(R.id.et_user_name);
        passwordEditText = (EditText) findViewById(R.id.et_password);
        loginButton=(TextView) findViewById(R.id.tv_login);

        signUpButton = (Button) findViewById(R.id.btn_sign_up);

        signUpButton.setOnClickListener(this);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void initializeViewByData() {

    }

    @Override
    public void listenView() {

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final User user = new User();
                user.userName = userNameEditText.getText().toString();
                user.password = passwordEditText.getText().toString();
                addUser(user);
            }
        });

        passwordEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                new DatePickerFragment().show(getSupportFragmentManager(), "Birthday");
                return true;
            }
        });
        loginButton.setOnClickListener(this);
    }

    public void addUser(final User user) {
        final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        final CustomToast customToast = new CustomToast(SignUpActivity.this);
        StringRequest addUserRequest = new StringRequest(Request.Method.POST, ApplicationConstants.ADD_USER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("Res", jsonObject.toString());
                    customToast.showLongToast(jsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.d("Err", volleyError.toString());
                customToast.showLongToast(volleyError.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(ApplicationConstants.USER_MODEL, user.toString());
                return params;
            }
        };
        Volley.newRequestQueue(SignUpActivity.this).add(addUserRequest);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tv_login:
                Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
        }
    }
}
