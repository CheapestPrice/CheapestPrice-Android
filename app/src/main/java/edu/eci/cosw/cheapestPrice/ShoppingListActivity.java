package edu.eci.cosw.cheapestPrice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import edu.eci.cosw.cheapestPrice.R;
import edu.eci.cosw.cheapestPrice.adapters.ListasMercadoAdapter;
import edu.eci.cosw.cheapestPrice.entities.ListaDeMercado;

public class ShoppingListActivity extends AppCompatActivity {

    private List<ListaDeMercado> listas;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
    }
}
