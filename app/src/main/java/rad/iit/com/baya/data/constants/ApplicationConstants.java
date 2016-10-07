package rad.iit.com.baya.data.constants;

/**
 * Created by iit on 8/16/2016.
 */
public class ApplicationConstants {

    public static  final String INDEX_URL="http://10.100.107.5/mamu/";
    public static final String ADD_USER_URL = INDEX_URL+"register.php/";
    public static final String LOGIN_URL = INDEX_URL+"login.php/";
    public static final String HELLO_URL=INDEX_URL+"annual.php/";

    public static final String YOUTUBE_VIDEO_DETAILS_URL="https://www.googleapis.com/youtube/v3/videos?part=snippet&key=AIzaSyAeZwtQvxh4vrNag7InMh8KCxp2N3HeK-4&id=";
    public static final String YOUTBE_VIDEOS_LIST_URL="http://trimarkworld.com/mamu/getVideos.php";

    public static  final String TOKEN_KEY ="token";
    public static final String ID_KEY ="ID";
    public static final String SUCCESS_KEY ="success";
    public static final String ERROR_KEY ="error";


    public static   String TOKEN_VALUE ="null";
    public static  long ID_VALUE =-1;
    public static  String SUCCESS_VALUE ="null";
    public static  String ERROR_VALUE ="null";

    public static final String SHARED_PREFERENCE = "mamu";
    public static final String USER_MODEL ="user_model";
}
