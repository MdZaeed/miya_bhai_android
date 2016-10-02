package rad.iit.com.baya.activities;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import rad.iit.com.baya.R;
import rad.iit.com.baya.activities.template.TemplateActivity;

public class CategoryViewActivity extends TemplateActivity {

    public static String CATEGORY[]={"Category 1","Category 1","Category 1","Category 1","Category 1",};
    LinearLayout linearLayout;
    @Override
    public void initView() {
        setContentView(R.layout.activity_category_view);
        linearLayout = (LinearLayout) findViewById(R.id.linear_page5);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void initializeViewByData() {
        addCategoryButtons();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void addCategoryButtons() {
        for (int i = 0; i < CATEGORY.length; i++) {
            Button button = new Button(this,null,i,R.style.ButtonStyle);
            button.setText(CATEGORY[i]);
            linearLayout.addView(button, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        }
    }

    @Override
    public void listenView() {

    }
}
