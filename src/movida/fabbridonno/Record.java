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

    public static <T> Movie toMovie(T t)
    {
        if(!(t instanceof Movie)) 
        {
            System.out.println("WARNING: l'elemento non è di tipo Movie, ritorno NULL");
            return null;
        }
        return (Movie)t;
    }

    public static <T> Movie[] toMovieArray(ArrayList<T> al)
    {
        Movie[] ms = new Movie[al.size()];
        int i = 0;
        for(T el : al)
        {
            ms[i++] = toMovie(el);
        }
        return ms;
    }

    public static <T> Person toPerson(T t)
    {
        if(!(t instanceof Person)) 
        {
            System.out.println("WARNING: l'elemento non è di tipo Person, ritorno NULL");
            return null;
        }
        return (Person)t;
    }

    public void addEl(T m)
    {
        Els.add(m);
    }

    public int getCarico()
    {
        return Els.size(); // O(1)
    }

    public T getEl()
    {
        return Els.get(0);
    }

    public ArrayList<T> getAllEls()
    {
        ArrayList<T> al = new ArrayList<T>();
        for(T m : Els)
        {
            al.add(m);
        }
        return al;
    }

    public K getKey()
    {
        return key;
    }

    public void print(){
        System.out.print("Chiave: " + key + "\nRecord: ");
        for(T m: Els){
            System.out.print(m.toString());
        }
        System.out.print("\n\n");
    }
}