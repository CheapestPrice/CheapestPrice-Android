package edu.eci.cosw.cheapestPrice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.eci.cosw.cheapestPrice.entities.Tienda;
import edu.eci.cosw.cheapestPrice.network.NetworkException;
import edu.eci.cosw.cheapestPrice.network.RequestCallback;
import edu.eci.cosw.cheapestPrice.network.ShopRetrofitNetwork;

public class MenuTenderoActivity extends AppCompatActivity {

    Bundle bundle;

    Tienda tienda;
    private int id=3;
    private int shop=1;
    private ShopRetrofitNetwork shopNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_tendero);
        Intent intent=getIntent();
        bundle = intent.getBundleExtra("bundle");
        setId(((int) bundle.getSerializable("id")));
        setShop((int)bundle.getSerializable("shopId"));
        shopNetwork = new ShopRetrofitNetwork();
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
                shopNetwork.getShop(new RequestCallback<Tienda>(){
                    @Override
                    public void onSuccess(Tienda response) {
                        System.out.println("response: "+response);
                        tienda=response;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView nombreTienda=(TextView) findViewById(R.id.shopName);
                                nombreTienda.setText(tienda.getNombre());

                                TextView nitTienda=(TextView) findViewById(R.id.shopNit);
                                nitTienda.setText(tienda.getNit());

                                TextView dirTienda=(TextView) findViewById(R.id.shopAddress);
                                dirTienda.setText(tienda.getDireccion());

                                TextView telTienda=(TextView) findViewById(R.id.shopPhone);
                                telTienda.setText(tienda.getTelefono());
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

    public void productos(View view){
        Intent intent= new Intent(this,ProductActivity.class);
        intent.putExtra("bundle",bundle);
        startActivity(intent);
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
