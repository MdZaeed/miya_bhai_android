package rad.iit.com.baya.activities.template;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rad.iit.com.baya.R;
import rad.iit.com.baya.adapters.RecyclerViewListAdapter;
import rad.iit.com.baya.data.constants.ApplicationConstants;
import rad.iit.com.baya.datamodels.Challenge;
import rad.iit.com.baya.datamodels.ExpertiseAnswer;

/**
 * Created by Zayed on 13-Oct-16.
 */
public abstract class TemplateQuestionActivity extends TemplateActivity {

    protected Toolbar templateToolbar;
    protected SwipeRefreshLayout productListSwipeRefreshLayout;
    protected RecyclerView myRecyclerView;
    protected TextView subTitleTextView;
    protected FloatingActionButton questionAskFloatingActionButton;
    protected Spinner topicSpinner;
    protected ArrayAdapter<String> topicArrayAdapter;
    protected List<String> topicStringList;
    protected ArrayList<Challenge> challenges;
    protected ArrayList<ExpertiseAnswer> expertiseAnswers;
    protected Button othersChallengesButton, ownChallengesButton;


    public static String PASSED_QUESTION_MODEL = "question_answer_model";

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_challenger_mamu);
        myRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_challenger_mamu);
        productListSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout_home);
        subTitleTextView = (TextView) findViewById(R.id.text_subtitle);
        questionAskFloatingActionButton = (FloatingActionButton) findViewById(R.id.ft_question_ask);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        topicSpinner = (Spinner) findViewById(R.id.spinner_topic_list);
        challenges = new ArrayList<>();
        expertiseAnswers = new ArrayList<>();
        othersChallengesButton = (Button) findViewById(R.id.bt_oq);
        ownChallengesButton = (Button) findViewById(R.id.btn_my_questions);

        setQuestionAnswers();
    }

    public abstract void setQuestionAnswers();

    public abstract void populateQuestionList();

    /**
     * Temp method
     */
    public abstract void setTopicList();

    public abstract void populateDropDown();


    @Override
    public void loadData() {

    }

    @Override
    public void initializeViewByData() {
        templateToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(templateToolbar);
        templateToolbar.setNavigationIcon(R.drawable.ic_mamu_home);
        productListSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.red);
        if (subTitleTextView != null) {
            subTitleTextView.setText("Category");
        }

        setToolBarTitle();
    }

    public abstract void setToolBarTitle();

    @Override
    public void listenView() {
        templateToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO go to Home Activity
                onBackPressed();
            }
        });
        questionAskFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onQuestioAskButtonPressed();
            }
        });
        productListSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                setQuestionAnswers();

            }
        });
        myRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int topRowVertical = (myRecyclerView == null || myRecyclerView.getChildCount() == 0) ? 0 : myRecyclerView.getChildAt(0).getTop();
                productListSwipeRefreshLayout.setEnabled(topRowVertical >= 0);
            }
        });
    }

    public abstract void onQuestioAskButtonPressed();

}
