package edu.eci.cosw.cheapestPrice.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.eci.cosw.cheapestPrice.R;
import edu.eci.cosw.cheapestPrice.entities.Opinion;
import edu.eci.cosw.cheapestPrice.network.ShopRetrofitNetwork;

/**
 * Created by nautiluz92 on 5/8/17.
 */

public class OpinionAdapter extends RecyclerView.Adapter<OpinionAdapter.ViewHolder>{

    private List<Opinion> items;
    Context context;
    int iduser;
    int idshop;
    ShopRetrofitNetwork network;
    ExecutorService executorService;

    @Override
    public OpinionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.opinionview, parent, false);
        return new ViewHolder(v);
    }

    public OpinionAdapter(List<Opinion> response, Context main, int id, int shop) {
        items=response;
        context=main;
        iduser=id;
        idshop=shop;
        network=new ShopRetrofitNetwork();
        executorService= Executors.newFixedThreadPool(1);
        System.out.println("items: "+items);
    }

    @Override
    public void onBindViewHolder(OpinionAdapter.ViewHolder holder, int position) {
        Opinion item = items.get(position);
        holder.getFecha().setText(item.getFecha().toString());
        holder.getComentario().setText(item.getComentario());
        holder.getGusta().setText((item.getGusta()? "Me gusta" : "No me gusta" ));
    }

    @Override
    public int getItemCount() {
        if (items==null){
            return 0;
        }
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView getUsuario() {
            return usuario;
        }

        public void setUsuario(TextView usuario) {
            this.usuario = usuario;
        }

        public TextView getComentario() {
            return comentario;
        }

        public void setComentario(TextView comentario) {
            this.comentario = comentario;
        }

        public TextView getFecha() {
            return fecha;
        }

        public void setFecha(TextView fecha) {
            this.fecha = fecha;
        }

        public TextView getGusta() {
            return gusta;
        }

        public void setGusta(TextView gusta) {
            this.gusta = gusta;
        }

        private TextView usuario;
        private TextView comentario;
        private TextView fecha;
        private TextView gusta;

        public ViewHolder(View opView) {
            super(opView);
            setFecha((TextView) opView.findViewById(R.id.fecha));
            setComentario((TextView) opView.findViewById(R.id.comentario));
            setGusta((TextView) opView.findViewById(R.id.gusta));
        }


    }
}
