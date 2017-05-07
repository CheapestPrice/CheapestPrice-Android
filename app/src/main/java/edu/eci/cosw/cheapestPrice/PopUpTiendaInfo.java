package edu.eci.cosw.cheapestPrice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import edu.eci.cosw.cheapestPrice.adapters.HorarioAdapter;
import edu.eci.cosw.cheapestPrice.entities.Tienda;

/**
 * Created by User on 06/05/17.
 */

public class PopUpTiendaInfo extends AppCompatActivity {

    private Tienda tienda;
    private TextView nombreTienda;
    private TextView telefonoTienda;
    private TextView direcionTienda;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popuptienda);
        Intent intent=getIntent();
        Bundle b = intent.getBundleExtra("bundleTienda");
        tienda=(Tienda) b.getSerializable("postTienda");
        nombreTienda=(TextView) findViewById(R.id.nombreTienda);
        telefonoTienda=(TextView) findViewById(R.id.telefonoTienda);
        direcionTienda=(TextView) findViewById(R.id.direccionTienda);
        configureRecyclerView();
        refresh();

    }

    private void configureRecyclerView() {
        nombreTienda.setText(tienda.getNombre());
        telefonoTienda.setText(tienda.getTelefono());
        direcionTienda.setText(tienda.getDireccion());
        setRecyclerView((RecyclerView) findViewById( R.id.recyclerViewHorariosTienda));
        getRecyclerView().setHasFixedSize( true );
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( this );
        getRecyclerView().setLayoutManager( layoutManager );
    }

    public void refresh(){
        recyclerView.setAdapter(new HorarioAdapter(tienda.getHorarios(),this));
    }


    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }
}
