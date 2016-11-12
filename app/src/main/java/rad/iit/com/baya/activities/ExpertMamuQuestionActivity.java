package rad.iit.com.baya.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

import rad.iit.com.baya.R;
import rad.iit.com.baya.activities.template.TemplateQuestionActivity;
import rad.iit.com.baya.adapters.RecyclerViewListAdapterForExpertAnswers;
import rad.iit.com.baya.data.constants.ApplicationConstants;
import rad.iit.com.baya.datamodels.CategoriesResponse;
import rad.iit.com.baya.datamodels.Category;
import rad.iit.com.baya.datamodels.ExpertAnswerResponse;
import rad.iit.com.baya.datamodels.ExpertiseAnswer;

/**
 * Created by Zayed on 13-Oct-16.
 */
public class ExpertMamuQuestionActivity extends TemplateQuestionActivity implements RecyclerViewListAdapterForExpertAnswers.OnExpertQuestionClicked, View.OnClickListener {

    CategoriesResponse categoriesResponse;
    public static final String PASSED_CATEGORY_NAME = "category";
    RecyclerViewListAdapterForExpertAnswers recyclerViewListAdapterForExpertAnswers;
    ArrayList<ExpertiseAnswer> filteredExpertiseAnswer;

    @Override
    public void setQuestionAnswers() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        if(!progressDialog.isShowing()) {
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.show();
        }

        JsonObjectRequest getCategoriesRequest = new JsonObjectRequest(ApplicationConstants.EXPERT_ANSWERS_GET_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                ExpertAnswerResponse expertAnswerResponse=new Gson().fromJson(jsonObject.toString(),ExpertAnswerResponse.class);

                expertiseAnswers=new ArrayList<>();

                for (ExpertiseAnswer expertiseAnswer:
                     expertAnswerResponse.getExpertiseAnswers()) {
                    expertiseAnswers.add(expertiseAnswer);
                }

                progressDialog.hide();
                populateQuestionList();

                productListSwipeRefreshLayout.setRefreshing(false);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

/*
                customToast.showLongToast(volleyError.toString());
*/
            }
        });
        Volley.newRequestQueue(ExpertMamuQuestionActivity.this).add(getCategoriesRequest);
    }

    @Override
    public void populateQuestionList() {
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewListAdapterForExpertAnswers=new RecyclerViewListAdapterForExpertAnswers(this,R.layout.card_question,expertiseAnswers);
        recyclerViewListAdapterForExpertAnswers.setOnQuestionClicked(this);
        myRecyclerView.setAdapter(recyclerViewListAdapterForExpertAnswers);

/*
        createFilteredChallengeArray();
*/
        filteredExpertiseAnswer=new ArrayList<>();
        filteredExpertiseAnswer=expertiseAnswers;

        othersChallengesButton.setOnClickListener(this);
        ownChallengesButton.setOnClickListener(this);

        setTopicList();
    }

    @Override
    public void setTopicList() {
        topicStringList = new ArrayList< >();
        topicStringList.add(getString(R.string.all_topics));

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
        Volley.newRequestQueue(ExpertMamuQuestionActivity.this).add(getCategoriesRequest);


    }

    @Override
    public void populateDropDown() {
        topicArrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item,topicStringList);
        topicSpinner.setAdapter(topicArrayAdapter);

        topicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                final ProgressDialog progressDialog=new ProgressDialog(ExpertMamuQuestionActivity.this);
                progressDialog.show();
                if(i!=0) {
                    String categoryName = adapterView.getItemAtPosition(i).toString();
                    createFilteredAnswerArrayByCategory(categoryName);
                    recyclerViewListAdapterForExpertAnswers.setExpertiseAnswers(filteredExpertiseAnswer);
                    recyclerViewListAdapterForExpertAnswers.notifyDataSetChanged();
                }else
                {
                    filteredExpertiseAnswer=expertiseAnswers;
                    recyclerViewListAdapterForExpertAnswers.setExpertiseAnswers(expertiseAnswers);
                    recyclerViewListAdapterForExpertAnswers.notifyDataSetChanged();
                }
                progressDialog.hide();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public ArrayList<ExpertiseAnswer> applyOwnChallengeFilter()
    {
        String id=getSavedOwnId();
        ArrayList<ExpertiseAnswer> tempExpertiseAnswers=new ArrayList<>();
        for (ExpertiseAnswer expertiseAnswer: filteredExpertiseAnswer) {
            if(expertiseAnswer.getAskedBy().equals(id))
            {
                tempExpertiseAnswers.add(expertiseAnswer);
            }
        }

        return tempExpertiseAnswers;
    }

    public void createFilteredAnswerArrayByCategory(String categoryName)
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
        filteredExpertiseAnswer=new ArrayList<>();
        for (ExpertiseAnswer expertiseAnswer: expertiseAnswers) {
            if(expertiseAnswer.getCategoryID().equals(id))
            {
                filteredExpertiseAnswer.add(expertiseAnswer);
            }
        }
    }

    @Override
    public void setToolBarTitle() {
        toolbarTitle.setText(R.string.expert_question_title);
    }

    @Override
    public void onQuestioAskButtonPressed() {
        goToActivity(new ExpertMamuComposeQuestionActivity());
    }

    @Override
    public void onIndividualQuestionClicked(ExpertiseAnswer expertiseAnswer) {
        Intent intent=new Intent(this,ExpertAnswerActivity.class);
        intent.putExtra(PASSED_QUESTION_MODEL,expertiseAnswer);
        String category="";
        for (Category category1 :
                categoriesResponse.getCategories()) {
            if (category1.getID().equals(expertiseAnswer.getCategoryID()))
            {
                category=category1.getTopic();
            }
        }
        intent.putExtra(PASSED_CATEGORY_NAME,category);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_oq:
                recyclerViewListAdapterForExpertAnswers.setExpertiseAnswers(filteredExpertiseAnswer);
                recyclerViewListAdapterForExpertAnswers.notifyDataSetChanged();
                break;

            case R.id.btn_my_questions:
                applyOwnChallengeFilter();
                recyclerViewListAdapterForExpertAnswers.setExpertiseAnswers(applyOwnChallengeFilter());
                recyclerViewListAdapterForExpertAnswers.notifyDataSetChanged();
                break;
        }
    }

    public void createFilteredChallengeArray()
    {
        String id=getSavedOwnId();
        filteredExpertiseAnswer=new ArrayList<>();
        for (ExpertiseAnswer expertiseAnswer: expertiseAnswers) {
            if(expertiseAnswer.getAskedBy().equals(id))
            {
                filteredExpertiseAnswer.add(expertiseAnswer);
            }
        }
    }
}
