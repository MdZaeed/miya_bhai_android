package rad.iit.com.baya.activities;

import android.app.ProgressDialog;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import rad.iit.com.baya.R;
import rad.iit.com.baya.activities.template.TemplateComposeQuestionActivity;
import rad.iit.com.baya.data.constants.ApplicationConstants;
import rad.iit.com.baya.datamodels.AddChallengeBody;
import rad.iit.com.baya.datamodels.ChallengeAddResponse;
import rad.iit.com.baya.datamodels.Postchallenge;
import rad.iit.com.baya.utils.CustomToast;

/**
 * Created by Zayed on 13-Oct-16.
 */
public class ChallengeMamuComposeChallengeActivity extends TemplateComposeQuestionActivity {

    LinearLayout topicHeaderLinearLayout;
    @Override
    public void initializeTopicList() {
        topicHeaderLinearLayout=(LinearLayout) findViewById(R.id.topic_header);
        topicHeaderLinearLayout.setVisibility(View.GONE);
    }

    @Override
    public void setToolbarText() {
        toolbarTitle.setText(R.string.add_a_challenge);
    }

    @Override
    protected void sendMessage() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();
        final CustomToast customToast = new CustomToast(this);

        AddChallengeBody addChallengeBody=new AddChallengeBody();
        addChallengeBody.setPostchallenge(new Postchallenge());
        addChallengeBody.getPostchallenge().setChallenger(getSavedOwnId());
        addChallengeBody.getPostchallenge().setQuestion(messageBodyEditText.getText().toString());
        JSONObject tempJsonObject=null;
        try {
            tempJsonObject=new JSONObject(new Gson().toJson(addChallengeBody));
            Log.i("json",new Gson().toJson(addChallengeBody));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest addChallengeRequest = new JsonObjectRequest(Request.Method.POST, ApplicationConstants.ADD_CHALLENGE_URL, tempJsonObject,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    progressDialog.hide();
                    ChallengeAddResponse challengeAddResponse = new Gson().fromJson(jsonObject.toString(), ChallengeAddResponse.class);
                    Toast.makeText(ChallengeMamuComposeChallengeActivity.this, R.string.challenge_add_success_message,Toast.LENGTH_LONG).show();
                    onBackPressed();
                }catch (Exception e)
                {
                    try {
                        String errorMessage=jsonObject.getString("error");
/*
                        Toast.makeText(ChallengeMamuComposeChallengeActivity.this,errorMessage,Toast.LENGTH_LONG).show();
*/
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
            }

        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
/*
                Log.d("Err", volleyError.toString());
*/
/*
                customToast.showLongToast(volleyError.toString());
*/
            }
        }) ;


        Volley.newRequestQueue(this).add(addChallengeRequest);
    }
}
