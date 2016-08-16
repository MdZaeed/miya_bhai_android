package rad.iit.com.baya.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    Button logInButton;

    @Override
    public void initView() {

    }

    @Override
    public void loadData() {

    }

    @Override
    public void initializeViewByData() {

    }

    @Override
    public void listenView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userNameEditText=(EditText) findViewById(R.id.et_user_name);
        passwordEditText=(EditText) findViewById(R.id.et_password);
        logInButton=(Button) findViewById(R.id.btn_login);

        logInButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_login:
                final User user = new User();
                user.userName = userNameEditText.getText().toString();
                user.password = passwordEditText.getText().toString();
                loginUser(user);
                break;
        }
    }

    public void loginUser(final User user){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        final CustomToast customToast = new CustomToast(this);
        StringRequest loginRequest = new StringRequest(Request.Method.POST, ApplicationConstants.LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("Res",jsonObject.toString());
                    customToast.showLongToast("Login Successful");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                Log.d("Err",volleyError.toString());
                customToast.showLongToast(volleyError.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put(User.Variable.USER_NAME,user.userName);
                params.put(User.Variable.PASS_WORD,user.password);
                return  params;

            }
        };
        Volley.newRequestQueue(this).add(loginRequest);
    }
}
