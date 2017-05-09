package edu.eci.cosw.cheapestPrice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.eci.cosw.cheapestPrice.adapters.OpinionAdapter;
import edu.eci.cosw.cheapestPrice.entities.Opinion;
import edu.eci.cosw.cheapestPrice.entities.Tienda;
import edu.eci.cosw.cheapestPrice.network.NetworkException;
import edu.eci.cosw.cheapestPrice.network.RequestCallback;
import edu.eci.cosw.cheapestPrice.network.ShopRetrofitNetwork;

/**
 * Created by nautiluz92 on 5/8/17.
 */

public class OpinionActivity extends AppCompatActivity {

    Bundle bundle;

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    private RecyclerView recyclerView;
    private ShopRetrofitNetwork network;
    private int id = 3;
    private int shop = 1;

    public List<Opinion> getOpiniones() {
        return opiniones;
    }

    public void setOpiniones(List<Opinion> opiniones) {
        this.opiniones = opiniones;
    }

    private List<Opinion> opiniones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinions);
        Intent intent = getIntent();
        bundle = intent.getBundleExtra("bundle");
        setId(((int) bundle.getSerializable("id")));
        setShop((int) bundle.getSerializable("shopId"));
        configureRecyclerView();
        setNetwork(new ShopRetrofitNetwork());
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                getNetwork().getOpinionesTienda(new RequestCallback<List<Opinion>>() {
                    @Override
                    public void onSuccess(List<Opinion> response) {
                        setOpiniones(response);
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
                }, id,shop);

            }});

    }

    public void refresh(){
        System.out.println("si trae opiniones en refresh");
        getRecyclerView().setAdapter(new OpinionAdapter(opiniones, this,this.id,this.shop));
    }

    private void configureRecyclerView()
    {
        recyclerView = (RecyclerView) findViewById( R.id.recyclerViewOpinion );
        recyclerView.setHasFixedSize( true );
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( this );
        recyclerView.setLayoutManager( layoutManager );
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

    public ShopRetrofitNetwork getNetwork() {
        return network;
    }

    public void setNetwork(ShopRetrofitNetwork network) {
        this.network = network;
    }

}
