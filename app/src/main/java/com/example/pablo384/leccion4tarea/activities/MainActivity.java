package com.example.pablo384.leccion4tarea.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.pablo384.leccion4tarea.R;
import com.example.pablo384.leccion4tarea.adapters.CityAdapter;
import com.example.pablo384.leccion4tarea.models.City;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements RealmChangeListener<RealmResults<City>> {


    private FloatingActionButton fab;

    private RecyclerView myrv;
    private RecyclerView.Adapter myadapter;
    private RecyclerView.LayoutManager layoutManager;

    //declaramos Base de datos
    private Realm realm;
    //necesitamos RealmResult para consultar la base de datos
    private RealmResults<City> citylist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //instanciamos base de datos
        realm=Realm.getDefaultInstance();
        //le decimos a nuestra columna DE OBJETOS donde
        // esta la base de datos usando la instancia de la misma(Base de datos)
        citylist=realm.where(City.class).findAll();
        //aca implementamos en la clase RealmChangeListener<RealmResults<City>> para usar este parametro
        //y avisar al adaptador el cambio en la lista
        citylist.addChangeListener(this);

        //RecyclerView declaracion
        myrv=(RecyclerView)findViewById(R.id.my_recycler_view_city);
        //optimizacion
        myrv.setHasFixedSize(true);
        //animacion
        myrv.setItemAnimator(new DefaultItemAnimator());

        layoutManager=new LinearLayoutManager(this);
        myrv.setLayoutManager(layoutManager);

        fab = (FloatingActionButton) findViewById(R.id.FABAddCity);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AgregaEditaCity.class);
                startActivity(intent);
            }
        });
        setHideShowFAB();

        myadapter=new CityAdapter(this,citylist,R.layout.layout_city_items, new CityAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(City city, int position) {
                //creamos intent para ir a pantalla de editar
                Intent intent = new Intent(MainActivity.this, AgregaEditaCity.class);
                //le pasamos el id de nuesto item a editar
                intent.putExtra("id", city.getId());
                //iniciamos la activity
                startActivity(intent);

            }
        },
                new CityAdapter.OnButtonClickListener() {
            @Override
            public void OnButtonClic(City city, int position) {

                showAlertForRemovingCity("Delete city", "Are you sure you want to delete " + city.getName() + "?", position);
            }
        });

        //le asignamos el adaptador a nuestro RecyclerView
        myrv.setAdapter(myadapter);
        //volvemos a avisar nuestra lista de cambios
        citylist.addChangeListener(this);






    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    private void setHideShowFAB() {
        myrv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0)
                    fab.hide();
                else if (dy < 0)
                    fab.show();
            }
        });
    }
    private void showAlertForRemovingCity(String title, String message, final int position) {

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Remove", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        deleteCity(position);
                        Toast.makeText(MainActivity.this, "It has been deleted successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null).show();
    }
    private void deleteCity(int position) {
        realm.beginTransaction();
        citylist.get(position).deleteFromRealm();
        realm.commitTransaction();
    }


    @Override
    public void onChange(RealmResults<City> element) {
        myadapter.notifyDataSetChanged();
    }
}
