package exeption;

/**
 * Holder for messages of exceptions.
 *
 * @author M.Palaguta
 *
 */
public class Messages {

	private Messages() {

	}
	
	public static final String ERR_CANNOT_OBTAIN_ALL_USERS = "Cannot obtain all users";
	public static final String ERR_CANNOT_OBTAIN_ALL_ROUTS = "Cannot obtain all routs";
	public static final String ERR_CANNOT_OBTAIN_ALL_STATIONS = "Cannot obtain all stations";
	public static final String ERR_CANNOT_OBTAIN_ALL_CARRIAGE_IN_TRAIN = "Cannot obtain all carriage in train";
	public static final String ERR_CANNOT_OBTAIN_ALL_TICKETS_IN_USER = "Cannot obtain all tickets in user";

	public static final String ERR_CANNOT_OBTAIN_USER_BY_ID = "Cannot obtain user by id";
	public static final String ERR_CANNOT_OBTAIN_ROUT_BY_ID = "Cannot obtain rout by id";
	public static final String ERR_CANNOT_OBTAIN_STATION_BY_ID = "Cannot obtain station by id";
	public static final String ERR_CANNOT_OBTAIN_INTERMEDIATE_STATION_BY_ID = "Cannot obtain intermediate station by id";
	public static final String ERR_CANNOT_OBTAIN_TRIP_BY_ID = "Cannot obtain trip by id";
	public static final String ERR_CANNOT_OBTAIN_CARRIAGE_BY_ID = "Cannot obtain carriage by id";
	public static final String ERR_CANNOT_OBTAIN_STATION_BY_NAME = "Cannot obtain station by name";
	public static final String ERR_CANNOT_OBTAIN_TRIP_BY_ROUT = "Cannot obtain trip by rout";
	public static final String ERR_CANNOT_OBTAIN_USER_BY_PASSWORD_AND_MAIL = "Cannot obtain user by password and mail";

	public static final String ERR_CANNOT_ADD_USER = "Cannot add user";
	public static final String ERR_CANNOT_ADD_ROUT = "Cannot add rout";
	public static final String ERR_CANNOT_ADD_STATION = "Cannot add station";
	public static final String ERR_CANNOT_ADD_INTERMEDIATE_STATION = "Cannot add intermediate station";
	public static final String ERR_CANNOT_ADD_TICKET = "Cannot add ticket";

	public static final String ERR_CANNOT_EDIT_USER = "Cannot edit user";
	public static final String ERR_CANNOT_EDIT_ROUT = "Cannot edit rout";
	public static final String ERR_CANNOT_EDIT_STATION = "Cannot edit station";
	public static final String ERR_CANNOT_EDIT_INTERMEDIATE_STATION = "Cannot edit intermediate station";
	public static final String ERR_CANNOT_EDIT_USER_COUNT = "Cannot edit user count";

	public static final String ERR_CANNOT_DELETE_ROUT = "Cannot delete station";
	public static final String ERR_CANNOT_DELETE_STATION = "Cannot delete station";
	public static final String ERR_CANNOT_DELETE_INTERMEDIATE_STATION = "Cannot delete station";

	public static final String ERR_CANNOT_BUY_SEAT_IN_CARRIAGE = "Cannot buy seat in carriage";
}