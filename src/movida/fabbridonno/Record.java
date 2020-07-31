package movida.fabbridonno;
import movida.commons.*;

public final class Record {
    /**
     * Elemento da conservare nel record
     */
    public Movie      movie;
    /**
     * Chiave associata all'elemento da conservare nel record
     */
    public Comparable  key;
    
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
        movie = m;
            key = k;
        next = prev = null;
    }
}