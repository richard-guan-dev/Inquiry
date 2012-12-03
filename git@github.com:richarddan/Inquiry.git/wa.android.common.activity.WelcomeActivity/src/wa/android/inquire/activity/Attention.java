package wa.android.inquire.activity;

import wa.android.common.activity.BaseActivity;
import wa.android.u8.inquire.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class Attention extends BaseActivity {
	private Button editButton = null;
	private Button back2HomeBtn = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_attention);
		
		back2HomeBtn = (Button) findViewById(R.id.back2home_button);
		back2HomeBtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClass(Attention.this, Main.class);
				startActivity(i);
				finish();
			}
		});
		
		editButton = (Button) findViewById(R.id.edit_button);
		editButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 打开编辑的界面
			}
		});
		
		//数据添加到ListView当中的部分，以下只是举例
		String[] str = { "1", "2", "3", "4", "5" };
		ListView list = (ListView) findViewById(R.id.list);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, str);
		list.setAdapter(adapter);
		
		list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent i = new Intent(Attention.this, Detail.class);
				startActivity(i);
			}
		});
	}

}
