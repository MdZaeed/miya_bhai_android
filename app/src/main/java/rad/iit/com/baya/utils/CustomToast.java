package rad.iit.com.baya.utils;

/**
 * Created by iit on 8/16/2016.
 */
import android.content.Context;
import android.widget.Toast;

/**
 * Created by chandradasdipok on 4/3/2016.
 */
public class CustomToast {

    Context context;
    public CustomToast(Context context) {
        this.context = context;
    }
    public void showLongToast(String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
    public void showShortToast(String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

}