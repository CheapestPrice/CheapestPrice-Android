package edu.eci.cosw.cheapestPrice.adapters;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import edu.eci.cosw.cheapestPrice.R;
import edu.eci.cosw.cheapestPrice.ShoppingListProductActivity;
import edu.eci.cosw.cheapestPrice.entities.Item;

/**
 * Created by Daniela on 4/10/17.
 */

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {
    List<Item> items;
    Context context;

    View.OnClickListener clickListener;

    public ItemsAdapter(List<Item> response,Context main, View.OnClickListener click) {
        items=response;
        context=main;
        clickListener=click;
        System.out.println("items: "+items);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private TextView price;
        private ImageView image;
        private TextView marca;
        private TextView categoria;
        private Button updateProduct;
        private Button deleteProduct;

        public ViewHolder(View itemView) {
            super(itemView);
            setName((TextView) itemView.findViewById(R.id.name));
            setPrice((TextView) itemView.findViewById(R.id.price));
            setImage((ImageView) itemView.findViewById(R.id.logo));
            setMarca((TextView) itemView.findViewById(R.id.marca));
            setCategoria((TextView) itemView.findViewById(R.id.categoria));
            setDeleteProduct((Button) itemView.findViewById(R.id.deleteProduct));
            setDeleteProduct((Button) itemView.findViewById(R.id.updateProduct));
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

        public TextView getCategoria() {
            return categoria;
        }

        public void setCategoria(TextView categoria) {
            this.categoria = categoria;
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
        v.findViewById(R.id.updateProduct).setOnClickListener(clickListener);
        v.findViewById(R.id.deleteProduct).setOnClickListener(clickListener);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemsAdapter.ViewHolder holder, final int position) {
        final Item item=items.get(position);
        //Image:
        Picasso.with(context).load("https://cheapestprice.herokuapp.com/items/"+item.getProducto().getId()+"/imagen").error(R.drawable.placeholder).placeholder(R.drawable.placeholder).fit().into(holder.getImage());
        //Name:
        holder.getName().setText(item.getProducto().getNombre());
        //Price:
        holder.getPrice().setText("$"+Long.toString(item.getPrecio()));
        //Marca
        holder.getMarca().setText(item.getProducto().getMarca());
        //categoria
        holder.getCategoria().setText(item.getProducto().getCategoria());
        ///update
        holder.getUpdateProduct().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),ShoppingListProductActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("item",item);
                Intent start=intent.putExtra("bundle",b);
                context.startActivity(start);
            }
        });
        ///eliminar
        holder.getDeleteProduct().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),ShoppingListProductActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("item",item);
                Intent start=intent.putExtra("bundle",b);
                context.startActivity(start);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public Item getItem(int i){
        return items.get(i);
    }
}