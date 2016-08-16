package rad.iit.com.baya.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

public class SignUpActivity extends TemplateActivity {

    Button signUpButton;
    String userName, password;

    @Override
    public void initView() {
        setContentView(R.layout.activity_sign_up);
        signUpButton = (Button) findViewById(R.id.button_hit);
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
                user.userName ="Dipok";
                user.password ="iit123";
                final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
                progressDialog.setMessage("Loading");
                progressDialog.show();
                final CustomToast customToast = new CustomToast(SignUpActivity.this);
                StringRequest addUserRequest = new StringRequest(Request.Method.POST, ApplicationConstants.ADD_USER_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.d("Res",jsonObject.toString());
                            customToast.showLongToast(jsonObject.toString());
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
                        params.put(User.Variable.USER_NAME,"DIPOK12");
                        params.put(User.Variable.PASS_WORD,"iit1234");
                        return  params;

                    }
                };
                Volley.newRequestQueue(SignUpActivity.this).add(addUserRequest);
            }
        });
    }


    public void addUser(final User user){
        final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        final CustomToast customToast = new CustomToast(SignUpActivity.this);
        StringRequest addUserRequest = new StringRequest(Request.Method.POST, ApplicationConstants.ADD_USER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("Res",jsonObject.toString());
                    customToast.showLongToast(jsonObject.toString());
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
        Volley.newRequestQueue(SignUpActivity.this).add(addUserRequest);
    }
    public void loginUser(final User user){
        final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        final CustomToast customToast = new CustomToast(SignUpActivity.this);
        StringRequest loginRequest = new StringRequest(Request.Method.POST, ApplicationConstants.LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.has(ApplicationConstants.SUCCESS_KEY)){
                        if (jsonObject.has(ApplicationConstants.TOKEN_KEY) && jsonObject.has(ApplicationConstants.ID_KEY)){
                            saveTokenAndID((String)jsonObject.get(ApplicationConstants.TOKEN_KEY),(long)jsonObject.get(ApplicationConstants.ID_KEY));
                        }
                    }
                    Log.d("Res",jsonObject.toString());
                    customToast.showLongToast(jsonObject.toString());
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
        Volley.newRequestQueue(SignUpActivity.this).add(loginRequest);
    }

    /**
     * save token and id of a user
     * @param token
     * @param id
     */
    public void saveTokenAndID(String token, long id){
        SharedPreferences sharedPreferences = getSharedPreferences(ApplicationConstants.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ApplicationConstants.TOKEN_KEY,token);
        editor.putLong(ApplicationConstants.ID_KEY,id);
        editor.commit();
    }
}
