package movida.fabbridonno;

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

    private void reshape()
    {

    }

    public void insert(T m, K k) {
        int i = 0;
        if(carico == array.length) {
            reshape();
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
        return false;
    }

    public T search(K k) {
        int i = 0;
        while(i<array.length) {
            int h = ispezione(i++, hash(k, array.length), array.length);
            if(array[h].getKey().equals(k)) {
                return array[h].getEl();
            }
        }
        return null; // TODO: lanciare eccezione?
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