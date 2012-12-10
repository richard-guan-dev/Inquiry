package wa.android.common.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import wa.android.common.App;
import wa.android.common.Module;
import wa.android.constants.Servers;
import wa.android.nc.availabilityinquire.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class WAResidentService extends Service {

	private NotificationManager nm;
	private NotifyCallBack callBack;

	private static int LOOP_PERIOD_DEFAULT = 3000; // in ms mode

	@Override
	public IBinder onBind(Intent intent) {
		System.out.println(" - service onBind()");
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		System.out.println(" - service onCreate()");
		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		callBack = new NotifyCallBack();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		System.out.println(" - service onStart()");
		final StringBuffer msg = new StringBuffer();

		final HttpClient client = new DefaultHttpClient();
		final HttpPost request = new HttpPost(/* find a way to get server address + */Servers.SERVER_SERVLET_LOOP);
		final List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("name", "myName"));
		new Thread(new Runnable() {
			int index = 0;
			public void run() {
				while (true) {
					try {
						StringEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
						request.setEntity(entity);
						HttpResponse rsponse = client.execute(request);
						String s = EntityUtils.toString(rsponse.getEntity());
						msg.append(s);
						for (final Module m : App.moduleList) {
							m.handleServiceCallBack(msg.toString(), callBack);
						}
						msg.delete(0, msg.length());
						index++;
						//TODO: get the next period from msg
						Thread.sleep(LOOP_PERIOD_DEFAULT);
					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.out.println(" - service onDestroy()");
		nm.cancelAll();
		Toast.makeText(this, " - stop service", Toast.LENGTH_SHORT).show();
	}

	/**
	 * Show a notification while this service is running.
	 */
	private void showNotification(int notifyID, int iconResID, String title, String message, Class<?> trigerClazz) {
		Notification notification = new Notification(R.drawable.salary_icon, message, System.currentTimeMillis());
		Intent trigerIntent = new Intent(this, trigerClazz);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, trigerIntent, 0);
		notification.setLatestEventInfo(this, title, message, contentIntent);
		nm.notify(notifyID, notification);
	}

	/**
	 * 一个用来生成系统通知的callback
	 * 
	 * @author Omi
	 * 
	 */
	public class NotifyCallBack {
		public void showNotification(int notifyID, int iconResID, String title, String message, Class<?> trigerClazz) {
			WAResidentService.this.showNotification(notifyID, iconResID, title, message, trigerClazz);
		}
	}
}
