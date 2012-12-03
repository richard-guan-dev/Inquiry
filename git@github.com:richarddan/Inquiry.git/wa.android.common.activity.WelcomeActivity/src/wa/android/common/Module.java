package wa.android.common;

import nc.vo.wa.component.struct.WAComponentInstanceVO;
import nc.vo.wa.component.struct.WAComponentInstancesVO;
import wa.android.common.activity.MainBoardActivity.GridItemView;
import wa.android.common.service.WAResidentService.NotifyCallBack;

/**
 * ģ�� ��������ҵ��ģ����������Ϣ
 * 
 * @author Omi
 * 
 */
public class Module {

	@SuppressWarnings("rawtypes")
	protected Class mainClazz;
	protected String moduleName;
	protected String title; // ��ʾ������������

	/**
	 * ����
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
	 * ���Module������
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
	 * ��ϵͳ��¼ʱ��������loginVO��װ��һ�������VO ���췽ʽ�ο����ݽṹ��sample
	 * 
	 * @return
	 */
	public WAComponentInstanceVO getAppendRequestVO(String groupId, String userId) {
		return null;
	}

	/**
	 * �����module����ʱ������ʹ�ô���Դid��Ϊģ����������ͼ��
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
	 * �����module����ʱ��������������������Լ�ͼ�����ʾ������ʾ����
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