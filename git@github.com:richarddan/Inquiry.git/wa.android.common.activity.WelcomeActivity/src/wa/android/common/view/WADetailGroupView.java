package wa.android.common.view;

import wa.android.u8.inquire.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * WADetailView中的组
 * 
 * @author Omi
 * 
 */
public class WADetailGroupView extends LinearLayout {

	private Context context;
	private TextView titleView;
	private boolean isFirstRow = true;

	private static final int TEXT_SIZE_TITLE = 14;

	public WADetailGroupView(Context context) {
		super(context);
		this.context = context;
		initial();
	}

	public WADetailGroupView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initial();
	}

	private void initial() {
		setBackgroundResource(R.drawable.wadetail_group_background);
		setOrientation(VERTICAL);
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(WADetailView.GROUP_MARGIN_LEFT, WADetailView.GROUP_MARGIN_TOP, WADetailView.GROUP_MARGIN_RIGHT, WADetailView.GROUP_MARGIN_BOTTOM);
		setLayoutParams(params);
		setPadding(0, WADetailView.GROUP_PADDING_TOP, 0, WADetailView.GROUP_PADDING_BOTTOM);
		titleView = new TextView(context);
		titleView.setVisibility(GONE);
		titleView.setTextSize(TEXT_SIZE_TITLE);
		titleView.setLayoutParams(params);
	}

	public String getTitle() {
		if (titleView != null) {
			return titleView.getText().toString();
		} else {
			return null;
		}
	}

	public void setTitle(String title) {
		titleView.setText(title);
		if (title != null && !"".equalsIgnoreCase(title)) {
			titleView.setVisibility(VISIBLE);
		} else {
			titleView.setVisibility(GONE);
		}
	}

	public TextView getTitleView() {
		return titleView;
	}

	/**
	 * 由上到下，顺序添加行
	 * 
	 * @param row
	 */
	public void addRow(WADetailRowView row) {
		if (isFirstRow) {
			isFirstRow = false;
			addView(row);
		} else {
			addRowSeparator();
			addView(row);
		}
	}

	/**
	 * 增加列之间的分割线
	 */
	private void addRowSeparator() {
		View child = new View(context);
		child.setBackgroundResource(R.drawable.wadetail_row_separator);
		addView(child);
	}
}
