package maca_com.example.pokedex.pokeApi;

import maca_com.example.pokedex.models.PokemonRespuesta;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokemonService {
    @GET("pokemon")
    Call<PokemonRespuesta> obtenerListaPokemon(@Query("limit")int limit, @Query("offset")int offset);
}
