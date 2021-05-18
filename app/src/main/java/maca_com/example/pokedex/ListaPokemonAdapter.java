package maca_com.example.pokedex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.w3c.dom.Text;

import java.security.Policy;
import java.util.ArrayList;

import maca_com.example.pokedex.models.Pokemon;

public class ListaPokemonAdapter extends RecyclerView.Adapter<ListaPokemonAdapter.ViewHolder> {
    private ArrayList<Pokemon> dataset;
    private Context context;
    private Pokemon pokemon;
    public ListaPokemonAdapter(Context context){
        this.context = context;
        dataset = new ArrayList<>();
    }
    @NonNull
    @Override
    public ListaPokemonAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ListaPokemonAdapter.ViewHolder holder, int position) {
        pokemon = dataset.get(position);
        holder.nombreTextview.setText(pokemon.getNombre());
        Glide.with(context).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/"+pokemon.getNumber()+".png")
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.fotoImageView);

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void adicionarListaPokemon(ArrayList<Pokemon> ListaPokemon){
        dataset.addAll(ListaPokemon);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView fotoImageView;
        private TextView nombreTextview;
        private CardView tarjetas;

        public ViewHolder(View itemView){
            super(itemView);
            fotoImageView= (ImageView) itemView.findViewById(R.id.ivfoto);
            nombreTextview= (TextView) itemView.findViewById(R.id.tvNombre);
            tarjetas= (CardView) itemView.findViewById(R.id.tarjeta);
        }
    }
}
