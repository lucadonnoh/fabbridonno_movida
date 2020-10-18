package movida.fabbridonno;
import java.util.ArrayList;

import movida.commons.*;

public final class Record {
    /**
     * Elemento da conservare nel record
     */
    private ArrayList<Movie> movie = new ArrayList<Movie>();
    /**
     * Chiave associata all'elemento da conservare nel record
     */
    private Comparable  key;

    /**
     * Puntatore al prossimo record nella struttura collegata
     */
    public Record     next;

    /**
     * Puntatore al record precedente nella struttura collegata
     */
    public Record     prev;
    /**
     * Costruttore per l'allocazione di un nuovo record
     *
     * @param e l'elemento da conservare nel record
     * @param k lakey associata all'elemento da conservare nel record
     */

    public Record(Movie m, Comparable k) {
        movie.add(m);
        key = k;
        next = prev = null;
    }

    public void addMovie(Movie m)
    {
        movie.add(m);
    }

    public int getCarico()
    {
        return movie.size(); // O(1)
    }

    public Movie getMovie()
    {
        return movie.get(0);
    }

    public Comparable getKey()
    {
        return key;
    }

    public void print(){
        System.out.print("Chiave: " + key + "\nRecord: ");
        for(Movie m: movie){
            System.out.print(m.getTitle());
        }
        System.out.print("\n\n");
    }
}