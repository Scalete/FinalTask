package path;

/**
 * Path holder (jsp pages, controller commands).
 *
 * @author M.Palaguta
 *
 */
public final class Path {
	
	// pages
	public static final String PAGE_REGISTRATION = "/user/registration.jsp";
	public static final String PAGE_MAIN = "/main.jsp";
	public static final String PAGE_LOGIN = "/user/login.jsp";
	public static final String PAGE_TICKET = "/user/ticket/ticket.jsp";
	public static final String PAGE_TRIP_INFO = "/user/search/trip_info.jsp";
	public static final String PAGE_SEARCH_ROUT_RESULT = "/user/search/search_rout_result.jsp";
	public static final String PAGE_EDIT_ACCOUNT = "/user/account/edit_account.jsp";
	public static final String PAGE_ACCOUNT = "/user/account/account.jsp";

	public static final String PAGE_ADMIN = "/admin/admin.jsp";
	public static final String PAGE_ADD_STATION = "/admin/station/add_station.jsp";
	public static final String PAGE_EDIT_STATION = "/admin/station/edit_station.jsp";
	public static final String PAGE_ROUT_INFO = "/admin/rout/info/rout_info.jsp";
	public static final String PAGE_EDIT_ROUT = "/admin/rout/edit_rout.jsp";
	public static final String PAGE_ADD_ROUT = "/admin/rout/add_rout.jsp";
	public static final String PAGE_ADD_INTERMEDIATE_STATION = "/admin/rout/add_intermediate_station.jsp";
	public static final String PAGE_EDIT_INTERMEDIATE_STATION =
			"/admin/rout/info/intermediate_station/edit_intermediate_station.jsp";


	// controllers
	public static final String CONTROLLER_LOAD_ADMIN_TABLES = "/loadAdminTables";
	public static final String CONTROLLER_LOAD_ACCOUNT = "/load_account";
	public static final String CONTROLLER_LOAD_ROUT_INFO = "/load_rout_info";
}