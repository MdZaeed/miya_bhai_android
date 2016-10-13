package rad.iit.com.baya.activities.template;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rad.iit.com.baya.R;
import rad.iit.com.baya.datamodels.CategoriesResponse;

public abstract class TemplateComposeQuestionActivity extends TemplateActivity {
    protected Toolbar templateToolbar;
    protected Spinner topicSpinner;
    protected ArrayAdapter<String> topicArrayAdapter;
    protected List<String> topicStringList;
    protected CategoriesResponse categoriesResponse;

    @Override
    public void initView() {
        setContentView(R.layout.activity_compose_question);
        templateToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(templateToolbar);
        templateToolbar.setNavigationIcon(R.drawable.arrow_back_white_24x24);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        topicSpinner = (Spinner) findViewById(R.id.spinner_topic_list);
        topicStringList = new ArrayList<String>();

        // temp method
        initializeTopicList();
    }

    @Override
    public void loadData() {

    }

    @Override
    public void initializeViewByData() {
        setToolbarText();
    }

    public abstract void initializeTopicList();
    public abstract void setToolbarText();

    @Override
    public void listenView() {
        templateToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.compose_question,menu);
        return true;
    }
}
