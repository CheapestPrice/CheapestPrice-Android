package edu.eci.cosw.cheapestPrice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by nautiluz92 on 5/8/17.
 */

public class OpinionActivity extends AppCompatActivity {

    Bundle bundle;

    private int id = 3;
    private int shop = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinions);
        Intent intent = getIntent();
        bundle = intent.getBundleExtra("bundle");
        setId(((int) bundle.getSerializable("id")));
        setShop((int) bundle.getSerializable("shopId"));
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
