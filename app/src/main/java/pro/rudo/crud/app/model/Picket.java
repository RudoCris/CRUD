package pro.rudo.crud.app.model;

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
 * Created by rudolf on 03.05.14.
 */
public class Picket {

    private static int count;
    private String objectId;
    private int caveId;
    private boolean isRay = false;
    private String from;
    private String to;
    private double length;
    private double azimuth;
    private double incline;
    private double backAzimuth;
    private double backIncline;
    private double left;
    private int id;
    private int mapId;
    private String leftComment;
    private double right;
    private String rightComment;
    private double up;
    private String upComment;
    private double down;
    private String downComment;
    private String comment;

    public Picket(PicketBuilder builder){
        this.isRay = builder.isRay();
        this.from = builder.getFrom();
        this.to = builder.getTo();
        this.azimuth = builder.getAzimuth();
        this.backAzimuth = builder.getBackAzimuth();
        this.incline = builder.getIncline();
        this.backIncline = builder.getBackIncline();
        this.left = builder.getLeft();
        this.leftComment = builder.getLeftComment();
        this.right = builder.getRight();
        this.rightComment = builder.getRightComment();
        this.up = builder.getUp();
        this.upComment = builder.getUpComment();
        this.down = builder.getDown();
        this.downComment = builder.getDownComment();
        this.comment = builder.getComment();
    }

    public Picket(Picket builder){
        this.isRay = builder.isRay();
        this.from = builder.getFrom();
        this.to = builder.getTo();
        this.azimuth = builder.getAzimuth();
        this.backAzimuth = builder.getBackAzimuth();
        this.incline = builder.getIncline();
        this.backIncline = builder.getBackIncline();
        this.left = builder.getLeft();
        this.leftComment = builder.getLeftComment();
        this.right = builder.getRight();
        this.rightComment = builder.getRightComment();
        this.up = builder.getUp();
        this.upComment = builder.getUpComment();
        this.down = builder.getDown();
        this.downComment = builder.getDownComment();
        this.comment = builder.getComment();
    }

    public interface PicketHelper {
        public void onPicketFindDo(Picket picket);
    }

    public interface GetAllPicketsCallback {
        public void getAllPickets(List<Picket> pickets);
    }
    public interface SavePicketCallback {
        public void onSavePicket();
    }

    public interface DeletePicketCallback {
        public void onDelete();
    }

    public static int newId(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Counter");
        query.fromLocalDatastore();
        query.whereEqualTo("class", "Picket");

        try {
            ParseObject countObj = query.getFirst();
            count = countObj.getInt("count");
            countObj.put("count", ++count);
            countObj.pin();
            return count;
        } catch (ParseException e) {
            ParseObject count = new ParseObject("Counter");
            count.put("class", "Picket");
            count.put("count", 0);
            count.pinInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                }
            });
            return 0;
        }
    }
    public static void find(int id, final PicketHelper helper){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Picket");
        query.whereEqualTo("id", id);
        query.fromLocalDatastore();
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (e == null) {
                    Picket picket = Picket.fromJson(parseObject.getString("json"));
                    helper.onPicketFindDo(picket);
                } else {
                    Log.e("ParseException", "при поиске объекта с id");
                }
            }
        });
    }

    public static void getAll(final GetAllPicketsCallback callback){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Picket");
        query.fromLocalDatastore();
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null){
                    List<Picket> pickets = new ArrayList<Picket>();
                    for(ParseObject object: objects ) {
                        pickets.add(Picket.fromJson(object.getString("json")));
                    }
                    callback.getAllPickets(pickets);
                } else {
                    Log.e("ParseException", "Ошибка при получении всех пикетов");
                }

            }
        });
    }

    public void save(final SavePicketCallback callback) {
        final Picket self = this;
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Picket");
        query.whereEqualTo("id", this.id);
        query.fromLocalDatastore();
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                SaveCallback saveCallback = new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        callback.onSavePicket();
                    }
                };
                if (parseObject == null) {
                    ParseObject picket = new ParseObject("Picket");
                    self.id = Picket.newId();
                    picket.put("id", self.id);
                    picket.put("json", self.toJson());
                    picket.put("caveId", self.caveId);
                    picket.pinInBackground(saveCallback);
                    picket.saveEventually(saveCallback);
                } else {
                    parseObject.put("json", self.toJson());
                    parseObject.put("caveId", self.caveId);
                    parseObject.pinInBackground(saveCallback);
                    parseObject.saveEventually(saveCallback);
                }
                if (e != null) {
                    e.printStackTrace();
                    Log.e("ParseException", "при поиске пикета");
                }
            }
        });
    }

    public void delete(final DeletePicketCallback callback) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Picket");
        query.fromLocalDatastore();
        query.getInBackground(this.objectId, new GetCallback<ParseObject>() {
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
                    Log.e("ParseException", "при удалении пикета");
                }
            }
        });
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static Picket fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Picket.class);
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String id) {
        this.objectId = id;
    }

    public int getCaveId() {
        return caveId;
    }

    public void setCaveId(int caveId) {
        this.caveId = caveId;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLeftComment() {
        return leftComment;
    }

    public void setLeftComment(String leftComment) {
        this.leftComment = leftComment;
    }

    public String getRightComment() {
        return rightComment;
    }

    public void setRightComment(String rightComment) {
        this.rightComment = rightComment;
    }

    public String getUpComment() {
        return upComment;
    }

    public void setUpComment(String upComment) {
        this.upComment = upComment;
    }

    public String getDownComment() {
        return downComment;
    }

    public void setDownComment(String downComment) {
        this.downComment = downComment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isRay() {
        return isRay;
    }

    public void setRay(boolean isRay) {
        this.isRay = isRay;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getAzimuth() {
        return azimuth;
    }

    public void setAzimuth(double azimuth) {
        this.azimuth = azimuth;
    }

    public double getIncline() {
        return incline;
    }

    public void setIncline(double incline) {
        this.incline = incline;
    }

    public double getBackAzimuth() {
        return backAzimuth;
    }

    public void setBackAzimuth(double backAzimuth) {
        this.backAzimuth = backAzimuth;
    }

    public double getBackIncline() {
        return backIncline;
    }

    public void setBackIncline(double backIncline) {
        this.backIncline = backIncline;
    }

    public double getLeft() {
        return left;
    }

    public void setLeft(double left) {
        this.left = left;
    }

    public double getRight() {
        return right;
    }

    public void setRight(double right) {
        this.right = right;
    }

    public double getUp() {
        return up;
    }

    public void setUp(double up) {
        this.up = up;
    }

    public double getDown() {
        return down;
    }

    public void setDown(double down) {
        this.down = down;
    }

    public String toString() {
        return "От " + this.from + " до " + this.to;
    }

}
