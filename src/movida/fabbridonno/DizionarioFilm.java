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

	/**
	 * Ritorna il numero di elementi salvati nel dizionario.
	 * @return il carico del dizionario.
	 */
	public int getCarico();

	/**
	 * Ritorna se il dizionario è vuoto.
	 * @return <code>true</code> se è vuoto, <code>false</code> altrimenti.
	 */
	public boolean isEmpty();

	/**
	 * Cancella tutti gli elementi del dizionario.
	 */
	public void clear();

	/**
	 * Printa tutti i record con chiave e valore.
	 */
	public void stampa();

	/**
	 * Ritorna il record di chiave K
	 * @param k la chiave del record
	 * @return il Record
	 */
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
	 * Esporta i Movie se il dizionario li contiene.
	 * @return array di <code>Movie</code> se li contiene, null altrimenti.
	 */
	public Movie[] export();

	/**
	 * Esporta le chiavi.
	 * @return array di Comparable contente le chiavi.
	 */
	public Comparable<K>[] exportKeys();

	/**
	 * Ritorna i primi n Movie del dizionario
	 * @param n numero di primi Movies da ritornare
	 * @return array di Movie
	 */
	public Movie[] firstNMovies(int n);

	/**
	 * Ritorna i primi n Person del dizionario
	 * @param n numeri di primi Person da ritornre
	 * @return array di Person
	 */
	public Person[] firstNActors(int n);

	/**
	 * Ritorna i film che contengono la stringa title
	 * @param title stringa da cercare
	 * @return array di Movie
	 */
	public Movie[] stringInTitle(String title);

	/**
	 * Trova i Movie con una certa key.
	 * @param k chiave dei Movie da cercare
	 * @return array di Movie
	 */
	public Movie[] searchMoviesByKey(K k);

	/**
	 * Ritorna tutti i film dentro un Record
	 * @param k la chiave del Record
	 * @return i Movie dentro il record con chiave k
	 */
	public Movie[] searchMoviesByRecord(K k);

	/**
	 * Ordina il dizionario
	 * @param index quale ordinamento usare
	 * @param b se <code>true</code> crescente, altrimenti decrescente.
	 */
	public void sort(int index, boolean b);

}