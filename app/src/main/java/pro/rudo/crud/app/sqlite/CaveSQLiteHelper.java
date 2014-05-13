package pro.rudo.crud.app.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import pro.rudo.crud.app.model.Cave;

/**
 * Created by rudolf on 12.05.14.
 */
public class CaveSQLiteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "SpeleoToolsDB";

    private static final String TABLE_CAVES = "caves";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_AUTHOR = "author";

    private static final String[] COLUMNS = {KEY_ID, KEY_NAME, KEY_AUTHOR};

    public CaveSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public long addCave(Cave cave) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, cave.getName());
        values.put(KEY_AUTHOR, cave.getAuthor());

        long id = db.insert(TABLE_CAVES, null, values);
        db.close();

        return id;
    }

    public Cave getCave(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_CAVES, COLUMNS,
                " id = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null
        );

        if (cursor != null) cursor.moveToFirst();

        Cave cave = new Cave();
        cave.setId(Integer.parseInt(cursor.getString(0)));
        cave.setName(cursor.getString(1));
        cave.setAuthor(cursor.getString(2));
        return cave;
    }

    public List<Cave> getAllCaves(){
        List<Cave> caves = new LinkedList<Cave>();

        String query = "SELECT * FROM " + TABLE_CAVES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Cave cave = null;
        if(cursor.moveToFirst()){
            do{
                cave = new Cave();
                cave.setId(Integer.parseInt(cursor.getString(0)));
                cave.setName(cursor.getString(1));
                cave.setAuthor(cursor.getString(2));

                caves.add(cave);
            } while (cursor.moveToNext());
        }

        return caves;
    }

    public int updateCave(Cave cave) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, cave.getName());
        values.put(KEY_AUTHOR, cave.getAuthor());

        int i = db.update(TABLE_CAVES,
                values,
                KEY_ID,
                new String[]{String.valueOf(cave.getId())});

        db.close();
        return i;
    }
    public void deleteCave(Cave cave){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_CAVES, KEY_ID + " = ?", new String[]{ String.valueOf(cave.getId())});
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CAVE_TABLE = "CREATE TABLE " + TABLE_CAVES + " ( " +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_NAME + " TEXT, " +
                KEY_AUTHOR + " TEXT )";

        sqLiteDatabase.execSQL(CREATE_CAVE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST " + TABLE_CAVES);

        this.onCreate(sqLiteDatabase);
    }
}
