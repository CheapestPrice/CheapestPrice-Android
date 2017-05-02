package edu.eci.cosw.cheapestPrice;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        configureRecyclerView();
        network = new ItemRetrofitNetwork();
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                network.getItems(new RequestCallback<List<Item>>() {
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
                });

            }

        });
    }

    private void configureRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void refresh(){
        recyclerView.setAdapter(new ItemsAdapter(items,this));
    }
}
