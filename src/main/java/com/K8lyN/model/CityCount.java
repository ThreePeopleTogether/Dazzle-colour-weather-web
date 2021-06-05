package com.K8lyN.model;

/**
 * @Author K8lyN
 * @Date 2021/04/08 22:39
 * @Version 1.0
 */
public class CityCount {

    private int id;
    private String cityName;
    private int count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "CityCount{" +
                "id=" + id +
                ", cityName='" + cityName + '\'' +
                ", count=" + count +
                '}';
    }
}
