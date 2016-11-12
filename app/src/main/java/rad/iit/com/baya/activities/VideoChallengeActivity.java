package rad.iit.com.baya.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import rad.iit.com.baya.R;
import rad.iit.com.baya.activities.template.TemplateActivity;

/**
 * Created by Zayed on 06-Oct-16.
 */
public class VideoChallengeActivity extends TemplateActivity implements View.OnClickListener {

    Button myVideosButton,allVideosButton, facebookGroupButton,youtubeChanelButton;

    public static final String INTENT_EXTRA = "Own";

    @Override
    public void initView() {

        setContentView(R.layout.activity_video_challenge_first_page);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myVideosButton=(Button) findViewById(R.id.btn_my_videos_button);
        allVideosButton=(Button) findViewById(R.id.btn_all_videos);
        facebookGroupButton=(Button) findViewById(R.id.btn_facebook_group_link);
        youtubeChanelButton=(Button) findViewById(R.id.btn_youtube_chanel_link);

        myVideosButton.setOnClickListener(this);
        allVideosButton.setOnClickListener(this);
        facebookGroupButton.setOnClickListener(this);
        youtubeChanelButton.setOnClickListener(this);

        setTitle(getResources().getString(R.string.video_challenge));
    }

    @Override
    public void loadData() {

    }

    @Override
    public void initializeViewByData() {

    }

    @Override
    public void listenView() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_my_videos_button:
                goToActivity(new VideoChallengeListActivity(),"yes");
                break;

            case R.id.btn_all_videos:
                goToActivity(new VideoChallengeListActivity(),"no");
                break;

            case R.id.btn_facebook_group_link:
            case R.id.btn_youtube_chanel_link:
                String url= ((Button)view).getText().toString();
                if (!url.startsWith("http://") && !url.startsWith("https://"))
                    url = "http://" + url;
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
                break;
        }
    }

    public void goToActivity(Activity activity,String ownOrNot)
    {
        Intent intent=new Intent(this,activity.getClass());
        intent.putExtra(INTENT_EXTRA,ownOrNot);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
