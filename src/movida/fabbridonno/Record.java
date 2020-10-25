package movida.fabbridonno;
import java.util.ArrayList;

import movida.commons.*;

public final class Record<T, K extends Comparable<K>> {
    /**
     * Elemento da conservare nel record
     */
    private ArrayList<T> Els = new ArrayList<T>();
    /**
     * Chiave associata all'elemento da conservare nel record
     */
    private K key;

    /**
     * Puntatore al prossimo record nella struttura collegata
     */
    public Record<T, K>     next;

    /**
     * Puntatore al record precedente nella struttura collegata
     */
    public Record<T, K>     prev;
    /**
     * Costruttore per l'allocazione di un nuovo record
     *
     * @param e l'elemento da conservare nel record
     * @param k lakey associata all'elemento da conservare nel record
     */

    public Record(T m, K k) {
        Els.add(m);
        key = k;
        next = prev = null;
    }

    public void addEl(T m)
    {
        Els.add(m);
    }

    public int getCarico()
    {
        return Els.size(); // O(1)
    }

    public Movie getMovie()
    {
        return (Movie)Els.get(0);
    }

    public Movie[] getAllMovies()
    {
        Movie[] ms = new Movie[getCarico()];
        int i = 0;
        for(T m : Els)
        {
            ms[i++] = (Movie)m;
        }

        return ms;
    }

    public K getKey()
    {
        return key;
    }

    public void print(){
        System.out.print("Chiave: " + key + "\nRecord: ");
        for(T m: Els){
            System.out.print(((Movie)m).getTitle());
        }
        System.out.print("\n\n");
    }
}