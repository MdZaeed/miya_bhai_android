package rad.iit.com.baya.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import rad.iit.com.baya.R;
import rad.iit.com.baya.activities.template.TemplateActivity;

public class ChallengerMamuQuestionAnswerActivity extends TemplateActivity {

    protected Toolbar templateToolbar;
    private static String LOG="ChallengerMamuQuestionAnswerActivity";
    TextView subTitleTextView;

    @Override
    public void initView() {
        setContentView(R.layout.activity_challenger_mamu_question_answer);
        templateToolbar = (Toolbar) findViewById(R.id.toolbar);
        subTitleTextView = (TextView) findViewById(R.id.text_subtitle);
    }

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
    }

    @Override
    public void listenView() {

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
