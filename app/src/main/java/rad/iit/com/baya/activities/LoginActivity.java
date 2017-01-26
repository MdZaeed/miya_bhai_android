package rad.iit.com.baya.activities;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import rad.iit.com.baya.R;
import rad.iit.com.baya.activities.chat.ChatActivity;
import rad.iit.com.baya.activities.template.TemplateActivity;
import rad.iit.com.baya.data.constants.ApplicationConstants;
import rad.iit.com.baya.datamodels.IFormValidation;
import rad.iit.com.baya.datamodels.User;
import rad.iit.com.baya.utils.CustomToast;

/**
 * Created by Zayed on 16-Aug-16.
 */
public class LoginActivity extends TemplateActivity implements View.OnClickListener, IFormValidation {

    User candidateUser;
    EditText userNameEditText;
    EditText mobileNumberText;
    TextView registerTextView;
    Button logInButton;

    @Override
    public void initView() {
        // checking logged in or not
        if (isLoggedIn()){
            Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
            startActivity(intent);
        }
        setContentView(R.layout.activity_login);
        setTitle(getResources().getString(R.string.app_name));
        userNameEditText = (EditText) findViewById(R.id.et_user_name);
        mobileNumberText = (EditText) findViewById(R.id.et_mobile);
        registerTextView=(TextView) findViewById(R.id.tv_sign_up_now);

        logInButton = (Button) findViewById(R.id.btn_login);

        logInButton.setOnClickListener(this);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void initializeViewByData() {

    }

    @Override
    public void listenView() {
        logInButton.setOnClickListener(this);
        registerTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if (isAllInputFieldNotNull()){
                    getAllInputFieldData();
                    loginUser(candidateUser);
                }
                break;

            case  R.id.tv_sign_up_now:
                Intent intent=new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void loginUser(final User user) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();
        final CustomToast customToast = new CustomToast(this);

        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, ApplicationConstants.LOGIN_URL, user.getSignInJSON(),new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.d("Res", jsonObject.toString());
                if (jsonObject.has(ApplicationConstants.SUCCESS_KEY)) {
                    if (jsonObject.has(ApplicationConstants.TOKEN_KEY) && jsonObject.has(ApplicationConstants.ID_KEY)) {
                        try {
                            saveTokenAndIDAndUser((String) jsonObject.get(ApplicationConstants.TOKEN_KEY), (String) jsonObject.get(ApplicationConstants.ID_KEY),user.userName);
                            goToActivity(new HomePageActivity());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else {
                    customToast.showLongToast("Sorry, Name or Password is not valid!");
                }
            }

        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
/*                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.d("Err", volleyError.toString());*/

                goToActivity(new HomePageActivity());
//                customToast.showLongToast(volleyError.toString());
            }
        }) ;

        Volley.newRequestQueue(this).add(loginRequest);
    }

    public void saveTokenAndIDAndUser(String token, String id,String user) {
        SharedPreferences sharedPreferences = getSharedPreferences(ApplicationConstants.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ApplicationConstants.TOKEN_KEY, token);
        editor.putString(ApplicationConstants.ID_KEY, id);
        editor.putString(ApplicationConstants.USER_KEY,user);
        ApplicationConstants.user = user;
        editor.apply();
    }


    @Override
    public boolean isAllInputFieldNotNull() {
        if (userNameEditText.getText().toString().equals("")){
            customToast.showLongToast("Please Enter User Name");
            userNameEditText.setFocusable(true);
            return false;
        }
        else if(mobileNumberText.getText().toString().equals("")){
            customToast.showLongToast("Please Enter Mobile Number");
            mobileNumberText.setFocusable(true);
            return false;
        }
        return true;
    }

    @Override
    public void getAllInputFieldData() {
        candidateUser = new User();
        candidateUser.userName = userNameEditText.getText().toString();
        candidateUser.mobileNumber = mobileNumberText.getText().toString();
    }

    /**
     * to check whether a user is logged in or not via token key
     * if token key is "" then return false
     * else return true
     * @return
     */
    private  boolean isLoggedIn(){
        SharedPreferences sharedPreferences = getSharedPreferences(ApplicationConstants.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(ApplicationConstants.TOKEN_KEY,"");
        if (!token.equals("")){
            return  true;
        }
        return false;
    }
}
