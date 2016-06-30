package retrofit.jackwaiting.jackwaiting_chars.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import retrofit.jackwaiting.jackwaiting_chars.R;
import retrofit.jackwaiting.jackwaiting_chars.view.ChartView;

public class ChartActivity extends AppCompatActivity {

    private ChartView charView;
    private float testData[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        init();
    }

    private void init() {
        charView = (ChartView) findViewById(R.id.mCharView);
        testData = new float []{7.70f,5.60f,7.50f,7.85f,6.33f,5.32f,7.25f,6.18f,5.10f,7.01f,6.85f,6.75f,6.74f,6.70f,6.65f,7.63f,6.58f,3.54f,4.50f,5.48f,7.43f,6.38f,6.32f,6.28f,6.18f,6.07f,5.97f,4.50f,4.20f,3.99f,3.33f};
        //testData = new float []{7.90f,5.60f,2.50f,7.45f,7.33f,7.32f,1.25f,7.18f,7.10f,0.01f,6.85f,6.75f,6.74f,5.70f,6.65f,6.63f,6.58f,6.54f,6.50f,4.48f,6.43f,6.38f,6.32f,3.28f,6.18f,6.07f,2.97f,4.50f,1.20f,3.99f,3.33f};
        charView.getLifeTimeData(testData);
    }
}
