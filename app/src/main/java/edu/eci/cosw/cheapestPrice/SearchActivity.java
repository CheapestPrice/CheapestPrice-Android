package edu.eci.cosw.cheapestPrice;

import android.content.Context;
import android.content.Intent;
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

    private GestureDetector gesture;

    private RecyclerView mRecyclerView;
    private List<Item> mArrayList;
    private SearchAdapter mAdapter;
    private ItemRetrofitNetwork network;
    private Usuario iduser;
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        context=this;
        gesture = new GestureDetector(new SwipeGestureDetector());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        initViews();
        Intent intent=getIntent();
        Bundle b = intent.getBundleExtra("bundle");
        iduser=((Usuario) b.getSerializable("user"));
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

    }

    private void initViews(){
        mRecyclerView = (RecyclerView)findViewById(R.id.card_view_search_products);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        this.overridePendingTransition(R.anim.anim_slide_left_to_right,R.anim.anim_slide_right_to_left);
        if (gesture.onTouchEvent(event))
        {
            return true;
        }
        return super.onTouchEvent(event);
    }

    private void onLeft()
    {
        Intent myIntent = new Intent(SearchActivity.this, ShoppingListActivity.class);
        startActivity(myIntent);
    }

    /*private void onRight()
    {
        Intent myIntent = new Intent(MainActivity.this, RightActivity.class);
        startActivity(myIntent);
    }*/

    private class SwipeGestureDetector  extends GestureDetector.SimpleOnGestureListener
    {
        private static final int SWIPE_MIN_DISTANCE = 120;
        private static final int SWIPE_MAX_OFF_PATH = 200;
        private static final int SWIPE_THRESHOLD_VELOCITY = 200;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2,float velocityX, float velocityY)
        {
            try
            {
                float diffAbs = Math.abs(e1.getY() - e2.getY());
                float diff = e1.getX() - e2.getX();

                if (diffAbs > SWIPE_MAX_OFF_PATH)
                    return false;

                // Left swipe
                if (diff > SWIPE_MIN_DISTANCE&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY)
                {
                    SearchActivity.this.onLeft();
                }
                // Right swipe

                /*else if (-diff > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY)
                {
                    SearchActivity.this.onRight();
                }*/
            }
            catch (Exception e)
            {
                Log.e("MainActivity", "Error on gestures");
            }
            return false;
        }
    }
}
