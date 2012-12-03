package wa.android.inquire.activity;

import java.util.ArrayList;

import wa.android.common.activity.BaseActivity;
import wa.android.u8.inquire.R;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

public class History extends BaseActivity {
	private Button history2home = null;
	private ListView historyList = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acticity_history);
		
		history2home = (Button) findViewById(R.id.history2home);
		history2home.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClass(History.this, Main.class);
				startActivity(i);
			}
		});
		
		historyList = (ListView) findViewById(R.id.history_list);
		//为查询历史的列表添加元素
		//以下是简单的小例子
		String[] s = {"1", "2", "3", "4", "5", "6"};
		ArrayAdapter<String> la = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, s);
		historyList.setAdapter(la);
		
		historyList.setOnItemClickListener(new HistoryListItemOnClickListener());
	}
	
	class HistoryListItemOnClickListener implements OnItemClickListener {

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			//查看历史列表中的项目被点击后，会根据选中的物料的信息跳转到相应的物料详情的Acticity
			Intent i = new Intent(History.this, Detail.class);
			//选中中的信息传递方式。。.
			
			
			startActivity(i);
		}
		
	}
}
