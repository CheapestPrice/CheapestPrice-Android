package edu.eci.cosw.cheapestPrice.services;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Spinner;

import java.util.List;

import edu.eci.cosw.cheapestPrice.R;
import edu.eci.cosw.cheapestPrice.entities.Usuario;

/**
 * Created by 2105403 on 5/8/17.
 */

public class AddItemToShoppingList extends AppCompatActivity {

    private Spinner elegirLista;
    private List<String> listas;
    private Usuario u;

    public void onCreate(Bundle savedIntanceState){
        super.onCreate(savedIntanceState);
        setContentView(R.layout.add_product_to_list);
        elegirLista=(Spinner) findViewById(R.id.shoppingList_spinner);

    }
}
