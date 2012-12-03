package wa.android.common;

import nc.vo.wa.component.struct.WAComponentInstanceVO;
import nc.vo.wa.component.struct.WAComponentInstancesVO;
import wa.android.common.activity.MainBoardActivity.GridItemView;
import wa.android.common.service.WAResidentService.NotifyCallBack;

/**
 * 模块 用来承载业务模块的主类等信息
 * 
 * @author Omi
 * 
 */
public class Module {

	@SuppressWarnings("rawtypes")
	protected Class mainClazz;
	protected String moduleName;
	protected String title; // 显示在主面板的名称

	/**
	 * 构造
	 * 
	 * @param moduleName
	 * @param mainClazz
	 */
	@SuppressWarnings("rawtypes")
	public Module(String moduleName, Class mainClazz) {
		this.mainClazz = mainClazz;
		this.moduleName = moduleName;
	}

	/**
	 * 获得Module的主类
	 * 
	 * @return
	 */
	public Class<?> getMainClazz() {
		return mainClazz;
	}

	public void setMainClazz(Class<?> mainClazz) {
		this.mainClazz = mainClazz;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 在系统登录时，期望与loginVO封装在一起的请求VO 构造方式参考数据结构及sample
	 * 
	 * @return
	 */
	public WAComponentInstanceVO getAppendRequestVO(String groupId, String userId) {
		return null;
	}

	/**
	 * 当多个module存在时，将会使用此资源id作为模块在主面板的图标
	 * 
	 * @return
	 */
	public int getIconResID() {
		return -1;
	}

	/**
	 * 
	 * just after login successfully
	 * @param waComponentInstancesVO 
	 * 
	 */
	public void onLoginSuccessfully(WAComponentInstancesVO waComponentInstancesVO){
		
	}
	
	/**
	 * vo will always be null
	 * 当多个module存在时，可以用来更新主面板自己图标的提示数及提示背景
	 * 
	 * @param v
	 *            GridItemView
	 * @see GridItemView
	 */
	public void onBoard(GridItemView v, WAComponentInstancesVO vo) {

	}

	/**
	 * for the service
	 * 
	 */
	public void handleServiceCallBack(String msg, NotifyCallBack callBack) {

	}
}