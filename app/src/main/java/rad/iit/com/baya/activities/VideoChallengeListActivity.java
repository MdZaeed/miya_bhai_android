package rad.iit.com.baya.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import rad.iit.com.baya.R;
import rad.iit.com.baya.adapters.YoutubeListAdapter;
import rad.iit.com.baya.data.constants.ApplicationConstants;
import rad.iit.com.baya.datamodels.Video;
import rad.iit.com.baya.datamodels.VideosResponse;
import rad.iit.com.baya.datamodels.YoutubeVideoModel;
import rad.iit.com.baya.utils.CustomToast;

/**
 * Created by Zayed on 06-Oct-16.
 */
public class VideoChallengeListActivity extends AppCompatActivity {

    RecyclerView youtubeVideoRecyclerView;

    ArrayList<String> responseFromServer;
    ArrayList<String> videoIds;
    ArrayList<YoutubeVideoModel> youtubeVideos;
    String ownOrNot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ownOrNot=getIntent().getStringExtra(VideoChallengeActivity.INTENT_EXTRA);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(getLanguage().equals(getResources().getString(R.string.bangla_string)))
        {
            setLanguageInApp(getResources().getString(R.string.bangla_string));
        } else if(getLanguage().equals(getResources().getString(R.string.english_string)))
        {
            setLanguageInApp(getResources().getString(R.string.english_string));
        }

        setContentView(R.layout.activity_video_challenge_list);

        youtubeVideoRecyclerView = (RecyclerView) findViewById(R.id.rclv_youtube_videos);
        youtubeVideos = new ArrayList<>();
        responseFromServer = new ArrayList<>();
        videoIds=new ArrayList<>();
        responseFromServer = loadDataInUrlList();

        setTitle(getResources().getString(R.string.video_challenge));
    }

    public void setLanguageInApp(String language_code) {
        Resources res = this.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.locale = new Locale(language_code.toLowerCase());
        res.updateConfiguration(conf, dm);
    }

    public String getLanguage() {
        SharedPreferences sharedPreferences = getSharedPreferences(ApplicationConstants.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(ApplicationConstants.LANGUAGE,getString(R.string.bangla_string));
    }

    public ArrayList<String> createYoutubeIdList(ArrayList<String> urls) {
        ArrayList<String> ids = new ArrayList<>();

        if(!urls.isEmpty()) {
            for (String url :
                    urls) {

                if (url.startsWith("https://www.youtube.com/watch?v=")) {
                    String[] parts = url.split("v=");
                    ids.add(parts[1]);
                }
            }
        }

        return ids;
    }

    public ArrayList<String> loadDataInUrlList() {
        final ArrayList<String> tempList = new ArrayList<String>();

        final ProgressDialog progressDialog = new ProgressDialog(VideoChallengeListActivity.this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();

        final CustomToast customToast = new CustomToast(VideoChallengeListActivity.this);
        JsonObjectRequest addUserRequest = new JsonObjectRequest(ApplicationConstants.YOUTBE_VIDEOS_LIST_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                VideosResponse videosResponse =new Gson().fromJson(jsonObject.toString(),VideosResponse.class);
                for (Video video:
                     videosResponse.getVideos()) {
                    if(ownOrNot.equals("yes") && video.getUploadedBy().equals(getSavedOwnId()))
                    {
                        tempList.add(video.getLink());
                    }else if(ownOrNot.equals("no")) {
                        tempList.add(video.getLink());
                    }
                }

                videoIds =createYoutubeIdList(tempList);

                if (!videoIds.isEmpty()) {
                    loadData();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.d("Err", volleyError.toString());
/*
                customToast.showLongToast(volleyError.toString());
*/
            }
        });
        Volley.newRequestQueue(VideoChallengeListActivity.this).add(addUserRequest);


        return tempList;
    }

    public String getUrlsAsString(ArrayList<String> urlStrings) {
        String queryString = "";
        for (String temp :
                urlStrings) {
            queryString = queryString + temp + ",";
        }

        queryString= String.copyValueOf(queryString.toCharArray(),0,queryString.length()-1);
        return queryString;
    }

    public void loadData() {

        final ProgressDialog progressDialog = new ProgressDialog(VideoChallengeListActivity.this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();

        final CustomToast customToast = new CustomToast(VideoChallengeListActivity.this);

        JsonObjectRequest addUserRequest = new JsonObjectRequest(ApplicationConstants.YOUTUBE_VIDEO_DETAILS_URL + getUrlsAsString(videoIds), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                try {
                    JSONArray itemsArray = jsonObject.getJSONArray("items");

                    for (int i = 0; i < itemsArray.length(); i++) {
                        JSONObject jsonObject1 = (JSONObject) itemsArray.get(i);
                        JSONObject snippetJsonObject = jsonObject1.getJSONObject("snippet");
                        JSONObject thumbObject = snippetJsonObject.getJSONObject("thumbnails");
                        JSONObject mediumObject = thumbObject.getJSONObject("medium");
                        String imageURL = mediumObject.getString("url");
                        String title = snippetJsonObject.getString("title");
                        String url=jsonObject1.getString("id");

                        youtubeVideos.add(new YoutubeVideoModel(title, url,imageURL));
                    }

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(VideoChallengeListActivity.this);
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    youtubeVideoRecyclerView.setLayoutManager(linearLayoutManager);
                    YoutubeListAdapter youtubeListAdapter = new YoutubeListAdapter(VideoChallengeListActivity.this, youtubeVideos);
                    youtubeVideoRecyclerView.setAdapter(youtubeListAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.d("Err", volleyError.toString());
/*
                customToast.showLongToast(volleyError.toString());
*/
            }
        });
        Volley.newRequestQueue(VideoChallengeListActivity.this).add(addUserRequest);

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

    public String getSavedOwnId()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(ApplicationConstants.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        String id=sharedPreferences.getString(ApplicationConstants.ID_KEY,"-1");
        return id;
    }
}
