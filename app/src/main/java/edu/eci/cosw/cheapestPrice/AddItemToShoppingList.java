package edu.eci.cosw.cheapestPrice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import edu.eci.cosw.cheapestPrice.R;
import edu.eci.cosw.cheapestPrice.entities.Item;
import edu.eci.cosw.cheapestPrice.entities.ItemLista;
import edu.eci.cosw.cheapestPrice.entities.ListaDeMercado;
import edu.eci.cosw.cheapestPrice.entities.Usuario;
import edu.eci.cosw.cheapestPrice.network.ListaMercadoRetrofitNetwork;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 2105403 on 5/8/17.
 */

public class AddItemToShoppingList extends AppCompatActivity {

    Spinner elegirLista;
    ArrayList<String> listas = new ArrayList<>();
    Usuario u;
    public String listaElegida;
    Item item;
    ItemLista itemLista;
    Button agregarItemALista;
    ListaMercadoRetrofitNetwork network;
    ExecutorService executorService;
    int idListaMercado;
    ProgressDialog cargando;

    public void cargar() {
        cargando.setMessage("Cargando...");
        cargando.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        cargando.setIndeterminate(true);
        cargando.setCanceledOnTouchOutside(false);
        cargando.show();
    }

    @Override
    public void onCreate(Bundle savedIntanceState) {
        super.onCreate(savedIntanceState);
        setContentView(R.layout.add_product_to_list);
        agregarItemALista = (Button) findViewById(R.id.agregarItemALista);
        elegirLista = (Spinner) findViewById(R.id.shoppingList_spinner);
        network = new ListaMercadoRetrofitNetwork();
        executorService = Executors.newFixedThreadPool(1);
        Intent intent = getIntent();
        Bundle b = intent.getBundleExtra("bundle");
        u = (Usuario) b.getSerializable("id");
        item = (Item) b.getSerializable("item");
        itemLista = new ItemLista();
        itemLista.setItem(item);
        itemLista.setItemId(item.getId());
        itemLista.setComprado(false);
        itemLista.setFavorito(false);
        cargando=new ProgressDialog(this);
        for (ListaDeMercado l : u.getListas()) {
            listas.add(l.getNombre());
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listas);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        elegirLista.setAdapter(spinnerArrayAdapter);
        enviarItem();
    }

    public void enviarItem() {
        cargar();
        agregarItemALista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                listaElegida = elegirLista.getSelectedItem().toString();
                System.out.println("444444444444444444444444444444444444444444444444 " + listaElegida);
                for (ListaDeMercado l : u.getListas()) {
                    if (l.getNombre().equals(listaElegida)) {
                        System.out.println("55555555555555555555555555555555555555555555555 " + l.getNombre() + " " + l.getId());
                        idListaMercado = l.getId();
                        itemLista.setListaId(l.getId());
                        itemLista.setItemId(item.getId());
                    }
                }
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        Callback<Void> callb = new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                System.out.println(call);
                                System.out.println(response);
                                Intent intent = new Intent(v.getContext(), SearchActivity.class);
                                Bundle b = new Bundle();
                                b.putSerializable("id", u.getId());
                                b.putSerializable("user", u);
                                Intent start = intent.putExtra("bundle", b);
                                v.getContext().startActivity(start);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        cargando.hide();
                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                System.out.println(t.getLocalizedMessage());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        cargando.hide();
                                    }
                                });
                            }
                        };
                        network.agregarItemListaMercado(callb, u.getId(), idListaMercado, itemLista);
                    }
                });

            }
        });
    }
}
