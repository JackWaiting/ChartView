package retrofit.jackwaiting.jackwaiting_chars.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import retrofit.jackwaiting.jackwaiting_chars.R;
import retrofit.jackwaiting.jackwaiting_chars.view.RuleCircleView;

public class

        RuleCircleActivity extends AppCompatActivity {

    private RuleCircleView mRuleCircleView;
    private TextView mRandomNum;
    private Button btnTest;

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


    //获取模拟的随机数
    public float getRandom(int maxValue) {
        return (float) (Math.random() * maxValue);
    }

    //初始化
    private void init() {
        mRuleCircleView = (RuleCircleView) findViewById(R.id.mRuleCircleView);
        mRandomNum = (TextView) findViewById(R.id.mRandomNum);
        btnTest = (Button) findViewById(R.id.btn_test);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float random = getRandom(10);
                mRuleCircleView.setDynamicDegrees(random);

                mRandomNum.setText((random + "").substring(0, 4));
            }
        });
    }


}
