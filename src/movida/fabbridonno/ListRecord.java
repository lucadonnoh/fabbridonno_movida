package movida.fabbridonno;

public class ListRecord<T,K extends Comparable<K>> extends Record<T,K> {

    /**
     * Puntatore al prossimo record nella struttura collegata
     */
    public ListRecord<T, K>     next;

    public ListRecord(T m, K k) {
        super(m, k);
        next = null;
    }

}
