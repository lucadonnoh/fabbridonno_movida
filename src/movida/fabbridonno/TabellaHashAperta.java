package movida.fabbridonno;
import movida.commons.*;

public class TabellaHashAperta<T, K extends Comparable<K>> implements DizionarioFilm<T, K>{
    protected Movie[] v;

    public TabellaHashAperta()
    {

    }

    public void insert(T m, K k)
    {

    }

    public boolean delete(K k)
    {
        return false;
    }

    public Movie search(K k)
    {
        return null;
    }

    public void stampa()
    {
        return;
    }

    public Movie[] export()
    {
        return null;
    }

    public Comparable<K>[] exportKeys()
    {
        return null;
    }

    public Boolean searchKey(K k)
    {
        return null;
    }


    public int getCarico(){
        return -1;
    }

    public void clear(){

    }

    public Movie[] searchMoviesByKey(K k)
    {
        return null;
    }

    public Movie[] firstNMovies(int n){
        return null;
    }

    public Person[] firstNActors (int n){
        return null;
    }

    public Movie[] stringInTitle(String title){
        return null;
    }

    public void sort(int index, boolean b)
    {

    }

    public Record<T, K> searchRecord(K k)
    {
        return null;
    }

    public void insertionSort(boolean b){
        return;
    }
}