package edu.eci.cosw.cheapestPrice;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        Intent intent=getIntent();

        Bundle b = intent.getBundleExtra("bundle");
        setIdUsuario(((int) b.getSerializable("id")));
        System.out.println(idUsuario);
        //Agregar función para agregar una nueva lista
        agregarLista=(FloatingActionButton) findViewById(R.id.agregarLista);
        agregarLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),AgregarLista.class);
                Bundle b=new Bundle();
                b.putSerializable("postUsuario",usuario);
                Intent start=intent.putExtra("bundleUsuario",b);
                startActivity(start);
            }
        });
        configureRecyclerView();
        setNetwork(new ListaMercadoRetrofitNetwork());
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                getNetwork().getUsuarioById(new RequestCallback<Usuario>() {
                    @Override
                    public void onSuccess(Usuario response) {
                        System.out.println("response: "+response.getId()+" "+response.getCorreo());
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

    }

    public void mas(View v){
        System.out.println("entró al mas");
        Intent intent=new Intent(v.getContext(),AgregarLista.class);
        startActivity(intent);
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
        return idUsuario;
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
