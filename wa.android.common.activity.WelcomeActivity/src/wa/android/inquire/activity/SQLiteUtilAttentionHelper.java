package wa.android.inquire.activity;

import wa.android.common.App;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import nc.vo.wa.component.available.AvailableInfoVO;

public class SQLiteUtilAttentionHelper extends SQLiteOpenHelper {

	public SQLiteUtilAttentionHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param avaVO
	 *            需要添加或删除的AvailableInfoVO
	 * @param act
	 *            查询时的Activity
	 * @param op
	 *            0为添加，其他值为删除
	 */
	public static void editAttention(AvailableInfoVO avaVO, int op, Activity atc) {
		SQLiteUtilAttentionHelper helper = new SQLiteUtilAttentionHelper(atc,
				"InquireAttention", null, 1);
		SQLiteDatabase db = helper.getWritableDatabase();
		String name = (String) avaVO.getAttributesMap().get("orgname");
		String id = (String) avaVO.getAttributesMap().get("orgcode");
		if (op == 0) {
			ContentValues values = new ContentValues();
			values.put("id", id);
			values.put("name", name);
			db.insert("InquireAttention", null, values);
		} else {
			db.delete("InquireAttention", "id=?", new String[] { id });
		}
		db.close();
	}

	/**
	 * 
	 * @param avaVO
	 *            需要查询的VO
	 * @param atc
	 *            查询时的Activity
	 * @return true 为已关注，false 为未关注
	 */
	public static boolean getVOAvailInfo(AvailableInfoVO avaVO, Activity atc) {
		SQLiteUtilAttentionHelper helper = new SQLiteUtilAttentionHelper(atc,
				"InquireAttention", null, 1);
		SQLiteDatabase db = helper.getReadableDatabase();
		String name = (String) avaVO.getAttributesMap().get("orgname");
		String id = (String) avaVO.getAttributesMap().get("orgcode");
		Cursor cursor = db.query("InquireAttention", new String[] { "id",
				"name" }, "id=?", new String[] { id }, null, null, null);
		if(cursor != null && cursor.moveToFirst() != false){
			cursor.close();
			db.close();
			return true;
		}
		else{
			cursor.close();
			db.close();
			return false;
			}
	}
	

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		App.Log('d', SQLiteUtilAttentionHelper.class,
				"Create Table InquireAttention");
		db.execSQL("CREATE table InquireAttention(id varchar(20), name varchar(20))");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		App.Log('d', SQLiteUtilAttentionHelper.class, "Update Table");

	}

}
