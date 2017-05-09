package edu.eci.cosw.cheapestPrice.adapters;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Blob;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.eci.cosw.cheapestPrice.DetalleProductActivity;
import edu.eci.cosw.cheapestPrice.ProductActivity;
import edu.eci.cosw.cheapestPrice.R;
import edu.eci.cosw.cheapestPrice.ShoppingListProductActivity;
import edu.eci.cosw.cheapestPrice.entities.Item;
import edu.eci.cosw.cheapestPrice.network.ItemRetrofitNetwork;
import edu.eci.cosw.cheapestPrice.network.NetworkException;
import edu.eci.cosw.cheapestPrice.network.RequestCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.id;

/**
 * Created by Daniela on 4/10/17.
 */

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {
    List<Item> items;
    Context context;
    int iduser;
    int idshop;
    ItemRetrofitNetwork network;
    ExecutorService executorService;

    public ItemsAdapter(List<Item> response,Context main, int id, int shop) {
        items=response;
        context=main;
        iduser=id;
        idshop=shop;
        network=new ItemRetrofitNetwork();
        executorService= Executors.newFixedThreadPool(1);
        System.out.println("items: "+items);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private TextView price;
        private ImageView image;
        private TextView marca;
        private TextView tienda;
        private Button updateProduct;
        private Button deleteProduct;

        public ViewHolder(View itemView) {
            super(itemView);
            setName((TextView) itemView.findViewById(R.id.name));
            setPrice((TextView) itemView.findViewById(R.id.price));
            setImage((ImageView) itemView.findViewById(R.id.logo));
            setMarca((TextView) itemView.findViewById(R.id.marca));
            setTienda((TextView) itemView.findViewById(R.id.tienda));
            setDeleteProduct((Button) itemView.findViewById(R.id.deleteProduct));
            setUpdateProduct((Button) itemView.findViewById(R.id.updateProduct));
        }

        public TextView getName() {
            return name;
        }

        public void setName(TextView name) {
            this.name = name;
        }

        public TextView getPrice() {
            return price;
        }

        public void setPrice(TextView pric) {
            this.price = pric;
        }

        public ImageView getImage() {
            return image;
        }

        public void setImage(ImageView image) {
            this.image = image;
        }

        public TextView getMarca() {
            return marca;
        }

        public void setMarca(TextView marca) {
            this.marca = marca;
        }

        public TextView getTienda() {
            return tienda;
        }

        public void setTienda(TextView tienda) {
            this.tienda = tienda;
        }

        public Button getUpdateProduct() { return updateProduct; }

        public void setUpdateProduct(Button updateProduct) { this.updateProduct = updateProduct; }

        public Button getDeleteProduct() { return deleteProduct; }

        public void setDeleteProduct(Button deleteProduct) { this.deleteProduct = deleteProduct; }
    }
    @Override
    public ItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemsAdapter.ViewHolder holder, final int position) {
        final Item item=items.get(position);
        //Image:
        Picasso.with(context).load("https://cheapestprice.herokuapp.com/api/items/"+iduser+"/shop/"+idshop+"/item/"+item.getId()+"/imagen").error(R.drawable.placeholder).placeholder(R.drawable.placeholder).fit().into(holder.getImage());
        //Name:
        holder.getName().setText(item.getProducto().getNombre());
        //Price:
        holder.getPrice().setText("$"+Long.toString(item.getPrecio()));
        //Marca
        holder.getMarca().setText(item.getProducto().getMarca());
        //categoria
        holder.getTienda().setText(item.getTienda().getNombre());
        ///update
        holder.getUpdateProduct().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),DetalleProductActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("item",item);
                b.putSerializable("id",iduser);
                b.putSerializable("shop",idshop);
                Intent start=intent.putExtra("bundle",b);
                context.startActivity(start);
            }
        });
        ///eliminar
        holder.getDeleteProduct().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        network.deleteItem(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call1, Response<Void> reponse) {
                                System.out.println("Success : "+call1+" r:"+reponse);
                                ((ProductActivity) context).runOnUiThread(
                                        new Runnable() {
                                            @Override
                                            public void run() {
                                                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                                builder.setMessage("Actualizacion completa..")
                                                        .setCancelable(false)
                                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int id) {
                                                                Intent intent=new Intent(v.getContext(),ProductActivity.class);
                                                                Bundle b = new Bundle();
                                                                b.putSerializable("id",iduser);
                                                                b.putSerializable("shopId",idshop);
                                                                Intent start=intent.putExtra("bundle",b);
                                                                context.startActivity(start);
                                                            }
                                                        });
                                                AlertDialog alert = builder.create();
                                                alert.show();
                                            }
                                        }
                                );
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                System.out.println("Fail: "+t);
                            }

                        },iduser,idshop,item.getId());
                    }

                });
            }
        });

    }

    @Override
    public int getItemCount() {
        if (items==null){
            return 0;
        }
        return items.size();
    }

    public Item getItem(int i){
        return items.get(i);
    }
}