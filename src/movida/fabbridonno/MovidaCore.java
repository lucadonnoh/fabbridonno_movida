package movida.fabbridonno;

//TODO: c'è da ricontrollare tutto movidaCore ma il resto dovrebbe essere a posto l'ho gia checkato e riordinato
//TODO: manca da commentare il codice sopra le funzioni, ci penso io domattina.

import movida.commons.*;
import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner; // Import the Scanner class to read text files
import java.io.PrintWriter;
//public class MovidaCore implements IMovidaConfig,IMovidaDB,IMovidaSearch,IMovidaCollaborations {

public class MovidaCore implements IMovidaConfig, IMovidaDB, IMovidaSearch {
    private SortingAlgorithm selectedSort;

    private MapImplementation selectedMap;
    // TODO: vedere cosa mettere private
    public DizionarioFilm<Movie, String> dizionariTitle;
    public DizionarioFilm<Movie, Integer> dizionariYear;
    public DizionarioFilm<Movie, Person> dizionariDirector;
    public DizionarioFilm<Movie, Integer> dizionariVotes;
    public DizionarioFilm<Movie, Person> dizionariCast;

    public Graph graph = new Graph();

    private int sortIndex;

    public MovidaCore() {
        selectedSort = null;
        selectedMap = null;
    }

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
                return true;
            } else if (a == SortingAlgorithm.QuickSort) {
                selectedSort = SortingAlgorithm.QuickSort;
                return true;
            }
        }
        return false;
    }

    public boolean setMap(MapImplementation m) {
        if (selectedMap != m) {
            selectedMap = m;
            if (m == MapImplementation.ListaNonOrdinata) {
                dizionariTitle = new ListaNonOrdinata<Movie, String>();
                dizionariYear = new ListaNonOrdinata<Movie, Integer>();
                dizionariVotes = new ListaNonOrdinata<Movie, Integer>();
                dizionariDirector = new ListaNonOrdinata<Movie, Person>();
                dizionariCast = new ListaNonOrdinata<Movie, Person>();
                return true;
            } else if (m == MapImplementation.HashIndirizzamentoAperto) {
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

    private String format(String nf, String label) {
        if (!nf.split(": ")[0].equals(label))
            throw new LabelException();
        return nf.split(": ")[1];
    }

    private Person[] formatCast(String nf) {
        String s = format(nf, "Cast");
        String[] a = s.split(", ");
        Person[] cast = new Person[a.length];
        for (int i = 0; i < a.length; i++) {
            cast[i] = new Person(a[i]);
        }

        return cast;
    }

    public void loadDirector(Movie m, Person Director) {
        Record<Movie, Person> regista = dizionariDirector.searchRecord(Director);
        if (regista == null) {
            dizionariDirector.insert(m, Director);
        } else {
            regista.addEl(m);
        }
    }

    public void loadCast(Movie m, Person[] cast) {
        for (int i = 0; i < cast.length; i++) {
            Record<Movie, Person> attore = dizionariCast.searchRecord(cast[i]);
            if (attore == null) {
                dizionariCast.insert(m, cast[i]);
            } else {
                attore.addEl(m);
            }
        }
    }

    private void addCollab(Person p1, Person p2, Movie m) {
        Person actorA, actorB;
        if (p1.compareTo(p2) <= 0) {
            actorA = p1;
            actorB = p2;
        } else {
            actorA = p2;
            actorB = p1;
        }
        graph.addActor(actorA);
        graph.addActor(actorB);
        Collaboration collab = new Collaboration(actorA, actorB);
        for (Collaboration c : graph.getCollabs(p1)) {
            if (Collaboration.areEquivalent(c, collab)) {
                c.addMovie(m);
                return;
            }
        }
        collab.addMovie(m);
        graph.getCollabs(p1).add(collab);
    }

    private void loadGraph() {
        for (Movie m : dizionariTitle.export()) {
            for (Person p1 : m.getCast()) {
                for (Person p2 : m.getCast()) {
                    if (!p1.equals(p2)) {
                        addCollab(p1, p2, m);
                    }
                }
            }
        }
    }

    public void loadFromFile(File f) {
        try {
            Scanner myReader = new Scanner(f);
            while (myReader.hasNextLine()) {
                String title = format(myReader.nextLine(), "Title");
                if ((dizionariTitle.search(title) != null))
                    dizionariTitle.deleteEl(dizionariTitle.search(title));
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

    private String printCast(Person[] cast) {
        int i;
        String s = "";
        for (i = 0; i < cast.length - 1; i++) {
            s += (cast[i].getName() + ", ");
        }
        s += (cast[i].getName());
        return s;
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

    // TODO: rimettere i sort a true
    public void sortAll() {
        dizionariTitle.sort(sortIndex, true);
        dizionariYear.sort(sortIndex, true);
        dizionariVotes.sort(sortIndex, true);
        dizionariDirector.sort(sortIndex, true);
        dizionariCast.sort(sortIndex, true);
    }

    public void clear() {
        dizionariTitle.clear();
        dizionariYear.clear();
        dizionariDirector.clear();
        dizionariCast.clear();
        dizionariVotes.clear();
    }

    //TODO: non serve a un cazzo?
    public void clearSubDictionaries() {
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

    public Movie[] searchMoviesInYear(Integer year) {
        return dizionariYear.searchMoviesByKey(year);
    }

    public Movie[] searchMoviesDirectedBy(String name) {
        return dizionariDirector.searchMoviesByPerson(new Person(name));
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
        if (r == null)
            return null;
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

        attività.sort(0, false);
        Person[] NActors = new Person[N];
        NActors = attività.firstNActors(N);
        return NActors;
    }

    void printGraph(){
        graph.printGraph();
    }

    public Person[] getDirectCollaboratorsOf(Person p) {
        int i = 0;
        Person[] collaborators = new Person[graph.getCollabs(p).size()];
        for (Collaboration c : graph.getCollabs(p)) {
            collaborators[i++] = (c.getActorA().equals(p)) ? c.getActorB() : c.getActorA();
        }
        return collaborators;
    }

    public Person[] getTeamOf(Person actor) {
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
        return null;
    }

    //FUNZIONI PER I TEST

    public void print(){
        if(dizionariTitle.isEmpty()){
            System.out.println("Non ci sono film");
        }
        dizionariCast.stampa();
        return;
    }

    public void printAll(){
        dizionariTitle.stampa();
        dizionariYear.stampa();
        dizionariVotes.stampa();
        dizionariDirector.stampa();
        dizionariCast.stampa();
    }

    public Graph getGraph(){
        return this.graph;
    }
}