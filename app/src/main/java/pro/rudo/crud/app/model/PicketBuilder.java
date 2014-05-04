package pro.rudo.crud.app.model;

public class PicketBuilder {
    private boolean isRay = false;
    private String from;
    private String to;
    private double length;
    private double azimuth;
    private double incline;
    private double backAzimuth;
    private double backIncline;
    private double left;
    private String leftComment;
    private double right;
    private String rightComment;
    private double up;
    private String upComment;
    private double down;
    private String downComment;
    private String comment;
    private int id;
    private int mapId;

    public PicketBuilder(){

    }

    public Picket createPicket() {
        return new Picket(this);
    }

    public String getLeftComment() {
        return leftComment;
    }

    public String getRightComment() {
        return rightComment;
    }

    public String getUpComment() {
        return upComment;
    }

    public String getDownComment() {
        return downComment;
    }

    public String getComment() {
        return comment;
    }

    public boolean isRay() {
        return isRay;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public double getLength() {
        return length;
    }

    public double getAzimuth() {
        return azimuth;
    }

    public double getIncline() {
        return incline;
    }

    public double getBackAzimuth() {
        return backAzimuth;
    }

    public double getBackIncline() {
        return backIncline;
    }

    public double getLeft() {
        return left;
    }

    public double getRight() {
        return right;
    }

    public double getUp() {
        return up;
    }

    public double getDown() {
        return down;
    }

    public PicketBuilder withRay(boolean ray) {
        this.isRay = ray;
        return this;
    }

    public PicketBuilder withFrom(String from) {
        this.from = from;
        return this;
    }

    public PicketBuilder withTo(String to) {
        this.to = to;
        return this;
    }

    public PicketBuilder withLength(double length) {
        this.length = length;
        return this;
    }

    public PicketBuilder withAzimuth(double azimuth) {
        this.azimuth = azimuth;
        return this;
    }

    public PicketBuilder withIncline(double incline) {
        this.incline = incline;
        return this;
    }

    public PicketBuilder withBackAzimuth(double azimuth) {
        this.backAzimuth = azimuth;
        return this;
    }

    public PicketBuilder withBackIncline(double incline) {
        this.backIncline = incline;
        return this;
    }

    public PicketBuilder withLeft(double left) {
        this.left = left;
        return this;
    }

    public PicketBuilder withLeftComment(String comment) {
        this.leftComment = comment;
        return this;
    }

    public PicketBuilder withRight(double right) {
        this.right = right;
        return this;
    }

    public PicketBuilder withRightComment(String comment) {
        this.rightComment = comment;
        return this;
    }

    public PicketBuilder withUp(double up) {
        this.up = up;
        return this;
    }

    public PicketBuilder withUpComment(String comment) {
        this.upComment = comment;
        return this;
    }

    public PicketBuilder withDown(double down) {
        this.down = down;
        return this;
    }

    public PicketBuilder withDownComment(String comment) {
        this.downComment = comment;
        return this;
    }

    public PicketBuilder withComment(String comment) {
        this.comment = comment;
        return this;
    }

    public PicketBuilder withMap(int id){
        this.mapId = id;
        return this;
    }

//    public PicketBuilder withMap(Map map){
//        this.mapId = map.getId();
//        return this;
//    }
}