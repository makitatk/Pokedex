package maca_com.example.pokedex.models;

public class Pokemon {
    private int number;
    private String nombre;
    private String url;

    public int getNumber() {
        return number;
    }

    public int setNumber(int number) {
        String[] urlPartes=url.split("/");
        return Integer.parseInt(urlPartes[urlPartes.length-1]);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
