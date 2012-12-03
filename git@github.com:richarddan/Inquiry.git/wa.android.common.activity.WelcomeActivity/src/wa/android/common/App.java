package wa.android.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.vo.wa.component.struct.WAComponentInstancesVO;
import wa.android.constants.Modules;
import wa.framework.component.network.VOHttpHandler;
import android.app.Application;

/**
 * 
 * App
 * 
 * @author Omi
 * 
 */
public class App extends Application {

	// App info
	public static String APP_ID = "1.0";	//for headerList
	public static String APP_LV = "1.0";
	public static String APP_HV = "1.0";
	public static String ENTERPRISEID = "";
	
	//App server info
	public static String APP_SERVER_NAME = "wa";
	public static String APP_SERVER_VERSION = "";
	
	public static String APPID = "SSTASK120521B";
	public static float APPVERSION = 1.0f;

	// global configuration
	public static boolean IS_DEBUG = true;

	public static List<Module> moduleList = new ArrayList<Module>();

	@Override
	public void onCreate() {
		System.out.println(" - APP on create");
		super.onCreate();
		VOHttpHandler.IS_DEBUG = IS_DEBUG;
		Modules.loadModules(moduleList);
	}
	
	@Override
	public void onTerminate() {
		System.out.println(" - App on terminate");
		super.onTerminate();
	}
	
	/**
	 * 用于全局的Log工具
	 * @param level
	 * @param clazz
	 * @param msg
	 */
	public static void Log(char level, Class<?> clazz, String msg){
		if(IS_DEBUG){
			switch(level){
			case 'i':
				android.util.Log.i(clazz.getName(), msg);
				break;
			case 'd':
				android.util.Log.d(clazz.getName(), msg);
				break;
			case 'w':
				android.util.Log.w(clazz.getName(), msg);
				break;
			case 'e':
				android.util.Log.e(clazz.getName(), msg);
				break;
			}
		}
	}
}

