package edu.eci.cosw.cheapestPrice.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.eci.cosw.cheapestPrice.PopUpTiendaInfo;
import edu.eci.cosw.cheapestPrice.R;
import edu.eci.cosw.cheapestPrice.ShoppingListActivity;
import edu.eci.cosw.cheapestPrice.ShoppingListProductActivity;
import edu.eci.cosw.cheapestPrice.entities.ItemLista;
import edu.eci.cosw.cheapestPrice.entities.ListaDeMercado;
import edu.eci.cosw.cheapestPrice.network.ListaMercadoRetrofitNetwork;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 2105403 on 5/2/17.
 */

public class ItemsListaAdapter extends RecyclerView.Adapter<ItemsListaAdapter.ViewHolder> {

    private ListaDeMercado productos;
    private Context context;
    private ListaMercadoRetrofitNetwork network;
    private ExecutorService executorService;
    ProgressDialog cargando;

    public void cargar() {
        cargando.setMessage("Cargando...");
        cargando.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        cargando.setIndeterminate(true);
        cargando.setCanceledOnTouchOutside(false);
        cargando.show();
    }

    public ItemsListaAdapter(ListaDeMercado productos,Context mainActivity){
        this.productos=productos;
        context=mainActivity;
        network=new ListaMercadoRetrofitNetwork();
        executorService= Executors.newFixedThreadPool(1);
        cargando= new ProgressDialog(context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView nombreProducto;
        private TextView precio;
        private CheckBox favorito;
        private CheckBox comprado;
        private Button eliminarProducto;
        private Button masInformacion;
        //Información de la tienda



        public ViewHolder(View v) {
            super(v);
            setNombreProducto((TextView) v.findViewById(R.id.nombreProducto));
            setPrecio((TextView) v.findViewById(R.id.precio));
            setFavorito((CheckBox) v.findViewById(R.id.favorito));
            setComprado((CheckBox) v.findViewById(R.id.comprado));
            setEliminarProducto((Button) v.findViewById(R.id.eliminarProducto));
            setMasInformacion((Button) v.findViewById(R.id.infoTienda));
        }

        public TextView getNombreProducto() {
            return nombreProducto;
        }

        public void setNombreProducto(TextView nombreProducto) {
            this.nombreProducto = nombreProducto;
        }

        public TextView getPrecio() {
            return precio;
        }

        public void setPrecio(TextView precio) {
            this.precio = precio;
        }

        public CheckBox getFavorito() {
            return favorito;
        }

        public void setFavorito(CheckBox favorito) {
            this.favorito = favorito;
        }

        public CheckBox getComprado() {
            return comprado;
        }

        public void setComprado(CheckBox comprado) {
            this.comprado = comprado;
        }

        public Button getMasInformacion() {return masInformacion;}

        public void setMasInformacion(Button masInformacion) {this.masInformacion = masInformacion;}

        public Button getEliminarProducto() {return eliminarProducto;}

        public void setEliminarProducto(Button eliminarProducto) {this.eliminarProducto = eliminarProducto;}
    }

    @Override
    public ItemsListaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_list_product_view, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemsListaAdapter.ViewHolder holder, final int position) {
        //Setear valores de favorito y comprado
        holder.getFavorito().setChecked(productos.getItems().get(position).getFavorito());
        holder.getComprado().setChecked(productos.getItems().get(position).getComprado());

        ItemLista item=productos.getItems().get(position);
        //Nombre del producto
        holder.getNombreProducto().setText(item.getItem().getProducto().getNombre());
        //Precio del producto
        holder.getPrecio().setText("$"+item.getItem().getPrecio());
        //Item favorito
        holder.getFavorito().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargar();
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        Callback<Void> callb=new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                System.out.println(response);
                                System.out.println(call);
                                ((ShoppingListProductActivity) context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        cargando.hide();
                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                System.out.println(t.getLocalizedMessage());
                                ((ShoppingListProductActivity) context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        cargando.hide();
                                    }
                                });
                            }
                        };
                        network.itemSeleccionadoFavorito(callb, productos.getIdUsuario(),
                                productos.getId(), productos.getItems().get(position).getId(),
                                !productos.getItems().get(position).getFavorito());
                    }

                });
            }
        });
        //Item comprado
        holder.getComprado().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargar();
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        Callback<Void> callb=new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                System.out.println(response);
                                System.out.println(call);
                                ((ShoppingListProductActivity) context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        cargando.hide();
                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                System.out.println(t.getLocalizedMessage());
                                ((ShoppingListProductActivity) context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        cargando.hide();
                                    }
                                });
                            }
                        };
                        network.itemSeleccionadoComprado(callb,productos.getIdUsuario(),
                                productos.getId(),productos.getItems().get(position).getId(),
                                !productos.getItems().get(position).getComprado());
                    }
                });
            }
        });
        //Mas informacion sobre la tienda
        holder.getMasInformacion().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), PopUpTiendaInfo.class);
                Bundle b=new Bundle();
                b.putSerializable("postTienda",productos.getItems().get(position).getItem().getTienda());
                Intent start=intent.putExtra("bundleTienda",b);
                context.startActivity(start);
            }
        });
        //Eliminar producto de la lista de mercado
        holder.getEliminarProducto().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargar();
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        Callback<Void> callb=new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                System.out.println(response);
                                System.out.println(call);
                                ((ShoppingListProductActivity) context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        cargando.hide();
                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                System.out.println(t.getLocalizedMessage());
                                ((ShoppingListProductActivity) context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        cargando.hide();
                                    }
                                });
                            }
                        };
                        network.eliminarItemListaMercado(callb,productos.getIdUsuario(),productos.getId(),productos.getItems().get(position).getId());
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return productos.getItems().size();
    }
}
