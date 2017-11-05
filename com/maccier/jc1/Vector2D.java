package com.maccier.jc1;

public class Vector2D {


    private double x,y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getDist(){
        return Math.sqrt(Math.pow( this.x, 2) + Math.pow( this.y, 2));
    }

    public Vector2D clone(){
        return new Vector2D(this.x, this.y);
    }

    public void normalise(){
        double d = this.getDist();
        this.x /= d;
        this.y /= d;
    }

    public void multiply(double nb){
        this.x *= nb;
        this.y *= nb;
    }

    public void add(Vector2D vec){
        this.x += vec.getX();
        this.y += vec.getY();
    }

    public void sous(Vector2D vec){
        this.x -= vec.getX();
        this.y -= vec.getY();
    }

    
    public void addToX(int x){
    	this.x += x;
    }
    
    public void addToY(int y){
    	this.y += y;
    }
    
    @Override
    public String toString() {
        return "Vector2D{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public double getAngle(){
        Vector2D vec = this.clone();
        vec.normalise();
        return Math.toDegrees(Math.atan2(vec.y, vec.x));
    }

    public static Vector2D getVectorWithAngle(double angle){


        double ang = 180+360+angle;
        ang %= 360;
        ang -= 180;



        double a =   ang ;
        a /= 180;
        a *= Math.PI ;



        double ax = Math.cos(Math.toRadians(angle));
        double ay = Math.sin(Math.toRadians(angle));




        return new Vector2D(ax, ay);
    }

    public double getX() {
        return x;
    }
    
    public void setX(double x){
    	this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y){
    	this.y = y;
    }

    
}
