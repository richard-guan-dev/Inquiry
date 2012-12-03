package wa.android.inquire.activity;

import wa.android.common.activity.BaseActivity;
import wa.android.u8.inquire.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class Main extends BaseActivity {
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
				i.setClass(Main.this, Search.class);
				startActivity(i);
			}
		});

		historyText.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent i = new Intent();
				i.setClass(Main.this, History.class);
				startActivity(i);
			}
		});

		attentionText.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent i = new Intent();
				i.setClass(Main.this, Attention.class);
				startActivity(i);
			}
		});
	}

}
