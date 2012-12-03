package wa.android.inquire.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import nc.vo.wa.component.available.AvailableInfoVO;
import nc.vo.wa.component.available.MaterialVO;
import nc.vo.wa.component.available.OrgnaTpNum;
import nc.vo.wa.component.struct.Action;
import nc.vo.wa.component.struct.Actions;
import nc.vo.wa.component.struct.ParamTagVO;
import nc.vo.wa.component.struct.ReqParamsVO;
import nc.vo.wa.component.struct.WAComponentInstanceVO;
import nc.vo.wa.component.struct.WAComponentInstancesVO;

import wa.android.common.activity.BaseActivity;
import wa.android.common.activity.BaseActivity.OnVORequestedListener;
import wa.android.constants.ActionTypes;
import wa.android.constants.ComponentIds;
import wa.android.constants.Servers;
import wa.android.constants.WAPreferences;
import wa.android.u8.inquire.R;
import wa.framework.component.network.VOHttpResponse;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class Detail extends BaseActivity {
	// 定义一个静态变量i,放在绑定在ImageButton detailTitalAddMatter的监听器里面起到记数作用。
	static int i = 1;

	private void getInfo(int invid, String qdate){

		// 这个是从Search传过来的invid，别忘了赋值。
	    //invid = 020101;
		// 请求invid的资料
		
		final ProgressDialog dialog = new ProgressDialog(Detail.this);
		dialog.setTitle("物料查询");
		dialog.setMessage("正在查询...");
		dialog.show();
		WAComponentInstancesVO waComponentInstancesVO = new WAComponentInstancesVO();
		ArrayList<WAComponentInstanceVO> waComponentInstanceVOList = new ArrayList<WAComponentInstanceVO>();
		WAComponentInstanceVO waComponentInstanceVO = new WAComponentInstanceVO();
		waComponentInstanceVO.setComponentid(ComponentIds.WA00009);

		Actions actions = new Actions();
		ArrayList<Action> actionList = new ArrayList<Action>();
		Action action = new Action();
		action.setActiontype(ActionTypes.INQUIRE_GETINVCNT);
		// Request Params
		ReqParamsVO paramVO = new ReqParamsVO();
		ArrayList<ParamTagVO> params = new ArrayList<ParamTagVO>();

		String groupId = readPreference(WAPreferences.GROUP_ID);
		String userId = readPreference(WAPreferences.USER_ID);
		params.add(new ParamTagVO("groupid", groupId));
		params.add(new ParamTagVO("usrid", userId));
		params.add(new ParamTagVO("invid", String.valueOf(invid)));
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		params.add(new ParamTagVO("qrydate", qdate));
		
		paramVO.setParamlist(params);
		action.setParamstags(paramVO);
		actionList.add(action);
		actions.setActions(actionList);
		waComponentInstanceVO.setActions(actions);

		waComponentInstanceVOList.add(waComponentInstanceVO);
		waComponentInstancesVO.setWaci(waComponentInstanceVOList);
		

		requestVO(Servers.getServerAddress(Detail.this)
				+ Servers.SERVER_SERVLET_WA, waComponentInstancesVO,
				new OnVORequestedListener() {
					@Override
					public void onVORequested(VOHttpResponse vo) {
						Action action = vo.getmWAComponentInstancesVO()
								.getWaci().get(0).getActions().getActions()
								.get(0);
						int flag = action.getResresulttags().getFlag();
						if (flag == 0) {
							//这里是成功验证后返回的数据
							AvailableInfoVO availableInfoVO =  (AvailableInfoVO) action.getResresulttags().getServcieCodesRes().getScres().get(0).getResdata().getList().get(0);
							//availableInfoVO.getAttributesMap().get(key); availableInfoVO 里面的信息用上面的方法去获取

							
							
						} else {
							toastMsg("加载失败"
									+ action.getResresulttags().getDesc());
							dialog.dismiss();
						}
					}

					@Override
					public void onVORequestFailed(VOHttpResponse vo) {
						dialog.dismiss();
					}
				});
	}
	/**
	 * 
	 * @param invid 物料id
	 * @param op     0 为添加 其他值为删除
	 */
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		//
		//在开始时调用getInfo这个方法，然后在设置日期后再次调用，每次调用只需要修改getInfo的参数即可，据台在哪里调用麻烦写界面的加一下吧~
		//getInfo(invid, yyyy-mm-dd);

		// 定义一个AlertDialog.Builder对象 ，用于在绑定ImageButton
		// detailPickDate的监听器里面生成一个日期选择器的Dialog
		final Builder builder = new AlertDialog.Builder(this);

		// 获取ID为et1的EditText控件er1.当做搜索栏使用
		EditText et1 = (EditText) findViewById(R.id.et1);

		// 为DI为listview2的ListView填充内容，ListView里面装的是物料的详细信息。
		ListView listview2 = (ListView) findViewById(R.id.ListView2);
		String[] arr = { "792545752           电脑", "792545752           电脑",
				"792545752           电脑 ", "792545752           电脑",
				"792545752           电脑" };
		ArrayAdapter<String> arrayAdatper = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, arr);
		listview2.setAdapter(arrayAdatper);

		// 获取UI界面中ID为R.id.detail_pick_date的按钮
		// ,并且为detail_pick_date按钮绑定一个单击事件的监听器，点击此按钮可以进入一个日日期选择器
		ImageButton detailPickDate = (ImageButton) findViewById(R.id.detail_pick_date);
		detailPickDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Calendar c = Calendar.getInstance();
				// 直接创建一个DatePickerDialog对话框实例，并将它显示出来
				new DatePickerDialog(Detail.this,
				// 绑定监听器
						new DatePickerDialog.OnDateSetListener() {
							@Override
							public void onDateSet(DatePicker dp, int year,
									int month, int dayOfMonth) {

							}
						}
						// 设置初始日期
						, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
								.get(Calendar.DAY_OF_MONTH)).show();

			}
		});
		// 获取UI界面中ID为R.id.detail_tital_add_matter的按钮 ,并且为b3按钮绑定一个单击事件的监听器
		ImageButton detailTitalAddMatter = (ImageButton) findViewById(R.id.detail_tital_add_matter);
		detailTitalAddMatter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				ImageButton detailTitalAddMatter = (ImageButton) findViewById(R.id.detail_tital_add_matter);
				if (i % 2 != 0) {

					ImageButton detailMatteredSign = (ImageButton) findViewById(R.id.detail_mattered_sign);
					detailMatteredSign.setVisibility(View.VISIBLE);
					detailTitalAddMatter
							.setImageResource(R.id.detail_tital_add_matter);
					i++;

				} else {
					ImageButton detailMatteredSign = (ImageButton) findViewById(R.id.detail_mattered_sign);
					detailMatteredSign.setVisibility(View.INVISIBLE);
					detailTitalAddMatter
							.setImageResource(R.drawable.salary_detail_bottom_btn_set_down);
					i = 1;
				}
				;

			}
		});
		// 获取UI界面中ID为R.id.detail_search的按钮 ,并且为detail_search按钮绑定一个单击事件的监听器
		// 点击之后进行搜索
		Button detailSearch = (Button) findViewById(R.id.detail_search);
		detailSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

			}
		});

		// 获取UI界面中ID为R.id.detail_tital_return的按钮
		// ,并且为detail_tital_return按钮绑定一个单击事件的监听器 点击之后返回上一界面
		final ImageButton detailTitalRetrun = (ImageButton) findViewById(R.id.detail_tital_return);
		detailTitalRetrun.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		// 获取UI界面中ID为R.id.detail_mattered_sign的按钮
		// ,并且为detail_mattered_sign按钮绑定一个单击事件的监听器 ，用于显示是否进行了关注，显示了关注，不显示则不关注
		ImageButton detailMatteredSign = (ImageButton) findViewById(R.id.detail_mattered_sign);
		detailMatteredSign.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

}
