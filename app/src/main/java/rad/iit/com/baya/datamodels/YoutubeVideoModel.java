package rad.iit.com.baya.datamodels;

/**
 * Created by Zayed on 06-Oct-16.
 */
public class YoutubeVideoModel {
    private String title;
    private String url;

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

    public YoutubeVideoModel(String title,String url)
    {
        this.title=title;
        this.url =url;
    }
}
