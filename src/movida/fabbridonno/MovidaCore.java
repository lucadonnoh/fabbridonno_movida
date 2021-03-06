package movida.fabbridonno;

import movida.commons.*;
import java.io.File;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner; // Import the Scanner class to read text files
import java.io.PrintWriter;
public class MovidaCore implements IMovidaConfig, IMovidaDB, IMovidaSearch, IMovidaCollaborations {

    private SortingAlgorithm selectedSort;
    private MapImplementation selectedMap;
    private int sortIndex;

    private DizionarioFilm<Movie, String> dizionariTitle;
    private DizionarioFilm<Movie, Integer> dizionariYear;
    private DizionarioFilm<Movie, Person> dizionariDirector;
    private DizionarioFilm<Movie, Integer> dizionariVotes;
    private DizionarioFilm<Movie, Person> dizionariCast;
    private Graph graph = new Graph();


    public MovidaCore() {
        selectedSort = null;
        selectedMap = null;
    }

    //IMovidaConfig

    /**
     * L'algoritmo è implementato in modo tale da supportare qualsiasi collezione di
     * SortingAlgorithm di cui è fornita l'implementazione. Il costo è O(n) se
     * selectedSort != a, altrimenti O(1).
     *
     * @param a l'algoritmo da selezionare
     */
    public boolean setSort(SortingAlgorithm a) {
        if (selectedSort != a) {
            if (a == SortingAlgorithm.InsertionSort) {
                selectedSort = SortingAlgorithm.InsertionSort;
                sortIndex = 0;
                return true;
            } else if (a == SortingAlgorithm.QuickSort) {
                selectedSort = SortingAlgorithm.QuickSort;
                sortIndex = 1;
                return true;
            }
        }
        return false;
    }

    public boolean setMap(MapImplementation m) {
        if (selectedMap != m) {
            if (m == MapImplementation.ListaNonOrdinata) {
                selectedMap = m;
                dizionariTitle = new ListaNonOrdinata<Movie, String>();
                dizionariYear = new ListaNonOrdinata<Movie, Integer>();
                dizionariVotes = new ListaNonOrdinata<Movie, Integer>();
                dizionariDirector = new ListaNonOrdinata<Movie, Person>();
                dizionariCast = new ListaNonOrdinata<Movie, Person>();
                return true;
            } else if (m == MapImplementation.HashIndirizzamentoAperto) {
                selectedMap = m;
                dizionariTitle = new TabellaHashAperta<Movie, String>();
                dizionariYear = new TabellaHashAperta<Movie, Integer>();
                dizionariVotes = new TabellaHashAperta<Movie, Integer>();
                dizionariDirector = new TabellaHashAperta<Movie, Person>();
                dizionariCast = new TabellaHashAperta<Movie, Person>();
                return true;
            }
        }
        return false;
    }

    //IMovidaDB

    private String format(String nf, String label) {
        if (!nf.split(": ")[0].equals(label)) throw new LabelException();
        if (nf.split(": ")[1].endsWith(" ") || nf.split(": ")[1].startsWith(" ")) throw new LabelException();
        if (nf.split(": ")[1].endsWith(",") || nf.split(": ")[1].startsWith(",")) throw new LabelException();
        return nf.split(": ")[1];
    }

    private Person[] formatCast(String nf) {
        String s = format(nf, "Cast");
        String[] a = s.split(", ");
        if(a[1].startsWith(" ") || a[1].startsWith(",")) throw new LabelException();
        Person[] cast = new Person[a.length];
        for (int i = 0; i < a.length; i++) {
            cast[i] = new Person(a[i]);
        }
        return cast;
    }

    private void loadDirector(Movie m, Person Director) {
        Record<Movie, Person> regista = dizionariDirector.searchRecord(Director);
        if (regista == null) {
            dizionariDirector.insert(m, Director);
        } else {
            regista.addEl(m);
        }
    }

    private void loadCast(Movie m, Person[] cast) {
        for (int i = 0; i < cast.length; i++) {
            Record<Movie, Person> attore = dizionariCast.searchRecord(cast[i]);
            if (attore == null) {
                dizionariCast.insert(m, cast[i]);
            } else {
                attore.addEl(m);
            }
        }
    }

    private void loadGraph() {
        for (Movie m : dizionariTitle.export()) {
            for (Person p1 : m.getCast()) {
                for (Person p2 : m.getCast()) {
                    if (!p1.equals(p2)) {
                        graph.addCollab(p1, p2, m);
                    }
                }
            }
        }
    }

    private String printCast(Person[] cast) {
        int i;
        String s = "";
        for (i = 0; i < cast.length - 1; i++) {
            s += (cast[i].getName() + ", ");
        }
        s += (cast[i].getName());   //È fatto cosi perchè non vogliamo una virgola in fondo
        return s;
    }

    public void loadFromFile(File f) {
        try {
            Scanner myReader = new Scanner(f);
            while (myReader.hasNextLine()) {
                String title = format(myReader.nextLine(), "Title");
                if ((dizionariTitle.searchKey(title))){
                    deleteMovieByTitle(title);
                }
                int year = Integer.parseInt(format(myReader.nextLine(), "Year"));
                Person director = new Person(format(myReader.nextLine(), "Director"));
                Person[] cast = formatCast(myReader.nextLine());
                int votes = Integer.parseInt(format(myReader.nextLine(), "Votes"));
                Movie m = new Movie(title, year, votes, cast, director);
                dizionariTitle.insert(m, title);
                dizionariVotes.insert(m, votes);
                dizionariYear.insert(m, year);
                loadDirector(m, director);
                loadCast(m, cast);
                if (myReader.hasNextLine())
                    myReader.nextLine();
            }
            myReader.close();
            sortAll();
            loadGraph();
        } catch (LabelException e) {
            System.out.println(e.getMessage());
            throw new MovidaFileException();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new MovidaFileException();
        }

    }

    public void saveToFile(File f) {
        try {
            PrintWriter writer = new PrintWriter(f);
            writer.print("");
            for (Movie m : dizionariTitle.export()) {
                writer.write("Title: " + m.getTitle() + "\n");
                writer.write("Year: " + m.getYear() + "\n");
                writer.write("Director: " + m.getDirector().getName() + "\n");
                writer.write("Cast: " + printCast(m.getCast()) + "\n");
                writer.write("Votes: " + m.getVotes() + "\n");
                writer.write("\n");
            }
            writer.flush();
            writer.close();
            return;
        } catch (Exception e) {
            throw new MovidaFileException();
        }
    }

    /**
     * Sorta i dizionari in ordine crescente, tranne
     * quelli che hanno come chiave i voti e l'anno.
     */
    public void sortAll() {
        dizionariTitle.sort(sortIndex, true);
        dizionariYear.sort(sortIndex, false);
        dizionariVotes.sort(sortIndex, false);
        dizionariDirector.sort(sortIndex, true);
        dizionariCast.sort(sortIndex, true);
    }

    /**
     * Azzera i dizionari.
     */
    public void clear() {
        dizionariTitle.clear();
        dizionariYear.clear();
        dizionariDirector.clear();
        dizionariCast.clear();
        dizionariVotes.clear();
    }

    public int countMovies() {
        return dizionariTitle.getCarico();
    }

    public int countPeople() {
        return dizionariDirector.getCarico() + dizionariCast.getCarico();
    }

    public boolean deleteMovieByTitle(String title) {
        Movie movie = dizionariTitle.search(title);
        if (movie == null)
            return false;
        dizionariYear.deleteEl(movie);
        dizionariVotes.deleteEl(movie);
        dizionariDirector.deleteEl(movie);
        dizionariCast.deleteEl(movie);
        dizionariTitle.deleteEl(movie);
        graph.deleteMovie(movie);
        return true;
    }

    public Movie getMovieByTitle(String title) {
        return dizionariTitle.search(title);
    }

    public Person getPersonByName(String name) {
        Person p = new Person(name);
        Record<Movie, Person> r = dizionariCast.searchRecord(p);
        if (r == null)
            return null;
        Comparable<Person> c = r.getKey();
        Person myPerson = (Person) c;
        return myPerson;
    }

    public Movie[] getAllMovies() {
        return dizionariTitle.export();
    }

    private Person[] getAllActors() {
        Comparable<Person>[] compCast = dizionariCast.exportKeys();
        Person[] actors = new Person[compCast.length];
        for (int i = 0; i < compCast.length; i++) {
            actors[i] = (Person) compCast[i];
        }
        return actors;
    }

    private Person[] getAllDirectors() {
        Comparable<Person>[] compDir = dizionariDirector.exportKeys();
        Person[] directors = new Person[compDir.length];
        for (int i = 0; i < compDir.length; i++) {
            directors[i] = (Person) compDir[i];
        }

        return directors;
    }

    public Person[] getAllPeople() {
        Person[] actors = getAllActors();
        Person[] directors = getAllDirectors();
        Person[] people = new Person[actors.length + directors.length];
        System.arraycopy(actors, 0, people, 0, actors.length);
        System.arraycopy(directors, 0, people, actors.length, directors.length);
        return people;
    }

    //IMovidaSearch

    public Movie[] searchMoviesInYear(Integer year) {
        return dizionariYear.searchMoviesByKey(year);
    }

    public Movie[] searchMoviesDirectedBy(String name) {
        return dizionariDirector.searchMoviesByRecord(new Person(name));
    }

    public Movie[] searchMoviesByTitle(String title) {
        return dizionariTitle.stringInTitle(title);
    }

    public Movie[] searchMostVotedMovies(Integer N) {
        return dizionariVotes.firstNMovies(N);
    }

    public Movie[] searchMostRecentMovies(Integer N) {
        return dizionariYear.firstNMovies(N);
    }

    public Movie[] searchMoviesStarredBy(String name) {
        Person p = new Person(name);
        Record<Movie, Person> r = dizionariCast.searchRecord(p);
        if (r == null){
            return new Movie[0];
        }
        return Record.toMovieArray(r.getAllEls());
    }

    public Person[] searchMostActiveActors(Integer N) {
        Person[] attori = getAllActors();
        int[] keys = new int[attori.length];
        int i = 0;
        for (Person p : attori) {
            keys[i++] = dizionariCast.searchRecord(p).getCarico();
        }

        // perché non c'è l'ordinamento sulle hash, allora sfruttiamo la lista che
        // abbiamo già
        DizionarioFilm<Person, Integer> attività = new ListaNonOrdinata<Person, Integer>();

        for (i = 0; i < attori.length; i++) {
            attività.insert(attori[i], keys[i]);
        }

        attività.sort(sortIndex, false);
        if(N>attori.length){
            N = attori.length;
        }
        Person[] NActors = new Person[N];
        NActors = attività.firstNActors(N);
        return NActors;
    }

    //IMovidaCollaborations

    public Graph getGraph() {
        return this.graph;
    }

    public Person[] getDirectCollaboratorsOf(Person p) {
        int i = 0;
        if(graph.getCollabs(p) == null) return new Person[0];
        Person[] collaborators = new Person[graph.getCollabs(p).size()];
        for (Collaboration c : graph.getCollabs(p)) {
            collaborators[i++] = (c.getActorA().equals(p)) ? c.getActorB() : c.getActorA();
        }
        return collaborators;
    }

    public Person[] getTeamOf(Person actor) {
        if(graph.getCollabs(actor) == null) return new Person[0];
        HashSet<Person> visited = new HashSet<Person>();
        Queue<Person> queue = new LinkedList<Person>();

        queue.add(actor);
        visited.add(actor);

        while (!queue.isEmpty()) {
            Person p = queue.poll();
            Person q;
            for (Collaboration c : graph.getCollabs(p)) {
                q = (c.getActorA().equals(p)) ? c.getActorB() : c.getActorA();
                if (!visited.contains(q)) {
                    queue.add(q);
                    visited.add(q);
                }
            }
        }
        return visited.toArray(new Person[0]);
    }

    public Collaboration[] maximizeCollaborationsInTheTeamOf(Person actor) {
        if(getTeamOf(actor).length == 0) return new Collaboration[0];
        HashMap<Person, Double> d = new HashMap<Person, Double>();
        HashMap<Person, Collaboration> maxCollab = new HashMap<Person, Collaboration>();
        Comparator<Entry> comparator = Comparator.comparing(Entry::getValue).reversed();
		PriorityQueue<Entry> q = new PriorityQueue<Entry>(comparator);
        Entry entry = new Entry(actor, 0.0);
        for(Person p : getTeamOf(actor)) {
            if(p.equals(actor)) {
                d.put(actor, Double.POSITIVE_INFINITY);
            } else {
                d.put(p, Double.NEGATIVE_INFINITY);
            }
        }
        q.add(entry);
        while(!q.isEmpty()) {
            Person actor1 = q.poll().getKey();
            for(Collaboration c : graph.getCollabs(actor1)) {
                Person actor2;
                if(c.getActorA().equals(actor1)) {
                    actor2 = c.getActorB();
                } else {
                    actor2 = c.getActorA();
                }

                if(d.get(actor2) == Double.NEGATIVE_INFINITY) {
                    q.add(new Entry(actor2, c.getScore()));
                    d.replace(actor2, c.getScore());
                    maxCollab.put(actor2, c);
                } else if (c.getScore() > d.get(actor2) ) {
                    //Se questo if ritorna falso allora il nodo è gia preso e non può essere modificato
                    if(q.remove(new Entry(actor2, 69.420))){
                        q.add(new Entry(actor2, c.getScore()));
                        d.replace(actor2, c.getScore());
                        maxCollab.put(actor2, c);
                    }
                }
            }
        }

        return maxCollab.values().toArray(new Collaboration[0]);
    }

    // FUNZIONI DI STAMPA PER I TEST

    public void print() {
        if (dizionariTitle.isEmpty()) {
            System.out.println("Non ci sono film in MovidaCore");
        }
        dizionariTitle.stampa();
        return;
    }

    public void printYear() {
        if (dizionariTitle.isEmpty()) {
            System.out.println("Non ci sono film in MovidaCore");
        }
        dizionariYear.stampa();
        return;
    }

    public void printVotes() {
        if (dizionariTitle.isEmpty()) {
            System.out.println("Non ci sono film in MovidaCore");
        }
        dizionariVotes.stampa();
        return;
    }

    public void printDirector() {
        if (dizionariTitle.isEmpty()) {
            System.out.println("Non ci sono film in MovidaCore");
        }
        dizionariDirector.stampa();
        return;
    }

    public void printCast() {
        if (dizionariTitle.isEmpty()) {
            System.out.println("Non ci sono film in MovidaCore");
            return;
        }
        dizionariCast.stampa();
        return;
    }

    public void printAll() {
        dizionariTitle.stampa();
        dizionariYear.stampa();
        dizionariVotes.stampa();
        dizionariDirector.stampa();
        dizionariCast.stampa();
    }

    public void printGraph() {
        graph.printGraph();
    }
}