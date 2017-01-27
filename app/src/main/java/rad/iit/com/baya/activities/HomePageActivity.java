package rad.iit.com.baya.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.fenchtose.tooltip.Tooltip;

import java.util.ArrayList;

import rad.iit.com.baya.R;
import rad.iit.com.baya.activities.chat.ChatActivity;
import rad.iit.com.baya.activities.template.TemplateActivity;
import rad.iit.com.baya.fragments.ExitAppFragment;

/**
 * Created by Zayed on 04-Oct-16.
 */
public class HomePageActivity extends TemplateActivity implements View.OnClickListener {
    Button expertMamuButton, normalMamuButton, videoChallengeButton, challengeMamuButton, contactUsButton, hotlineButton;
    ArrayList<Button> homepageButtons = new ArrayList<>();
    FrameLayout frameLayout;
    PercentRelativeLayout percentRelativeLayout;
    DrawerLayout drawerLayout;
    Button settingsButton,logoutButton;

    @Override
    public void initView() {
        setContentView(R.layout.activtiy_homepage);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_mamu_home);
        getSupportActionBar().setHomeButtonEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.dl_home_page_drawer);
        setTitle(getResources().getString(R.string.app_name));
        expertMamuButton = (Button) findViewById(R.id.btn_expert_mamu_hmpgb1);
        normalMamuButton = (Button) findViewById(R.id.btn_normal_mamu_hmpgb2);
        videoChallengeButton = (Button) findViewById(R.id.btn_video_challenge_hmpgb3);
        challengeMamuButton = (Button) findViewById(R.id.challenge_mamau_hmpgb4);
        contactUsButton = (Button) findViewById(R.id.btn_contact_us_hmpgb5);
        hotlineButton = (Button) findViewById(R.id.btn_hotline_hmpgb6);
        settingsButton=(Button) findViewById(R.id.btn_settings);
        logoutButton=(Button) findViewById(R.id.btn_logout);

        frameLayout = (FrameLayout) findViewById(R.id.fl_root_layout);
        percentRelativeLayout = (PercentRelativeLayout) findViewById(R.id.prl_root_layout);

        homepageButtons = createListWithHomeButtons();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }

        return true;
    }

    @Override
    public void loadData() {

    }

    @Override
    public void initializeViewByData() {

    }

    @Override
    public void listenView() {
        for (Button homePageButton :
                homepageButtons) {
            homePageButton.setOnClickListener(this);
        }

    }

    public ArrayList<Button> createListWithHomeButtons() {
        ArrayList<Button> tempHomepageButtons = new ArrayList<>();
        tempHomepageButtons.add(expertMamuButton);
        tempHomepageButtons.add(normalMamuButton);
        tempHomepageButtons.add(videoChallengeButton);
        tempHomepageButtons.add(challengeMamuButton);
        tempHomepageButtons.add(contactUsButton);
        tempHomepageButtons.add(hotlineButton);
        tempHomepageButtons.add(settingsButton);
        tempHomepageButtons.add(logoutButton);

        return tempHomepageButtons;
    }

    @Override
    public void onClick(View view) {
        View child = getLayoutInflater().inflate(R.layout.tooltip_window, null);
        TextView textView = (TextView) child.findViewById(R.id.tv_popup_text);
        Button popupButton = (Button) child.findViewById(R.id.btn_popup_enter_button);

        switch (view.getId()) {
            case R.id.btn_expert_mamu_hmpgb1:
                textView.setText(R.string.first_button_tooltip_text);
                popupButton.setOnClickListener(new popupButtonOnClickListener(view));
                break;

            case R.id.btn_normal_mamu_hmpgb2:
                textView.setText(R.string.second_button_tooltip_text);
                popupButton.setOnClickListener(new popupButtonOnClickListener(view));
                break;

            case R.id.btn_video_challenge_hmpgb3:
                textView.setText(R.string.third_button_tooltip_text);
                popupButton.setOnClickListener(new popupButtonOnClickListener(view));
                break;

            case R.id.challenge_mamau_hmpgb4:
                textView.setText(R.string.fourth_buton_tooltip_text);
                popupButton.setOnClickListener(new popupButtonOnClickListener(view));
                break;

            case R.id.btn_contact_us_hmpgb5:
                textView.setText(R.string.campus_hero_cafe);
                popupButton.setText(R.string.mail);
                popupButton.setOnClickListener(new popupButtonOnClickListener(view));
                break;

            case R.id.btn_hotline_hmpgb6:
                textView.setText(R.string.sixth_button_tooltip_text);
                popupButton.setText(R.string.dial);
                popupButton.setOnClickListener(new popupButtonOnClickListener(view));
                break;

            case R.id.btn_settings:
                goToActivity(new SettingsActivity());
                break;

            case R.id.btn_logout:
                deleteSharedPreferenceValues();
                goToActivity(new LoginActivity());
                break;
        }

        new Tooltip.Builder(this)
                .anchor(view, Tooltip.TOP)
                .content(child)
                .into(frameLayout)
                .withTip(new Tooltip.Tip(10, 10, android.R.color.holo_blue_dark))
                .show();
    }

    public class popupButtonOnClickListener implements View.OnClickListener {
        View anchorView;

        public popupButtonOnClickListener(View view) {
            this.anchorView = view;
        }

        @Override
        public void onClick(View view) {
            switch (anchorView.getId()) {
                case R.id.btn_expert_mamu_hmpgb1:
                    goToActivity(new ExpertMamuQuestionActivity());
                    break;

                case R.id.btn_normal_mamu_hmpgb2:
                    goToActivity(new ChatActivity());
                    break;

                case R.id.btn_video_challenge_hmpgb3:
                    goToActivity(new VideoChallengeActivity());
                    break;

                case R.id.challenge_mamau_hmpgb4:
                    goToActivity(new ChallengeMamuQuestionActivity());
                    break;

                case R.id.btn_contact_us_hmpgb5:
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_EMAIL, "emailaddress@emailaddress.com");
                    startActivity(Intent.createChooser(intent, "Send Email"));
                    break;

                case R.id.btn_hotline_hmpgb6:
                    String phone_number = getString(R.string.hotline_number);
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone_number, null)));
                    break;
            }

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

    @Override
    public void onBackPressed() {
        ExitAppFragment exitAppFragment = new ExitAppFragment();
        exitAppFragment.show(this.getSupportFragmentManager(), null);
    }
}
