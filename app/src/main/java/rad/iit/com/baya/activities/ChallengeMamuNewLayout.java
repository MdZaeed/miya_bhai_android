package rad.iit.com.baya.activities;

import android.content.Intent;
import android.text.Html;
import android.widget.TextView;

import rad.iit.com.baya.R;
import rad.iit.com.baya.activities.template.TemplateActivity;
import rad.iit.com.baya.activities.template.TemplateQuestionAnswerActivity;
import rad.iit.com.baya.datamodels.Challenge;

/**
 * Created by Zayed on 27-Jan-17.
 */
public class ChallengeMamuNewLayout extends TemplateActivity {

    TextView questionTextView,answerTextView;
    Challenge ownChallenge;


    @Override
    public void initView() {
        setContentView(R.layout.activity_challenge_question_answer);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        questionTextView=(TextView) findViewById(R.id.tv_challenge_question);
        answerTextView=(TextView) findViewById(R.id.tv_challenge_answer);
    }

    @Override
    public void loadData() {
        Intent intent=getIntent();
        ownChallenge=(Challenge)intent.getSerializableExtra(TemplateQuestionAnswerActivity.PASSED_QUESTION_MODEL);
    }

    @Override
    public void initializeViewByData() {
        questionTextView.setText(ownChallenge.getQuestion());
        if(null!=ownChallenge.getAnswer() && !ownChallenge.getAnswer().equals("")) {
            answerTextView.setText(Html.fromHtml(handleBanglaString(ownChallenge.getAnswer())));
        }else
        {
            answerTextView.setText(R.string.no_answer_text);
        }
    }

    @Override
    public void listenView() {

    }
}
