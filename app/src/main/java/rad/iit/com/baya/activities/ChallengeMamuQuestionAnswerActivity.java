package rad.iit.com.baya.activities;

import android.content.Intent;
import android.view.View;

import rad.iit.com.baya.activities.template.TemplateQuestionAnswerActivity;
import rad.iit.com.baya.datamodels.Challenge;

/**
 * Created by Zayed on 13-Oct-16.
 */
public class ChallengeMamuQuestionAnswerActivity extends TemplateQuestionAnswerActivity {
    Challenge ownChallenge;
    @Override
    public void getSentData() {
        Intent intent=getIntent();
        ownChallenge=(Challenge)intent.getSerializableExtra(ChallengerMamuActivity.PASSED_QUESTION_MODEL);
    }

    @Override
    public void setCategorySubtitle() {
        subtitleCardView.setVisibility(View.GONE);
    }

    @Override
    public void setToolbarText() {
        toolbarTitle.setText("Answer to the challenge");
    }

    @Override
    public void bindDataInViews() {
        questionTextView.setText(ownChallenge.getQuestion());
        answerTextView.setText(ownChallenge.getAnswer());
    }
}
