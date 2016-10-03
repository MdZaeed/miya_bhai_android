package rad.iit.com.baya.datamodels;

import org.json.JSONObject;

/**
 * Created by iit on 8/16/2016.
 */
public class User extends JSONObject{
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
        return "{"+"\"name\":\""+userName+"\",\"mobile\":\""+ mobileNumber +"\","+"\"bday\":\""+bday+"\",\"sex\":\""+ sex +"\""+"}";
    }
}
