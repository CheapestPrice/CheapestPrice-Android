package edu.eci.cosw.cheapestPrice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import edu.eci.cosw.cheapestPrice.adapters.ItemsListaAdapter;
import edu.eci.cosw.cheapestPrice.entities.ListaDeMercado;
import edu.eci.cosw.cheapestPrice.network.ListaMercadoRetrofitNetwork;

/**
 * Created by User on 02/05/17.
 */

public class ShoppingListProductActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListaDeMercado listaMercadoUsuario;
    private TextView nombreLista;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_list_products);
        nombreLista=(TextView) findViewById(R.id.nombreTitulo);
        Intent intent=getIntent();
        Bundle b = intent.getBundleExtra("bundle");
        setListaMercadoUsuario((ListaDeMercado) b.getSerializable("post"));
        configureRecyclerView();
        refresh();
    }

    private void configureRecyclerView() {
        setRecyclerView((RecyclerView) findViewById( R.id.recyclerViewShoppingListProduct));
        getRecyclerView().setHasFixedSize( true );
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( this );
        getRecyclerView().setLayoutManager( layoutManager );
    }

    private void refresh(){
        nombreLista.setText(listaMercadoUsuario.getListaid().getNombre());
        getRecyclerView().setAdapter(new ItemsListaAdapter(getListaMercadoUsuario(),this));
    }


    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public ListaDeMercado getListaMercadoUsuario() {
        return listaMercadoUsuario;
    }

    public void setListaMercadoUsuario(ListaDeMercado listaMercadoUsuario) {
        this.listaMercadoUsuario = listaMercadoUsuario;
    }
}
