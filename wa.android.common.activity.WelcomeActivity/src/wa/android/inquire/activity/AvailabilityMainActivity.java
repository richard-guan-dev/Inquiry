package wa.android.inquire.activity;

import wa.android.common.activity.BaseActivity;
import wa.android.nc.availabilityinquire.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class AvailabilityMainActivity extends BaseActivity {
	private TextView attentionText, sortText, historyText;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main); 

		attentionText = (TextView) findViewById(R.id.attention);
		historyText = (TextView) findViewById(R.id.history);
		sortText = (TextView) findViewById(R.id.sort);

		sortText.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent i = new Intent();
				i.setClass(AvailabilityMainActivity.this, AvailabilitySearchActivity.class);
				startActivity(i);
			}
		});

		historyText.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent i = new Intent();
				i.setClass(AvailabilityMainActivity.this, AvailabilityHistoryActivity.class);
				startActivity(i);
			}
		});

		attentionText.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent i = new Intent();
				i.setClass(AvailabilityMainActivity.this, AvailabilityAttentionActivity.class);
				startActivity(i);
			}
		});
	}

}
