package rad.iit.com.baya.datamodels;

import org.json.JSONObject;

/**
 * Created by iit on 8/16/2016.
 */
public class User extends JSONObject{
    public String userName ="";
    public String password ="";

    public static class Variable{
        public static final String USER_NAME ="Username";
        public static final String PASS_WORD ="Password";
    }

}
