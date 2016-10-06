package rad.iit.com.baya.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rad.iit.com.baya.Adapter.YoutubeListAdapter;
import rad.iit.com.baya.R;
import rad.iit.com.baya.data.constants.ApplicationConstants;
import rad.iit.com.baya.datamodels.YoutubeVideoModel;
import rad.iit.com.baya.utils.CustomToast;

/**
 * Created by Zayed on 06-Oct-16.
 */
public class VideoChallengeListActivity extends AppCompatActivity {

    RecyclerView youtubeVideoRecyclerView;

    ArrayList<String> mockUrls;
    ArrayList<YoutubeVideoModel> youtubeVideos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video_challenge_list);

        youtubeVideoRecyclerView=(RecyclerView) findViewById(R.id.rclv_youtube_videos);

        mockUrls=new ArrayList<>();
        mockUrls.add("TXGTA3X99g0");
        mockUrls.add("Kf6MsltI7lQ");

        youtubeVideos=new ArrayList<>();

        loadData();
    }

    public void loadData() {

        final ProgressDialog progressDialog = new ProgressDialog(VideoChallengeListActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        final CustomToast customToast = new CustomToast(VideoChallengeListActivity.this);
        JsonObjectRequest addUserRequest= new JsonObjectRequest(ApplicationConstants.YOUTUBE_VIDEO_DETAILS_URL,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
/*                Log.d("Res",jsonObject.toString());
                Log.d("Res", jsonObject.toString());
                customToast.showLongToast(jsonObject.toString());*/

                try {
                    JSONArray itemsArray=jsonObject.getJSONArray("items");

                    for (int i=0;i<itemsArray.length();i++) {
                        JSONObject jsonObject1=(JSONObject) itemsArray.get(i);
                        JSONObject snippetJsonObject= jsonObject1.getJSONObject("snippet");
                        JSONObject thumbObject=snippetJsonObject.getJSONObject("thumbnails");
                        JSONObject mediumObject=thumbObject.getJSONObject("medium");
                        String imageURL=mediumObject.getString("url");
                        String title=snippetJsonObject.getString("title");

                        youtubeVideos.add(new YoutubeVideoModel(title,imageURL));
                    }

/*                    for (YoutubeVideoModel youtubeVideoModel :
                            youtubeVideos) {
                        Log.i("Object : ", youtubeVideoModel.getTitle() + ";" + youtubeVideoModel.getUrl());
                    }*/

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(VideoChallengeListActivity.this);
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    youtubeVideoRecyclerView.setLayoutManager(linearLayoutManager);
                    YoutubeListAdapter youtubeListAdapter=new YoutubeListAdapter(VideoChallengeListActivity.this,youtubeVideos);
                    youtubeVideoRecyclerView.setAdapter(youtubeListAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.d("Err", volleyError.toString());
                customToast.showLongToast(volleyError.toString());
            }
        }) {
            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("part","snippet");
                params.put("key","AIzaSyAeZwtQvxh4vrNag7InMh8KCxp2N3HeK-4");
                params.put("id","TXGTA3X99g0,Kf6MsltI7lQ,xGhK6qgPtM");
                return params;
            };
        } ;
        Volley.newRequestQueue(VideoChallengeListActivity.this).add(addUserRequest);

    }
}
