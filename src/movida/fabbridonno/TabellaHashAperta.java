package movida.fabbridonno;
import movida.commons.*;

public class TabellaHashAperta implements DizionarioFilm{
    protected Movie[] v;

    public TabellaHashAperta()
    {

    }

    public void insert(Movie m, Comparable k)
    {

    }

    public boolean delete(Comparable k)
    {
        return false;
    }

    public Movie search(Comparable k)
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

    public int getCarico(){
        return -1;
    }

    public void clear(){

    }

    public Movie[] searchMoviesByKey(Comparable k)
    {
        return null;
    }

    public Movie[] firstNMovies(int n){
        return null;
    }

    public Movie[] stringInTitle(String title){
        return null;
    }

    public void sort(int index, boolean b)
    {
        
    }

    public Record searchRecord(Comparable k)
    {
        return null;
    }
}