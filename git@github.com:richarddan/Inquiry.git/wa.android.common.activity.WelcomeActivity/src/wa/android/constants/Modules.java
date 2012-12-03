package wa.android.constants;

import java.util.List;

import wa.android.common.Module;
import wa.android.inquire.InquireModule;
import wa.android.inquire.activity.Main;


/**
 * ע�����ҵ��module
 * @author Omi
 *
 */
public class Modules {

	public static String MODULE_INQUIRE = "MODULE_INQUIRE";
	public static String MODULE_MESSAGE = "MODULE_MESSAGE";

	/**
	 * �������е�Module ��д��Module��Ҫ����moduleList
	 * 
	 * @see Module
	 * @param moduleList
	 */
	public static void loadModules(List<Module> moduleList) {
		Module inquireModule = new InquireModule(MODULE_INQUIRE, Main.class);
		inquireModule.setTitle("��������ѯ");
		moduleList.add(inquireModule);
	}
}
