package movida.fabbridonno;
import movida.commons.*;

public class porcoddio {

    private ListaFilm   filmPerAttore; //-> film associati all'attore

    private Comparable  key; //-> nome attore

    private int nFilm;

    public Record     next; //-> prossimo attore

    public Record     prev;

    public porcoddio(ListaFilm m, Comparable k) {
        filmPerAttore = m;
        key = k;
        next = prev = null;
        nFilm = 0;
    }

    public ListaFilm getMovies()
    {
        return filmPerAttore;
    }

    public Comparable getKey()
    {
        return key;
    }

    public void insert(Movie m)
    {
        ListaFilm p = new ListaFilm(m);

        if (filmPerAttore == null)
            filmPerAttore = p;
        else {
            p.next = filmPerAttore;
            filmPerAttore = p;
        }

        nFilm++;
    }

    public int getNFilm() {
        return nFilm;
    }
}

