package rad.iit.com.baya.activities.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import rad.iit.com.baya.R;
import rad.iit.com.baya.activities.HomePageActivity;
import rad.iit.com.baya.activities.LoginActivity;
import rad.iit.com.baya.data.constants.ApplicationConstants;
import rad.iit.com.baya.utils.CustomToast;


public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_chat);
        if (ApplicationConstants.user.equals("")){
            CustomToast customToast = new CustomToast(ChatActivity.this);
            customToast.showLongToast("Sorry! Please Login Again");
            Intent intent = new Intent(ChatActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }
}
