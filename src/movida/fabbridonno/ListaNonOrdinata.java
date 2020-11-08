package movida.fabbridonno;

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

    public T search(K k) {
        if (searchRecord(k) != null)
            return searchRecord(k).getEl();
        else
            return null;
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

    public Boolean searchKey(K k) {
        if (record == null)
            return false;
        Record<T, K> p = record;
        while (p != null) {
            if (p.getKey().equals(k))
                return true;
            p = p.next;
        }
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
            movies[i++] = (Movie)p.getEl();
            p = p.next;
        }
        return movies;
    }

    @SuppressWarnings("unchecked")
    public Comparable<K>[] exportKeys() {
        Comparable<K>[] movies = new Comparable[carico];
        int i = 0;

        Record<T, K> p = record;
        while (p != null ) {
            movies[i++] = p.getKey();
            p = p.next;
        }
        return movies;
    }

    //Java ha il garbage collector che eliminat tutti gli elementi non referenziati.
    public void clear() {
        record = null;
    }

    //TODO: mettere a posto il cast fra el/movies/ecc
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
            } //TODO: manca p.next o sbaglio?
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
        }
        else{
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

    // takes first and last Record<T,K>,
    // but do not break any links in
    // the whole linked list
    private Record<T,K> paritionLast(Record<T,K> start, Record<T,K> end)
    {
        if(start == end ||
        start == null || end == null)
            return start;

        Record<T,K> pivot_prev = start;
        Record<T,K> curr = start;
        K pivot = end.getKey();

        // iterate till one before the end,
        // no need to iterate till the end
        // because end is pivot
        while(start != end )
        {
            //TODO: da problemi questo if in base al risultato del compare to, a volte sfora arrivando a  null idk why
            if(start.getKey().compareTo(pivot) < 0)
            {
                // keep tracks of last modified item
                pivot_prev = curr;
                K temp = curr.getKey();
                curr.setKey(start.getKey());
                start.setKey(temp);
                curr = curr.next;
            }
            start = start.next;
        }

        // swap the position of curr i.e.
        // next suitable index and pivot
        K temp = curr.getKey();
        curr.setKey(pivot);
        end.setKey(temp);

        // return one previous to current
        // because current is now pointing to pivot
        return pivot_prev;
    }

    private void sorting(Record<T,K> start, Record<T,K> end)
    {
        if(start == end )
            return;

        // split list and partion recurse
        Record<T,K> pivot_prev = paritionLast(start, end);
        sorting(start, pivot_prev);

        // if pivot is picked and moved to the start,
        // that means start and pivot is same
        // so pick from next of pivot
        if( pivot_prev != null && pivot_prev == start ){
            sorting(pivot_prev.next, end);
        }
        // if pivot is in between of the list,
        // start from next of pivot,
        // since we have pivot_prev, so we move two Record<T,K>s
        else if(pivot_prev != null && pivot_prev.next != null){
            sorting(pivot_prev.next, end);
        }
    }

    public void quicksort(boolean b){
        Record<T, K> start = this.record;
        Record<T, K> tmp = this.record;
        while(tmp.next != null){
            tmp = tmp.next;
        }
        sorting(start, tmp);
    }
}
