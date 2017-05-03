package edu.eci.cosw.cheapestPrice;

import android.content.Intent;
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

public class ShoppingListActivity extends AppCompatActivity {

    private Usuario usuario;
    private String correoUsuario;
    private RecyclerView recyclerView;
    private ListaMercadoRetrofitNetwork network;
    private Button ver;
    private Button eliminar;
    private Button agregarLista;

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
                }, getCorreoUsuario());

            }

        });
        //Colocar intent para ver la lista
        ver=(Button) findViewById(R.id.ver);
        ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),ShoppingListProductActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("post",usuario);
                Intent start=intent.putExtra("bundle",b);
                startActivity(start);
            }
        });
        //Colocar acción para eliminar una lista
        eliminar=(Button) findViewById(R.id.eliminar);
        //Agregar función para agregar una nueva lista
        agregarLista=(Button) findViewById(R.id.agregarLista);
    }

    private void configureRecyclerView() {
        setRecyclerView((RecyclerView) findViewById( R.id.recyclerView ));
        getRecyclerView().setHasFixedSize( true );
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( this );
        getRecyclerView().setLayoutManager( layoutManager );
    }

    private void refresh(){
        getRecyclerView().setAdapter(new ListasMercadoAdapter(getUsuario().getListas(),this));
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
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
