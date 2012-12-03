package wa.android.constants;

import wa.android.common.activity.BaseActivity;

public class Servers {

	public static String getServerAddress(BaseActivity act){
		if("".equalsIgnoreCase(SERVER_ADDRESS)){
			SERVER_ADDRESS = act.readPreference(WAPreferences.SERVER_ADDRESS);
		}
		return SERVER_ADDRESS;
	}
	
	public static void setServerAddress(String adr){
		SERVER_ADDRESS = adr;
	}
	
	private static String SERVER_ADDRESS = "";
	public static String SERVER_SERVLET_GETACCOUNTSET = "/servlet/waunneededloginservlet";
	public static String SERVER_SERVLET_LOGIN = "/servlet/waloginservlet";
	public static String SERVER_SERVLET_PRELOGIN = "/servlet/wapreloginservlet";
	public static String SERVER_SERVLET_LOGOUT = "/servlet/walogoutservlet";
	public static String SERVER_SERVLET_DOWNLOAD = "/servlet/wadownloadservlet";
	public static String SERVER_SERVLET_WA = "/servlet/waservlet";
	public static String SERVER_SERVLET_LOOP = "/servlet/waloop";

//	public static String SERVER_SERVLET_LOGIN = "/test.php";
//	public static String SERVER_SERVLET_PRELOGIN = "/test.php";
//	public static String SERVER_SERVLET_LOGOUT = "/test.php";
//	public static String SERVER_SERVLET_DOWNLOAD = "/test.php";
//	public static String SERVER_SERVLET_WA = "/test.php";
//	public static String SERVER_SERVLET_LOOP = "/test.php";
}
