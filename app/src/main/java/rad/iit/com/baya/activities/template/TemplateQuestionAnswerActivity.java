package rad.iit.com.baya.activities.template;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import rad.iit.com.baya.R;

public abstract class TemplateQuestionAnswerActivity extends TemplateActivity implements View.OnClickListener {

    protected Toolbar templateToolbar;
    protected TextView subTitleTextView,questionTextView,answerTextView,questionDateTextView,answerDateTextView;
    protected CardView subtitleCardView;
    protected Button contactUsButton,dialButton;

    public static String PASSED_QUESTION_MODEL="question_answer_model";


    @Override
    public void initView() {
        setContentView(R.layout.activity_challenger_mamu_question_answer);
        templateToolbar = (Toolbar) findViewById(R.id.toolbar);
        subTitleTextView = (TextView) findViewById(R.id.text_subtitle);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        questionTextView=(TextView) findViewById(R.id.tv_question);
        answerTextView=(TextView) findViewById(R.id.tv_answer);
        subtitleCardView= (CardView) findViewById(R.id.subtitle);
        questionDateTextView=(TextView) findViewById(R.id.tv_question_date);
        answerDateTextView=(TextView) findViewById(R.id.tv_answer_date);
        contactUsButton=(Button) findViewById(R.id.btn_contact_us);
        dialButton=(Button) findViewById(R.id.btn_dial);

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

        contactUsButton.setOnClickListener(this);
        dialButton.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_contact_us:
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_EMAIL, "emailaddress@emailaddress.com");
            startActivity(Intent.createChooser(intent, "Send Email"));
            break;

            case R.id.btn_dial:
                String phone_number = getString(R.string.hotline_number);
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone_number, null)));
        }
    }
}
