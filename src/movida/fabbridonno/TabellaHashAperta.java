package movida.fabbridonno;
import movida.commons.*;

<<<<<<< Updated upstream
public class TabellaHashAperta implements DizionarioFilm{
    protected Movie[] v;
=======
public class TabellaHashAperta<T, K extends Comparable<K>> implements DizionarioFilm<T, K>{
    private Record<T,K> v[];
    //private int tableSize;
    private int carico;
    private HashDivisione fHash;
    private ScansioneQuadratica fScansione;
    private Record<T,K> DELETED = new Record<T,K>(null, null);
>>>>>>> Stashed changes


<<<<<<< Updated upstream
    public void insert(Movie m, Comparable k)
=======
    @SuppressWarnings({"unchecked"})
    public TabellaHashAperta(HashDivisione fHash, ScansioneQuadratica fScansione)
>>>>>>> Stashed changes
    {
        this.fHash = fHash;
		this.fScansione = fScansione;
		v = (Record<T,K>[]) new Object[0];
    }

    public void insert(T e, K k) {
		int hash = fHash.h(k, v.length);
		for (int i = 0; i < v.length - 1; i++) {
			int indice = fScansione.s(i, hash, v.length);
			if (v[indice] == null || v[indice]== DELETED){
				v[indice] = new Record<T,K>(e, k);
				return;
			}
		}
		//TODO: throw new EccezioneTabellaHashPiena();
	}

    protected int indice(K k) {
		int hash = fHash.h(k, v.length);
		for (int i = 0; i < v.length - 1; i++) {
			int indice = fScansione.s(i, hash, v.length);
			if (v[indice] == null) break;
			if (v[indice].getKey().equals(k)
					&& v[indice] != DELETED)
				return indice;
		}
		return -1;
	}

    public boolean delete(Comparable k)
    {
            int indice = indice(k);
            if (indice != -1) v[indice] = DELETED;
            return true;
    }

<<<<<<< Updated upstream
    public Movie search(Comparable k)
=======
    public Record<T, K> searchRecord(K k)
>>>>>>> Stashed changes
    {
     int indice = indice(k);
		if (indice == -1) return null;
		return v[indice];
    }

    public T search(K k) {
        if (searchRecord(k) != null)
            return searchRecord(k).getEl();
        return null;
    }

    public Boolean searchKey(K k) {
        if (searchRecord(k) != null)
            return true;

        return false;
    }

    public void stampa()
    {
        for (int i = 0; i < v.length - 1; i++) {
			if (v[i] != null && v[i] != DELETED){
                v[i].print();
            }
		}
    }

    public Movie[] export()
    {
<<<<<<< Updated upstream
        return null;
    }

    public Comparable[] exportKeys()
    {
        return null;
    }

    public Boolean searchKey(Comparable k)
    {
        return null;
=======
        Movie[] movies = new Movie[carico];
        int j=0;
        for(int i=0; i<v.length; i++){
            if (v[i] != null && v[i] != DELETED){
                movies[j] = (Movie)v[i].getEl();
                j++;
            }
        }
        return movies;
    }

    @SuppressWarnings("unchecked")
    public Comparable<K>[] exportKeys()
    {
        Comparable<K>[] keys = new Comparable[carico];
        int j=0;
        for(int i=0; i<v.length; i++){
            if (v[i] != null && v[i] != DELETED){
                keys[j] = v[i].getKey();
                j++;
            }
        }
        return keys;
>>>>>>> Stashed changes
    }

    public int getCarico(){
        return carico;
    }

    @SuppressWarnings("unchecked")
    public void clear(){
        v = (Record<T,K>[]) new Object[0];
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

<<<<<<< Updated upstream
    public Record searchRecord(Comparable k)
    {
        return null;
=======
    public void insertionSort(boolean b){
        return;
>>>>>>> Stashed changes
    }
}