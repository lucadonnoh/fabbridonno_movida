package movida.fabbridonno;
import movida.commons.*;

public class ListaFilm {
    private Movie film;
    public ListaFilm next;
    public ListaFilm prev;

    public ListaFilm(Movie m){
        film = m;
        next = prev = null;
    }

    public Movie getMovie()
    {
        return film;
    }
}
