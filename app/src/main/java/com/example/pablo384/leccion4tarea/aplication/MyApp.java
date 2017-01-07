package com.example.pablo384.leccion4tarea.aplication;

import android.app.Application;

import com.example.pablo384.leccion4tarea.models.City;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;


public class MyApp extends Application {

    public static AtomicInteger CityID =new AtomicInteger();

    @Override
    public void onCreate() {
        configuracionInicial();

        //declaramos nuestra base de datos
        Realm realmbd=Realm.getDefaultInstance();

        CityID=obtenerIdTabla(realmbd, City.class);

        realmbd.close();
    }

    //COnfiguracion Inicial Obligatoria
    private void configuracionInicial(){
        //iniciamos nuestra base de datos (pasamos un this porque esta declarada mas arriba en la misma clase)
        Realm.init(this);
        //configuracion inicial
        RealmConfiguration config=new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        //insertamos configuracion inicial a la Base de datoss
        Realm.setDefaultConfiguration(config);
    }

    //configuracion de ID de cada item de tabla
    private <T extends RealmObject> AtomicInteger obtenerIdTabla (Realm realmbd, Class<T> anyclass){
        //buscamos nuestra base de datos
        RealmResults<T> results=realmbd.where(anyclass).findAll();
        //si nuestra Base de datos es mayor que 0 (tiene items o esta creada) obtenemos el id mas alto
        //para agregar nuevo elemento partiendo del id maximo SINO podemos aregar elemntos partiendo de id 0
        return (results.size()>0) ? new AtomicInteger(results.max("id").intValue()):new AtomicInteger();
    }
}
