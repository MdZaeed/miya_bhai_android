package rad.iit.com.baya.activities;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rad.iit.com.baya.R;
import rad.iit.com.baya.activities.template.DrawerTemplateActivity;
import rad.iit.com.baya.adapters.RecyclerViewListAdapter;
import rad.iit.com.baya.data.constants.ApplicationConstants;
import rad.iit.com.baya.datamodels.Category;
import rad.iit.com.baya.datamodels.OnRecyclerViewItemListener;
import rad.iit.com.baya.datamodels.QuestionAnswer;


public class ChallengerMamuActivity extends DrawerTemplateActivity implements OnRecyclerViewItemListener {

    protected Toolbar templateToolbar;
    private static String LOG="ChallengerMamuActivity";
    SwipeRefreshLayout productListSwipeRefreshLayout ;
    List<QuestionAnswer> questionAnswerList = new ArrayList<QuestionAnswer>();
    RecyclerView myRecyclerView;
    TextView subTitleTextView;

    @Override
    public void loadDataFromLocalDB(){
        super.loadDataFromLocalDB();
        // load all the questions and answers
        demoQuestionAnswer();
    }
    @Override
    public void onBackPressed() {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_challenger_mamu);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        myRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_challenger_mamu);
        productListSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout_home);
        subTitleTextView = (TextView) findViewById(R.id.text_subtitle);
    }

    @Override
    public void loadData() {
        loadDataFromLocalDB();
    }

    @Override
    public void initializeViewByData() {
        templateToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(templateToolbar);
        toggle = new ActionBarDrawerToggle(
                this, drawer, templateToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(false);
        templateToolbar.setNavigationIcon(R.drawable.drawer_16);
        templateToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupNavigationHeader();
                setUpNavigationDrawerMenu();
                drawer.openDrawer(Gravity.LEFT);
            }
        });
        navigationView.setNavigationItemSelectedListener(this);
        setupNavigationHeader();
        setUpNavigationDrawerMenu();
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myRecyclerView.setAdapter(new RecyclerViewListAdapter(ChallengerMamuActivity.this,R.layout.card_question,questionAnswerList.size()));
        productListSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.red);
        if (subTitleTextView != null){
            subTitleTextView.setText("Just Arrived");
        }
    }

    @Override
    public void listenView() {

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
                Intent intent = new Intent(ChallengerMamuActivity.this,ChallengerMamuQuestionAnswerActivity.class);
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