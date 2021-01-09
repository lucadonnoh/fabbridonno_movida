package movida.fabbridonno;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ArrayList;

import movida.commons.*;

public class TabellaHashAperta<T, K extends Comparable<K>> implements DizionarioFilm<T, K> {

    private int carico;
    private Record<T, K>[] array;
    //TODO: volevo metterlo static ma mi da errore "Cannot make a static reference to the non-static type"
    private Record<T, K> DELETED;

    //La tabella viene istanziata di dimensione 1, poi viene ingrandita e rimpicciolità in base alle necessità
    @SuppressWarnings("unchecked")
    public TabellaHashAperta() {
        carico = 0;
        array = new Record[1];
        DELETED = new Record<T,K>(null, null);
    }

    /**
     * Effettua l'hash della chiave
     * @param k valore della chiave
     * @param m lunghezza dell'array
     * @return l'hash della chiave
     */
    private int hash(K k, int m) {
        if(k instanceof Person) {
            return Math.abs(((Person)k).getName().hashCode()) % m;
        }
        return Math.abs(k.hashCode()) % m;
    }

    /**
     * Effettua una ispezione quadratica
     * @param i numero di tentativo
     * @param hk hash della chiave
     * @param m lunghezza dell'array
     * @return indice dell'elemento nell'array
     */
    private int ispezione(int i, int hk, int m) {
        double c1 = 0.5, c2 = 0.5;
        return (int) Math.floor(hk + c1 * i + c2 * i * i) % m;
    }

    /**
     * Ritorna tutti i record della tabella
     * @return ArrayList di tutti i record
     */
    private ArrayList<Record<T, K>> exportAll() {
        ArrayList<Record<T, K>> list = new ArrayList<Record<T, K>>();
        for (Record<T, K> r : array) {
            if(r != null && r != DELETED){
                list.add(r);
            }
        }
        return list;
    }

    /**
     * Inserisce gli elementi di un Record
     * @param allEls ArrayList di elementi del Record
     * @param k chiave del Record
     */
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


    /**
     * Modifica la dimensione della tabella: se è piena la raddoppia, se ci sono meno della metà degli elementi la dimezza.
     * @param inc se <code>true</code> la raddoppia, altrimenti la dimezza.
     */
    @SuppressWarnings("unchecked")
    private void reshape(Boolean inc) {
        ArrayList<Record<T, K>> records = exportAll();
        if (inc) {
            array = new Record[array.length * 2];
        } else {
            int l = array.length;
            while(carico <= l/4){
                l = l/2;
            }
            array = new Record[l];
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
        if ((k instanceof String)) {
            k = (K) ((String) k).toLowerCase();
        }
        // a questo punto c'è almeno uno spazio libero
        while (i<array.length) {
            int h = ispezione(i++, hash(k, array.length), array.length);
            if (array[h] == null || array[h] == DELETED) {
                array[h] = new Record<T, K>(m, k);
                carico++;
                return;
            }
        }
    }

    //Dato che la funzione deve valere sia per i dizionari con 1 solo elemento sia per quelli con un'ArrayList come elemento
    //Non facciamo l'ispezione perchè si otterrebbe comunque una complessità lineare
    public void deleteEl(Movie m) {
        for(Record<T,K> r : array) {
            if(r!=null && r != DELETED){
                ArrayList<T> l = r.getAllEls();
                if(l.contains(m)) {
                    if(l.size() == 1) {
                        //TODO: dopo il reshape l'equals non va piu e quindi torna -1 ???
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
        //TODO: l'ho messo fuori dal for perchè è meglio prima cancellare tutto poi
        //mettersi a rifare l'array, se no potresti avere piu reshape nella stessa cancellazione che ha poco senso
        //tanto è lineare e quindi non ci son vantaggi potenziali dal reshape
        //Ho messo while perchè potrebbero esser necessarie più reshape
        if(carico < (Integer)(array.length/4)) {
            reshape(false);
        }
    }

    @SuppressWarnings("unchecked")
    public Record<T, K> searchRecord(K k) {
        int i=0;
        if ((k instanceof String)) {
            k = (K) ((String) k).toLowerCase();
        }

        while (i<carico)//Qui è corretto tenere i<carico in quanto non serve far piu ispezioni di quanti ne abbiamo in tabella
        {
            int h = ispezione(i++, hash(k, array.length), array.length);
            if (array[h] == null) {
                return null;
            }
            //TODO: io ho fatto in modo che se in una ricerca trovi un deleted non si stoppi ma continui, questo perchè ci potrebbero essere casi di hash uguali e quindi cerchi oltre
            if(array[h] != DELETED){
                if(array[h].getKey().equals(k)){
                    return array[h];
                }
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
            if(r != null && r != DELETED){
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
            if(array[j] != null && array[j] != DELETED){
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

    public Movie[] searchMoviesByKey(K k) {

        int i = 0;
        ArrayList<Movie> list = new ArrayList<Movie>();
        int h;
        while(i<carico)//Qui è corretto tenere i<carico in quanto non serve far piu ispezioni di quanti ne abbiamo in tabella
        {
            h = ispezione(i++, hash(k, array.length), array.length);
            if(array[h] != DELETED && array[h] != null){
                if(array[h].getKey().equals(k)){
                    list.add((Movie)array[h].getEl());
                }
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
        if(n>carico) n=carico;
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
            if(array[i] != null && array[i] != DELETED){
                if(Record.toMovie(array[i].getEl()).getTitle().contains(title)){
                    n++;
                }
            }
        }
        Movie[] movies = new Movie[n];
        if(n == 0 ) return movies;
        for(int i = 0; i<array.length; i++){
            if(array[i] != null && array[i] != DELETED){
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