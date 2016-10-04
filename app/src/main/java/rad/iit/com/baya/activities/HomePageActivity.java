package rad.iit.com.baya.activities;

import android.graphics.drawable.BitmapDrawable;
import android.support.percent.PercentRelativeLayout;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import com.fenchtose.tooltip.Tooltip;

import java.util.ArrayList;

import rad.iit.com.baya.R;
import rad.iit.com.baya.activities.template.TemplateActivity;

/**
 * Created by Zayed on 04-Oct-16.
 */
public class HomePageActivity extends TemplateActivity implements View.OnClickListener {
    Button expertMamuButton,normalMamuButton,videoChallengeButton,challengeMamuButton,contactUsButton,hotlineButton;
    ArrayList<Button> homepageButtons=new ArrayList<>();
    FrameLayout frameLayout;
    PercentRelativeLayout percentRelativeLayout;

    @Override
    public void initView() {

        setContentView(R.layout.activtiy_homepage);
        expertMamuButton=(Button) findViewById(R.id.btn_expert_mamu_hmpgb1);
        normalMamuButton=(Button) findViewById(R.id.btn_normal_mamu_hmpgb2);
        videoChallengeButton=(Button) findViewById(R.id.btn_video_challenge_hmpgb3);
        challengeMamuButton=(Button) findViewById(R.id.challenge_mamau_hmpgb4);
        contactUsButton=(Button) findViewById(R.id.btn_contact_us_hmpgb5);
        hotlineButton=(Button) findViewById(R.id.btn_hotline_hmpgb6);

        frameLayout=(FrameLayout) findViewById(R.id.fl_root_layout);
        percentRelativeLayout= (PercentRelativeLayout) findViewById(R.id.prl_root_layout);

        homepageButtons=createListWithHomeButtons();
    }

    @Override
    public void loadData() {

    }

    @Override
    public void initializeViewByData() {

    }

    @Override
    public void listenView() {
        for (Button homePageButton:
             homepageButtons) {
            homePageButton.setOnClickListener(this);
        }

    }

    public ArrayList<Button> createListWithHomeButtons()
    {
        ArrayList<Button> tempHomepageButtons=new ArrayList<>();
        tempHomepageButtons.add(expertMamuButton);
        tempHomepageButtons.add(normalMamuButton);
        tempHomepageButtons.add(videoChallengeButton);
        tempHomepageButtons.add(challengeMamuButton);
        tempHomepageButtons.add(contactUsButton);
        tempHomepageButtons.add(hotlineButton);

        return tempHomepageButtons;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_expert_mamu_hmpgb1:
                View child=getLayoutInflater().inflate(R.layout.tooltip_window,null);
                Tooltip tooltip = new Tooltip.Builder(this)
                        .anchor(view, Tooltip.TOP)
                        .content(child)
                        .into(frameLayout)
                        .withTip(new Tooltip.Tip(10, 10, android.R.color.black))
                        .show();
                break;

            case R.id.btn_normal_mamu_hmpgb2:
                break;

            case R.id.btn_video_challenge_hmpgb3:
                break;

            case R.id.challenge_mamau_hmpgb4:
                View child1=getLayoutInflater().inflate(R.layout.tooltip_window,null);
                Tooltip tooltip1 = new Tooltip.Builder(this)
                        .anchor(view, Tooltip.TOP)
                        .content(child1)
                        .into(frameLayout)
                        .withTip(new Tooltip.Tip(10, 10, android.R.color.black))
                        .show();
                break;

            case R.id.btn_contact_us_hmpgb5:
                break;

            case R.id.btn_hotline_hmpgb6:
                break;
        }
    }

/*    private void displayPopupWindow(View anchorView) {
        PopupWindow popup = new PopupWindow(HomePageActivity.this);
        View layout = getLayoutInflater().inflate(R.layout.popup_background_up_arrow, null);
        popup.setContentView(layout);
        // Set content width and height
        popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popup.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        // Closes the popup window when touch outside of it - when looses focus
        popup.setOutsideTouchable(true);
        popup.setFocusable(true);
        // Show anchored to button
        popup.setBackgroundDrawable(new BitmapDrawable());
        popup.showAsDropDown(anchorView);
    }*/
}
