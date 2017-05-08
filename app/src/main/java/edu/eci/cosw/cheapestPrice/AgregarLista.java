package edu.eci.cosw.cheapestPrice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.eci.cosw.cheapestPrice.entities.ListaDeMercado;
import edu.eci.cosw.cheapestPrice.network.ListaMercadoRetrofitNetwork;
import edu.eci.cosw.cheapestPrice.network.NetworkException;
import edu.eci.cosw.cheapestPrice.network.RequestCallback;
import okhttp3.ResponseBody;

/**
 * Created by User on 07/05/17.
 */

public class AgregarLista extends AppCompatActivity {

    private EditText nombreListaNueva;
    private String nombreLista;
    private ListaDeMercado listaDeMercado;
    private Button agregar;
    private Button volver;
    private ExecutorService executorService;
    private ListaMercadoRetrofitNetwork network;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        executorService= Executors.newFixedThreadPool(1);
        setContentView(R.layout.add_shopping_list);
        network=new ListaMercadoRetrofitNetwork();
        nombreListaNueva=(EditText) findViewById(R.id.nombreListaNueva);
        nombreLista=nombreListaNueva.getText().toString();
        agregar=(Button) findViewById(R.id.agregarListaNueva);
        volver=(Button) findViewById(R.id.volverNueva);
        listaDeMercado=new ListaDeMercado(nombreLista);
        mostrar();
    }

    public void mostrar(){
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view=v;
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        network.agregarNuevaListaMercado(new RequestCallback<ResponseBody>() {
                            @Override
                            public void onSuccess(ResponseBody response) {
                                Intent intent=new Intent(view.getContext(),ShoppingListActivity.class);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailed(NetworkException e) {
                                System.out.println(e);
                            }
                        },listaDeMercado.getId(),listaDeMercado);
                    }
                });
            }
        });
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),ShoppingListActivity.class);
                startActivity(intent);
            }
        });
    }
}
