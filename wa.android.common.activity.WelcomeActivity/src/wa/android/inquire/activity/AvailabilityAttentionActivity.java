package wa.android.inquire.activity;

import wa.android.common.activity.BaseActivity;
import wa.android.nc.availabilityinquire.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class AvailabilityAttentionActivity extends BaseActivity implements OnScrollListener{
	private Button editButton = null;
	private Button back2HomeBtn = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_attention);
		
		back2HomeBtn = (Button) findViewById(R.id.go_home1);
		back2HomeBtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				AvailabilityAttentionActivity.this.finish();
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
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, str);
		list.setAdapter(adapter);
		
		list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent i = new Intent(AvailabilityAttentionActivity.this, AvailabilityDetailActivity.class);
				startActivity(i);
			}
		});
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	}

}
