package movida.fabbridonno;

import java.util.ArrayList;

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
                return true;
            }
        }
        if (carico <= array.length / 4) {
            reshape(false);
        }
        return false;
    }

    public T search(K k) {
        int i = 0;
        // per come è fatta la funzione di hashing se arriviamo ad un null prima
        // dell'elemento vuol dire che tale elemento non è presente
        while (i < array.length && !(array[i].equals(null))) {
            int h = ispezione(i++, hash(k, array.length), array.length);
            if (array[h].getKey().equals(k)) {
                return array[h].getEl();
            }
        }
        return null;
    }

    public void stampa() {
        return;
    }

    public Movie[] export() {
        return null;
    }

    public Comparable<K>[] exportKeys() {
        return null;
    }

    public Boolean searchKey(K k) {
        return null;
    }

    public int getCarico() {
        return -1;
    }

    public void clear() {

    }

    public Movie[] searchMoviesByKey(K k) {
        return null;
    }

    public Movie[] firstNMovies(int n) {
        return null;
    }

    public Person[] firstNActors(int n) {
        return null;
    }

    public Movie[] stringInTitle(String title) {
        return null;
    }

    public void sort(int index, boolean b) {

    }

    public Record<T, K> searchRecord(K k) {
        return null;
    }

    public void insertionSort(boolean b) {
        return;
    }
}