package movida.fabbridonno;

import movida.commons.*;

public class ListaNonOrdinata<T> implements DizionarioFilm<T> {

    private Record<T> record;
    private int carico;

    public ListaNonOrdinata() {
        carico = 0;
        record = null;
    }

    public Record<T> getRecord() {
        return record;
    }

    public int getCarico() {
        return carico;
    }

    public void insert(T e, Comparable k) {// TODO meglio inserimento in coda?

        Record<T> p = new Record<T>(e, k);

        if (record == null)
            record = p;
        else {
            p.next = record;
            record = p;
        }

        carico++;
    }

    public boolean delete(Comparable k) {

        Record<T> tmp = record, prev = null;
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

    public Movie search(Comparable k) {
        if (searchRecord(k) != null)
            return searchRecord(k).getMovie();
        else
            return null;
    }

    public Record<T> searchRecord(Comparable k) {
        if (record == null)
            return null;
        Record<T> p = record;
        while (p != null) {
            if (p.getKey().equals(k))
                return p;
            p = p.next;
        }
        return null;
    }

    public Boolean searchKey(Comparable k) {
        if (record == null)
            return false;
        Record<T> p = record;
        while (p != null) {
            if (p.getKey().equals(k))
                return true;
            p = p.next;
        }
        return false;
    }

    public Record<T> convertToList(DizionarioFilm p){
        return null;
    }

    public void stampa() {//TODO
        Record<T> p = record;
        while (p != null) {
            p.print();
            p = p.next;
        }
    }

    public Movie[] export() {
        Movie[] movies = new Movie[carico];
        int i = 0;

        Record<T> p = record;
        while (p != null) {
            movies[i++] = p.getMovie();
            p = p.next;
        }
        return movies;
    }

    public Comparable[] exportKeys() {
        Comparable[] movies = new Comparable[carico];
        int i = 0;

        Record<T> p = record;
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

    private Movie[] nNextMovies(Record<T> p, int n) {
        Movie[] movies = new Movie[n];
        for (int i = 0; i < n; i++) {
            movies[i] = p.getMovie();
            p = p.next;
        }
        return movies;
    }

    public Movie[] searchMoviesByKey(Comparable k) {
        Record<T> p = searchRecord(k);
        Record<T> tmp = p;
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
        Record<T> p = record;
        movies = nNextMovies(p, n);
        return movies;
    }

    public Movie[] stringInTitle(String title) {
        Record<T> p = record;
        int i = 0;
        while (p != null) {
            if (p.getMovie().getTitle().contains(title)) {
                i++;
            }
            p = p.next;
        }
        p = record;
        i = 0;
        Movie[] movies = new Movie[i];
        while (p != null) {
            if (p.getMovie().getTitle().contains(title)) {
                movies[i] = p.getMovie();
                i++;
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
                quick();
                break;
            default:
                break;
        }
    }

    public void insertionSort(boolean b) {

        // Initialize sorted linked list
        Record<T> sorted = null;
        Record<T> current = record;
        // Traverse the given linked list and insert every
        // Record to sorted
        while (current != null) {
            // Store next for next iteration
            Record<T> next = current.next;
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
    private Record<T> sortedInsert(Record<T> newRecord, Record<T> sorted, boolean b) {
        /* Special case for the head end */
        if (b) {
            if (sorted == null || sorted.getKey().compareTo(newRecord.getKey()) >= 0) {
                newRecord.next = sorted;
                sorted = newRecord;
            } else {
                Record<T> current = sorted;
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
                Record<T> current = sorted;
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

    private Record<T> paritionLast(Record<T> start, Record<T> end) {
        if (start == end || start == null || end == null)
            return start;

        Record<T> pivot_prev = start;
        Record<T> curr = start;
        Record<T> pivot = end;
        // iterate till one before the end,
        // no need to iterate till the end
        // because end is pivot
        while (start != end) {
            if (start.getKey().compareTo(pivot.getKey()) < 0) {
                // keep tracks of last modified item
                pivot_prev = curr;
                Record<T> temp = curr;
                curr = start;
                start = temp;
                curr = curr.next;
            }
            start = start.next;
        }

        // swap the position of curr i.e.
        // next suitable index and pivot
        Record<T> temp = curr;
        curr = pivot;
        end = temp;

        // return one previous to current
        // because current is now pointing to pivot
        return pivot_prev;
    }

    private void quickSort(Record<T> start, Record<T> end) {
        if (start == end)
            return;

        // split list and partion recurse
        Record<T> pivot_prev = paritionLast(start, end);
        quickSort(start, pivot_prev);

        // if pivot is picked and moved to the start,
        // that means start and pivot is same
        // so pick from next of pivot
        if (pivot_prev != null && pivot_prev == start)
            quickSort(pivot_prev.next, end);

        // if pivot is in between of the list,
        // start from next of pivot,
        // since we have pivot_prev, so we move two Records
        else if (pivot_prev != null && pivot_prev.next != null)
            quickSort(pivot_prev.next.next, end);
    }

    private void quick() {
        Record<T> last = record;
        while (last.next != null)
            last = last.next;
        quickSort(record, last);
    }
}