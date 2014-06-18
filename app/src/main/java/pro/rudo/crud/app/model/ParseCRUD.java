package pro.rudo.crud.app.model;

import android.util.Log;

import com.google.gson.Gson;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by rudolf on 17.06.14.
 */
public class ParseCRUD {
    private static final String TABLE_NAME = "crud";
    private String objectId;

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static ParseCRUD fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, ParseCRUD.class);
    }

    public void save() {
        final ParseCRUD self = this;
        ParseQuery<ParseObject> query = ParseQuery.getQuery(TABLE_NAME);
        query.fromLocalDatastore();
        query.getInBackground(this.objectId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (e == null) {
                    if (parseObject == null) {
                        ParseObject cave = new ParseObject("Cave");
                        cave.put("json", self.toJson());
                        cave.saveEventually();
                    } else {
                        parseObject.put("json", self.toJson());
                        parseObject.saveEventually();
                    }
                } else {
                    Log.e("ParseException", "при поиске объекта с id");
                }
            }
        });
    }

    public void delete() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(TABLE_NAME);
        query.fromLocalDatastore();
        query.getInBackground(this.objectId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (e == null) {
                    parseObject.deleteEventually();
                } else {
                    Log.e("ParseException", "при поиске объекта с id");
                }
            }
        });
    }
}
