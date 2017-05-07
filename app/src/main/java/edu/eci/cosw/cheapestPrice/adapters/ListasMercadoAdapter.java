package edu.eci.cosw.cheapestPrice.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.eci.cosw.cheapestPrice.R;
import edu.eci.cosw.cheapestPrice.ShoppingListActivity;
import edu.eci.cosw.cheapestPrice.ShoppingListProductActivity;
import edu.eci.cosw.cheapestPrice.entities.ListaDeMercado;
import edu.eci.cosw.cheapestPrice.entities.Usuario;
import edu.eci.cosw.cheapestPrice.network.ListaMercadoRetrofitNetwork;
import edu.eci.cosw.cheapestPrice.network.NetworkException;
import edu.eci.cosw.cheapestPrice.network.RequestCallback;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created by 2105403 on 5/2/17.
 */

public class ListasMercadoAdapter extends RecyclerView.Adapter<ListasMercadoAdapter.ViewHolder> {

    private Usuario usuario;
    private Context context;
    public View.OnClickListener clickListener;
    private ListaMercadoRetrofitNetwork network;

    public ListasMercadoAdapter(Usuario usuario,Context mainActivity,View.OnClickListener clickListener) {
        this.usuario = usuario;
        context = mainActivity;
        this.clickListener = clickListener;
        network = new ListaMercadoRetrofitNetwork();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView nombreLista;
        private TextView fechaCreacion;
        private Button ver;
        private Button eliminarLista;
        public ViewHolder(View v) {
            super(v);
            setNombreLista((TextView) v.findViewById(R.id.nombreLista));
            setFechaCreacion((TextView) v.findViewById(R.id.fecha));
            setVer((Button) v.findViewById(R.id.ver));
            setEliminarLista((Button) v.findViewById(R.id.eliminar));
        }

        public TextView getNombreLista() {
            return nombreLista;
        }

        public void setNombreLista(TextView nombreLista) {
            this.nombreLista = nombreLista;
        }

        public TextView getFechaCreacion() {
            return fechaCreacion;
        }

        public void setFechaCreacion(TextView fechaCreacion) {
            this.fechaCreacion = fechaCreacion;
        }

        public Button getVer() {return ver;}

        public void setVer(Button ver) {this.ver = ver;}

        public Button getEliminarLista() {return eliminarLista;}

        public void setEliminarLista(Button eliminarLista) {this.eliminarLista = eliminarLista;}
    }

    @Override
    public ListasMercadoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shoppinglistview, parent, false);
        v.findViewById(R.id.eliminar).setOnClickListener(clickListener);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ListasMercadoAdapter.ViewHolder holder, final int position) {
        ListaDeMercado lista=usuario.getListas().get(position);
        //Nombre lista
        holder.getNombreLista().setText(lista.getNombre());
        //Fecha de creacion
        holder.getFechaCreacion().setText(lista.getFechaCreacion().toString());
        //Ver
        holder.getVer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),ShoppingListProductActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("post",usuario.getListas().get(position));
                Intent start=intent.putExtra("bundle",b);
                context.startActivity(start);
            }
        });
        //Eliminar lista de mercado
        holder.getEliminarLista().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExecutorService executorService = Executors.newFixedThreadPool(1);
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        network.eliminarListaMercado(new RequestCallback<ResponseBody>() {
                            @Override
                            public void onSuccess(ResponseBody response) {
                                ((ShoppingListActivity) context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ((ShoppingListActivity) context).getRecyclerView().getAdapter().notifyDataSetChanged();
                                    }
                                });
                            }
                            @Override
                            public void onFailed(NetworkException e) {
                                System.out.println(e);
                            }
                        },usuario.getId(), usuario.getListas().get(position).getId());
                    }
                });
            }});
    }

            @Override
            public int getItemCount() {
                return usuario.getListas().size();
            }
        }

