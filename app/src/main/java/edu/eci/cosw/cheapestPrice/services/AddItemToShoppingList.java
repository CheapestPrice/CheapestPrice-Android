package edu.eci.cosw.cheapestPrice.services;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;


import edu.eci.cosw.cheapestPrice.R;
import edu.eci.cosw.cheapestPrice.entities.ListaDeMercado;
import edu.eci.cosw.cheapestPrice.entities.Usuario;

/**
 * Created by 2105403 on 5/8/17.
 */

public class AddItemToShoppingList extends AppCompatActivity {

    private Spinner elegirLista;
    private ArrayList<String> listas = new ArrayList<>();
    private Usuario u;

    public void onCreate(Bundle savedIntanceState){
        super.onCreate(savedIntanceState);
        setContentView(R.layout.add_product_to_list);
        elegirLista=(Spinner) findViewById(R.id.shoppingList_spinner);
        for(ListaDeMercado l:u.getListas()){
            listas.add(l.getNombre());
        }
        Intent intent=getIntent();
        Bundle b=intent.getBundleExtra("bundle");
        u=(Usuario) b.getSerializable("id");
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listas); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        elegirLista.setAdapter(spinnerArrayAdapter);
    }
}
