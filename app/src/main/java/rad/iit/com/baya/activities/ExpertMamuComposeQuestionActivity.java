package rad.iit.com.baya.activities;

import android.app.ProgressDialog;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import rad.iit.com.baya.R;
import rad.iit.com.baya.activities.template.TemplateComposeQuestionActivity;
import rad.iit.com.baya.data.constants.ApplicationConstants;
import rad.iit.com.baya.datamodels.AddChallengeBody;
import rad.iit.com.baya.datamodels.CategoriesResponse;
import rad.iit.com.baya.datamodels.Category;
import rad.iit.com.baya.datamodels.ChallengeAddResponse;
import rad.iit.com.baya.datamodels.ExpertiseAnswer;
import rad.iit.com.baya.datamodels.Postchallenge;
import rad.iit.com.baya.datamodels.Question;
import rad.iit.com.baya.datamodels.QuestionPostBody;
import rad.iit.com.baya.utils.CustomToast;

/**
 * Created by Zayed on 14-Oct-16.
 */
public class ExpertMamuComposeQuestionActivity extends TemplateComposeQuestionActivity {
    @Override
    public void initializeTopicList() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        if(!progressDialog.isShowing()) {
            progressDialog.setMessage(getResources().getString(R.string.loading));
            progressDialog.show();
        }

        JsonObjectRequest getCategoriesRequest = new JsonObjectRequest(ApplicationConstants.CATEGORIES_GET_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                categoriesResponse=new Gson().fromJson(jsonObject.toString(), CategoriesResponse.class);

                for (Category topic :
                        categoriesResponse.getCategories()) {
                    topicStringList.add(handleBanglaString(topic.getTopic()));
                }

                progressDialog.hide();
                populateDropDown();

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

/*
                customToast.showLongToast(volleyError.toString());
*/
            }
        });
        Volley.newRequestQueue(ExpertMamuComposeQuestionActivity.this).add(getCategoriesRequest);
    }

    public void populateDropDown() {
        topicArrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item,topicStringList);
        topicSpinner.setAdapter(topicArrayAdapter);
    }

    @Override
    public void setToolbarText() {
        toolbarTitle.setText(R.string.add_a_question);
    }

    @Override
    protected void sendMessage() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();
        final CustomToast customToast = new CustomToast(this);

        QuestionPostBody questionPostBody=new QuestionPostBody();
        questionPostBody.setQuestion(new Question());
        questionPostBody.getQuestion().setQuestion(messageBodyEditText.getText().toString());
        questionPostBody.getQuestion().setAskedBy(getSavedOwnId());
        questionPostBody.getQuestion().setCategoryId(getCategoryId(topicSpinner.getSelectedItem().toString()));
        JSONObject tempJsonObject=null;
        try {
            tempJsonObject=new JSONObject(new Gson().toJson(questionPostBody));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest addQuestionRequest = new JsonObjectRequest(Request.Method.POST, ApplicationConstants.ADD_QUESTION_URL, tempJsonObject,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    progressDialog.hide();
                    ChallengeAddResponse challengeAddResponse = new Gson().fromJson(jsonObject.toString(), ChallengeAddResponse.class);
                    Toast.makeText(ExpertMamuComposeQuestionActivity.this,getResources().getString(R.string.challenge_add_success_message),Toast.LENGTH_LONG).show();
                    onBackPressed();
                }catch (Exception e)
                {
                    try {
                        String errorMessage=jsonObject.getString("error");
/*
                        Toast.makeText(ExpertMamuComposeQuestionActivity.this,errorMessage,Toast.LENGTH_LONG).show();
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
                Log.d("Err", volleyError.toString());
/*
                customToast.showLongToast(volleyError.toString());
*/
            }
        }) ;


        Volley.newRequestQueue(this).add(addQuestionRequest);
    }

    public String getCategoryId(String categoryName)
    {
        String id="-1";
        for (Category category :
                categoriesResponse.getCategories()) {
            if (categoryName.equals(handleBanglaString(category.getTopic())))
            {
                id=category.getID();
                break;
            }
        }

        return id;
    }
}
