package wa.android.common.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

public class OnIconClickedActions {
	public static void onMobileIconClicked(final Context context, final String value) {
		new AlertDialog.Builder(context)  
		.setTitle("�ֻ�����")  
		.setItems(new String[] {"�����ֻ�","���Ͷ���"}, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				if (which == 0) {
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_CALL);
					intent.setData(Uri.parse("tel:" + value));
					context.startActivity(intent);
				} else if (which == 1) {
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_SENDTO);
					intent.setData(Uri.parse("smsto:" + value));
					context.startActivity(intent);
				}
			}
		})
		.show();
	}
	
	public static void onTelIconClicked(final Context context, final String value){
		new AlertDialog.Builder(context)  
		.setTitle("�칫�绰")  
		.setItems(new String[] {"����绰"}, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_CALL);
				intent.setData(Uri.parse("tel:" + value));
				context.startActivity(intent);
			}
		})
		.show();
	}
	
	public static void onMailIconClicked(final Context context, final String value){
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_SEND);
		intent.setType("plain/text");
		intent.putExtra(android.content.Intent.EXTRA_EMAIL, value);
		context.startActivity(Intent.createChooser(intent, "ѡ��һ���ʼ�Ӧ��"));   
	}
}

