package rad.iit.com.baya.data.constants;

/**
 * Created by iit on 8/16/2016.
 */
public class ApplicationConstants {

    public static int CATEGORY_ID = 0;
    public static  final String INDEX_URL="http://trimarkworld.com/mamu/";
    public static final String ADD_USER_URL = INDEX_URL+"register.php/";
    public static final String CHAT_URL = "http://10.100.107.5/mamu/a/"+"getAllChat.php?user=";
    public static final String LOGIN_URL = INDEX_URL+"login.php/";
    public static final String HELLO_URL=INDEX_URL+"annual.php/";

    public static final String YOUTUBE_VIDEO_DETAILS_URL="https://www.googleapis.com/youtube/v3/videos?part=snippet&key=AIzaSyAeZwtQvxh4vrNag7InMh8KCxp2N3HeK-4&id=";
    public static final String YOUTBE_VIDEOS_LIST_URL=INDEX_URL+"getVideos.php/";
    public static final String CATEGORIES_GET_URL=INDEX_URL + "getCategories.php/";
    public static final String CHALLENGES_GET_URL=INDEX_URL+"getChallenges.php/";
    public static final String EXPERT_ANSWERS_GET_URL=INDEX_URL+"getExpertAnswers.php/";
    public static final String ADD_CHALLENGE_URL = INDEX_URL + "addChallenge.php/";
    public static final String ADD_QUESTION_URL = INDEX_URL + "addExpertQuestion.php/";

    public static  final String TOKEN_KEY ="token";
    public static  final String USER_KEY ="user";
    public static final String ID_KEY ="ID";
    public static final String SUCCESS_KEY ="success";
    public static final String ERROR_KEY ="error";


    public static   String TOKEN_VALUE ="null";
    public static  long ID_VALUE =-1;
    public static  String SUCCESS_VALUE ="null";
    public static  String ERROR_VALUE ="null";

    public static final String SHARED_PREFERENCE = "mamu";
    public static final String USER_MODEL ="user_model";

    /*
     * demo question, answer, temporary
     */
    public static String demo_question="This is a demo question. This is a demo question. This is a demo question. This is a demo question.";
    public static String demo_answer="This is a demo answer. This is a demo answer. This is a demo answer. This is a demo answer.";
    public static String LANGUAGE="bn";
    // chat server url
    public static final String CHAT_SERVER_URL = "http://10.100.107.5:3000/";
    public static String user ="";

}
