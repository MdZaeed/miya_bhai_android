package rad.iit.com.baya.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
        Volley.newRequestQueue(ChallengeMamuQuestionActivity.this).add(getCategoriesRequest);
    }

    public void populateQuestionList() {
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewListAdapter=new RecyclerViewListAdapter(this, R.layout.card_question,challenges);
        recyclerViewListAdapter.setOnQuestionClicked(this);
        myRecyclerView.setAdapter(recyclerViewListAdapter);
        createFilteredChallengeArray();

        othersChallengesButton.setText("Others Challenges");
        ownChallengesButton.setText("Own Challenges");
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
        SharedPreferences sharedPreferences = getSharedPreferences(ApplicationConstants.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        String id=sharedPreferences.getString(ApplicationConstants.ID_KEY,"-1");
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
        Intent intent=new Intent(this,ChallengeMamuQuestionAnswerActivity.class);
        intent.putExtra(PASSED_QUESTION_MODEL,challenge);
        startActivity(intent);
    }
}
