package rad.iit.com.baya.activities;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.google.gson.Gson;

import rad.iit.com.baya.R;
import rad.iit.com.baya.activities.ChallengeMamuNewLayout;
import rad.iit.com.baya.activities.template.TemplateQuestionActivity;
import rad.iit.com.baya.adapters.RecyclerViewListAdapter;
import rad.iit.com.baya.datamodels.Challenge;
import rad.iit.com.baya.datamodels.QuestionsResponse;

/**
 * Created by Zayed on 02-Feb-17.
 */
public class FaqQustionsActivity extends TemplateQuestionActivity implements RecyclerViewListAdapter.OnQuestionClicked {

    RecyclerViewListAdapter recyclerViewListAdapter;
    View bottomBar;

    @Override
    public void setQuestionAnswers() {
        QuestionsResponse questionsResponse=QuestionsResponse.faqData();
        for (Challenge challenge :
                questionsResponse.getChallenges()) {
            challenges.add(challenge);
        }

        populateQuestionList();
    }

    @Override
    public void populateQuestionList() {
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewListAdapter=new RecyclerViewListAdapter(this, R.layout.card_question,challenges);
        recyclerViewListAdapter.setOnQuestionClicked(this);
        myRecyclerView.setAdapter(recyclerViewListAdapter);

        othersChallengesButton.setText(R.string.others_challenges);
        ownChallengesButton.setText(R.string.my_challenges);

        setTopicList();
    }

    @Override
    public void setTopicList() {
        bottomBar=findViewById(R.id.lay_bottom_bar);

        try {
            CardView cardView = (CardView) findViewById(R.id.subtitle);
            assert cardView != null;
            cardView.setVisibility(View.GONE);
            bottomBar.setVisibility(View.GONE);
            questionAskFloatingActionButton.setVisibility(View.GONE);

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
        toolbarTitle.setText(getResources().getString(R.string.faq));
    }

    @Override
    public void onQuestioAskButtonPressed() {

    }

    @Override
    public void onIndividualQuestionClicked(Challenge challenge) {
        Intent intent=new Intent(this,ChallengeMamuNewLayout.class);
        intent.putExtra(PASSED_QUESTION_MODEL,challenge);
        startActivity(intent);
    }
}
