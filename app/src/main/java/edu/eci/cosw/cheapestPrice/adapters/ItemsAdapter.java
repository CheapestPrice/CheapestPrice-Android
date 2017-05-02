package edu.eci.cosw.cheapestPrice.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Blob;
import java.util.List;

import edu.eci.cosw.cheapestPrice.R;
import edu.eci.cosw.cheapestPrice.entities.Item;

/**
 * Created by amoto on 4/10/17.
 */

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {
    List<Item> items;
    Context context;
    public ItemsAdapter(List<Item> response,Context main) {
        items=response;
        context=main;
        System.out.println("items: "+items);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private TextView price;
        private ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            setName((TextView) itemView.findViewById(R.id.name));
            setPrice((TextView) itemView.findViewById(R.id.price));
            setImage((ImageView) itemView.findViewById(R.id.logo));
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
    }
    @Override
    public ItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemsAdapter.ViewHolder holder, int position) {
        Item item=items.get(position);
        //Image:
        Picasso.with(context).load("https://cheapestprice.herokuapp.com/items/"+item.getProducto().getId()+"/imagen").error(R.drawable.placeholder).placeholder(R.drawable.placeholder).fit().into(holder.getImage());
        //Name:
        holder.getName().setText(item.getProducto().getNombre());
        //Price:
        holder.getPrice().setText(Long.toString(item.getPrecio()));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}