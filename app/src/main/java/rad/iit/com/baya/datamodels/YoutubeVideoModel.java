package rad.iit.com.baya.datamodels;

/**
 * Created by Zayed on 06-Oct-16.
 */
public class YoutubeVideoModel {
    private String title;
    private String url;
    private String imageUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public YoutubeVideoModel(String title,String url,String imageUrl)
    {
        this.title=title;
        this.url =url;
        this.setImageUrl(imageUrl);
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
