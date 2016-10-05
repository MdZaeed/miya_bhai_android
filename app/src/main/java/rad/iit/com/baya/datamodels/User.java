package rad.iit.com.baya.datamodels;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by iit on 8/16/2016.
 */
public class User {
    public String userName ="";
    public String mobileNumber ="";
    public String bday="";
    public String sex="";

    public static class Variable{
        public static final String USER_NAME ="name";
        public static final String MOBILE ="mobile";
        public static final String BIRTH_DAY ="bday";
        public static final String SEX ="sex";
    }

    @Override
    public String toString() {
        return "{\"user_model\":{"+"\"name\":\""+userName+"\",\"mobile\":\""+ mobileNumber +"\","+"\"bday\":\""+bday+"\",\"sex\":\""+ sex +"\""+"}}";
    }

    public JSONObject getSignUpJSON(){
        JSONObject signUPJSON= null;
        try {
            signUPJSON = new JSONObject(toString());
        } catch (JSONException e) {
            e.printStackTrace();
            return  null;
        }
        return signUPJSON;
    }

    public JSONObject getSignInJSON(){
        JSONObject signUPJSON= null;
        try {
            signUPJSON = new JSONObject("{\"user_model\":{"+"\"name\":\""+userName+"\",\"mobile\":\""+ mobileNumber +"\"}}");
        } catch (JSONException e) {
            e.printStackTrace();
            return  null;
        }
        return signUPJSON;
    }

}
