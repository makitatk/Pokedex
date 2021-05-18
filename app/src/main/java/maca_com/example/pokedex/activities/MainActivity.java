package maca_com.example.pokedex.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import maca_com.example.pokedex.ListaPokemonAdapter;
import maca_com.example.pokedex.R;
import maca_com.example.pokedex.models.Pokemon;
import maca_com.example.pokedex.models.PokemonRespuesta;
import maca_com.example.pokedex.pokeApi.PokemonService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private Retrofit retrofit;
    private static final String TAG = "POKEDEX";

    private RecyclerView recyclerView;
    private ListaPokemonAdapter listaPokemonAdapter;

    private int offset;
    private boolean aptoParaCargar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycleviewMain);
        listaPokemonAdapter = new ListaPokemonAdapter(this);
        recyclerView.setAdapter(listaPokemonAdapter);
        recyclerView.setHasFixedSize(true);

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy>0){
                    int visibleItemCount = gridLayoutManager.getChildCount();
                    int totalItemCount = gridLayoutManager.getItemCount();
                    int pastVisibleItems= gridLayoutManager.findFirstVisibleItemPosition();

                    if(aptoParaCargar){
                        if(visibleItemCount+pastVisibleItems>=totalItemCount){
                            Log.i(TAG,"llegamos al final");
                            aptoParaCargar=false;
                            offset+=20;
                            obtenerDatos(offset);
                        }
                    }
                }
            }
        });
        retrofit = new  Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        aptoParaCargar=true;
        offset=0;
        obtenerDatos(offset);
    }

    private void obtenerDatos(int offset){
        PokemonService service= retrofit.create(PokemonService.class);
        Call<PokemonRespuesta>pokemonRespuestaCall=service.obtenerListaPokemon(20, offset);
        pokemonRespuestaCall.enqueue(new Callback<PokemonRespuesta>() {
            @Override
            public void onResponse(Call<PokemonRespuesta> call, Response<PokemonRespuesta> response) {
                aptoParaCargar=true;
                if(response.isSuccessful()){
                    PokemonRespuesta pokemonRespuesta= response.body();
                    ArrayList<Pokemon> listaPokemon=pokemonRespuesta.getResults();
                    listaPokemonAdapter.adicionarListaPokemon(listaPokemon);
                }else
                    Log.e(TAG,"on response "+response.errorBody());
            }

            @Override
            public void onFailure(Call<PokemonRespuesta> call, Throwable t) {
                aptoParaCargar=true;
                Log.e(TAG," on failure "+ t.getMessage());
            }
        });
    }
}