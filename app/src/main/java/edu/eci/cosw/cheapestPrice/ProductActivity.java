package edu.eci.cosw.cheapestPrice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.eci.cosw.cheapestPrice.adapters.ItemsAdapter;
import edu.eci.cosw.cheapestPrice.entities.Item;
import edu.eci.cosw.cheapestPrice.entities.Tienda;
import edu.eci.cosw.cheapestPrice.network.ItemRetrofitNetwork;
import edu.eci.cosw.cheapestPrice.network.NetworkException;
import edu.eci.cosw.cheapestPrice.network.RequestCallback;


public class ProductActivity extends AppCompatActivity {

    private List<Item> items;
    private RecyclerView recyclerView;
    private ItemRetrofitNetwork network;
    private int id;
    private int shop;
    private Tienda tienda;
    private FloatingActionButton agregarItemTendero;
    ProgressDialog cargando;
    public void cargar(){
        cargando.setMessage("Cargando...");
        cargando.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        cargando.setIndeterminate(true);
        cargando.setCanceledOnTouchOutside(false);
        cargando.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        cargando=new ProgressDialog(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        agregarItemTendero=(FloatingActionButton) findViewById(R.id.agregarItemNuevo);
        Intent intent=getIntent();
        final Bundle b = intent.getBundleExtra("bundle");
        setId(((int) b.getSerializable("id")));
        setShop((int)b.getSerializable("shopId"));
        tienda=(Tienda)b.getSerializable("tienda");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        configureRecyclerView();
        agregarItemTendero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),AgregarItemTendero.class);
                b.putSerializable("postShopId",shop);
                b.putSerializable("postId",id);
                b.putSerializable("tienda",tienda);
                Intent start= intent.putExtra("bundleLOL",b);
                startActivity(start);
            }
        });
        network = new ItemRetrofitNetwork();
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        executorService.execute(new Runnable() {
            int shop;
            int id;

            public Runnable init(int iduser, int idshop){
                shop=idshop;
                id=iduser;
                return this;
            }

            @Override
            public void run() {
                network.getItemsByShop(new RequestCallback<List<Item>>(){
                    @Override
                    public void onSuccess(List<Item> response) {
                        System.out.println("response: "+response);
                        items=response;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                cargando.hide();
                                refresh();
                            }
                        });
                    }
                    @Override
                    public void onFailed(NetworkException e) {
                        System.out.println(e);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                cargando.hide();
                            }
                        });
                    }
                }, id, shop);

            }
        }.init(getId(), getShop()));

    }

    private void configureRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void refresh(){
        recyclerView.setAdapter(new ItemsAdapter(items, this,id, getShop()));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShop() {
        return shop;
    }

    public void setShop(int shop) {
        this.shop = shop;
    }
}
