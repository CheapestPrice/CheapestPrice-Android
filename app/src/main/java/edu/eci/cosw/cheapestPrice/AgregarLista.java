package edu.eci.cosw.cheapestPrice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.eci.cosw.cheapestPrice.entities.ListaDeMercado;
import edu.eci.cosw.cheapestPrice.entities.Usuario;
import edu.eci.cosw.cheapestPrice.network.ListaMercadoRetrofitNetwork;
import edu.eci.cosw.cheapestPrice.network.NetworkException;
import edu.eci.cosw.cheapestPrice.network.RequestCallback;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 07/05/17.
 */

public class AgregarLista extends AppCompatActivity {

    private EditText nombreListaNueva;
    private Usuario u;
    private String nombreLista;
    private ListaDeMercado listaDeMercado;
    private Button agregar;
    private Button volver;
    private ExecutorService executorService;
    private ListaMercadoRetrofitNetwork network;
    ProgressDialog cargando;

    public void cargar() {
        cargando.setMessage("Cargando...");
        cargando.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        cargando.setIndeterminate(true);
        cargando.setCanceledOnTouchOutside(false);
        cargando.show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle b = intent.getBundleExtra("bundleUsuario");
        u = (Usuario) b.getSerializable("postUsuario");
        executorService = Executors.newFixedThreadPool(1);
        setContentView(R.layout.add_shopping_list);
        network = new ListaMercadoRetrofitNetwork();
        nombreListaNueva = (EditText) findViewById(R.id.nombreListaNueva);
        nombreLista = nombreListaNueva.getText().toString();
        agregar = (Button) findViewById(R.id.agregarListaNueva);
        volver = (Button) findViewById(R.id.volverNueva);
        listaDeMercado = new ListaDeMercado(nombreLista);
        listaDeMercado.setUsuario(u);
        listaDeMercado.setIdUsuario(u.getId());
        cargando= new ProgressDialog(this);
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
                        listaDeMercado.setNombre(nombreListaNueva.getText().toString());
                        Callback<Void> callb=new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                System.out.println(response);
                                System.out.println(call);
                                Intent intent=new Intent(view.getContext(),ShoppingListActivity.class);
                                Bundle b = new Bundle();
                                b.putSerializable("id",u.getId());
                                Intent start=intent.putExtra("bundle",b);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        cargando.hide();
                                    }
                                });
                                startActivity(start);
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        cargando.hide();
                                    }
                                });
                                System.out.println(t.getLocalizedMessage());
                            }
                        };
                        network.agregarNuevaListaMercado(callb,u.getId(),listaDeMercado);
                    }
                });
            }
        });
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),ShoppingListActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("id",u.getId());
                Intent start=intent.putExtra("bundle",b);
                startActivity(start);
            }
        });
    }
}
