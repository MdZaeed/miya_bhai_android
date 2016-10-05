package rad.iit.com.baya.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.percent.PercentRelativeLayout;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

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
        View child=getLayoutInflater().inflate(R.layout.tooltip_window,null);
        TextView textView=(TextView) child.findViewById(R.id.tv_popup_text);
        Button popupButton=(Button) child.findViewById(R.id.btn_popup_enter_button);

        switch (view.getId())
        {
            case R.id.btn_expert_mamu_hmpgb1:
                textView.setText("Want to talk about adolescence and sex, reproduction health? Openly discuss with our expert mamu.");
                popupButton.setOnClickListener(new popupButtonOnClickListener(view));
                break;

            case R.id.btn_normal_mamu_hmpgb2:
                textView.setText("Chat with mamu, Tell him anything");
                popupButton.setOnClickListener(new popupButtonOnClickListener(view));
                break;

            case R.id.btn_video_challenge_hmpgb3:
                textView.setText("Find your answer via our videos, update your own videos");
                popupButton.setOnClickListener(new popupButtonOnClickListener(view));
                break;

            case R.id.challenge_mamau_hmpgb4:
                textView.setText("Is your knowledge about sex and reproduction health correct? Challenge Mamu");
                popupButton.setOnClickListener(new popupButtonOnClickListener(view));
                break;

            case R.id.btn_contact_us_hmpgb5:
                textView.setText(R.string.campus_hero_cafe);
                popupButton.setVisibility(View.GONE);
                break;

            case R.id.btn_hotline_hmpgb6:
                textView.setText("Talk with mamu on our hotline number");
                popupButton.setVisibility(View.GONE);
                break;
        }

        new Tooltip.Builder(this)
                .anchor(view, Tooltip.TOP)
                .content(child)
                .into(frameLayout)
                .withTip(new Tooltip.Tip(10,10,android.R.color.holo_blue_dark))
                .show();
    }

    public class popupButtonOnClickListener implements View.OnClickListener
    {
        View anchorView;
        public popupButtonOnClickListener(View view)
        {
            this.anchorView=view;
        }

        @Override
        public void onClick(View view) {
            switch (anchorView.getId())
            {
                case R.id.btn_expert_mamu_hmpgb1:
                    goToActivity(new LoginActivity());
                    break;

                case R.id.btn_normal_mamu_hmpgb2:
                    break;

                case R.id.btn_video_challenge_hmpgb3:
                    break;

                case R.id.challenge_mamau_hmpgb4:
                    break;
            }

        }
    }

    public void goToActivity(Activity activity)
    {
        Intent intent=new Intent(this,activity.getClass());
        startActivity(intent);
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
