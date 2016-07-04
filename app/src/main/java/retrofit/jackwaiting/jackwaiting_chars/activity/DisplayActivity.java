package retrofit.jackwaiting.jackwaiting_chars.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import retrofit.jackwaiting.jackwaiting_chars.R;
import retrofit.jackwaiting.jackwaiting_chars.view.DisPlayCircleView;

public class DisplayActivity extends AppCompatActivity {

    private DisPlayCircleView mDisPlayCircleView;
    private TextView mRandomNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        mDisPlayCircleView = (DisPlayCircleView) findViewById(R.id.mDisPlayCircleView);
        mRandomNum = (TextView) findViewById(R.id.mRandomNum);
        float randomNum =  (float)(Math.random()*10);
        mRandomNum.setText((randomNum + "").subSequence(0, 4) + "V");
        getBackGround(randomNum);
    }

    //根据标记获取对应的背景图
    private void getBackGround (float mRandomNum) {
        mDisPlayCircleView.setViewBackGround(R.mipmap.img_num13_v);
        mDisPlayCircleView.getNum6VData(mRandomNum);

    }
}
