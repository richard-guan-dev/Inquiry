package wa.android.common.activity;

import java.util.ArrayList;
import java.util.List;

import wa.android.nc.availabilityinquire.R;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 设置界面，任何module共有此设置界面的三个设置项 各别module会有自己单独的设置项，需在module里添加
 * 
 * @author Omi
 * 
 */
public class SettingActivity extends BaseActivity {

	private ListView listView;
	private SettingListAdapter adapter;

	private List<SettingOption> optionList = new ArrayList<SettingOption>();

	public static final String BUNDLE_KEY_OPTIONS = "BUNDLE_OPTIONS";
	public static final String BUNDLE_KEY_ABOUT_RES_ID = "BUNDLE_KEY_ABOUT_RES_ID";
	
	private int aboutResId = -1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initialView();
		initialData(savedInstanceState);
	}

	private void initialView() {
		setContentView(R.layout.activity_setting);
		listView = (ListView) findViewById(R.id.setting_listView);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Class<?> clazz = optionList.get(position).getTriggerClazz();
				if(clazz != null){
					Intent i = new Intent(getBaseContext(), clazz);
					startActivity(i);
				}
			}
		});

		adapter = new SettingListAdapter();
		listView.setAdapter(adapter);
	}

	private void initialData(Bundle savedInstanceState) {

		SettingOption aboutOption = new SettingOption();
		aboutOption.setDes("关于");
		aboutOption.setTriggerClazz(AboutActivity.class);
		optionList.add(aboutOption);

		SettingOption setConnectionOption = new SettingOption();
		setConnectionOption.setDes("连接设置");
		setConnectionOption.setTriggerClazz(SetConnectionActivity.class);
		optionList.add(setConnectionOption);

		SettingOption logoutOption = new SettingOption();
		logoutOption.setDes("退出登录");
		logoutOption.setTriggerClazz(LogoutActivity.class);
		optionList.add(logoutOption);

		System.out.println(getIntent());
		System.out.println(getIntent().getExtras());
		System.out.println(getIntent().getSerializableExtra(BUNDLE_KEY_OPTIONS));
		if (getIntent().getSerializableExtra(BUNDLE_KEY_OPTIONS) != null) {
			Object[] appendOptions = (Object[]) (getIntent().getSerializableExtra(BUNDLE_KEY_OPTIONS));
			if (appendOptions != null && appendOptions.length > 0) {
				for (Object o : appendOptions) {
					optionList.add((SettingOption)o);
				}
				aboutResId = getIntent().getIntExtra(BUNDLE_KEY_ABOUT_RES_ID,-1);
			}
		}


	}

	class SettingListAdapter implements ListAdapter {

		private LayoutInflater inflater = getLayoutInflater();

		public SettingListAdapter() {

		}

		@Override
		public int getCount() {
			return optionList.size();
		}

		@Override
		public SettingOption getItem(int position) {
			return optionList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public int getItemViewType(int position) {
			return 1;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.setting_item, null);
			}

			if (convertView != null) {
				TextView textView = (TextView) (convertView.findViewById(R.id.setting_item_title));
				textView.setTextSize(18);
				textView.setText(getItem(position).getDes());
			}
			return convertView;
		}

		@Override
		public int getViewTypeCount() {
			return 1;
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public boolean isEmpty() {
			return optionList.isEmpty();
		}

		@Override
		public void registerDataSetObserver(DataSetObserver observer) {
		}

		@Override
		public void unregisterDataSetObserver(DataSetObserver observer) {
		}

		@Override
		public boolean areAllItemsEnabled() {
			return true;
		}

		@Override
		public boolean isEnabled(int position) {
			return true;
		}

	}
}
