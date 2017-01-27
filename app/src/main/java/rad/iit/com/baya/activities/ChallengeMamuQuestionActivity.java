package rad.iit.com.baya.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

import rad.iit.com.baya.R;
import rad.iit.com.baya.activities.template.TemplateQuestionActivity;
import rad.iit.com.baya.adapters.RecyclerViewListAdapter;
import rad.iit.com.baya.data.constants.ApplicationConstants;
import rad.iit.com.baya.datamodels.Challenge;
import rad.iit.com.baya.datamodels.QuestionsResponse;

/**
 * Created by Zayed on 13-Oct-16.
 */
public class ChallengeMamuQuestionActivity extends TemplateQuestionActivity implements View.OnClickListener, RecyclerViewListAdapter.OnQuestionClicked {

    RecyclerViewListAdapter recyclerViewListAdapter;
    protected ArrayList<Challenge> challenges1;

    public void setQuestionAnswers() {
        questionAskFloatingActionButton.hide();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        if(!progressDialog.isShowing()) {
            progressDialog.setMessage(getResources().getString(R.string.loading));
            progressDialog.show();
        }

        JsonObjectRequest getCategoriesRequest = new JsonObjectRequest(ApplicationConstants.CHALLENGES_GET_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                saveOfflineData(ApplicationConstants.OFFLINE_CHALLENGES,jsonObject.toString());
                setChallengeData(jsonObject.toString());
                progressDialog.hide();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

/*
                customToast.showLongToast(volleyError.toString());
*/


/*                String data=getOfflineData(ApplicationConstants.OFFLINE_CHALLENGES);
                if(!data.equals("-1"))
                {
                    setChallengeData(data);
                }

                progressDialog.hide();
                Toast.makeText(ChallengeMamuQuestionActivity.this,R.string.disconnect,Toast.LENGTH_LONG).show();*/

                setChallengeData(new Gson().toJson(QuestionsResponse.mockData()));
                progressDialog.hide();

            }
        });
        Volley.newRequestQueue(ChallengeMamuQuestionActivity.this).add(getCategoriesRequest);
    }

    public void setChallengeData(String value)
    {
        QuestionsResponse questionsResponse=new Gson().fromJson(value,QuestionsResponse.class);
        for (Challenge challenge :
                questionsResponse.getChallenges()) {
            challenges.add(challenge);
        }

        populateQuestionList();
        productListSwipeRefreshLayout.setRefreshing(false);
    }

    public void populateQuestionList() {
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewListAdapter=new RecyclerViewListAdapter(this, R.layout.card_question,challenges);
        recyclerViewListAdapter.setOnQuestionClicked(this);
        myRecyclerView.setAdapter(recyclerViewListAdapter);
        createFilteredChallengeArray();

        othersChallengesButton.setText(R.string.others_challenges);
        ownChallengesButton.setText(R.string.my_challenges);
        othersChallengesButton.setOnClickListener(this);
        ownChallengesButton.setOnClickListener(this);

        setTopicList();
    }

    @Override
    public void setTopicList() {
        try {
            CardView cardView = (CardView) findViewById(R.id.subtitle);
            assert cardView != null;
            cardView.setVisibility(View.GONE);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void populateDropDown() {

    }

    @Override
    public void setToolBarTitle() {
        toolbarTitle.setText(getResources().getString(R.string.challenge_mamu));
    }

    @Override
    public void onQuestioAskButtonPressed() {
        goToActivity(new ChallengeMamuComposeChallengeActivity());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_oq:
                recyclerViewListAdapter.setChallenges(challenges);
                recyclerViewListAdapter.notifyDataSetChanged();
                break;

            case R.id.btn_my_questions:
                recyclerViewListAdapter.setChallenges(challenges1);
                recyclerViewListAdapter.notifyDataSetChanged();
                break;
        }
    }

    public void createFilteredChallengeArray()
    {
        String id=getSavedOwnId();
        recyclerViewListAdapter.setFilterId(id);
        challenges1=new ArrayList<>();
        for (Challenge challenge : challenges) {
            if(challenge.getChallenger().equals(id))
            {
                challenges1.add(challenge);
            }
        }
    }
    @Override
    public void onIndividualQuestionClicked(Challenge challenge) {
        Intent intent=new Intent(this,ChallengeMamuNewLayout.class);
        intent.putExtra(PASSED_QUESTION_MODEL,challenge);
        startActivity(intent);
    }
}
