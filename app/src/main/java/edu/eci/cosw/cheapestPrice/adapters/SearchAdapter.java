package edu.eci.cosw.cheapestPrice.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.eci.cosw.cheapestPrice.R;
import edu.eci.cosw.cheapestPrice.entities.Item;
import edu.eci.cosw.cheapestPrice.network.ItemRetrofitNetwork;

/**
 * Created by daniela on 8/05/17.
 */

public class SearchAdapter  extends RecyclerView.Adapter<SearchAdapter.ViewHolder> implements Filterable {

    private List<Item> items;
    private List<Item> itemsFiltred;
    private Context context;
    private int iduser;

    public SearchAdapter(List<Item> listItem,Context context,int id){
        items=listItem;
        itemsFiltred=listItem;
        this.context=context;
        iduser=id;
    }

    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_search_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchAdapter.ViewHolder holder, int position) {
        Item item=itemsFiltred.get(position);
        holder.getName().setText(item.getProducto().getNombre());
        holder.getPrice().setText(Long.toString(item.getPrecio()));
        holder.getCategoria().setText(item.getProducto().getCategoria());
        holder.getMarca().setText(item.getProducto().getMarca());
        holder.getMarca().setText(item.getProducto().getMarca());
        Picasso.with(context).load("https://cheapestprice.herokuapp.com/api/items/"+iduser+"/shop/"+item.getTienda().getId()+"/item/"+item.getId()+"/imagen").error(R.drawable.placeholder).placeholder(R.drawable.placeholder).fit().into(holder.getImage());
        holder.getAddList().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent=new Intent(v.getContext(),.class);
                Bundle b = new Bundle();
                b.putSerializable("item",item);
                b.putSerializable("id",iduser);
                b.putSerializable("shop",idshop);
                Intent start=intent.putExtra("bundle",b);
                context.startActivity(start);*/
            }
        });

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private TextView price;
        private ImageView image;
        private TextView marca;
        private TextView categoria;
        private Button addList;

        public ViewHolder(View itemView) {
            super(itemView);
            setName((TextView) itemView.findViewById(R.id.name));
            setPrice((TextView) itemView.findViewById(R.id.price));
            setImage((ImageView) itemView.findViewById(R.id.logo));
            setMarca((TextView) itemView.findViewById(R.id.marca));
            setCategoria((TextView) itemView.findViewById(R.id.categoria));
            setAddList((Button) itemView.findViewById(R.id.addList));
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

        public Button getAddList(){ return addList; }

        public void setAddList(Button add) { this.addList = add; }
    }
}
