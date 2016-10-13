package rad.iit.com.baya.activities;

import android.app.ProgressDialog;
import android.widget.ArrayAdapter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

import rad.iit.com.baya.R;
import rad.iit.com.baya.activities.template.TemplateComposeQuestionActivity;
import rad.iit.com.baya.data.constants.ApplicationConstants;
import rad.iit.com.baya.datamodels.CategoriesResponse;
import rad.iit.com.baya.datamodels.Category;

/**
 * Created by Zayed on 14-Oct-16.
 */
public class ExpertMamuComposeQuestionActivity extends TemplateComposeQuestionActivity {
    @Override
    public void initializeTopicList() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        if(!progressDialog.isShowing()) {
            progressDialog.setMessage("Loading");
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
                customToast.showLongToast(volleyError.toString());
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
        toolbarTitle.setText("Add a question");
    }
}
