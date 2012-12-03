package wa.android.common.activity;

import java.util.Arrays;

import nc.vo.wa.component.struct.WAComponentInstancesVO;

import org.apache.http.Header;

import wa.android.constants.Servers;
import wa.android.constants.WAIntents;
import wa.android.u8.inquire.R;
import wa.framework.component.network.VOHttpResponse;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * 欢迎界面 在这个界面中将：检测网络连接情况，读取保存的ip，检测是否自动登录，预登录。
 * 
 * @author Omi
 * 
 */
@SuppressLint("HandlerLeak")
public class WelcomeActivity extends BaseActivity {

	private Animation logoAnimation;
	private ImageView logoImageView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		
		
		/////////////////
		//directly test the activity
		//TODO: delete test codes
//		startActivity(new Intent(this,wa.android.message.activity.MessageMainActivity.class));
//		if(true){
//			return;
//		}
		//
		/////////////////
		
		if(getIntent().getBooleanExtra("finish",false)){
			finish();
			return;
		}
		
		initialViews();
		checkSavedServerAddressAndNetWork();
	}
	
	/**
	 * 检查是否保存了server地址 以及 网络状态
	 */
	private void checkSavedServerAddressAndNetWork() {
		String serverAddress = Servers.getServerAddress(WelcomeActivity.this);

		Log.i(getClass().getName(), "serverAddress:" + serverAddress);
		if (null == serverAddress || "".equals(serverAddress.trim())) {
			startActivityForResult(WAIntents.getSETCONNECTION_ACTIVITY(getBaseContext()), ACT_REQUESTCODE_SETCONNECTION);
			return;
		}

		checkNetWorkStatus(new OnNetworkStatusCheckedListener() {
			@Override
			public void onNetworkStatusAvilable() {
				preLogin();
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK){
			checkSavedServerAddressAndNetWork();
		}else if (resultCode == RESULT_CANCELED){
		}
	}

	private void initialViews() {
		logoImageView = (ImageView) findViewById(R.id.welcome_imageView_logo);
		logoAnimation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.welcome_logo_rotate);
		logoImageView.startAnimation(logoAnimation);
	}

	private void preLogin() {
		Log.i(getClass().getName(), "start preLogin");
		requestVO( Servers.getServerAddress(WelcomeActivity.this) + Servers.SERVER_SERVLET_PRELOGIN, new WAComponentInstancesVO(),
				new OnVORequestedListener() {
					@Override
					public void onVORequested(VOHttpResponse vo) {
						Log.i(getClass().getName(), "pre login requested");
						Log.i(getClass().getName(), "request headers : " + Arrays.toString(vo.getRequestHeaders()));
						Log.i(getClass().getName(), "response headers : " + Arrays.toString(vo.getResponseHeaders()));
						
						// A fack push service
						// Intent serviceIntent = new
						// Intent(WelcomeActivity.this,
						// WAResidentService.class);
						// startService(serviceIntent);
						
						//this clientSessionId is not used for the moment
						String clientSessionId = "";
						for(Header h : vo.getResponseHeaders()){
							if("Set-Cookie".equalsIgnoreCase(h.getName())){
								clientSessionId = (h.getValue().split(";"))[0];
								clientSessionId = clientSessionId.substring(clientSessionId.indexOf('=') + 1, clientSessionId.indexOf('.'));
							}
						}
						startActivity(WAIntents.getLOGIN_ACTIVITY(getBaseContext()));
					}

					@Override
					public void onVORequestFailed(VOHttpResponse vo) {
						toastMsg("预登录失败");
						startActivity(WAIntents.getLOGIN_ACTIVITY(getBaseContext()));
					}
				});
	}
}
