package rad.iit.com.baya.utils;

import android.content.Context;
import android.content.SharedPreferences;

import rad.iit.com.baya.data.constants.ApplicationConstants;

/**
 * deal with apps data preferences
 * Created by Dipok on 12/1/2016.
 */
public class AppManager {

    private String token ="";
    private String user ="";
    private String ID ="";
    private String success ="";
    private String error ="";
    private String language="bn";

    private SharedPreferences sharedPreferences;

    private static AppManager appManager = null;
    public static AppManager getAppManagerInstance(Context context){
        if (appManager == null){
            return  new AppManager(context);
        }
        else return appManager;
    }
    private AppManager(Context context){
        sharedPreferences = context.getSharedPreferences(ApplicationConstants.SHARED_PREFERENCE,Context.MODE_PRIVATE);
        changeToken(getToken(""));
        changeUser(getUser(""));
        changeID(getID(""));
        changeSuccess(getSuccess(""));
        changeError(getError(""));
        changeLanguage(getLanguage(""));

    }
    public String getToken(String defValue){
        String value = sharedPreferences.getString(ApplicationConstants.TOKEN_KEY,defValue);
        return value;
    }
    public String getUser(String defValue){
        String value = sharedPreferences.getString(ApplicationConstants.USER_KEY,defValue);
        return value;
    }
    public String getID(String defValue){
        String value = sharedPreferences.getString(ApplicationConstants.ID_KEY,defValue);
        return value;
    }
    public String getSuccess(String defValue){
        String value = sharedPreferences.getString(ApplicationConstants.SUCCESS_KEY,defValue);
        return value;
    }
    public String getError(String defValue){
        String value = sharedPreferences.getString(ApplicationConstants.ERROR_KEY,defValue);
        return value;
    }
    public String getLanguage(String defValue){
        String value = sharedPreferences.getString(ApplicationConstants.LANGUAGE,defValue);
        return value;
    }

    public AppManager changeToken(String token){
        this.token = token;
        return this;
    }
    public AppManager changeUser(String user){
        this.user = user;
        return this;
    }
    public AppManager changeID(String ID){
        this.ID = ID;
        return this;
    }
    public AppManager changeSuccess(String success){
        this.success = success;
        return this;
    }
    public AppManager changeError(String error){
        this.error = error;
        return this;
    }
    public AppManager changeLanguage(String language){
        if (language.equals("")){
            language ="bn";
        }
        this.language = language;
        return this;
    }
    public void store(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ApplicationConstants.TOKEN_KEY,token);
        editor.putString(ApplicationConstants.USER_KEY,user);
        editor.putString(ApplicationConstants.ID_KEY,ID);
        editor.putString(ApplicationConstants.SUCCESS_KEY,success);
        editor.putString(ApplicationConstants.ERROR_KEY,error);
        editor.putString(ApplicationConstants.LANGUAGE,language);
        editor.apply();
    }

    public boolean isLoggedIn(){
        if (!token.equals("") && !user.equals("")&& !ID.equals("")){
            return  true;
        }
        return false;
    }
    public boolean logOut(){
        changeToken("").changeUser("").changeID("").store();
        if (isLoggedIn()){
            return false;
        }
        return true;
    }

 }
