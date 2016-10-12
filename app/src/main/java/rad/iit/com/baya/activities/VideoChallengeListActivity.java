package rad.iit.com.baya.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import rad.iit.com.baya.adapters.YoutubeListAdapter;
import rad.iit.com.baya.R;
import rad.iit.com.baya.data.constants.ApplicationConstants;
import rad.iit.com.baya.datamodels.VideosResponse;
import rad.iit.com.baya.datamodels.Video;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video_challenge_list);

        youtubeVideoRecyclerView = (RecyclerView) findViewById(R.id.rclv_youtube_videos);
        youtubeVideos = new ArrayList<>();
        responseFromServer = new ArrayList<>();
        videoIds=new ArrayList<>();
        responseFromServer = loadDataInUrlList();
    }

    public ArrayList<String> createYoutubeIdList(ArrayList<String> urls) {
        ArrayList<String> ids = new ArrayList<>();

        for (String url :
                urls) {

            if (url.startsWith("https://www.youtube.com/watch?v=")) {
                String[] parts = url.split("v=");
                ids.add(parts[1]);

/*
                Toast.makeText(this,parts[1],Toast.LENGTH_LONG).show();
*/
            }
        }

        return ids;
    }

    public ArrayList<String> loadDataInUrlList() {
        final ArrayList<String> tempList = new ArrayList<String>();

        final ProgressDialog progressDialog = new ProgressDialog(VideoChallengeListActivity.this);
        progressDialog.setMessage("Loading");
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
                    tempList.add(video.getLink());
                }

                videoIds =createYoutubeIdList(tempList);
                loadData();

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.d("Err", volleyError.toString());
                customToast.showLongToast(volleyError.toString());
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
        progressDialog.setMessage("Loading");
        progressDialog.show();

        final CustomToast customToast = new CustomToast(VideoChallengeListActivity.this);

        Toast.makeText(VideoChallengeListActivity.this,"Ids:" + getUrlsAsString(videoIds),Toast.LENGTH_LONG).show();

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

                        youtubeVideos.add(new YoutubeVideoModel(title, imageURL));

                        Toast.makeText(VideoChallengeListActivity.this,"Vidoes:" + title,Toast.LENGTH_LONG).show();

                    }

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(VideoChallengeListActivity.this);
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    youtubeVideoRecyclerView.setLayoutManager(linearLayoutManager);
                    YoutubeListAdapter youtubeListAdapter = new YoutubeListAdapter(VideoChallengeListActivity.this, youtubeVideos);
                    youtubeVideoRecyclerView.setAdapter(youtubeListAdapter);

/*
                    Toast.makeText(VideoChallengeListActivity.this,"Ids:" + getUrlsAsString(videoIds),Toast.LENGTH_LONG).show();
*/
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
                customToast.showLongToast(volleyError.toString());
            }
        });
        Volley.newRequestQueue(VideoChallengeListActivity.this).add(addUserRequest);

    }
}
