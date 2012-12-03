package wa.android.common.activity;

import java.util.ArrayList;

import nc.vo.wa.component.struct.Action;
import nc.vo.wa.component.struct.Actions;
import nc.vo.wa.component.struct.ParamTagVO;
import nc.vo.wa.component.struct.ReqParamsVO;
import nc.vo.wa.component.struct.WAComponentInstanceVO;
import nc.vo.wa.component.struct.WAComponentInstancesVO;
import wa.android.constants.ActionTypes;
import wa.android.constants.ComponentIds;
import wa.android.constants.Servers;
import wa.android.constants.WAPreferences;
import android.content.Intent;
import android.os.Bundle;

public class LogoutActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//logout vo request
		WAComponentInstancesVO waComponentInstancesVO = new WAComponentInstancesVO();
		ArrayList<WAComponentInstanceVO> waComponentInstanceVOList = new ArrayList<WAComponentInstanceVO>();

		WAComponentInstanceVO waComponentInstanceVO = new WAComponentInstanceVO();
		waComponentInstanceVO.setComponentid(ComponentIds.WA00001);
		Actions actions = new Actions();
		ArrayList<Action> actionList = new ArrayList<Action>();
		Action action = new Action();
		action.setActiontype(ActionTypes.LOGOUT);
		ReqParamsVO paramVO = new ReqParamsVO();
		ArrayList<ParamTagVO> params = new ArrayList<ParamTagVO>();
		paramVO.setParamlist(params);
		action.setParamstags(paramVO);
		actionList.add(action);
		actions.setActions(actionList);
		waComponentInstanceVO.setActions(actions);

		waComponentInstanceVOList.add(waComponentInstanceVO);
		waComponentInstancesVO.setWaci(waComponentInstanceVOList);
		
		requestVO(Servers.getServerAddress(LogoutActivity.this) + Servers.SERVER_SERVLET_LOGOUT, waComponentInstancesVO, null);
		
		//
		Intent i = new Intent(LogoutActivity.this, WelcomeActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(i);
	}
}
