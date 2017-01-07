package com.example.pablo384.leccion4tarea.models;


import com.example.pablo384.leccion4tarea.aplication.MyApp;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class City extends RealmObject{


    @PrimaryKey
    private int id;
    @Required
    private String name;
    @Required
    private String descripcion;
    @Required
    private String imgCity;
    private float stars;

    //siempre debemos declarar un constructor sin argumentos
    public City(){

    }

    public City(String name, String descripcion, String imgCity, float stars) {
        this.id = MyApp.CityID.incrementAndGet();
        this.name = name;
        this.descripcion = descripcion;
        this.imgCity = imgCity;
        this.stars = stars;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImgCity() {
        return imgCity;
    }

    public void setImgCity(String imgCity) {
        this.imgCity = imgCity;
    }

    public float getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }
}
