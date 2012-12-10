package wa.android.constants;

public class WAPermission {

	/**
	 * 服务器直接给到返回值中，客户端不请求权限
	 * 
	 * 不需要在Modules里加入
	 */
	public static final int WA_PERMISSION_0 = 0;

	/**
	 * 新增在登录时请求权限，编辑在获取详情时请求权限
	 */
	public static final int WA_PERMISSION_1 = 1;
	
	/**
	 * 登录时请求权限，无权限不显示
	 */
	public static final int WA_PERMISSION_2 = 2;
	
	/**
	 * 登录时请求权限，无权限灰色
	 */
	public static final int WA_PERMISSION_3 = 3;

}

