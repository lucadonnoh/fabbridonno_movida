package movida.fabbridonno;

import java.util.ArrayList;

import movida.commons.*;

public class ListaNonOrdinata<T, K extends Comparable<K>> implements DizionarioFilm<T, K> {

    private Record<T, K> record;
    private int carico;
    private Boolean crescente;
    private Boolean ordinata;

    public ListaNonOrdinata() {
        carico = 0;
        record = null;
        crescente = true;
        ordinata = false;
    }

    //Non serve cancellare p perchè java ha il garbage collector
    public void insert(T e, K k) {


        Record<T, K> p = new Record<T, K>(e, k);

        if (record == null)
            record = p;
        else {
            p.next = record;
            record = p;
        }

        carico++;
    }

    public void deleteEl(Movie m) {
        if (record == null)
            return;
        Record<T, K> p = record;
        Record<T,K> prev = null;
        while (p != null) {
            ArrayList<T> l = p.getAllEls();
            if (l.contains(m)) {
                if(l.size() == 1) {
                    carico--;
                    if(prev == null){
                        p=p.next;
                        record = p;
                        continue;
                    }else{
                        prev.next = p.next;
                    }
                }else{
                    l.remove(m);
                    p.setAllEls(l);
                }
            }
            prev = p;
            p = p.next;
        }
        return;
    }

    //Ritorna il record associato alla chiave se è presente, null altrimenti
    //TODO: fare in modo che si fermi prima perché è ordinata
    @SuppressWarnings("unchecked")
    public Record<T, K> searchRecord(K k) {
        if ((k instanceof String)) {
            k = (K) ((String) k).toLowerCase();
        }
        if (record == null)
            return null;
        Record<T, K> p = record;
        while (p != null) {
            if(this.ordinata && this.crescente) {
                if(p.getKey().compareTo(k) > 0) return null;
            }
            if(this.ordinata && !this.crescente) {
                if(p.getKey().compareTo(k) < 0) return null;
            }
            if (p.getKey().equals(k))
                return p;
            p = p.next;
        }
        return null;
    }

    //Ritorna l'elemento associato alla chiave k
    public T search(K k) {
        Record<T,K> p = searchRecord(k);
        if (p != null)
            return p.getEl();
        return null;
    }

    //Ritorna True se la chiave è presente, False altrimenti
    public Boolean searchKey(K k) {
        if (searchRecord(k) != null)
            return true;
        else
            return false;
    }

    /**
     * Ritorna l'n-esimo Record della lista
     * @param n posizione del Record
     * @return il Record
     */
    private Record<T, K> getNthRecord(int n) {
        Record<T, K> tmp = this.record;
        while (tmp != null && n >= 0) {
            if (n == 0)
                return tmp;
            n--;
            tmp = tmp.next;
        }
        return null;
    }

    public void stampa() {
        Record<T, K> p = record;
        while (p != null) {
            p.print();
            p = p.next;
        }
    }

    public int getCarico() {
        return carico;
    }

    // Java ha il garbage collector che eliminat tutti gli elementi non referenziati.
    public void clear() {
        record = null;
        carico = 0;
    }

    public boolean isEmpty() {
        return carico == 0;
    }

    //Funzione che esporta gli elementi dei record in un array
    public Movie[] export() {
        Movie[] movies = new Movie[carico];
        int i = 0;
        Record<T, K> p = record;
        if(Record.toMovie(p.getEl()) == null) return null;

        while (p != null) {
            movies[i++] = Record.toMovie(p.getEl());
            p = p.next;
        }
        return movies;
    }

    //Funzione che esporta le chiavi dei record in un array
    @SuppressWarnings("unchecked")
    public Comparable<K>[] exportKeys() {
        Comparable<K>[] keys = new Comparable[carico];
        int i = 0;
        Record<T, K> p = record;

        while (p != null) {
            keys[i++] = p.getKey();
            p = p.next;
        }
        return keys;
    }

    //Sfruttiamo il fatto che la lista sia sempre ordinata quindi se ci son chiavi uguali sono adiacenti
    public Movie[] searchMoviesByKey(K k) {
        Record<T, K> p = searchRecord(k);
        if(p==null){
            return new Movie[0];
        }
        Record<T, K> tmp = p;
        int n = 1;
        while (tmp.next.getKey().equals(k)) {
            n++;
            tmp = tmp.next;
        }
        Movie[] movies = nNextMovies(p, n);
        return movies;
    }

    public Movie[] searchMoviesByRecord(K k) {
        int i=0;
        Record<T, K> p = searchRecord(k);
        if(p==null) return new Movie[0];
        ArrayList<T> l = p.getAllEls();
        Movie[] movies = new Movie[l.size()];
        for(T film : l){
            movies[i] = (Movie)film;
            i++;
        }
        return movies;
    }

    /**
     * Ritorna gli n Movie successivi al Record passato come parametro
     * @param p Record da cui partire
     * @param n numero di Movie da prendere
     * @return array di Movie
     */
    private Movie[] nNextMovies(Record<T, K> p, int n) {
        Movie[] movies = new Movie[n];
        for (int i = 0; i < n; i++) {
            movies[i] = Record.toMovie(p.getEl());
            p = p.next;
        }
        return movies;
    }

    /**
     * Ritorna gli n Person successivi al Record passato come parametro
     * @param p Record da cui partire
     * @param n numero di Person da prendere
     * @return array di Person 
     */
    private Person[] nNextActors(Record<T, K> p, int n) {
        if(n>carico) n=carico;
        Person[] actors = new Person[n];
        for (int i = 0; i < n; i++) {
            actors[i] = Record.toPerson(p.getEl());
            p = p.next;
        }
        return actors;
    }

    public Movie[] firstNMovies(int n) {
        Movie[] movies = new Movie[n];
        Record<T, K> p = record;
        movies = nNextMovies(p, n);
        return movies;
    }

    public Person[] firstNActors(int n) {
        Person[] actors = new Person[n];
        Record<T, K> p = record;
        actors = nNextActors(p, n);
        return actors;
    }

    public Movie[] stringInTitle(String title) {
        Record<T, K> p = record;
        int i = 0;
        while (p != null) {
            if ((Record.toMovie(p.getEl())).getTitle().toLowerCase().contains(title.toLowerCase())) {
                i++;
            }
            p = p.next;
        }
        p = record;
        Movie[] movies = new Movie[i];
        if(i==0) return movies;
        i = 0;
        while (p != null) {
            if ((Record.toMovie(p.getEl())).getTitle().toLowerCase().contains(title.toLowerCase())) {
                movies[i] = (Record.toMovie(p.getEl()));
                i++;
            }
            p=p.next;
        }
        return movies;
    }

    //Il booleano indica se ordinare in senso crescente(true) o decrescente(false)
    public void sort(int index, boolean b) {
        crescente = b;
        switch (index) {
            case 0:
                insertionSort(b);
                break;
            case 1:
                quickSort(b);
            default:
                break;
        }
        ordinata = true;
    }

    /**
     * Effettua l'insertion sort
     * @param b se true crescente, altrimenti decrescente
     */
    private void insertionSort(boolean b) {

        // Initialize sorted linked list
        Record<T, K> sorted = null;
        Record<T, K> current = record;
        // Traverse the given linked list and insert every
        // Record to sorted
        while (current != null) {
            // Store next for next iteration
            Record<T, K> next = current.next;
            // insert current in sorted linked list
            sorted = sortedInsert(current, sorted, b);
            // Update current
            current = next;
        }
        // Update head_ref to point to sorted linked list
        record = sorted;
    }

    //TODO: fare commento
    private Record<T, K> sortedInsert(Record<T, K> newRecord, Record<T, K> sorted, boolean b) {
        /* Special case for the head end */
        if (b) {
            if (sorted == null || sorted.getKey().compareTo(newRecord.getKey()) >= 0) {
                newRecord.next = sorted;
                sorted = newRecord;
            } else {
                Record<T, K> current = sorted;
                /* Locate the Record before the point of insertion */
                while (current.next != null && current.next.getKey().compareTo(newRecord.getKey()) < 0) {
                    current = current.next;
                }
                newRecord.next = current.next;
                current.next = newRecord;
            }
        } else {
            if (sorted == null || sorted.getKey().compareTo(newRecord.getKey()) <= 0) {
                newRecord.next = sorted;
                sorted = newRecord;
            } else {
                Record<T, K> current = sorted;
                /* Locate the Record before the point of insertion */
                while (current.next != null && current.next.getKey().compareTo(newRecord.getKey()) > 0) {
                    current = current.next;
                }
                newRecord.next = current.next;
                current.next = newRecord;
            }
        }
        return sorted;
    }

    /**
     * Effettua il quicksort
     * @param b se true crescente, altrimenti decrescente
     */
    private void quickSort(boolean b) {
        quicksortRec(0, this.carico - 1, b);
    } //TODO: i commenti di quelli sotto li fai tu jurino (forse non servono)

    private void quicksortRec(int i, int f, boolean b) {
        if (i >= f)
            return;
        int m = partition(i, f, b);
        quicksortRec(i, m - 1, b);
        quicksortRec(m + 1, f, b);

    }

    private int partition(int i, int f, boolean b) {
        int inf = i;
        int sup = f + 1;

        Record<T, K> x = getNthRecord(i);

        if(b){
            while (true) {
                do {
                    inf++;
                } while (inf <= f && getNthRecord(inf).getKey().compareTo(x.getKey()) <= 0);
                do {
                    sup--;
                } while (getNthRecord(sup).getKey().compareTo(x.getKey()) > 0);
                if (inf < sup) {
                    swap(getNthRecord(inf), getNthRecord(sup));
                } else
                    break;
            }
        }else{
            while (true) {
                do {
                    inf++;
                } while (inf <= f && getNthRecord(inf).getKey().compareTo(x.getKey()) > 0);
                do {
                    sup--;
                } while (getNthRecord(sup).getKey().compareTo(x.getKey()) < 0);
                if (inf < sup) {
                    swap(getNthRecord(inf), getNthRecord(sup));
                } else
                    break;
            }
        }

        swap(getNthRecord(i), getNthRecord(sup));
        return sup;
    }

    private void swap(Record<T, K> A, Record<T, K> B) {
        ArrayList<T> A_els = A.getAllEls();
        ArrayList<T> B_els = B.getAllEls();

        // swappa le key
        K tmp = A.getKey();
        A.setKey(B.getKey());
        B.setKey(tmp);

        A.setAllEls(B_els);
        B.setAllEls(A_els);
    }
}