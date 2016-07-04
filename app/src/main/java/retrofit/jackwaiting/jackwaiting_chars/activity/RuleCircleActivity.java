package retrofit.jackwaiting.jackwaiting_chars.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import retrofit.jackwaiting.jackwaiting_chars.R;
import retrofit.jackwaiting.jackwaiting_chars.view.RuleCircleView;

public class

        RuleCircleActivity extends AppCompatActivity {

    private RuleCircleView mRuleCircleView;
    private TextView mRandomNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);
        init();
        getRandomNum();
    }


    //获取随机数并显示
    private void getRandomNum() {
        float randomNum = (float) (Math.random() * 10);
        mRandomNum.setText((randomNum + "").subSequence(0, 4) + "V");
        mRuleCircleView.getNum6VData(randomNum);
        mRuleCircleView.setViewBackGround(R.mipmap.img_num_6v);

    }
    //初始化
    private void init() {
        mRuleCircleView = (RuleCircleView) findViewById(R.id.mRuleCircleView);
        mRandomNum = (TextView) findViewById(R.id.mRandomNum);
    }


}
