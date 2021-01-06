package movida.fabbridonno;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import movida.commons.*;

public class TabellaHashAperta<T, K extends Comparable<K>> implements DizionarioFilm<T, K> {

    private int carico;
    private Record<T, K>[] array;

    private Record<T, K> DELETED;

    //La tabella viene istanziata di dimensione 1, poi viene ingrandita e rimpicciolità in base alle necessità
    @SuppressWarnings("unchecked")
    public TabellaHashAperta() {
        carico = 0;
        array = new Record[1];
        DELETED = new Record<T,K>(null, null);
    }

    private int hash(K k, int m) {
        if(k instanceof Person) {
            return Math.abs(((Person)k).getName().hashCode()) % m;
        }
        return Math.abs(k.hashCode()) % m;
    }

    //È stata scelta questo tipo di ispezione per i vantaggi che porta
    private int ispezione(int i, int hk, int m) {
        double c1 = 0.5, c2 = 0.5;
        return (int) Math.floor(hk + c1 * i + c2 * i * i) % m;
    }

    //ritorna un'arraylist contenente tutti i record presenti al momento nella tabella hash
    private ArrayList<Record<T, K>> exportAll() {
        ArrayList<Record<T, K>> list = new ArrayList<Record<T, K>>();
        for (Record<T, K> r : array) {
            if(r != null){
                list.add(r);
            }
        }
        return list;
    }

    //inserisce tutti gli elementi di una lista all'interno della tabella
    private void insertFromList(ArrayList<T> allEls, K k) {
        int i = 0;
        int l = array.length;
        while (i < array.length) 
        {
            int h = ispezione(i++, hash(k, l), l);
            if (array[h] == null) {
                array[h] = new Record<T, K>(null, k);
                array[h].setAllEls(allEls);
                return;
            }
        }
    }

    //Se si riempie la tabella viene raddoppiata mentre se si arriva ad un quarto della capienza viene dimezzata.
    //Vengono poi rinseriti nuovamente tutti gli elementi
    @SuppressWarnings("unchecked")
    private void reshape(Boolean inc) {
        ArrayList<Record<T, K>> records = exportAll();
        if (inc) {
            array = new Record[array.length * 2];
        } else {
            array = new Record[array.length / 2];
        }
        for (Record<T, K> r : records) {
            insertFromList(r.getAllEls(), r.getKey());
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

    public void deleteEl(Movie m) {
        for(Record<T,K> r : array) {
            ArrayList<T> l = r.getAllEls();
            if(r!=null && l.contains(m)) {
                if(l.size() == 1) {
                    int i = Arrays.asList(array).indexOf(r);
                    array[i] = DELETED;
                    carico--;
                }
                else{
                    l.remove(m);
                    r.setAllEls(l);
                }
            }
        }
    }

    public Record<T, K> searchRecord(K k) {
        int i=0;
        while (i<carico)//Qui è corretto tenere i<carico in quanto non serve far piu ispezioni di quanti ne abbiamo in tabella
        {
            int h = ispezione(i++, hash(k, array.length), array.length);
            if (array[h] == null) {
                return null;
            }
            if(array[h].getKey().equals(k)){
                return array[h];
            }
        }
        return null;
    }

    public T search(K k) {
        Record<T,K> p = searchRecord(k);
        if ( p != null)
            return p.getEl();
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
        for (Record<T, K> r : array) {
            if(r != null){
                r.print();
            }
        }
    }

    public int getCarico() {
        return carico;
    }

    public Movie[] export() {
        int i=0;
        int j=0;
        Movie[] movies = new Movie[carico];
        while(i<carico){
            if(array[j] != null && array[j] != DELETED){
                movies[i] = (Movie)array[j].getEl();
                i++;
            }
            j++;
        }
        return movies;
    }

    @SuppressWarnings("unchecked")
    public Comparable<K>[] exportKeys() {
        Comparable<K>[] keys = new Comparable[carico];
        int i=0, j=0;
        while(i<carico){
            if(array[j] != null){
                keys[i] = array[j].getKey();
                i++;
            }
            j++;
        }
        return keys;
    }

    @SuppressWarnings("unchecked")
    public void clear() {
        array = new Record[1];
        carico = 0;
    }

    public boolean isEmpty(){
        return carico == 0;
    }

    //TODO: questa va testata nei prossimi giorni
    public Movie[] searchMoviesByKey(K k) {

        int i = 0;
        ArrayList<Movie> list = new ArrayList<Movie>();
        int h;
        while(i<carico)//Qui è corretto tenere i<carico in quanto non serve far piu ispezioni di quanti ne abbiamo in tabella
        {
            h = ispezione(i++, hash(k, array.length), array.length);
            if(array[h].getKey().equals(k)){
                list.add((Movie)array[h].getEl());
            }
        }
        Movie[] movies = list.toArray(new Movie[0]);
        return movies;
    }

    public Movie[] searchMoviesByRecord(K k){
        int i = 0;
        ArrayList<Movie> list = new ArrayList<Movie>();
        int h;
        while(i<carico)//Qui è corretto tenere i<carico in quanto non serve far piu ispezioni di quanti ne abbiamo in tabella
        {
            h = ispezione(i++, hash(k, array.length), array.length);
            if(array[h].getKey().equals(k)){
                for(T m : array[h].getAllEls())
                list.add((Movie) m);
            }
        }
        Movie[] movies = list.toArray(new Movie[0]);
        return movies;

    }

    //Estrai tutti i film e li ordina in base alla chiave e ritorna i primi N film
    @SuppressWarnings("unchecked")
    public Movie[] firstNMovies(int n) {
        Record<T,K>[] records = exportAll().toArray(new Record[0]);
        Comparator<Record<T,K>> byKey = Comparator.comparing(Record::getKey);
        Arrays.sort(records, byKey);
        Movie[] movies = new Movie[n];
        for(int i = 0; i<n; i++) {
            movies[i] = (Movie)records[i].getEl();
        }
        return movies;
    }

    //Estrai tutti i film e li ordina in base alla chiave e ritorna i primi N attori
    @SuppressWarnings("unchecked")
    public Person[] firstNActors(int n) {
        Object[] r = exportAll().toArray();
        Record<T,K>[] records = new Record[r.length];
        int i = 0;
        for(Object el : r) {
            records[i++] = (Record<T,K>)el;
        }
        Comparator<Record<T,K>> byKey = Comparator.comparing(Record::getKey);
        Arrays.sort(records, byKey);
        Person[] actors = new Person[n];
        for(i = 0; i<n; i++) {
            actors[i] = (Person)records[i].getEl();
        }
        return actors;
    }

    public Movie[] stringInTitle(String title) {
        int n=0, j=0;
        for(int i = 0; i<array.length; i++){
            if(array[i] != null){
                if(Record.toMovie(array[i].getEl()).getTitle().contains(title)){
                    n++;
                }
            }
        }
        Movie[] movies = new Movie[n];
        for(int i = 0; i<array.length; i++){
            if(array[i] != null){
                if(Record.toMovie(array[i].getEl()).getTitle().contains(title)){
                    movies[j] = Record.toMovie(array[i].getEl());
                    j++;
                }
            }
        }
        return movies;
    }

    public void sort(int index, boolean b) {
        //System.out.println("WARNING: la tabella hash non è ordinabile");
        return;
    }
}