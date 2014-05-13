package pro.rudo.crud.app.sqlite;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

import pro.rudo.crud.app.model.Picket;
import pro.rudo.crud.app.model.PicketBuilder;

/**
 * Created by rudolf on 05.05.14.
 */
public class PicketSQLiteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "SpeleoToolsDB";

    private static final String TABLE_PICKETS = "pickets";

    private static final String KEY_IS_RAY = "isRay";
    private static final String KEY_FROM = "`from`";
    private static final String KEY_TO = "`to`";
    private static final String KEY_LENGTH = "length";
    private static final String KEY_AZIMUTH = "azimuth";
    private static final String KEY_INCLINE = "incline";
    private static final String KEY_BACK_AZIMUTH = "backAzimuth";
    private static final String KEY_BACK_INCLINE = "backIncline";
    private static final String KEY_LEFT = "left";
    private static final String KEY_ID = "id";
    private static final String KEY_MAP_ID = "mapId";
    private static final String KEY_LEFT_COMMENT = "leftComment";
    private static final String KEY_RIGHT = "right";
    private static final String KEY_RIGHT_COMMENT = "rightComment";
    private static final String KEY_UP = "up";
    private static final String KEY_UP_COMMENT = "upComment";
    private static final String KEY_DOWN = "down";
    private static final String KEY_DOWN_COMMENT = "downComment";
    private static final String KEY_COMMENT = "comment";

    private static final String[] COLUMNS = {KEY_IS_RAY, KEY_FROM, KEY_TO, KEY_LENGTH, KEY_AZIMUTH, KEY_INCLINE, KEY_BACK_AZIMUTH, KEY_BACK_INCLINE, KEY_LEFT, KEY_ID, KEY_MAP_ID, KEY_LEFT_COMMENT, KEY_RIGHT, KEY_RIGHT_COMMENT, KEY_UP, KEY_UP_COMMENT, KEY_DOWN, KEY_DOWN_COMMENT, KEY_COMMENT};

    public PicketSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public long addPicket(Picket picket) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_IS_RAY, picket.isRay());
        values.put(KEY_LENGTH, picket.getLength());
        values.put(KEY_FROM, picket.getFrom());
        values.put(KEY_TO, picket.getTo());
        values.put(KEY_AZIMUTH, picket.getAzimuth());
        values.put(KEY_BACK_AZIMUTH, picket.getBackAzimuth());
        values.put(KEY_INCLINE, picket.getIncline());
        values.put(KEY_BACK_INCLINE, picket.getBackIncline());
        values.put(KEY_LEFT, picket.getLeft());
        values.put(KEY_RIGHT, picket.getRight());
        values.put(KEY_UP, picket.getUp());
        values.put(KEY_DOWN, picket.getDown());
        values.put(KEY_COMMENT, picket.getComment());
        values.put(KEY_LEFT_COMMENT, picket.getLeftComment());
        values.put(KEY_RIGHT_COMMENT, picket.getRightComment());
        values.put(KEY_UP_COMMENT, picket.getUpComment());
        values.put(KEY_DOWN_COMMENT, picket.getDownComment());
        values.put(KEY_MAP_ID, picket.getMapId());

        long id = db.insert(TABLE_PICKETS, null, values);
        db.close();

        return id;
    }

    public Picket getPicket(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_PICKETS, COLUMNS,
                " id = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null
        );

        if (cursor != null) cursor.moveToFirst();

        Picket picket = new PicketBuilder()
                .withRay(Boolean.parseBoolean(cursor.getString(0)))
                .withFrom(cursor.getString(1))
                .withTo(cursor.getString(2))
                .withLength(cursor.getDouble(3))
                .withAzimuth(cursor.getDouble(4))
                .withIncline(cursor.getDouble(5))
                .withBackAzimuth(cursor.getDouble(6))
                .withBackIncline(cursor.getDouble(7))
                .withLeft(cursor.getDouble(8))
                .withId(cursor.getInt(9))
                .withMap(cursor.getInt(10))
                .withLeftComment(cursor.getString(11))
                .withRight(cursor.getDouble(12))
                .withRightComment(cursor.getString(13))
                .withUp(cursor.getDouble(14))
                .withUpComment(cursor.getString(15))
                .withDown(cursor.getDouble(16))
                .withDownComment(cursor.getString(17))
                .withComment(cursor.getString(18))
                .createPicket();

        return picket;
    }

    public List<Picket> getAllPickets(){
        List<Picket> pickets = new LinkedList<Picket>();

        String query = "SELECT * FROM " + TABLE_PICKETS;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Picket picket = null;
        if(cursor.moveToFirst()){
            do{
                picket = new PicketBuilder()
                    .withRay(Boolean.parseBoolean(cursor.getString(0)))
                    .withFrom(cursor.getString(1))
                    .withTo(cursor.getString(2))
                    .withLength(cursor.getDouble(3))
                    .withAzimuth(cursor.getDouble(4))
                    .withIncline(cursor.getDouble(5))
                    .withBackAzimuth(cursor.getDouble(6))
                    .withBackIncline(cursor.getDouble(7))
                    .withLeft(cursor.getDouble(8))
                    .withId(cursor.getInt(9))
                    .withMap(cursor.getInt(10))
                    .withLeftComment(cursor.getString(11))
                    .withRight(cursor.getDouble(12))
                    .withRightComment(cursor.getString(13))
                    .withUp(cursor.getDouble(14))
                    .withUpComment(cursor.getString(15))
                    .withDown(cursor.getDouble(16))
                    .withDownComment(cursor.getString(17))
                    .withComment(cursor.getString(18))
                    .createPicket();

                pickets.add(picket);
            } while (cursor.moveToNext());
        }
        return pickets;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_PICKET_TABLE = "CREATE TABLE " + TABLE_PICKETS + " ( " +
                KEY_IS_RAY + " BOOLEAN, " +
                KEY_FROM + " TEXT, " +
                KEY_TO + " TEXT, " +
                KEY_LENGTH + " DOUBLE, " +
                KEY_AZIMUTH + " DOUBLE, " +
                KEY_INCLINE + " DOUBLE, " +
                KEY_BACK_AZIMUTH + " DOUBLE, " +
                KEY_BACK_INCLINE + " DOUBLE, " +
                KEY_LEFT + " DOUBLE, " +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_MAP_ID + " INTEGER, " +
                KEY_LEFT_COMMENT + " TEXT, " +
                KEY_RIGHT + " DOUBLE, " +
                KEY_RIGHT_COMMENT + " TEXT, " +
                KEY_UP + " DOUBLE, " +
                KEY_UP_COMMENT + " TEXT, " +
                KEY_DOWN + " DOUBLE, " +
                KEY_DOWN_COMMENT + " TEXT, " +
                KEY_COMMENT + " TEXT) ";

        sqLiteDatabase.execSQL(CREATE_PICKET_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PICKETS);

        this.onCreate(sqLiteDatabase);
    }
}