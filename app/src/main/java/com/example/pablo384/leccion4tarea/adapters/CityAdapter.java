package com.example.pablo384.leccion4tarea.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pablo384.leccion4tarea.R;
import com.example.pablo384.leccion4tarea.models.City;
import com.squareup.picasso.Picasso;

import java.util.List;


public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    private Context context;
    private List<City> cities;
    private int layout;
    private OnItemClickListener itemClickListener;
    private OnButtonClickListener btnClickListener;

    public CityAdapter(Context context, List<City> cities, int layout, OnItemClickListener itemClickListener, OnButtonClickListener btnClickListener) {
        this.context = context;
        this.cities = cities;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
        this.btnClickListener = btnClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflamos nuestra vista de parent(nuestro mainactivity) y el layouy que usaremos
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        //obtenemos el contexto de nuestro parent(mainactivity)
        context = parent.getContext();
        //creamos ViewHlder y le pasamos nuestra vista inflada
        ViewHolder vh = new ViewHolder(v);
        //devolvemos nuestro ViewHolder
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //al holder.bin le pasamos la posicion de nuestra lista y eventos del cardview y boton
        holder.bind(cities.get(position),itemClickListener,btnClickListener);
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{


        //declaramos todos los elementos que necesitamos en nuestra vista cardview
        public TextView name;
        public TextView description;
        public TextView stars;
        public ImageView image;
        public Button btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            //recojemos las referencias de los items que necesitamos
            name = (TextView) itemView.findViewById(R.id.textViewCityName);
            description = (TextView) itemView.findViewById(R.id.textViewCityDescription);
            stars = (TextView) itemView.findViewById(R.id.textViewStars);
            image = (ImageView) itemView.findViewById(R.id.imageViewCityimage);
            btnDelete = (Button) itemView.findViewById(R.id.buttonDeleteCity);
        }

        public void bind(final City city, final OnItemClickListener itemListener, final OnButtonClickListener btnListener){
            //les establecemos sus datos correspondiente a los items de nuestra vista
            name.setText(city.getName());
            description.setText(city.getDescripcion());
            stars.setText(city.getStars() + "");
            Picasso.with(context).load(city.getImgCity()).fit().into(image);

            //procedemos a asignarles los eventos a nuestros items tanto cardview como boton
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //usamos la interfas de cada Eveto que creamos abajo, para asignar evento
                    btnListener.OnButtonClic(city, getAdapterPosition());
                }
            });


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //usamos la interfas de cada Eveto que creamos abajo, para asignar evento
                    itemListener.OnItemClick(city, getAdapterPosition());
                }
            });
        }
    }

    //eventos de cardvire y boton
    public interface OnItemClickListener{
        void OnItemClick(City city, int position);
    }

    public interface OnButtonClickListener{
        void OnButtonClic(City city, int position);
    }
}
