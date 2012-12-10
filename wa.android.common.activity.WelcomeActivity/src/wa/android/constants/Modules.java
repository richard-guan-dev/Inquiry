package wa.android.constants;

import java.util.List;

import wa.android.common.Module;
import wa.android.inquire.AvailabilityInquireModule;
import wa.android.inquire.activity.AvailabilityMainActivity;


/** 
 * 注锟斤拷锟斤拷锟揭碉拷锟絤odule
 * @author Omi 
 *
 */
public class Modules {

	public static String MODULE_INQUIRE = "MODULE_INQUIRE";
	public static String MODULE_MESSAGE = "MODULE_MESSAGE";

	/**
	 * 锟斤拷锟斤拷锟斤拷锟叫碉拷Module 锟斤拷写锟斤拷Module锟斤拷要锟斤拷锟斤拷moduleList
	 * 
	 * @see Module
	 * @param moduleList
	 */
	public static void loadModules(List<Module> moduleList) {
		Module inquireModule = new AvailabilityInquireModule(MODULE_INQUIRE, AvailabilityMainActivity.class);
		inquireModule.setTitle("锟斤拷锟斤拷锟斤拷锟斤拷询");
		moduleList.add(inquireModule);
	}
}
