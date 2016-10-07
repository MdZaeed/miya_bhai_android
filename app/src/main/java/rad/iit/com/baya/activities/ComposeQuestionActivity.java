package rad.iit.com.baya.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rad.iit.com.baya.R;
import rad.iit.com.baya.activities.template.TemplateActivity;
import rad.iit.com.baya.datamodels.Category;

public class ComposeQuestionActivity extends TemplateActivity {
    protected Toolbar templateToolbar;
    private static String LOG="ChallengerMamuActivity";

    Spinner topicSpinner;
    ArrayAdapter<String> topicArrayAdapter;
    List<String> topicStringList;



    @Override
    public void initView() {
        setContentView(R.layout.activity_compose_question);
        templateToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(templateToolbar);
        templateToolbar.setNavigationIcon(R.drawable.arrow_back_white_24x24);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        topicSpinner = (Spinner) findViewById(R.id.spinner_topic_list);
        // temp method
        setTopicList();
    }

    @Override
    public void loadData() {

    }

    @Override
    public void initializeViewByData() {
        topicArrayAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,topicStringList);
        topicSpinner.setAdapter(topicArrayAdapter);
        toolbarTitle.setText("Compose a Question");
    }

    @Override
    public void listenView() {
        templateToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    /**
     * Temp method
     */
    private void setTopicList() {
        topicStringList = new ArrayList<String>();
        topicStringList.add("Category One");
        topicStringList.add("Category One");
        topicStringList.add("Category One");
        topicStringList.add("Category One");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.compose_question,menu);
        return true;
    }
}
