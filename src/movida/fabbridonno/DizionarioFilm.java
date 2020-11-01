package movida.fabbridonno;
import movida.commons.*;

public interface DizionarioFilm<T, K extends Comparable<K>> {
	/**
	 * Aggiunge al dizionario la coppia <code>(e,k)</code>.
	 *
	 * @param e elemento da mantenere nel dizionario
	 * @param k chiave associata all'elemento
	 */
	public void insert(T e, K k);

	/**
	 * Rimuove dal dizionario l'elemento con chiave <code>k</code>.
	 * In caso di duplicati, l'elemento cancellato
	 * &egrave; scelto arbitrariamente tra quelli con chiave <code>k</code>.
	 *
	 * @param k chiave dell'elemento da cancellare
	 */
	public boolean delete(K k);

	/**
	 * Restituisce l'elemento e con chiave <code>k</code>.
	 * In caso di duplicati, l'elemento restituito
	 * &egrave; scelto arbitrariamente tra quelli con chiave <code>k</code>.
	 *
	 * @param k chiave dell'elemento da ricercare
	 * @return elemento di chiave <code>k</code>, <code>null</code> se assente
	 */
	public Movie search(K k);

	public void stampa();

	public Movie[] export();

	public int getCarico();

	public void clear();

	public Movie[] searchMoviesByKey(K k);

	public Record<T,K> searchRecord(K k);

	public Boolean searchKey(K k);

	public Comparable<K>[] exportKeys() ;

	public Movie[] firstNMovies(int n);

	//TODO: vedere se tenerla
	public Person[] firstNActors(int n);

	public Movie[] stringInTitle(String title);

	public void sort(int index, boolean b);

	public void insertionSort(boolean b);

}