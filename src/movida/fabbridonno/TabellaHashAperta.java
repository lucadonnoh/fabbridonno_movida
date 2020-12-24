package movida.fabbridonno;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import movida.commons.*;

public class TabellaHashAperta<T, K extends Comparable<K>> implements DizionarioFilm<T, K> {
    protected Movie[] v;

    private int carico;
    private Record<T, K>[] array;

    private Record<T, K> DELETED;

    @SuppressWarnings("unchecked")
    public TabellaHashAperta() {
        carico = 0;
        array = new Record[1];
    }

    public int hash(Object k, int m) {
        return Math.abs(k.hashCode()) % m;
    }

    public int ispezione(int i, int hk, int m) {
        double c1 = 0.5, c2 = 0.5;
        return (int) Math.floor(hk + c1 * i + c2 * i * i) % m;
    }

    private ArrayList<Record<T, K>> exportAll() {
        ArrayList<Record<T, K>> list = new ArrayList<Record<T, K>>();
        for (Record<T, K> r : array) {
            list.add(r);
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    private void reshape(Boolean inc) {
        ArrayList<Record<T, K>> records = exportAll();
        if (inc) {
            array = new Record[array.length * 2];
        } else {
            array = new Record[array.length / 2];
        }
        for (Record<T, K> r : records) {
            insert(r.getAllEls(), r.getKey());
        }
    }

    private void insert(ArrayList<T> allEls, K k) {
        int i = 0;
        while (true) {
            int h = ispezione(i++, hash(k, array.length), array.length);
            if (array[h] == null) {
                array[h] = new Record<T, K>(null, k);
                array[h].setAllEls(allEls);
                return;
            }
        }
    }

    public void insert(T m, K k) {
        int i = 0;
        if (carico == array.length) {
            reshape(true);
        }
        // a questo punto c'è almeno uno spazio libero
        while (true) {
            int h = ispezione(i++, hash(k, array.length), array.length);
            if (array[h] == null || array[h] == DELETED) {
                array[h] = new Record<T, K>(m, k);
                carico++;
                return;
            }
        }
    }

    public boolean delete(K k) {
        int i = 0;
        // per come è fatta la funzione di hashing se arriviamo ad un null prima
        // dell'elemento vuol dire che tale elemento non è presente
        while (i < array.length && !(array[i].equals(null))) {
            int h = ispezione(i++, hash(k, array.length), array.length);
            if (array[h].getKey().equals(k)) {
                array[h] = DELETED;
                carico--;
                return true;
            }
        }
        if (carico <= array.length / 4) {
            reshape(false);
        }
        return false;
    }

    public T search(K k) {
        if (searchRecord(k) != null)
            return searchRecord(k).getEl();
        else
            return null;
    }

    public void stampa() {
        for (Record<T, K> r : array) {
            r.print();
        }
    }

    public Movie[] export() {
        Movie[] movies = new Movie[carico];
        for(int i=0; i<carico;i++){
            movies[i] = (Movie)array[i].getEl();
        }
        return movies;
    }

    @SuppressWarnings("unchecked")
    public Comparable<K>[] exportKeys() {
        Comparable<K>[] keys = new Comparable[carico];

        for(int i=0; i<carico;i++){
            keys[i] = array[i].getKey();
        }
        return keys;
    }

    public Record<T, K> searchRecord(K k) {
        int i=0;
        while (i<carico) {
            int h = ispezione(i++, hash(k, array.length), array.length);
            if(array[h].getKey().equals(k)){
                return array[h];
            }
            if (array[h] == null) {
                return null;
            }
        }
        return null;
    }

    public Boolean searchKey(K k) {

        if (searchRecord(k) != null)
            return true;
        else
            return false;
    }

    public int getCarico() {
        return carico;
    }

    @SuppressWarnings("unchecked")
    public void clear() {
        array = new Record[1];
        carico = 0;

    }

    public Movie[] searchMoviesByKey(K k) {

        int i = 0;
        int h = ispezione(i++, hash(k, array.length), array.length);
        ArrayList<Movie> list = new ArrayList<Movie>();
        while(i<carico){
            if(array[h].getKey().equals(k)){
                list.add((Movie)array[h].getEl());
            }
            h = ispezione(i++, hash(k, array.length), array.length);
        }
        Movie[] movies = list.toArray(new Movie[0]);
        return movies;
    }

    public Movie[] firstNMovies(int n) {
        Record<T,K>[] records = (Record<T,K>[])(exportAll().toArray());
        Comparator<Record<T,K>> byKey = Comparator.comparing(Record::getKey);
        Arrays.sort(records, byKey);
        Movie[] movies = new Movie[n];
        for(int i = 0; i<n; i++) {
            movies[i] = (Movie)records[i].getEl();
        }
        return movies;
    }

    public Person[] firstNActors(int n) {
        Record<T,K>[] records = (Record<T,K>[])(exportAll().toArray());
        Comparator<Record<T,K>> byKey = Comparator.comparing(Record::getKey);
        Arrays.sort(records, byKey);
        Person[] actors = new Person[n];
        for(int i = 0; i<n; i++) {
            actors[i] = (Person)records[i].getEl();
        }
        return actors;
    }

    public Movie[] stringInTitle(String title) {
        return null;
    }

    public void sort(int index, boolean b) {
        return;
    }

    public void insertionSort(boolean b) {
        return;
    }
}