package edu.eci.cosw.cheapestPrice;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.eci.cosw.cheapestPrice.adapters.SearchAdapter;
import edu.eci.cosw.cheapestPrice.entities.Item;
import edu.eci.cosw.cheapestPrice.entities.Usuario;
import edu.eci.cosw.cheapestPrice.network.ItemRetrofitNetwork;
import edu.eci.cosw.cheapestPrice.network.NetworkException;
import edu.eci.cosw.cheapestPrice.network.RequestCallback;

public class SearchActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private RecyclerView mRecyclerView;
    private List<Item> mArrayList;
    private SearchAdapter mAdapter;
    private ItemRetrofitNetwork network;
    private Usuario iduser;
    private int idU;
    public Context context;
    public FloatingActionButton boton;
    public FloatingActionButton price;
    public FloatingActionButton location;
    ProgressDialog cargando;

    //Para sacar ubicación
    public Item[] org;
    public Item[] array;
    public Item[] temp;
    public int length;
    GoogleApiClient googleApiClient;
    Location lastLocation;
    LocationRequest locationRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        context = this;
        boton = (FloatingActionButton) findViewById(R.id.redirect);
        price = (FloatingActionButton) findViewById(R.id.priceOrder);
        location = (FloatingActionButton) findViewById(R.id.locationOrder);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initViews();
        Intent intent = getIntent();
        Bundle b = intent.getBundleExtra("bundle");
        iduser = ((Usuario) b.getSerializable("user"));
        idU = ((int) b.getSerializable("id"));
        System.out.println("user " + iduser.toString());
        network = new ItemRetrofitNetwork();
        cargando=new ProgressDialog(this);
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        googleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        googleApiClient.connect();
        if (!(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        }

        cargar();
        executorService.execute(new Runnable() {
            Usuario id;

            public Runnable init(Usuario iduser) {
                id = iduser;
                System.out.println("1212121212121212121212121211212212121212121212121212121 " + id.getCorreo());
                return this;
            }

            @Override
            public void run() {
                network.getItemsByUser(new RequestCallback<List<Item>>() {
                    @Override
                    public void onSuccess(List<Item> response) {
                        System.out.println("response: " + response);
                        mArrayList = response;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                cargando.hide();
                                mAdapter = new SearchAdapter(mArrayList, context, id);
                                mRecyclerView.setAdapter(mAdapter);
                            }
                        });
                    }

                    @Override
                    public void onFailed(NetworkException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                cargando.hide();
                            }
                        });
                        System.out.println(e);
                    }
                }, id.getId());

            }
        }.init(iduser));

        boton.bringToFront();
        price.bringToFront();
        location.bringToFront();

    }
    public void cargar(){
        cargando.setMessage("Cargando...");
        cargando.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        cargando.setIndeterminate(true);
        cargando.setCanceledOnTouchOutside(false);
        cargando.show();
    }
    private void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.card_view_search_products);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    public void redirect(View v) {
        Intent intent = new Intent(this, ShoppingListActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("id", idU);
        b.putSerializable("user", iduser);
        Intent start = intent.putExtra("bundle", b);
        startActivity(start);
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

    public void orderPrice() {
        Collections.sort(mArrayList);
        mAdapter = new SearchAdapter(mArrayList, context, iduser);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void orderPos() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        System.out.println("antes: "+lastLocation);
        if(lastLocation!=null) {
            org = new Item[mArrayList.size()];
            mArrayList.toArray(org);
            System.out.println(lastLocation);
            System.out.println(org);
            sort(org);
            mArrayList = Arrays.asList(org);
            mAdapter = new SearchAdapter(mArrayList, context, iduser);
            mRecyclerView.setAdapter(mAdapter);
        }else{
            alertDialog("Por favor encienda la ubicación del dispositivo");
        }
    }

    public void orderPrice(View view) {
        orderPrice();
    }

    public void orderPos(View view){
        orderPos();
    }

    private void sort(Item[] item) {
        System.out.println("sort");
        array = item;
        length = item.length;
        temp = new Item[length];
        doMege(0,length-1);
    }

    private void doMege(int low, int hi) {
        if (low < hi) {
            int middle = low + (hi - low) / 2;
            // Below step sorts the left side of the array
            doMege(low, middle);
            // Below step sorts the right side of the array
            doMege(middle + 1, hi);
            // Now merge both sides
            mergeParts(low, middle, hi);
        }
    }

    private void mergeParts(int lowerIndex, int middle, int higherIndex) {

        for (int i = lowerIndex; i <= higherIndex; i++) {
            temp[i] = array[i];
        }
        int i = lowerIndex;
        int j = middle + 1;
        int k = lowerIndex;
        while (i <= middle && j <= higherIndex) {
            if (Math.sqrt(Math.pow(lastLocation.getLatitude()-temp[i].getTienda().getY(),2)+Math.pow(lastLocation.getLongitude()-temp[i].getTienda().getX(),2))
                    <= Math.sqrt(Math.pow(lastLocation.getLatitude()-temp[j].getTienda().getY(),2)+Math.pow(lastLocation.getLongitude()-temp[j].getTienda().getX(),2))) {
                array[k] = temp[i];
                i++;
            } else {
                array[k] = temp[j];
                j++;
            }
            k++;
        }
        while (i <= middle) {
            array[k] = temp[i];
            k++;
            i++;
        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest,
                new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                        stopLocationUpdates();
                    }
                });
    }
    public void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            }
        });
    }


    @Override
    public void onConnectionSuspended(int i) {
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient,(LocationListener)null);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        startLocationUpdates();
    }

    //@SuppressWarnings( "MissingPermission" )
    private void startLocationUpdates() {
        String[] permissions = {android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION};
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
                return;
            }
        }
        LocationServices.FusedLocationApi.requestLocationUpdates( googleApiClient, locationRequest,
                new LocationListener()
                {
                    @Override
                    public void onLocationChanged( Location location )
                    {
                        lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                        stopLocationUpdates();
                    }
                } );
    }

    public void alertDialog(String e) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(e)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
