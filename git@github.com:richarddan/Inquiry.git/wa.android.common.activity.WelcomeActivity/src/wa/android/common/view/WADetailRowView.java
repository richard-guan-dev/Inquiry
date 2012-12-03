package wa.android.common.view;

import junit.framework.Assert;
import wa.android.u8.inquire.R;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils.TruncateAt;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Group中的每一行
 * 
 * @author Omi
 * 
 */
public class WADetailRowView extends LinearLayout {

	/**
	 * C means colon
	 * 
	 * @author Omi
	 * 
	 */
	public static enum RowType {
		CUSTOMIZE,
		TEXT,
		NAME_C_VALUE, 
		NAME_C_VALUE_ICON,
		/**
		 * 图标描述
		 */
		NAME_C_VALUE_ICON_DES_CLICKABLE,
		/**
		 * 移动电话
		 */
		NAME_C_VALUE_ICON_MOBILE,
		/**
		 * 图定电话
		 */
		NAME_C_VALUE_ICON_TEL, 
		NAME_C_VALUE_ICON_MAIL, 
		NAME_C_ICON, 
		INDEX_NAME_CLICKABLE,
	}

	private RowType type;
	private View customView = null;
	private View iconView = null;
	
	private String index = null;
	private String name = null;
	private String value = null;
	private int iconResID = -1;
	private String iconDes = null;
	
	private Context context;
	
	private static final int TEXT_SIZE_NAME = 16;
	private static final int TEXT_SIZE_VALUE = 16;
	private static final int TEXT_SIZE_COLON = 16;
	private static final int TEXT_SIZE_TEXT = 16;
	
	private static final int ROW_HEIGHT = 68;

	private static final int ROW_PADDING_LEFT = 13;
	private static final int ROW_PADDING_TOP = 12;
	private static final int ROW_PADDING_RIGHT = 13;
	private static final int ROW_PADDING_BOTTOM = 12;

	
	//for INDEX_NAME_CLICKABLE
	private static final int INDEX_WIDTH = 34;
	private static final int NAME_WIDTH = 340;
	private static final int INDEX_MARGIN_LEFT = 2;
	private static final int NAME_MARGIN_LEFT = 12;
	private static final int ARRAY_MARGIN_LEFT = 8;
	
	public WADetailRowView(Context context, View customView) {
		super(context);
		this.customView = customView;
		this.type = RowType.CUSTOMIZE;
		this.context = context;
		initial();
	}

	public WADetailRowView(Context context, RowType type) {
		super(context);
		this.type = type;
		this.context = context;
		initial();
	}

	private void initial(){
		setOrientation(HORIZONTAL);
		setGravity(Gravity.CENTER_VERTICAL);
		if(type == RowType.TEXT){
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			setLayoutParams(params);
			setPadding(ROW_PADDING_LEFT, ROW_PADDING_TOP, ROW_PADDING_RIGHT, ROW_PADDING_BOTTOM);
		}else{
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, ROW_HEIGHT);
			setLayoutParams(params);
			setPadding(ROW_PADDING_LEFT, 0, ROW_PADDING_RIGHT, 0);
		}
	}
	
	public RowType getRowType() {
		return this.type;
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		confirm();
	}

	/**
	 * 所有值设置完毕后需要通过此方法做确认
	 * @param TEXT use the 'value' string field
	 */
	private void confirm() {
		switch (type) {
		case TEXT:
			{
				TextView textTextView = new TextView(context);
				textTextView.setGravity(Gravity.CENTER_VERTICAL);
				textTextView.setTextColor(Color.rgb(0x80, 0x80, 0x80));
				textTextView.setTextSize(TEXT_SIZE_TEXT);
				textTextView.setText(value);
				addView(textTextView);
			}
			break;
		case NAME_C_VALUE:
			{
				TextView nameTextView = new TextView(context);
				TextView valueTextView = new TextView(context);
				TextView cTextView = new TextView(context);
				cTextView.setTextSize(TEXT_SIZE_COLON);
				if(null == name || "".equalsIgnoreCase(name)){
					cTextView.setText("   ");
				}else{
					cTextView.setText(" : ");
				}
				cTextView.setTextColor(Color.rgb(0x80, 0x80, 0x80));
				
				nameTextView.setSingleLine();
				valueTextView.setSingleLine();
				nameTextView.setEllipsize(TruncateAt.END);
				valueTextView.setEllipsize(TruncateAt.END);
	
				nameTextView.setWidth(120);
				nameTextView.setGravity(Gravity.RIGHT);
				nameTextView.setTextSize(TEXT_SIZE_NAME);
				nameTextView.setText(name);
				nameTextView.setSingleLine();
				nameTextView.setTextColor(Color.rgb(0x80, 0x80, 0x80));
	
				valueTextView.setTextSize(TEXT_SIZE_VALUE);
				valueTextView.setGravity(Gravity.LEFT);
				valueTextView.setText(value);
				addView(nameTextView);
				addView(cTextView);
				addView(valueTextView);
			}
			break;
		case NAME_C_VALUE_ICON_DES_CLICKABLE:
			break;
		case NAME_C_VALUE_ICON_MOBILE:
			{
				TextView nameTextView = new TextView(context);
				TextView valueTextView = new TextView(context);
				TextView cTextView = new TextView(context);
				iconView = new ImageView(context);
				cTextView.setTextColor(Color.rgb(0x80, 0x80, 0x80));
				cTextView.setText(" : ");
				
				nameTextView.setSingleLine();
				valueTextView.setSingleLine();
				nameTextView.setEllipsize(TruncateAt.END);
				valueTextView.setEllipsize(TruncateAt.END);
	
				nameTextView.setWidth(120);
				nameTextView.setGravity(Gravity.RIGHT);
				nameTextView.setTextSize(TEXT_SIZE_NAME);
				nameTextView.setText(name);
				nameTextView.setSingleLine();
				nameTextView.setTextColor(Color.rgb(0x80, 0x80, 0x80));

				valueTextView.setWidth(240);
				valueTextView.setGravity(Gravity.LEFT);
				valueTextView.setTextSize(TEXT_SIZE_VALUE);
				valueTextView.setText(value);

				{
					LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
					params.setMargins(ARRAY_MARGIN_LEFT, 0, 0, 0);
					iconView.setLayoutParams(params);
					((ImageView)iconView).setImageResource(R.drawable.wadetail_row_mobile);
					iconView.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							System.out.println(" mobile icon");
							OnIconClickedActions.onMobileIconClicked(context,value);
						}
					});
				}
				
				addView(nameTextView);
				addView(cTextView);
				addView(valueTextView);
				addView(iconView);
			}
			break;
		case NAME_C_VALUE_ICON_TEL:
			{
				TextView nameTextView = new TextView(context);
				TextView valueTextView = new TextView(context);
				TextView cTextView = new TextView(context);
				iconView = new ImageView(context);
				cTextView.setTextColor(Color.rgb(0x80, 0x80, 0x80));
				cTextView.setText(" : ");
				
				nameTextView.setSingleLine();
				valueTextView.setSingleLine();
				nameTextView.setEllipsize(TruncateAt.END);
				valueTextView.setEllipsize(TruncateAt.END);
	
				nameTextView.setWidth(120);
				nameTextView.setGravity(Gravity.RIGHT);
				nameTextView.setTextSize(TEXT_SIZE_NAME);
				nameTextView.setText(name);
				nameTextView.setSingleLine();
				nameTextView.setTextColor(Color.rgb(0x80, 0x80, 0x80));
	
				valueTextView.setWidth(240);
				valueTextView.setGravity(Gravity.LEFT);
				valueTextView.setTextSize(TEXT_SIZE_VALUE);
				valueTextView.setText(value);
	
				{
					LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
					params.setMargins(ARRAY_MARGIN_LEFT, 0, 0, 0);
					iconView.setLayoutParams(params);
					((ImageView)iconView).setImageResource(R.drawable.wadetail_row_tel);
					iconView.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							System.out.println(" tel icon");
							OnIconClickedActions.onTelIconClicked(context,value);
						}
					});
				}
				
				addView(nameTextView);
				addView(cTextView);
				addView(valueTextView);
				addView(iconView);
			}
			break;
		case NAME_C_VALUE_ICON_MAIL:
			{
				TextView nameTextView = new TextView(context);
				TextView valueTextView = new TextView(context);
				TextView cTextView = new TextView(context);
				iconView = new ImageView(context);
				cTextView.setTextColor(Color.rgb(0x80, 0x80, 0x80));
				cTextView.setText(" : ");
				
				nameTextView.setSingleLine();
				valueTextView.setSingleLine();
				nameTextView.setEllipsize(TruncateAt.END);
				valueTextView.setEllipsize(TruncateAt.END);
	
				nameTextView.setWidth(120);
				nameTextView.setGravity(Gravity.RIGHT);
				nameTextView.setTextSize(TEXT_SIZE_NAME);
				nameTextView.setText(name);
				nameTextView.setSingleLine();
				nameTextView.setTextColor(Color.rgb(0x80, 0x80, 0x80));
	
				valueTextView.setWidth(240);
				valueTextView.setGravity(Gravity.LEFT);
				valueTextView.setTextSize(TEXT_SIZE_VALUE);
				valueTextView.setText(value);
	
				{
					LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
					params.setMargins(ARRAY_MARGIN_LEFT, 0, 0, 0);
					iconView.setLayoutParams(params);
					((ImageView)iconView).setImageResource(R.drawable.wadetail_row_email);
					iconView.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							System.out.println(" mail icon");
							OnIconClickedActions.onMailIconClicked(context,value);
						}
					});
				}
				
				addView(nameTextView);
				addView(cTextView);
				addView(valueTextView);
				addView(iconView);
			}
			break;
		case INDEX_NAME_CLICKABLE:
			{
				TextView indexTextView = new TextView(context);
				TextView nameTextView = new TextView(context);
				iconView = new ImageView(context);
				{
					LayoutParams params = new LayoutParams(INDEX_WIDTH,LayoutParams.WRAP_CONTENT);
					params.setMargins(INDEX_MARGIN_LEFT, 0, 0, 0);
					indexTextView.setLayoutParams(params);
					indexTextView.setGravity(Gravity.RIGHT | Gravity.CENTER_HORIZONTAL);
					indexTextView.setTextSize(TEXT_SIZE_NAME);
					indexTextView.setText(index);
				}
				{
					LayoutParams params = new LayoutParams(NAME_WIDTH,LayoutParams.WRAP_CONTENT);
					params.setMargins(NAME_MARGIN_LEFT, 0, 0, 0);
					nameTextView.setSingleLine();
					nameTextView.setEllipsize(TruncateAt.END);
					nameTextView.setLayoutParams(params);
					nameTextView.setTextSize(TEXT_SIZE_NAME);
					nameTextView.setText(name);
				}
				
				{
					LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
					params.setMargins(ARRAY_MARGIN_LEFT, 0, 0, 0);
					iconView.setLayoutParams(params);
					((ImageView)iconView).setImageResource(R.drawable.wadetail_row_array);
				}
				
				addView(indexTextView);
				addView(nameTextView);
				addView(iconView);
			}
			break;
		case NAME_C_ICON:
			break;
		case CUSTOMIZE:
			addView(customView);
			break;
		case NAME_C_VALUE_ICON:
		default:
			break;
		}
	}

	/**
	 * 设置图标点击事件
	 * Should be set -after- setIconResID();
	 */
	public void setOnIconClickListener(View.OnClickListener onIconClickListener) {
		Assert.assertTrue(iconResID != -1);
	}

	public void setOnRowClickListener(View.OnClickListener onRowClickListener){
		setOnClickListener(onRowClickListener);
	}

	public View getCustomView() {
		return customView;
	}

	public void setCustomView(View customView) {
		this.customView = customView;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getIconResID() {
		return iconResID;
	}

	public void setIconResID(int iconResID) {
		this.iconResID = iconResID;
		if (iconResID > 0) {
			iconView = new View(context);
			iconView.setBackgroundResource(iconResID);
		}
	}

	public String getIconDes() {
		return iconDes;
	}

	public void setIconDes(String iconDes) {
		this.iconDes = iconDes;
	}
}

