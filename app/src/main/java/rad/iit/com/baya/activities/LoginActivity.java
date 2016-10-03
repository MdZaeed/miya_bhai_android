package rad.iit.com.baya.activities;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import rad.iit.com.baya.R;
import rad.iit.com.baya.activities.template.TemplateActivity;
import rad.iit.com.baya.data.constants.ApplicationConstants;
import rad.iit.com.baya.datamodels.User;
import rad.iit.com.baya.utils.CustomToast;

/**
 * Created by Zayed on 16-Aug-16.
 */
public class LoginActivity extends TemplateActivity implements View.OnClickListener {

    EditText userNameEditText;
    EditText passwordEditText;
    TextView registerTextView;
    Button logInButton;

    @Override
    public void initView() {

        setContentView(R.layout.activity_login);

        userNameEditText = (EditText) findViewById(R.id.et_user_name);
        passwordEditText = (EditText) findViewById(R.id.et_password);
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
                final User user = new User();
                user.userName = userNameEditText.getText().toString();
                user.mobileNumber = passwordEditText.getText().toString();
                loginUser(user);
                break;

            case  R.id.tv_sign_up_now:
                Intent intent=new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void loginUser(final User user) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        final CustomToast customToast = new CustomToast(this);
        StringRequest loginRequest = new StringRequest(Request.Method.POST, ApplicationConstants.LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.has(ApplicationConstants.SUCCESS_KEY)) {
                        if (jsonObject.has(ApplicationConstants.TOKEN_KEY) && jsonObject.has(ApplicationConstants.ID_KEY)) {
                            saveTokenAndID((String) jsonObject.get(ApplicationConstants.TOKEN_KEY), (long) jsonObject.get(ApplicationConstants.ID_KEY));
                        }
                    }
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
                customToast.showLongToast("Error in login");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(ApplicationConstants.USER_MODEL, user.toString());
                return params;

            }
        };
        Volley.newRequestQueue(this).add(loginRequest);
    }

    public void saveTokenAndID(String token, long id) {
        SharedPreferences sharedPreferences = getSharedPreferences(ApplicationConstants.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ApplicationConstants.TOKEN_KEY, token);
        editor.putLong(ApplicationConstants.ID_KEY, id);
        editor.commit();
    }
}
