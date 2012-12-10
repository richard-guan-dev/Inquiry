package wa.android.inquire;

import java.util.ArrayList;
import java.util.List;

import nc.vo.wa.component.salarymanagement.SalaryPswdFlagVO;
import nc.vo.wa.component.struct.Action;
import nc.vo.wa.component.struct.Actions;
import nc.vo.wa.component.struct.ParamTagVO;
import nc.vo.wa.component.struct.ReqParamsVO;
import nc.vo.wa.component.struct.WAComponentInstanceVO;
import nc.vo.wa.component.struct.WAComponentInstancesVO;
import wa.android.common.App;
import wa.android.common.Module;
import wa.android.common.activity.MainBoardActivity.GridItemView;
import wa.android.constants.ActionTypes;
import wa.android.constants.ComponentIds;

public class AvailabilityInquireModule extends Module {

//	public static boolean needPass = false;
//	public static String pass = "";
	
	@SuppressWarnings("rawtypes")
	public AvailabilityInquireModule(String moduleName, Class mainClazz) {
		super(moduleName, mainClazz);
	}

//	@Override
//	public WAComponentInstanceVO getAppendRequestVO(String groupId, String userId) {
//		WAComponentInstanceVO waComponentInstanceVO = new WAComponentInstanceVO();
//		waComponentInstanceVO.setComponentid(ComponentIds.WA00008);
//		Actions actions = new Actions();
//		ArrayList<Action> actionList = new ArrayList<Action>();
//		Action action = new Action();
//		action.setActiontype(ActionTypes.SALARY_PASSFLAG);
//		ReqParamsVO paramVO = new ReqParamsVO();
//		ArrayList<ParamTagVO> params = new ArrayList<ParamTagVO>();
//
//		params.add(new ParamTagVO("groupid", groupId));
//		params.add(new ParamTagVO("usrid", userId));
//		
//		paramVO.setParamlist(params);
//		action.setParamstags(paramVO);
//		actionList.add(action);
//		actions.setActions(actionList);
//		waComponentInstanceVO.setActions(actions);
//		return waComponentInstanceVO;
//	}
//	
	@Override
	public void onLoginSuccessfully(WAComponentInstancesVO waComponentInstancesVO) {
		//TODO: judge if need pass
		for(WAComponentInstanceVO waci : waComponentInstancesVO.getWaci()){
//			if(waci.getComponentid().equalsIgnoreCase(ComponentIds.WA00008)){
//				for(Action action : waci.getActions().getActions()){
//					if(action != null && action.getActiontype().equalsIgnoreCase(ActionTypes.SALARY_PASSFLAG)){
//						int flag = action.getResresulttags().getFlag();
//						if(flag == 0){
//							List resDataList = action.getResresulttags().getServcieCodesRes().getScres().get(0).getResdata().getList();
//							String pswdFlag = ((SalaryPswdFlagVO)resDataList.get(0)).getFlag();
//							if("Y".equalsIgnoreCase(pswdFlag)){
//								needPass = true;
//								App.Log('d', InquireModule.class, "need pwd");
//							}else{
//								App.Log('d', InquireModule.class, "no need pwd");
//							}
//						}else{
//							//TODO: handle the exception flag
//						}
//					}
//				}
//			}
			App.Log('d', AvailabilityInquireModule.class, "Successfully login");
		}
	}

	@Override
	public void onBoard(GridItemView v, WAComponentInstancesVO vo) {
//		v.setTipNum(1);
//		v.setTipBackgroundColor(Color.argb(0xFF, 0x66, 0x33, 0x00));
		System.out.println(" - this is salary module - onBoard()");
	}
}