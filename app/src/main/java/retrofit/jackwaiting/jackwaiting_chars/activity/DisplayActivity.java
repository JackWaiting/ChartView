package retrofit.jackwaiting.jackwaiting_chars.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import retrofit.jackwaiting.jackwaiting_chars.R;
import retrofit.jackwaiting.jackwaiting_chars.view.DisPlayCircleView;

public class DisplayActivity extends AppCompatActivity {

    private DisPlayCircleView mDisPlayCircleView;
    private TextView mRandomNum;
    private Button btnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        mDisPlayCircleView = (DisPlayCircleView) findViewById(R.id.mDisPlayCircleView);
        mRandomNum = (TextView) findViewById(R.id.mRandomNum);
        float randomNum =  (float)(Math.random()*10);
        mRandomNum.setText((randomNum + "").subSequence(0, 4) + "V");
        getBackGround(randomNum);
        btnTest = (Button) findViewById(R.id.btn_test);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float random = getRandom(32);
                mDisPlayCircleView.setDynamicDegrees(random);

                mRandomNum.setText((random + "").substring(0, 4));
            }
        });
    }


    //获取模拟的随机数
    public float getRandom(int maxValue) {
        return (float) (Math.random() * maxValue);
    }


    //根据标记获取对应的背景图
    private void getBackGround (float mRandomNum) {
        mDisPlayCircleView.setViewBackGround(R.mipmap.img_num13_v);
        mDisPlayCircleView.getNum6VData(mRandomNum);

    }
}
