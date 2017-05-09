package edu.eci.cosw.cheapestPrice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.eci.cosw.cheapestPrice.adapters.ItemsAdapter;
import edu.eci.cosw.cheapestPrice.entities.Item;
import edu.eci.cosw.cheapestPrice.network.ItemRetrofitNetwork;
import edu.eci.cosw.cheapestPrice.network.NetworkException;
import edu.eci.cosw.cheapestPrice.network.RequestCallback;


public class ProductActivity extends AppCompatActivity {

    private List<Item> items;
    private RecyclerView recyclerView;
    private ItemRetrofitNetwork network;
    private int id;
    private int shop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Intent intent=getIntent();
        Bundle b = intent.getBundleExtra("bundle");
        setId(((int) b.getSerializable("id")));
        setShop((int)b.getSerializable("shopId"));
        System.out.println(id+" "+shop);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        configureRecyclerView();
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
                                refresh();
                            }
                        });
                    }
                    @Override
                    public void onFailed(NetworkException e) {
                        System.out.println(e);
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
