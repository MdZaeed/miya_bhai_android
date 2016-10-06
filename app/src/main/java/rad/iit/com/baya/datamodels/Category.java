package rad.iit.com.baya.datamodels;


import android.content.ContentValues;
import android.database.Cursor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chandradasdipok on 3/21/2016.
 */
public class Category {

    public int category_id;
    public String category_name;

    public Category() {
    }

    public Category(int category_id, String category_name) {
        this.category_id = category_id;
        this.category_name = category_name;
    }
    public static class Variable {
        public  static  final String INT_CATEGORY_ID ="category_id";
        public  static  final String STRING_CATEGORY_NAME ="category_name";
    }

    @Override
    public String toString() {
        return "("+ category_id+","+
                category_name+")";
    }
}
