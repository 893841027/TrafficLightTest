package com.example.zzy4f5da2.trafficjsondatatest1118;

public class LightBean {
    int road;
    int red;
    int yellow;
    int green;

    public int getRoad() {
        return road;
    }

    public void setRoad(int road) {
        this.road = road;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getYellow() {
        return yellow;
    }

    public void setYellow(int yellow) {
        this.yellow = yellow;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    @Override
    public String toString() {
        return "LightBean{" +
                "road=" + road +
                ", red=" + red +
                ", yellow=" + yellow +
                ", green=" + green +
                '}';
    }
}
