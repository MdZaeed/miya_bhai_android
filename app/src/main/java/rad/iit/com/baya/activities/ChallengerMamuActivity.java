package rad.iit.com.baya.activities;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import rad.iit.com.baya.R;
import rad.iit.com.baya.activities.template.TemplateActivity;
import rad.iit.com.baya.adapters.RecyclerViewListAdapter;
import rad.iit.com.baya.data.constants.ApplicationConstants;
import rad.iit.com.baya.datamodels.CategoriesResponse;
import rad.iit.com.baya.datamodels.Category;
import rad.iit.com.baya.datamodels.Challenge;
import rad.iit.com.baya.datamodels.OnRecyclerViewItemListener;
import rad.iit.com.baya.datamodels.QuestionAnswer;
import rad.iit.com.baya.datamodels.QuestionsResponse;
import rad.iit.com.baya.datamodels.Video;
import rad.iit.com.baya.datamodels.VideosResponse;
import rad.iit.com.baya.utils.CustomToast;

public class ChallengerMamuActivity extends TemplateActivity implements RecyclerViewListAdapter.OnQuestionClicked {

    protected Toolbar templateToolbar;
    private static String LOG="ChallengerMamuActivity";
    SwipeRefreshLayout productListSwipeRefreshLayout ;
    List<QuestionAnswer> questionAnswerList = new ArrayList<QuestionAnswer>();
    RecyclerView myRecyclerView;
    TextView subTitleTextView;
    FloatingActionButton questionAskFloatingActionButton;
    Spinner topicSpinner;
    ArrayAdapter<String> topicArrayAdapter;
    List<String> topicStringList;
    ArrayList<Challenge> challenges;

    public static String PASSED_QUESTION_MODEL="question_answer_model";

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_challenger_mamu);
        myRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_challenger_mamu);
        productListSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout_home);
        subTitleTextView = (TextView) findViewById(R.id.text_subtitle);
        questionAskFloatingActionButton = (FloatingActionButton) findViewById(R.id.ft_question_ask);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        topicSpinner = (Spinner) findViewById(R.id.spinner_topic_list);
        challenges=new ArrayList<>();

        // temp method
        setTopicList();
        setQuestionAnswers();

    }

    private void setQuestionAnswers() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        if(!progressDialog.isShowing()) {
            progressDialog.setMessage("Loading");
            progressDialog.show();
        }

        JsonObjectRequest getCategoriesRequest = new JsonObjectRequest(ApplicationConstants.CHALLENGES_GET_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                QuestionsResponse questionsResponse=new Gson().fromJson(jsonObject.toString(),QuestionsResponse.class);
                for (Challenge challenge :
                        questionsResponse.getChallenges()) {
                    challenges.add(challenge);
                }

                progressDialog.hide();
                populateQuestionList();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                customToast.showLongToast(volleyError.toString());
            }
        });
        Volley.newRequestQueue(ChallengerMamuActivity.this).add(getCategoriesRequest);
    }

    private void populateQuestionList() {
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewListAdapter recyclerViewListAdapter=new RecyclerViewListAdapter(this,R.layout.card_question,challenges);
        recyclerViewListAdapter.setOnQuestionClicked(this);
        myRecyclerView.setAdapter(recyclerViewListAdapter);
    }

    /**
     * Temp method
     */
    private void setTopicList() {
        topicStringList = new ArrayList< >();
        topicStringList.add("All Topics");

        final ProgressDialog progressDialog = new ProgressDialog(this);
        if(!progressDialog.isShowing()) {
            progressDialog.setMessage("Loading");
            progressDialog.show();
        }

        JsonObjectRequest getCategoriesRequest = new JsonObjectRequest(ApplicationConstants.CATEGORIES_GET_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                CategoriesResponse categoriesResponse=new Gson().fromJson(jsonObject.toString(), CategoriesResponse.class);

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
        Volley.newRequestQueue(ChallengerMamuActivity.this).add(getCategoriesRequest);


    }

    private void populateDropDown() {
        topicArrayAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,topicStringList);
        topicSpinner.setAdapter(topicArrayAdapter);
    }


    @Override
    public void loadData() {

    }

    @Override
    public void initializeViewByData() {
        templateToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(templateToolbar);
        templateToolbar.setNavigationIcon(R.drawable.ic_mamu_home);
        productListSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.red);
        if (subTitleTextView != null){
            subTitleTextView.setText("Category");
        }
        toolbarTitle.setText(getResources().getString(R.string.challenge_mamu));
    }

    @Override
    public void listenView() {
        templateToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO go to Home Activity
                onBackPressed();
            }
        });
        questionAskFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(ChallengerMamuActivity.this, ComposeQuestionActivity.class);
                    startActivity(intent);
            }
        });
        productListSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productListSwipeRefreshLayout.setRefreshing(false);
            }
        });
        myRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int topRowVertical = (myRecyclerView == null || myRecyclerView.getChildCount() == 0) ? 0 : myRecyclerView.getChildAt(0).getTop();
                productListSwipeRefreshLayout.setEnabled(topRowVertical >= 0);
            }
        });
    }

    @Override
    public void onIndividualQuestionClicked(Challenge position) {
        Intent intent=new Intent(this,ChallengerMamuQuestionAnswerActivity.class);
        intent.putExtra(PASSED_QUESTION_MODEL,position);
        startActivity(intent);
    }
}