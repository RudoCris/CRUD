package pro.rudo.crud.app.model;

import com.google.gson.Gson;

/**
 * Created by rudolf on 03.05.14.
 */
public class Picket {
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

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
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
