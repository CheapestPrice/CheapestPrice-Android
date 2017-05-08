package edu.eci.cosw.cheapestPrice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MenuTenderoActivity extends AppCompatActivity {

    //Tendero:
    boolean isTendero;
    EditText shopName;
    EditText shopAddress;
    EditText shopPhone;
    EditText shopNit;
    Bundle bundle;
    private int id=3;
    private int shop=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_tendero);
        Intent intent=getIntent();
        bundle = intent.getBundleExtra("bundle");
        setId(((int) bundle.getSerializable("id")));
        setShop((int)bundle.getSerializable("shopId"));
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
