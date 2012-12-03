package wa.android.common.activity;

import java.io.IOException;
import java.io.Reader;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import nc.vo.wa.component.struct.SessionInfo;
import nc.vo.wa.component.struct.WAComponentInstancesVO;

import org.apache.http.Header;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.message.BasicHeader;

import wa.android.common.App;
import wa.android.constants.ComponentIds;
import wa.android.constants.Servers;
import wa.android.constants.WAIntents;
import wa.android.constants.WAPreferences;
import wa.android.inquire.activity.Search;
import wa.framework.component.network.VOHttpHandler;
import wa.framework.component.network.VOHttpResponse;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

/**
 * 所有activity的根类， 添加了一些帮助方法
 * 
 * @author Omi
 * 
 */

@SuppressLint("HandlerLeak")
public class BaseActivity extends Activity {

	private final int THREAD_STACK_SIZE = 2*1024*1024;
	
	// used for handler
	protected final int REQUEST_SUC = 0x10;
	protected final int REQUEST_FAIL_NOTWASERVER = 0x11;
	protected final int REQUEST_FAIL_SESSIONTIMEOUT = 0x12;
	protected final int REQUEST_FAIL = 0x1F;
	// is not used in thread
	protected final int BASE_ACTION_TOAST = 0x15;

	// used for the result of activity
	protected final int ACT_REQUESTCODE_NETWORKSETTING = 0x20;
	protected final int ACT_REQUESTCODE_SETCONNECTION = 0x21;

	private OnVORequestedListener onVORequestedListener;
	private OnNetworkStatusCheckedListener onNetworkStatusCheckedListener;

	protected boolean isRequesting = false;
	
	// 虽然Toast及Dialog已经可以再UI线程中调用，但是为了调用更加统一，仍然使用handler的方式
	private final Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case REQUEST_SUC:
				if (onVORequestedListener != null)
					onVORequestedListener.onVORequested((VOHttpResponse) msg.obj);
				break;
			case REQUEST_FAIL:
				if (onVORequestedListener != null)
					onVORequestedListener.onVORequestFailed((VOHttpResponse) msg.obj);
				break;
			case REQUEST_FAIL_NOTWASERVER:
				if (onVORequestedListener != null)
					onVORequestedListener.onVORequestFailed((VOHttpResponse) msg.obj);
				break;
			case REQUEST_FAIL_SESSIONTIMEOUT:
				if (onVORequestedListener != null)
					onVORequestedListener.onVORequestFailed((VOHttpResponse) msg.obj);
				break;
			case BASE_ACTION_TOAST:
				Toast.makeText(getBaseContext(), (String) msg.obj, Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
	
	/**
	 * SharedPreferences工具方法,用来读取一个值
	 * 如果没有读取到，会返回""
	 * 
	 * @param key
	 * @return
	 */
	public String readPreference(String key) {
		SharedPreferences sharedPreferences = getSharedPreferences(WAPreferences.NAME_COMMON, MODE_PRIVATE);
		String value = sharedPreferences.getString(key, "");
		return value;
	}

	/**
	 * SharedPreferences工具方法,用来写入一个值
	 * 
	 * @param key
	 * @param value
	 */
	protected void writePreference(String key, String value) {
		SharedPreferences sharedPreferences = getSharedPreferences(WAPreferences.NAME_COMMON, MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	private synchronized void setIsRequesting(boolean isRequesting){
		this.isRequesting = isRequesting;
	}
	
	private synchronized boolean isRequesting(){
		return isRequesting;
	}
	
	/**
	 * 请求VO的工具方法
	 * 
	 * @param url
	 * @param mWAComponentInstancesVO
	 * @param listener
	 */
	@SuppressLint("HandlerLeak")
	protected void requestVO(final String url, final WAComponentInstancesVO mWAComponentInstancesVO,
			final OnVORequestedListener listener) {
		requestVO(url, mWAComponentInstancesVO, headerList, listener);
	}

	/**
	 * 请求VO的工具方法 该方法处理了VO请求过程中可能遇到的大部分错误
	 * 
	 * @param url
	 * @param mWAComponentInstancesVO
	 * @param headerList
	 * @param listener
	 */
	@SuppressLint({ "HandlerLeak" })
	protected void requestVO(final String url, final WAComponentInstancesVO mWAComponentInstancesVO,
			final List<Header> headerList, final OnVORequestedListener listener) {
		if(isRequesting()){
			toastMsg("尚未完成前一个请求，请稍后再试。");
			Log.d(getClass().getName(), "is still work on last request.");
		}
		Log.i(getClass().getName(), "start request vo : " + url);
		this.onVORequestedListener = listener;
		new Thread(null,new Runnable() {
			@SuppressLint("HandlerLeak")
			@Override
			public void run() {
				setIsRequesting(true);
				Message msg = new Message();
				try {
					VOHttpResponse voHttpResponse = null;
					
					String sessionIdSp = readPreference(WAPreferences.SESSION_ID_SP);
					if(!"".equals(sessionIdSp)){
						SessionInfo sp = new SessionInfo();
						String m_sessionid = sessionIdSp;
						sp.setSessionid(m_sessionid);
						mWAComponentInstancesVO.setSp(sp);
					}
					
					//加入一个cookie
					String sessionIdHeader = readPreference(WAPreferences.SESSION_ID_HEADER);
					if(!"".equalsIgnoreCase(sessionIdHeader)){
						headerList.add(new BasicHeader("Set-Cookie", sessionIdHeader));
					}
					
					voHttpResponse = VOHttpHandler.requestVOFromServer(url, mWAComponentInstancesVO,
							headerList);
					//
//					if( voHttpResponse.getStatusCode() == 200)
//						App.Log('d', Search.class, readPreference(ComponentIds.WA00019) + " Mark");
					//
					if (voHttpResponse == null || voHttpResponse.getStatusCode() != 200) {
						msg.what = REQUEST_FAIL;
						toastMsg("服务器处理错误！ status code：" + voHttpResponse.getStatusCode() + "!");
					} else {
						for(org.apache.http.Header h : voHttpResponse.getResponseHeaders()){
							if(h.getName().trim().equalsIgnoreCase("Sessiontimout")){
								App.Log('w', BaseActivity.class, "session timeout!");
								msg.what = REQUEST_FAIL_SESSIONTIMEOUT;
								toastMsg("session过期，请重新登陆");
							}
						}
						String appServer = "";
						for(Header h : voHttpResponse.getResponseHeaders()){
							if("Appserver".equalsIgnoreCase(h.getName())){
								appServer = h.getValue();
							}else if ("Set-Cookie".equalsIgnoreCase(h.getName())){
								writePreference(WAPreferences.SESSION_ID_HEADER, h.getValue());
							}
						}
						if(App.APP_SERVER_NAME.equalsIgnoreCase(appServer)){
							msg.what = REQUEST_SUC;
						}else{
							msg.what = REQUEST_FAIL_NOTWASERVER;
							toastMsg("请求的不是WA服务器或者网络被路由服务阻断！");
						}
					}
					msg.obj = voHttpResponse;
				} catch (ConnectTimeoutException e) {
					e.printStackTrace();
					msg.what = REQUEST_FAIL;
					toastMsg("连接超时");
				} catch (SocketTimeoutException e) {
					e.printStackTrace();
					msg.what = REQUEST_FAIL;
					toastMsg("连接超时");
				} catch (IOException e) {
					e.printStackTrace();
					msg.what = REQUEST_FAIL;
					toastMsg("VO错误");
				} catch (IllegalArgumentException e) {
					//服务器地址不合法
					e.printStackTrace();
					msg.what = REQUEST_FAIL;
					toastMsg("服务器地址或参数不合法");
				} catch (IllegalStateException e) {
					e.printStackTrace();
					msg.what = REQUEST_FAIL;
					toastMsg("服务器地址不合法");
				} catch (Exception e) {
					e.printStackTrace();
					msg.what = REQUEST_FAIL;
					App.Log('e', BaseActivity.class, "other errors!");
					toastMsg("其他错误！");
				} finally {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					handler.sendMessage(msg);
					setIsRequesting(false);
				}
			}
		},"REQUEST_THREAD",THREAD_STACK_SIZE).start();
	}

	/**
	 * 查看网络连接状态，如果没有任何wifi或gprs数据连接将提示用户设置网络连接
	 * 该检查并不会检查是否真的接入internet，而仅仅是检查本机连接是否开启、接入点是否有效。
	 * 
	 * @param onNetworkStatusCheckedListener
	 */
	protected void checkNetWorkStatus(OnNetworkStatusCheckedListener onNetworkStatusCheckedListener) {
		Log.i(getClass().getName(), "start check network status");
		this.onNetworkStatusCheckedListener = onNetworkStatusCheckedListener;
		boolean isNetworkAvilable = false;
		ConnectivityManager cwjManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		cwjManager.getActiveNetworkInfo();
		if (cwjManager.getActiveNetworkInfo() != null) {
			isNetworkAvilable = cwjManager.getActiveNetworkInfo().isAvailable();
		}
		if (!isNetworkAvilable) {
			Builder b = new AlertDialog.Builder(this).setTitle("没有可用的网络").setMessage("是否对网络进行设置？");
			b.setPositiveButton("是", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					startActivityForResult(new Intent(Settings.ACTION_WIRELESS_SETTINGS),
							ACT_REQUESTCODE_NETWORKSETTING);
				}
			}).setNeutralButton("否", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					dialog.cancel();
					finish();
				}
			}).show();
		} else {
			onNetworkStatusCheckedListener.onNetworkStatusAvilable();
		}
	}

	/**
	 * 从设置网络连接界面返回时调用
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ACT_REQUESTCODE_NETWORKSETTING) {
			checkNetWorkStatus(onNetworkStatusCheckedListener);
		}
	}

	/**
	 * Toast 一个信息的工具方法
	 * 
	 * @param msgText
	 */
	protected void toastMsg(String msgText) {
		Message msg = new Message();
		msg.what = BASE_ACTION_TOAST;
		msg.obj = new String(msgText);
		handler.sendMessage(msg);
	}

	/**
	 * 打开应用的设置界面，对于不同的module， 会有不同的设置界面，这是应该加入正确的SettingOption数组对象
	 * 
	 * @param settingOptions
	 * @see SettingOption
	 */
	protected void startSettingActivity(SettingOption[] settingOptions,int aboutResId) {
		Intent i = WAIntents.getSETTING_ACTIVITY(getBaseContext());
		i.putExtra(SettingActivity.BUNDLE_KEY_OPTIONS, settingOptions);
		i.putExtra(SettingActivity.BUNDLE_KEY_ABOUT_RES_ID,aboutResId);
		startActivity(i);
	}

	private static List<Header> headerList = createHeaderList(true, false, false, false, false);

	/**
	 * 请求的头信息
	 * 
	 * @return a request http header list
	 */
	protected static List<Header> createHeaderList(boolean isEncryption, boolean isWithBase64, boolean isCompress,
			boolean isEncryptionFirst, boolean isPreLogin) {
		List<Header> headerList = new ArrayList<Header>();
		BasicHeader h1 = new BasicHeader("compress", isCompress ? "Y" : "N");
		headerList.add(h1);
		BasicHeader h2 = new BasicHeader("contaiver", "N");
		headerList.add(h2);
		BasicHeader h3 = new BasicHeader("encryption", isEncryption ? "Y" : "N");
		headerList.add(h3);
		// 加密类型1带base64 2 不带base64
		BasicHeader h4 = new BasicHeader("encryptiontype", isWithBase64 ? "1" : "2");
		headerList.add(h4);
		BasicHeader h5 = new BasicHeader("translatetype", "jobject");
		headerList.add(h5);
		BasicHeader h6 = new BasicHeader("translateversion", "1");
		headerList.add(h6);
		if (isEncryption && isCompress) {
			// 如果没有压缩加密同时存在，不需要这个header
			BasicHeader h7 = new BasicHeader("comencorder", isEncryptionFirst ? "2" : "1");
			headerList.add(h7);
		}
		if (isCompress) {
			// 压缩类型，目前只有1, 不压缩的时,不需要这个header
			BasicHeader h8 = new BasicHeader("compresstype", "1");
			headerList.add(h8);
		}

		if (isPreLogin) {
			// 以下是预登陆的headers
			BasicHeader h9 = new BasicHeader("apphv", App.APP_HV);
			headerList.add(h9);

			BasicHeader h10 = new BasicHeader("appid", App.APP_ID);
			headerList.add(h10);

			BasicHeader h11 = new BasicHeader("applv", App.APP_LV);
			headerList.add(h11);

			BasicHeader h12 = new BasicHeader("enterpriseid", App.ENTERPRISEID);
			headerList.add(h12);
		}
		for(Header h : headerList){
			System.out.println(" - - : " + h.getName() + "," + h.getValue());
		}
		return headerList;
	}
	
	/**
	 * if request error, the wacomponentinstancesvo in VOHttpResponse is null
	 * 
	 * @author Omi
	 * 
	 */
	public interface OnVORequestedListener {
		public void onVORequested(VOHttpResponse vo);
		public void onVORequestFailed(VOHttpResponse vo);
	}

	public interface OnNetworkStatusCheckedListener {
		public void onNetworkStatusAvilable();
	}
}
