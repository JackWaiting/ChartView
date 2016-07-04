package retrofit.jackwaiting.jackwaiting_chars.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import retrofit.jackwaiting.jackwaiting_chars.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnChart,btnRuleCircle,btnDisplayView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btnChart = (Button) findViewById(R.id.btn_chart);
        btnRuleCircle = (Button) findViewById(R.id.btn_rule_circle);
        btnDisplayView = (Button) findViewById(R.id.btn_display_circle);
        btnChart.setOnClickListener(this);
        btnRuleCircle.setOnClickListener(this);
        btnDisplayView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_chart :
                startActivity(new Intent(MainActivity.this,ChartActivity.class));
                break;
            case R.id.btn_rule_circle :
                startActivity(new Intent(MainActivity.this,RuleCircleActivity.class));
                break;
            case R.id.btn_display_circle :
                startActivity(new Intent(MainActivity.this,DisplayActivity.class));
                break;
        }

    }
}
