package edu.eci.cosw.cheapestPrice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.eci.cosw.cheapestPrice.entities.Item;
import edu.eci.cosw.cheapestPrice.entities.Producto;
import edu.eci.cosw.cheapestPrice.entities.Tienda;
import edu.eci.cosw.cheapestPrice.network.ItemRetrofitNetwork;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 09/05/17.
 */

public class AgregarItemTendero extends AppCompatActivity {

    EditText nombreItemNuevo;
    EditText precioItemNuevo;
    EditText marcaItemNuevo;
    EditText categoriaItemNuevo;
    ImageView imagenItemNuevo;
    Button buscarImagen;
    Button agregarItemTendero;
    int tenderoId;
    int tiendaId;
    Tienda tienda;
    Item item;
    Producto producto;
    ItemRetrofitNetwork network;
    ExecutorService executorService;

    public void onCreate(Bundle savedIntance){
        super.onCreate(savedIntance);
        setContentView(R.layout.tendero_agregar_item);
        network=new ItemRetrofitNetwork();
        executorService= Executors.newFixedThreadPool(1);
        nombreItemNuevo=(EditText) findViewById(R.id.nombreItem);
        precioItemNuevo=(EditText) findViewById(R.id.precioItem);
        marcaItemNuevo=(EditText) findViewById(R.id.marcaItem);
        categoriaItemNuevo=(EditText) findViewById(R.id.categoriaItem);
        imagenItemNuevo=(ImageView) findViewById(R.id.imagenItem);
        buscarImagen=(Button) findViewById(R.id.agregarImagenItem);
        agregarItemTendero=(Button) findViewById(R.id.agregarProducto);
        Intent intent=getIntent();
        Bundle b=intent.getBundleExtra("bundleLOL");
        tenderoId=(Integer) b.getSerializable("postId");
        tiendaId=(Integer) b.getSerializable("postShopId");
        tienda=(Tienda) b.getSerializable("tienda");
        setear();
    }

    public void setear(){
        item=new Item();
        producto=new Producto();
        item.setTienda(tienda);
        agregarItemTendero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setPrecio(Long.parseLong(precioItemNuevo.getText().toString()));
                producto.setNombre(nombreItemNuevo.getText().toString());
                producto.setCategoria(categoriaItemNuevo.getText().toString());
                producto.setMarca(marcaItemNuevo.getText().toString());
                //producto.setImagen(imagenItemNuevo);
                item.setProducto(producto);
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        Callback<Void> callb=new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                System.out.println(call);
                                System.out.println(response);
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                System.out.println(t.getLocalizedMessage());
                            }
                        };
                        network.postItem(callb,tenderoId,tiendaId,item);
                    }
                });
            }
        });
    }
}
