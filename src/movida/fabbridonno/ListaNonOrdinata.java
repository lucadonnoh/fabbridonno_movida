package movida.fabbridonno;

import java.util.ArrayList;

import movida.commons.*;

public class ListaNonOrdinata<T, K extends Comparable<K>> implements DizionarioFilm<T, K> {

    private Record<T, K> record;
    private int carico;

    public ListaNonOrdinata() {
        carico = 0;
        record = null;
    }

    public Record<T, K> getRecord() {
        return record;
    }

    public Record<T, K> getNthRecord(int n) {
        Record<T, K> tmp = this.record;
        while (tmp != null && n >= 0) {
            if (n == 0)
                return tmp;
            n--;
            tmp = tmp.next;
        }
        return null;
    }

    public int getCarico() {
        return carico;
    }

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

    public boolean delete(K k) {

        Record<T, K> tmp = record, prev = null;
        if (tmp != null && tmp.getKey().equals(k)) {

            record = record.next;
            carico--;
            return true;
        }
        while (tmp != null && !tmp.getKey().equals(k)) {
            prev = tmp;
            tmp = tmp.next;
        }

        if (tmp == null)
            return false;
        prev.next = tmp.next;
        carico--;
        return true;
    }

    public Record<T, K> searchRecord(K k) {
        if (record == null)
            return null;
        Record<T, K> p = record;
        while (p != null) {
            if (p.getKey().equals(k))
                return p;
            p = p.next;
        }
        return null;
    }

    public T search(K k) {
        if (searchRecord(k) != null)
            return searchRecord(k).getEl();
        else
            return null;
    }

    public Boolean searchKey(K k) {
        if (searchRecord(k) != null)
            return true;
        else
            return false;
    }

    public void stampa() {
        Record<T, K> p = record;
        while (p != null) {
            p.print();
            p = p.next;
        }
    }

    public Movie[] export() {
        Movie[] movies = new Movie[carico];
        int i = 0;

        Record<T, K> p = record;
        while (p != null) {
            movies[i++] = (Movie) p.getEl();
            p = p.next;
        }
        return movies;
    }

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

    // Java ha il garbage collector che eliminat tutti gli elementi non
    // referenziati.
    public void clear() {
        record = null;
        carico = 0;
    }

    private Movie[] nNextMovies(Record<T, K> p, int n) {
        Movie[] movies = new Movie[n];
        for (int i = 0; i < n; i++) {
            movies[i] = Record.toMovie(p.getEl());
            p = p.next;
        }
        return movies;
    }

    private Person[] nNextActors(Record<T, K> p, int n) {
        Person[] actors = new Person[n];
        for (int i = 0; i < n; i++) {
            actors[i] = Record.toPerson(p.getEl());
            p = p.next;
        }
        return actors;
    }

    public Movie[] searchMoviesByKey(K k) {
        Record<T, K> p = searchRecord(k);
        Record<T, K> tmp = p;
        int n = 0;
        while (tmp.next.getKey() == k) {
            n++;
            tmp = tmp.next;
        }
        Movie[] movies = new Movie[n];
        movies = nNextMovies(p, n);
        return movies;
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
            if ((Record.toMovie(p.getEl())).getTitle().contains(title)) {
                i++;
            }
            p = p.next;
        }
        p = record;
        i = 0;
        Movie[] movies = new Movie[i];
        while (p != null) {
            if ((Record.toMovie(p.getEl())).getTitle().contains(title)) {
                movies[i] = (Record.toMovie(p.getEl()));
                i++;
                p=p.next;
            }
        }
        return movies;
    }

    public void sort(int index, boolean b) {
        switch (index) {
            case 0:
                insertionSort(b);
                break;
            case 1:
                quicksort(b);
            default:
                break;
        }
    }

    public void insertionSort(boolean b) {

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

    /*
     * function to insert a new_Record in a list. Note that this function expects a
     * pointer to head_ref as this can modify the head of the input linked list
     * (similar to push())
     */
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

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //TODO: fare anche il verso decrescente
    private void quicksort(boolean b) {
        quicksortRec(0, this.carico - 1);
    }

    private void quicksortRec(int i, int f) {
        if (i >= f)
            return;
        int m = partition(i, f);
        quicksortRec(i, m - 1);
        quicksortRec(m + 1, f);

    }

    private int partition(int i, int f) {
        int inf = i;
        int sup = f + 1;

        Record<T, K> x = getNthRecord(i);

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