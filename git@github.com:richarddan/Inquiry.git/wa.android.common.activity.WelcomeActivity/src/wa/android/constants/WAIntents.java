package wa.android.constants;

import wa.android.common.App;
import wa.android.common.activity.AboutActivity;
import wa.android.common.activity.LoginActivity;
import wa.android.common.activity.MainBoardActivity;
import wa.android.common.activity.SetConnectionActivity;
import wa.android.common.activity.SettingActivity;
import wa.android.common.activity.WelcomeActivity;
import android.content.Context;
import android.content.Intent;

public class WAIntents {

	public static Intent getMAIN_ACTIVITY(Context fromContext) {
		if (App.moduleList.size() > 1 || App.moduleList.size() == 0) {
			return new Intent(fromContext, MainBoardActivity.class);
		}else{
			Class<?> mainClazz = App.moduleList.get(0).getMainClazz();
			return new Intent(fromContext, mainClazz);
		}
	}

	public static Intent getWELCOME_ACTIVITY(Context fromContext) {
		return new Intent(fromContext, WelcomeActivity.class);
	}

	public static Intent getSETTING_ACTIVITY(Context fromContext) {
		return new Intent(fromContext, SettingActivity.class);
	}

	public static Intent getLOGIN_ACTIVITY(Context fromContext) {
		return new Intent(fromContext, LoginActivity.class);
	}

	public static Intent getSETCONNECTION_ACTIVITY(Context fromContext) {
		return new Intent(fromContext, SetConnectionActivity.class);
	}

	public static Intent getABOUT_ACTIVITY(Context fromContext) {
		return new Intent(fromContext, AboutActivity.class);
	}
}
