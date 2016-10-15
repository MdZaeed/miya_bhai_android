package rad.iit.com.baya.activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
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
import rad.iit.com.baya.datamodels.IFormValidation;
import rad.iit.com.baya.datamodels.User;
import rad.iit.com.baya.utils.CustomTime;
import rad.iit.com.baya.utils.CustomToast;

public class SignUpActivity extends TemplateActivity implements View.OnClickListener, IFormValidation{

    EditText userNameEditText;
    EditText mobileEditText;
    TextView bdayEditText;
    TextView loginButton;
    Calendar birthdayCalendar = Calendar.getInstance();

    RadioGroup radioSexGroup;
    RadioButton radioSexButton;

    Button signUpButton;

    User candidateUser;

    @Override
    public void initView() {
        setContentView(R.layout.activity_sign_up);

        setTitle(getResources().getString(R.string.app_name));
        userNameEditText = (EditText) findViewById(R.id.et_user_name);
        mobileEditText = (EditText) findViewById(R.id.et_mobile);
        bdayEditText = (TextView) findViewById(R.id.et_bday);
        loginButton=(TextView) findViewById(R.id.tv_login);

        radioSexGroup = (RadioGroup) findViewById(R.id.rg_sex);
        radioSexButton = (RadioButton) findViewById(R.id.rb_male);
        radioSexButton.setChecked(true);
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
                if (isAllInputFieldNotNull()){
                    getAllInputFieldData();
                    CustomToast toast = new CustomToast(SignUpActivity.this);
                    toast.showLongToast(candidateUser.toString());
                    addUser(candidateUser);
                }
            }
        });
        bdayEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(SignUpActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        birthdayCalendar.set(Calendar.YEAR, year);
                        birthdayCalendar.set(Calendar.MONTH, monthOfYear);
                        birthdayCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        bdayEditText.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                    }
                }, birthdayCalendar.get(Calendar.YEAR), birthdayCalendar.get(Calendar.MONTH), birthdayCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        radioSexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioSexButton = (RadioButton) findViewById(checkedId);
                Log.d("Sex",radioSexButton.getText().toString());
            }
        });
        loginButton.setOnClickListener(this);
    }
    public void addUser(final User user) {
        final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        final CustomToast customToast = new CustomToast(SignUpActivity.this);
        JsonObjectRequest addUserRequest = addUserRequest = new JsonObjectRequest(Request.Method.POST, ApplicationConstants.ADD_USER_URL, user.getSignUpJSON(),new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.d("Res",jsonObject.toString());
                Log.d("Res", jsonObject.toString());
                customToast.showLongToast(jsonObject.toString());

                goToActivity(new LoginActivity());
            }

        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.d("Err", volleyError.toString());
                customToast.showLongToast(volleyError.toString());
            }
        }) ;
        Volley.newRequestQueue(SignUpActivity.this).add(addUserRequest);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tv_login:

                /*Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
                */
                // testing
                Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
    @Override
    public boolean isAllInputFieldNotNull() {
        if (userNameEditText.getText().toString().equals("")){
            customToast.showLongToast("Please Enter User Name");
            userNameEditText.setFocusable(true);
            return false;
        }
        else if(bdayEditText.getText().toString().equals("")){
            customToast.showLongToast("Please Enter Birthday");
            bdayEditText.setFocusable(true);
            return false;
        }
        else if(mobileEditText.getText().toString().equals("")){
            customToast.showLongToast("Please Enter Mobile Number");
            mobileEditText.setFocusable(true);
            return false;
        }
        return true;
    }

    @Override
    public void getAllInputFieldData() {
        candidateUser = new User();
        candidateUser.userName = userNameEditText.getText().toString();
        candidateUser.bday = CustomTime.toStandardFormat(birthdayCalendar);
        candidateUser.mobileNumber = mobileEditText.getText().toString();
        candidateUser.sex = radioSexButton.getText().toString();
    }
}
