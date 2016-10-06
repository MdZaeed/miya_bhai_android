package rad.iit.com.baya.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import rad.iit.com.baya.R;
import rad.iit.com.baya.datamodels.YoutubeVideoModel;

/**
 * Created by BS62 on 6/20/2016.
 */
public class YoutubeListAdapter extends RecyclerView.Adapter<YoutubeListAdapter.YoutubeVideoHolder> {

    ArrayList<YoutubeVideoModel> youtubeVideoData;
    protected Context context;

    public YoutubeListAdapter(Context context,ArrayList<YoutubeVideoModel> youtubeVideoData)
    {
        this.context=context;
        this.youtubeVideoData=youtubeVideoData;
    }

    @Override
    public YoutubeVideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_youtube_video, parent, false);
        return new YoutubeVideoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(YoutubeVideoHolder holder, int position) {
        YoutubeVideoModel youtubeVideoModel=getItem(position);
        String title=youtubeVideoModel.getTitle();
        String url=youtubeVideoModel.getUrl();

        holder.youtubeVideoNameTextView.setText(title);
        Picasso.with(context).load(url).fit().into(holder.youtubeVideoImageView);
    }

    @Override
    public int getItemCount() {
        return youtubeVideoData.size();
    }

    public class YoutubeVideoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView youtubeVideoImageView;
        public TextView youtubeVideoNameTextView;

        public YoutubeVideoHolder(View itemView) {
            super(itemView);

            youtubeVideoImageView = (ImageView) itemView.findViewById(R.id.iv_youtube_video);
            youtubeVideoNameTextView = (TextView) itemView.findViewById(R.id.tv_youtube_video_title);

            youtubeVideoImageView.setOnClickListener(this);
            youtubeVideoNameTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_VIEW);

            String title = "Choose an app";
            Intent chooser = Intent.createChooser(intent, title);
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(chooser);
            }
        }
    }

    public YoutubeVideoModel getItem(int position) {
        return youtubeVideoData.get(position);
    }

}
