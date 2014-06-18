package pro.rudo.crud.app.model;



import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by rudolf on 12.05.14.
 */
public class Cave {
    private static int count;
    private String objectId;
    private int id;
    private String name;
    private String author;


    public Cave(){

    }

    public Cave(String name, Context context) {
        this.name = name;
        SharedPreferences pref = context.getSharedPreferences(context.getPackageName() + "_preferences", Context.MODE_PRIVATE);
        this.author = pref.getString("author", "");
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static Cave fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Cave.class);
    }

    public static int newId(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Counter");
        query.fromLocalDatastore();
        query.whereEqualTo("class", "Cave");

        try {
            ParseObject countObj = query.getFirst();
            count = countObj.getInt("count");
            countObj.put("count", ++count);
            countObj.pin();
            return count;
        } catch (ParseException e) {
            ParseObject count = new ParseObject("Counter");
            count.put("class", "Cave");
            count.put("count", 0);
            count.pinInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                }
            });
            return 0;
        }
    }

    public static void find(int id, final CaveHelper helper){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Cave");
        query.fromLocalDatastore();
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (e == null) {
                    Cave cave = Cave.fromJson(parseObject.getString("json"));
                    helper.onCaveFindDo(cave);
                } else {
                    Log.e("ParseException", "при поиске объекта с id");
                }
            }
        });
    }

    public static void getAll(final GetAllCavesCallback callback){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Cave");
        query.fromLocalDatastore();
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null){
                    List<Cave> caves = new ArrayList<Cave>();
                    for(ParseObject object: objects ) {
                        caves.add(Cave.fromJson(object.getString("json")));
                    }
                    callback.getAllCaves(caves);
                } else {
                    Log.e("ParseException", "Ошибка при получении всех пещер");
                }

            }
        });
    }

    public void save(final SaveCaveCallback callback) {
        final Cave self = this;
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Cave");
        query.whereEqualTo("id", this.id);
        query.fromLocalDatastore();
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                SaveCallback saveCallback = new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        callback.onSaveCave();
                    }
                };
                if (parseObject == null) {
                    ParseObject cave = new ParseObject("Cave");
                    self.id = Cave.newId();
                    cave.put("id", self.id);
                    cave.put("json", self.toJson());
                    cave.pinInBackground(saveCallback);
                    cave.saveEventually(saveCallback);
                } else {
                    parseObject.put("json", self.toJson());
                    parseObject.pinInBackground(saveCallback);
                    parseObject.saveEventually(saveCallback);
                }
                if (e != null) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void delete(final DeleteCaveCallback callback) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Cave");
        query.whereEqualTo("id", this.id);
        query.fromLocalDatastore();
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (e == null) {
                    parseObject.deleteInBackground(new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {
                            callback.onDelete();
                        }
                    });
                } else {
                    e.printStackTrace();
                    Log.e("ParseException", "при удалении");
                }
            }
        });
    }

    public void getPickets(final PicketsFinder finder){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Picket");
        query.whereEqualTo("caveId", this.id);
        query.fromLocalDatastore();
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> picketObjects, ParseException e) {
                if (e == null) {
                    List<Picket> pickets = new ArrayList<Picket>();
                    for(ParseObject object : picketObjects) {
                        pickets.add(Picket.fromJson(object.getString("json")));
                    }
                    finder.onPicketsFindDo(pickets);

                } else {
                    Log.e("ParseException", "при получении пикетов пещеры");
                }
            }
        });
    }

    public void addPicket(Picket picket, final Picket.SavePicketCallback callback) {
        picket.setCaveId(this.id);
        picket.save(callback);
    }

    public interface CaveHelper {
        public void onCaveFindDo(Cave cave);
    }

    public interface SaveCaveCallback {
        public void onSaveCave();
    }

    public interface GetAllCavesCallback {
        public void getAllCaves(List<Cave> caves);
    }

    public interface PicketsFinder {
        public void onPicketsFindDo(List<Picket> pickets);
    }

    public interface DeleteCaveCallback {
        public void onDelete();
    }
    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String toString() {
        return this.name + "(from " + this.author + ")";
    }
}
