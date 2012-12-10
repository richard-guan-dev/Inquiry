package wa.android.common.activity;

import wa.android.common.App;
import wa.android.common.Module;
import wa.android.nc.availabilityinquire.R;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 多module时，将会使用这个Activity作为主面板，用来提示各module的信息及启动各module的主类
 * 
 * @author Omi
 * 
 */
public class MainBoardActivity extends BaseActivity {

	GridView gridView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initialViews();
		initialData();
	}

	private void initialViews() {
		setContentView(R.layout.activity_mainboard);
		gridView = (GridView) findViewById(R.id.mainboard_gridView);
		GridAdapter adapter = new GridAdapter();
		gridView.setAdapter(adapter);
		if (App.moduleList != null && App.moduleList.size() > 1) {
			for (Module m : App.moduleList) {
				m.onBoard((GridItemView) gridView.getChildAt(App.moduleList.indexOf(m)),
						null);
			}
		}
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Class<?> clazz = App.moduleList.get(position).getMainClazz();
				startActivity(new Intent(MainBoardActivity.this,clazz));
			}
		});

		// may need to invalidate once;
		// gridView.postInvalidate();
	}

	private void initialData() {
		// TODO: initial data if need
	}

	/**
	 * 一个图标的封装View，多module时使用 目前只可以设置
	 * 
	 * @author Omi
	 * 
	 */
	public class GridItemView extends RelativeLayout {

		public GridItemView(Context context) {
			super(context);
			intial();
		}

		public GridItemView(Context context, AttributeSet attrs) {
			super(context, attrs);
			intial();
		}

		public GridItemView(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			intial();
		}

		@SuppressWarnings("static-access")
		private void intial() {
			// TODO:初始化一个图标的View，布局
			GridItemView.this.inflate(MainBoardActivity.this, R.layout.mainboard_item, GridItemView.this);
		}

		/**
		 * 
		 */
		public void setTipNum(int number) {
			// TODO: 设置提示数量
			TextView textView = (TextView)GridItemView.this.findViewById(R.id.mainboard_item_tipNumTextView);
		}

		/**
		 * 
		 */
		public void setTipBackgroundColor(int tipBackgroundColor) {
			// TODO: 设置提示背景颜色
			ViewGroup container = (ViewGroup)GridItemView.this.findViewById(R.id.mainboard_item_tipContainer);
		}
	}

	/**
	 * 
	 * 主面板GridView所使用的adapter
	 * 
	 * @author Omi
	 * 
	 */
	class GridAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return App.moduleList.size();
		}

		@Override
		public Object getItem(int position) {
			if (position < App.moduleList.size())
				return App.moduleList.get(position);
			else
				return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO: 生成图标
			// TODO: 点击启动对应的module的activity
			if (convertView == null) {
				convertView = new GridItemView(MainBoardActivity.this);
			}
			TextView textView = (TextView)((GridItemView)convertView).findViewById(R.id.mainboard_item_titleTextView);
			textView.setText(App.moduleList.get(position).getTitle());
			// TODO: 设置tip
			return convertView;
		}

		@Override
		public boolean isEmpty() {
			return (App.moduleList == null || App.moduleList.isEmpty());
		}
	}
}
