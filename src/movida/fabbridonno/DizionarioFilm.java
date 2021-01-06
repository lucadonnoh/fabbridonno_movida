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
	public void deleteEl(Movie m);

	// public boolean delete(String title, K k);

	// public boolean delete(K k);

	public Record<T,K> searchRecord(K k);

	/**
	 * Restituisce l'elemento e con chiave <code>k</code>.
	 * In caso di duplicati, l'elemento restituito
	 * &egrave; scelto arbitrariamente tra quelli con chiave <code>k</code>.
	 *
	 * @param k chiave dell'elemento da ricercare
	 * @return elemento di chiave <code>k</code>, <code>null</code> se assente
	 */
	public T search(K k);

	/**
	 * Restituisce se se un elemento
	 * con la chiave <code>k</code> è presente.
	 * 
	 * @param k chiave dell'elemento 
	 * @return <code>true</code> se l'elemento è presente, <code>false</code> altrimenti.
	 */
	public Boolean searchKey(K k);

	/**
	 * Printa tutti i record con chiave e valore.
	 */
	public void stampa();

	/**
	 * Ritorna il numero di elementi salvati nel dizionario.
	 * @return il carico del dizionario.
	 */
	public int getCarico();

	/**
	 * Cancella tutti gli elementi del dizionario.
	 */
	public void clear();

	/**
	 * Ritorna se il dizionario è vuoto.
	 * @return <code>true</code> se è vuoto, <code>false</code> altrimenti.
	 */
	public boolean isEmpty();

	/**
	 * Esporta i Movie se il dizionario li contiene.
	 * @return array di <code>Movie</code> se li contiene, null altrimenti.
	 */
	public Movie[] export();

	/**
	 * Esporta le chiavi.
	 * @return array di Comparable contente le chiavi.
	 */
	public Comparable<K>[] exportKeys();

	public Movie[] vettoreVuoto();

	public Movie[] searchMoviesByKey(K k);

	public Movie[] searchMoviesByPerson(K k);

	public Movie[] firstNMovies(int n);

	public Person[] firstNActors(int n);

	public Movie[] stringInTitle(String title);

	public void sort(int index, boolean b);

}