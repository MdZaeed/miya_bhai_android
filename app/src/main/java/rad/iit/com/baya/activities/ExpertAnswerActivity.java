package rad.iit.com.baya.activities;

import android.content.Intent;

import rad.iit.com.baya.R;
import rad.iit.com.baya.activities.template.TemplateQuestionAnswerActivity;
import rad.iit.com.baya.datamodels.Challenge;
import rad.iit.com.baya.datamodels.ExpertiseAnswer;

/**
 * Created by Zayed on 13-Oct-16.
 */
public class ExpertAnswerActivity extends TemplateQuestionAnswerActivity{
    ExpertiseAnswer expertiseAnswer;
    String categoryName;

    @Override
    public void getSentData() {
        Intent intent=getIntent();
        expertiseAnswer=(ExpertiseAnswer)intent.getSerializableExtra(TemplateQuestionAnswerActivity.PASSED_QUESTION_MODEL);
        categoryName=intent.getStringExtra(ExpertMamuQuestionActivity.PASSED_CATEGORY_NAME);
    }

    @Override
    public void setCategorySubtitle() {
        subTitleTextView.setText(handleBanglaString(categoryName));
    }

    @Override
    public void setToolbarText() {
        toolbarTitle.setText(R.string.question_answer_title);
    }

    @Override
    public void bindDataInViews() {
        questionTextView.setText(handleBanglaString(expertiseAnswer.getQuestion()));
        if(null!=expertiseAnswer.getAnswer() && expertiseAnswer.getAnswer().equals("")) {
            answerTextView.setText(handleBanglaString(expertiseAnswer.getAnswer().toString()));
            answerDateTextView.setText(expertiseAnswer.getAnswerDate().toString());
        }else
        {
            answerTextView.setText(getResources().getString(R.string.no_answer_text));
        }

        questionDateTextView.setText(expertiseAnswer.getQuestionDate());
    }
}
