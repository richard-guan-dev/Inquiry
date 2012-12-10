package wa.android.common.activity;


import wa.android.constants.Servers;
import wa.android.constants.WAIntents;
import wa.android.constants.WAPreferences;
import wa.android.nc.availabilityinquire.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * 设置网络连接的界面
 * 启动时如果检测到未保存连接地址，将会启动这个界面让用户进行设置
 * 用户主动点击设置选项中的连接地址设置也会启动这个界面进行设置
 * @author Omi
 *
 */
public class SetConnectionActivity extends BaseActivity {

	private EditText srvipEditText;
	private EditText srvportEditText;
	private Button saveBtn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setconnection);
        initialViews();
        //check saved state if need this activity, or go next activity directly
    }
    
    private void initialViews(){
    	srvipEditText = (EditText) findViewById(R.id.setconnection_ipEditText);
    	srvportEditText = (EditText) findViewById(R.id.setconnection_portEditText);
    	
    	String savedSrvAddress = Servers.getServerAddress(this);
    	if(!savedSrvAddress.equalsIgnoreCase("") && savedSrvAddress.contains(":")){
    		String savedIp = (String) savedSrvAddress.subSequence(savedSrvAddress.lastIndexOf("/") + 1, savedSrvAddress.lastIndexOf(":"));
    		String savedPort = (String) savedSrvAddress.subSequence(savedSrvAddress.lastIndexOf(":") + 1, savedSrvAddress.length());
    		srvipEditText.setText(savedIp);
    		srvportEditText.setText(savedPort);
    	}else{
    		srvipEditText.setText("");
    		srvportEditText.setText("");
    	}
    	
    	saveBtn = (Button)findViewById(R.id.setconnection_saveButton);
    	saveBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String serverAddress = "http://" + srvipEditText.getText() + ":" + srvportEditText.getText();
				writePreference(WAPreferences.SERVER_ADDRESS, serverAddress);
				System.out.println(" - write ");
				System.out.println(" - Servers: " + Servers.getServerAddress(SetConnectionActivity.this));
				System.out.println(" - serverAddress: " + serverAddress);
				Servers.setServerAddress(serverAddress);
				setResult(RESULT_OK);
				Intent loginActivity = WAIntents.getLOGIN_ACTIVITY(SetConnectionActivity.this);
				loginActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(loginActivity);
				finish();
				//TODO: 如果没输入则返回RESULT_CANCLED
			}
		});
    }
}
