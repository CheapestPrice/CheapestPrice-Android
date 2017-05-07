package edu.eci.cosw.cheapestPrice;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.eci.cosw.cheapestPrice.adapters.ListasMercadoAdapter;
import edu.eci.cosw.cheapestPrice.entities.Usuario;
import edu.eci.cosw.cheapestPrice.network.ListaMercadoRetrofitNetwork;
import edu.eci.cosw.cheapestPrice.network.NetworkException;
import edu.eci.cosw.cheapestPrice.network.RequestCallback;
import okhttp3.ResponseBody;

public class ShoppingListActivity extends AppCompatActivity {

    private Usuario usuario;
    private int idUsuario;
    private RecyclerView recyclerView;
    private ListaMercadoRetrofitNetwork network;
    private FloatingActionButton agregarLista;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        configureRecyclerView();
        setNetwork(new ListaMercadoRetrofitNetwork());
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                getNetwork().getUsuarioByCorreo(new RequestCallback<Usuario>() {
                    @Override
                    public void onSuccess(Usuario response) {
                        System.out.println("response: "+response);
                        setUsuario(response);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refresh();
                            }
                        });
                    }

                    @Override
                    public void onFailed(NetworkException e) {
                        System.out.println(e);
                    }
                }, getIdUsuario());

            }

        });
        //Agregar función para agregar una nueva lista
        agregarLista=(FloatingActionButton) findViewById(R.id.agregarLista);
    }

    private void configureRecyclerView() {
        setRecyclerView((RecyclerView) findViewById( R.id.recyclerViewShoppingList));
        getRecyclerView().setHasFixedSize( true );
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( this );
        getRecyclerView().setLayoutManager( layoutManager );
    }

    public void refresh(){
        getRecyclerView().setAdapter(new ListasMercadoAdapter(getUsuario(), this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.eliminar:
                        //refresh();
                }
            }
        }));
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getIdUsuario() {
        return 2;
        //return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public ListaMercadoRetrofitNetwork getNetwork() {
        return network;
    }

    public void setNetwork(ListaMercadoRetrofitNetwork network) {
        this.network = network;
    }
}
