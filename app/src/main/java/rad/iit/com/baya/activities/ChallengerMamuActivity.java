package rad.iit.com.baya.activities;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rad.iit.com.baya.R;
import rad.iit.com.baya.activities.template.TemplateActivity;
import rad.iit.com.baya.adapters.RecyclerViewListAdapter;
import rad.iit.com.baya.data.constants.ApplicationConstants;
import rad.iit.com.baya.datamodels.OnRecyclerViewItemListener;
import rad.iit.com.baya.datamodels.QuestionAnswer;

public class ChallengerMamuActivity extends TemplateActivity implements OnRecyclerViewItemListener {

    protected Toolbar templateToolbar;
    private static String LOG="ChallengerMamuActivity";
    SwipeRefreshLayout productListSwipeRefreshLayout ;
    List<QuestionAnswer> questionAnswerList = new ArrayList<QuestionAnswer>();
    RecyclerView myRecyclerView;
    TextView subTitleTextView;
    FloatingActionButton questionAskFloatingActionButton;
    Spinner topicSpinner;
    ArrayAdapter<String> topicArrayAdapter;
    List<String> topicStringList;


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

        // temp method
        setTopicList();

    }
    /**
     * Temp method
     */
    private void setTopicList() {
        topicStringList = new ArrayList<String>();
        topicStringList.add("\\u09ac\\u09af\\u09bc\\u0983\\u09b8\\u09a8\\u09cd\\u09a7\\u09bf");
        topicStringList.add("Category One");
        topicStringList.add("Category One");
        topicStringList.add("Category One");


    }


    @Override
    public void loadData() {
        demoQuestionAnswer();
    }

    @Override
    public void initializeViewByData() {
        templateToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(templateToolbar);
        templateToolbar.setNavigationIcon(R.drawable.ic_mamu_home);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myRecyclerView.setAdapter(new RecyclerViewListAdapter(ChallengerMamuActivity.this, R.layout.card_question, questionAnswerList.size()));
        productListSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.red);
        if (subTitleTextView != null){
            subTitleTextView.setText("Category");
        }
        topicArrayAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,topicStringList);
        topicSpinner.setAdapter(topicArrayAdapter);
        toolbarTitle.setText("Expert Mamu");
    }

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
                    Intent intent = new Intent(ChallengerMamuActivity.this, ComposeQuestionActivity.class);
                    startActivity(intent);
            }
        });
        productListSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productListSwipeRefreshLayout.setRefreshing(false);
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

    @Override
    public void listenItem(View view, final int position) {
        TextView questionView = (TextView) view.findViewById(R.id.tv_question);
        questionView.setText(questionAnswerList.get(position).question);
        questionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChallengerMamuActivity.this, ChallengerMamuQuestionAnswerActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * demo Question-Answer
     */
    private void demoQuestionAnswer(){
        String question = ApplicationConstants.demo_question;
        String answer = ApplicationConstants.demo_answer;

        questionAnswerList.add(new QuestionAnswer(1,question,answer,1,1));
        questionAnswerList.add(new QuestionAnswer(2,question,answer,1,2));
        questionAnswerList.add(new QuestionAnswer(3,question,answer,1,3));
        questionAnswerList.add(new QuestionAnswer(4,question,answer,1,4));
        questionAnswerList.add(new QuestionAnswer(5,question,answer,1,5));


        questionAnswerList.add(new QuestionAnswer(6,question,answer,1,1));
        questionAnswerList.add(new QuestionAnswer(7,question,answer,1,2));
        questionAnswerList.add(new QuestionAnswer(8,question,answer,1,3));
        questionAnswerList.add(new QuestionAnswer(9,question,answer,1,4));
        questionAnswerList.add(new QuestionAnswer(10,question,answer,1,5));

        questionAnswerList.add(new QuestionAnswer(11,question,answer,1,1));
        questionAnswerList.add(new QuestionAnswer(12,question,answer,1,2));
        questionAnswerList.add(new QuestionAnswer(13,question,answer,1,3));
        questionAnswerList.add(new QuestionAnswer(14,question,answer,1,4));
        questionAnswerList.add(new QuestionAnswer(15,question,answer,1,5));

        questionAnswerList.add(new QuestionAnswer(16,question,answer,1,1));
        questionAnswerList.add(new QuestionAnswer(17,question,answer,1,2));
        questionAnswerList.add(new QuestionAnswer(18,question,answer,1,3));
        questionAnswerList.add(new QuestionAnswer(19,question,answer,1,4));
        questionAnswerList.add(new QuestionAnswer(20,question,answer,1,5));
    }
}