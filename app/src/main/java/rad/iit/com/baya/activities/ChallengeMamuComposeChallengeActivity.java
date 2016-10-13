package rad.iit.com.baya.activities;

import android.view.View;
import android.widget.LinearLayout;

import rad.iit.com.baya.R;
import rad.iit.com.baya.activities.template.TemplateComposeQuestionActivity;

/**
 * Created by Zayed on 13-Oct-16.
 */
public class ChallengeMamuComposeChallengeActivity extends TemplateComposeQuestionActivity {

    LinearLayout topicHeaderLinearLayout;
    @Override
    public void initializeTopicList() {
        topicHeaderLinearLayout=(LinearLayout) findViewById(R.id.topic_header);
        topicHeaderLinearLayout.setVisibility(View.GONE);
    }

    @Override
    public void setToolbarText() {
        toolbarTitle.setText("Compose a Challenge");
    }
}
