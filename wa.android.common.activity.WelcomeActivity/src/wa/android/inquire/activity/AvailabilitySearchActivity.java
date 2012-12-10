package wa.android.inquire.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nc.vo.wa.component.available.AvailabledListVO;
import nc.vo.wa.component.available.MaterialVO;
import nc.vo.wa.component.struct.Action;
import nc.vo.wa.component.struct.Actions;
import nc.vo.wa.component.struct.ParamTagVO;
import nc.vo.wa.component.struct.ReqParamsVO;
import nc.vo.wa.component.struct.WAComponentInstanceVO;
import nc.vo.wa.component.struct.WAComponentInstancesVO;
import wa.android.common.activity.BaseActivity;
import wa.android.constants.ActionTypes;
import wa.android.constants.ComponentIds;
import wa.android.constants.Servers;
import wa.android.constants.WAPreferences;
import wa.android.nc.availabilityinquire.R;
import wa.framework.component.network.VOHttpResponse;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class AvailabilitySearchActivity extends BaseActivity {

	private EditText search = null;
	private Button searchBtn = null;
	private Button search2homeBtn = null;
	private ListView searchList = null;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		search = (EditText) findViewById(R.id.search_context);
		searchBtn = (Button) findViewById(R.id.search_button);
		searchBtn.setOnClickListener(new SearchButtonOnClickListener());

		search2homeBtn = (Button) findViewById(R.id.go_back2);
		search2homeBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				AvailabilitySearchActivity.this.finish();
			}
		});
	}

	// 点击搜索按钮之后的操作
	class SearchButtonOnClickListener implements OnClickListener {

		public void onClick(View v) {
			// 获得搜索栏内的内容
			String condition = search.getText().toString();
			// 与物料数据匹配，找到后生成一个数组或其他
			WAComponentInstancesVO waComponentInstancesVO = new WAComponentInstancesVO();
			ArrayList<WAComponentInstanceVO> waComponentInstanceVOList = new ArrayList<WAComponentInstanceVO>();
			WAComponentInstanceVO waComponentInstanceVO = new WAComponentInstanceVO();
			waComponentInstanceVO.setComponentid(ComponentIds.WA00009);

			Actions actions = new Actions();
			ArrayList<Action> actionList = new ArrayList<Action>();
			Action action = new Action();
			action.setActiontype(ActionTypes.INQUIRE_GETINVLIST);
			// Request Params
			ReqParamsVO paramVO = new ReqParamsVO();
			ArrayList<ParamTagVO> params = new ArrayList<ParamTagVO>();

			String groupId = readPreference(WAPreferences.GROUP_ID);
			String userId = readPreference(WAPreferences.USER_ID);
			params.add(new ParamTagVO("groupid", groupId));
			params.add(new ParamTagVO("usrid", userId));
			params.add(new ParamTagVO("condition", condition));
			params.add(new ParamTagVO("startline", "1"));
			params.add(new ParamTagVO("count", "10"));

			paramVO.setParamlist(params);
			action.setParamstags(paramVO);
			actionList.add(action);
			actions.setActions(actionList);
			waComponentInstanceVO.setActions(actions);

			waComponentInstanceVOList.add(waComponentInstanceVO);
			waComponentInstancesVO.setWaci(waComponentInstanceVOList);

			final ProgressDialog dialog = new ProgressDialog(AvailabilitySearchActivity.this);
			dialog.setTitle("物料查询");
			dialog.setMessage("正在查询...");
			dialog.show();

			requestVO(Servers.getServerAddress(AvailabilitySearchActivity.this)
					+ Servers.SERVER_SERVLET_WA, waComponentInstancesVO,
					new OnVORequestedListener() {
						@SuppressWarnings("unchecked")
						@Override
						public void onVORequested(VOHttpResponse vo) {

							Action action = vo.getmWAComponentInstancesVO()
									.getWaci().get(0).getActions().getActions()
									.get(0);
							int flag = action.getResresulttags().getFlag();
							if (flag == 0) {

								// 与物料数据匹配，找到后生成一个数组或其他 
								String[] example = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k"};
								// materialVoList 这是一个搜索结果的List，将它显示到ListView中。
								List<MaterialVO> materialVoList =  ((AvailabledListVO) action.getResresulttags().getServcieCodesRes().getScres().get(0).getResdata().getList().get(0)).getMaterialList();
								
								searchList = (ListView) findViewById(R.id.search_list);
                                
							 /*
								
								ArrayAdapter<String> adapter = new ArrayAdapter<String>(
										AvailabilitySearchActivity.this,
										android.R.layout.simple_list_item_1,
										example);
								searchList.setAdapter(adapter);
								 
							 */
								
								 
								List<HashMap<String,Object>> data = new ArrayList<HashMap<String,Object>>();
								
							 
								for(int i = 0;i<materialVoList.size();i++)
								{
									
									MaterialVO itemMaterialVO= materialVoList.get(i);
									HashMap<String,Object> h=new HashMap<String, Object>();
						            h.put("invcode", itemMaterialVO.getInvcode());
						            h.put("invname", itemMaterialVO.getInvname());	 				
						            data.add(h);
						        }
		 						
						        SimpleAdapter adapter=new SimpleAdapter(AvailabilitySearchActivity.this,data,R.layout.listview_item, 
						                new String[]{"invcode","invname"},new int[]{R.id.material_code,R.id.matetial_name});
						        searchList.setAdapter(adapter);
								
								searchList
										.setOnItemClickListener(new OnItemClickListener() {

											public void onItemClick(
													AdapterView<?> arg0,
													View arg1, int arg2,
													long arg3) {
												//在这里加一个bundles 传给我被点击的Item的invid，方法遍历materialVoList，找到匹配的MaterialVO，然后读取invid就Okay~
												
												/*
												 
				
				
				
												 
												 */
												
												Intent i = new Intent(
														AvailabilitySearchActivity.this,
														AvailabilityDetailActivity.class);
												
												//i.putExtra("invid", selecteditem.getInvid());
												
												startActivity(i);
												finish();
											}
										});

							} else {
								toastMsg("验证失败"
										+ action.getResresulttags().getDesc());
							}
							dialog.dismiss();
						}

						@Override
						public void onVORequestFailed(VOHttpResponse vo) {
							dialog.dismiss();
						}
					});

		}

	}
}
