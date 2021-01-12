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
     * Costruttore per l'allocazione di un nuovo record
     *
     * @param e l'elemento da conservare nel record
     * @param k lakey associata all'elemento da conservare nel record
     */
    public Record(T m, K k) {
        Els.add(m);
        key = k;
        next = null;
    }

    /**
     * Imposta la chiave del record
     * @param k chiave da settare
     */
    public void setKey(K k)
    {
        this.key = k;
    }

    /**
     * Imposta tutti gli elementi di un record
     * @param al elementi da settare
     */
    public void setAllEls(ArrayList<T> al)
    {
        Els = al;
    }

    /**
     * Aggiunge un elemento al Record
     * @param m elemento da aggiungere
     */
    public void addEl(T m)
    {
        Els.add(m);
    }

    /**
     * Ritorna la chiave del Record
     * @return la chiave del Record
     */
    @SuppressWarnings("unchecked")
    public K getKey()
    {
        if(this.key instanceof String) {
            return (K)((String) key).toLowerCase();
        }
        return key;
    }

    /**
     * Ritorna quanti elementi contiene il record
     * @return numero di elementi
     */
    public int getCarico()
    {
        return Els.size(); // O(1)
    }

    /**
     * Ritorna il primo elemento del record
     * @return primo elemento
     */
    public T getEl()
    {
        return Els.get(0);
    }

    /**
     * Ritorna tutti gli elementi del record
     * @return un ArrayList degli elementi del record
     */
    public ArrayList<T> getAllEls()
    {
        ArrayList<T> al = new ArrayList<T>();
        for(T m : Els)
        {
            al.add(m);
        }
        return al;
    }

    

    

    /**
     * Stampa il Record
     */
    public void print(){
        Boolean tanti= false;
        System.out.print("Chiave: " + key + "\nRecord: ");
        for(T m: Els){
            if(tanti){
                System.out.print("- ");
            }
            System.out.print(m.toString() + " ");
            tanti= true;
        }
        System.out.print("\n\n");
    }

    /**
     * Controlla se un generic è un Movie
     * @param <T> tipo del generic
     * @param t il generic
     * @return l'elemento convertito in Movie, null se non possibile
     */
    public static <T> Movie toMovie(T t)
    {
        if(!(t instanceof Movie))
        {
            System.out.println("WARNING: l'elemento non è di tipo Movie, ritorno NULL");
            return null;
        }
        return (Movie)t;
    }

    /**
     * Converte una lista di generic in Movie se possibile
     * @param <T> Tipo della lista
     * @param al lista di elementi
     * @return array di Movie o null se non possibile
     */
    public static <T> Movie[] toMovieArray(ArrayList<T> al)
    {
        Movie[] ms = new Movie[al.size()];
        int i = 0;
        if(toMovie(al.get(0)) == null) return null;
        for(T el : al)
        {
            ms[i++] = toMovie(el);
        }
        return ms;
    }

    /**
     * Converte un generic in una Persona se possibile
     * @param <T> Tipo del generic
     * @param t il generic
     * @return l'elemento convertito in Persona se possibile, null altrimenti
     */
    public static <T> Person toPerson(T t)
    {
        if(!(t instanceof Person))
        {
            System.out.println("WARNING: l'elemento non è di tipo Person, ritorno NULL");
            return null;
        }
        return (Person)t;
    }
}

