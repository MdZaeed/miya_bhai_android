package rad.iit.com.baya.activities.template;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import rad.iit.com.baya.R;
import rad.iit.com.baya.activities.ChallengerMamuActivity;
import rad.iit.com.baya.datamodels.Challenge;

public abstract class TemplateQuestionAnswerActivity extends TemplateActivity {

    protected Toolbar templateToolbar;
    protected TextView subTitleTextView,questionTextView,answerTextView;
    protected CardView subtitleCardView;

    @Override
    public void initView() {
        setContentView(R.layout.activity_challenger_mamu_question_answer);
        templateToolbar = (Toolbar) findViewById(R.id.toolbar);
        subTitleTextView = (TextView) findViewById(R.id.text_subtitle);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        questionTextView=(TextView) findViewById(R.id.tv_question);
        answerTextView=(TextView) findViewById(R.id.tv_answer);
        subtitleCardView= (CardView) findViewById(R.id.subtitle);

        getSentData();
    }

    public abstract void getSentData();

    @Override
    public void loadData() {

    }

    @Override
    public void initializeViewByData() {
        templateToolbar.setNavigationIcon(R.drawable.arrow_back_white_24x24);
        templateToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        setCategorySubtitle();
        setToolbarText();
        bindDataInViews();
    }

    public abstract void setCategorySubtitle();
    public abstract void setToolbarText();
    public abstract void bindDataInViews();

    @Override
    public void listenView() {

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
