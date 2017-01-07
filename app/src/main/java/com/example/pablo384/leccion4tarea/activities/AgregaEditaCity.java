package com.example.pablo384.leccion4tarea.activities;

import android.content.Intent;
import android.content.Loader;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pablo384.leccion4tarea.R;
import com.example.pablo384.leccion4tarea.models.City;
import com.squareup.picasso.Picasso;

import io.realm.Realm;

public class AgregaEditaCity extends AppCompatActivity {

    private FloatingActionButton fab;

    private int id;
    private EditText name;
    private EditText descripcion;
    private EditText editTextimgCity;
    private Button previwimg;
    private ImageView imageViewpreview;
    private RatingBar stars;

    private Realm realm;
    private City city;

    private int cityId;
    private boolean isCreation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agrega_edita_city);

        realm=Realm.getDefaultInstance();
        referencias();

        // Comprobar si va a ser una acción para editar o para creación
        if (getIntent().getExtras() != null) {
            cityId = getIntent().getExtras().getInt("id");
            isCreation = false;
        } else {
            isCreation = true;
        }
        setActivityTitle();

        if (!isCreation) {
            city = getCityById(cityId);
            bindDataToFields();
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditNewCity();
            }
        });

        previwimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link = editTextimgCity.getText().toString();
                if (link.length() > 0)
                    cargarImageLinkForPreview(editTextimgCity.getText().toString());
            }
        });




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //goToMainActivity();
    }

    private void referencias(){
        name=(EditText)findViewById(R.id.editTextCityName);
        descripcion=(EditText)findViewById(R.id.editTextCityDescription);
        editTextimgCity=(EditText)findViewById(R.id.editTextCityImage);
        stars=(RatingBar) findViewById(R.id.ratingBarCity);
        previwimg=(Button)findViewById(R.id.buttonPreview);
        imageViewpreview=(ImageView)findViewById(R.id.imageViewPreview);
        fab=(FloatingActionButton)findViewById(R.id.FABEditCity);
    }
    private void setActivityTitle() {
        String title = "Edit City";
        if (isCreation) title = "Create New City";
        setTitle(title);
    }
    private City getCityById(int cityId) {
        return realm.where(City.class).equalTo("id", cityId).findFirst();
    }

    private void bindDataToFields() {
        name.setText(city.getName());
        descripcion.setText(city.getDescripcion());
        editTextimgCity.setText(city.getImgCity());
        cargarImageLinkForPreview(city.getImgCity());
        stars.setRating(city.getStars());
    }

    private void cargarImageLinkForPreview(String link) {
        Picasso.with(this).load(link).fit().into(imageViewpreview);
    }

    private boolean isValidDataForNewCity() {
        if (name.getText().toString().length() > 0 &&
                descripcion.getText().toString().length() > 0 &&
                editTextimgCity.getText().toString().length() > 0) {
            return true;
        } else {
            return false;
        }
    }
    private void goToMainActivity() {
        Intent intent = new Intent(AgregaEditaCity.this, MainActivity.class);
        startActivity(intent);
    }

    private void addEditNewCity() {
        if (isValidDataForNewCity()) {
            String namec = name.getText().toString();
            String description = descripcion.getText().toString();
            String link = editTextimgCity.getText().toString();
            float starsc = stars.getRating();

            City city = new City(namec, description, link, starsc);
            // En caso de que sea una edición en vez de creación pasamos el ID
            if (!isCreation) city.setId(cityId);

            realm.beginTransaction();
            realm.copyToRealmOrUpdate(city);
            realm.commitTransaction();

            goToMainActivity();
        } else {
            Toast.makeText(this, "The data is not valid, please check the fields again", Toast.LENGTH_SHORT).show();
        }
    }


}
