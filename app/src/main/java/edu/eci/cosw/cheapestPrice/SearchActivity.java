package edu.eci.cosw.cheapestPrice;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.eci.cosw.cheapestPrice.adapters.SearchAdapter;
import edu.eci.cosw.cheapestPrice.entities.Item;
import edu.eci.cosw.cheapestPrice.entities.Usuario;
import edu.eci.cosw.cheapestPrice.network.ItemRetrofitNetwork;
import edu.eci.cosw.cheapestPrice.network.NetworkException;
import edu.eci.cosw.cheapestPrice.network.RequestCallback;

public class SearchActivity extends AppCompatActivity {



    private RecyclerView mRecyclerView;
    private List<Item> mArrayList;
    private SearchAdapter mAdapter;
    private ItemRetrofitNetwork network;
    private Usuario iduser;
    private int idU;
    public Context context;
    public FloatingActionButton boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        context=this;
        boton= (FloatingActionButton) findViewById(R.id.redirect);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        initViews();
        Intent intent=getIntent();
        Bundle b = intent.getBundleExtra("bundle");
        iduser=((Usuario) b.getSerializable("user"));
        idU=((int) b.getSerializable("id"));
        System.out.println("user "+iduser.toString());
        network = new ItemRetrofitNetwork();
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        executorService.execute(new Runnable() {
            Usuario id;

            public Runnable init(Usuario iduser){
                id=iduser;
                System.out.println("1212121212121212121212121211212212121212121212121212121 "+id.getCorreo());
                return this;
            }

            @Override
            public void run() {
                network.getItemsByUser(new RequestCallback<List<Item>>(){
                    @Override
                    public void onSuccess(List<Item> response) {
                        System.out.println("response: "+response);
                        mArrayList=response;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter=new SearchAdapter(mArrayList,context,id);
                                mRecyclerView.setAdapter(mAdapter);
                            }
                        });
                    }
                    @Override
                    public void onFailed(NetworkException e) {
                        System.out.println(e);
                    }
                }, id.getId());

            }
        }.init(iduser));

        boton.bringToFront();

    }

    private void initViews(){
        mRecyclerView = (RecyclerView)findViewById(R.id.card_view_search_products);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    public void redirect(View v){
        Intent intent = new Intent(this, ShoppingListActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("id",idU);
        b.putSerializable("user",iduser);
        Intent start = intent.putExtra("bundle", b);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (mAdapter != null) mAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }


}
