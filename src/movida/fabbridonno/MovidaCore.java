package movida.fabbridonno;

import movida.commons.*;
import java.io.File;
import java.util.Scanner; // Import the Scanner class to read text files
import java.io.PrintWriter;
//public class MovidaCore implements IMovidaConfig,IMovidaDB,IMovidaSearch,IMovidaCollaborations {

public class MovidaCore implements IMovidaConfig, IMovidaDB, IMovidaSearch {
    private final SortingAlgorithm SAs[] = {
        SortingAlgorithm.InsertionSort,
        SortingAlgorithm.QuickSort
    };
    private SortingAlgorithm selectedSort;

    private final MapImplementation Maps[] = {
        MapImplementation.ListaNonOrdinata,
        MapImplementation.HashIndirizzamentoAperto
    };
    private MapImplementation selectedMap;

    public DizionarioFilm<Movie, String> dizionariTitle;
    public DizionarioFilm<Movie, Integer> dizionariYear;
    public DizionarioFilm<Movie, Person> dizionariDirector;
    public DizionarioFilm<Movie, Integer> dizionariVotes;
    public DizionarioFilm<Movie, Person> dizionariCast;

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
    public boolean setSort(SortingAlgorithm a) { //TODO: da cambiare
        if (selectedSort != a) {
            for (int i = 0; i < SAs.length; i++) {
                if (a == SAs[i]) {
                    sortIndex = i;
                    selectedSort = a;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean setMap(MapImplementation m) {
        if (selectedMap != m) {
            selectedMap = m;
            if(m == MapImplementation.ListaNonOrdinata)
            {
                dizionariTitle = new ListaNonOrdinata<Movie, String>();
                dizionariYear = new ListaNonOrdinata<Movie, Integer>();
                dizionariVotes = new ListaNonOrdinata<Movie, Integer>();
                dizionariDirector = new ListaNonOrdinata<Movie, Person>();
                dizionariCast = new ListaNonOrdinata<Movie, Person>();
            }
            else
            {
                dizionariTitle = new TabellaHashAperta<Movie, String>();
                dizionariYear = new TabellaHashAperta<Movie, Integer>();
                dizionariVotes = new TabellaHashAperta<Movie, Integer>();
                dizionariDirector = new TabellaHashAperta<Movie, Person>();
                dizionariCast = new TabellaHashAperta<Movie, Person>();
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
        System.out.println(Director.toString());
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

    public void loadFromFile(File f) {
        try {
            Scanner myReader = new Scanner(f);
            while (myReader.hasNextLine()) {
                String title = format(myReader.nextLine(), "Title");
                if ((dizionariTitle.search(title) != null))
                    dizionariTitle.delete(title);
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
            dizionariTitle.sort(sortIndex, true);
            dizionariYear.sort(sortIndex, true);
            dizionariVotes.sort(sortIndex, true);
            dizionariDirector.sort(sortIndex, true);
            dizionariCast.sort(sortIndex, true);
        } catch (LabelException e) {
            System.out.println(e.getMessage());
            throw new MovidaFileException();
        } catch (Exception e) {
            throw new MovidaFileException();
        }

    }

    private String printCast(Person[] cast) {
        int i;
        String s = "";
        for (i = 0; i < cast.length - 2; i++) {
            s += (cast[i].getName() + ", ");
        }
        s += (cast[i].getName());
        return s;
    }

    public void saveToFile(File f) {
        try {
            PrintWriter writer = new PrintWriter(f);
            // if (f.createNewFile()) {
            // System.out.println("File created: " + f.getName());
            // } else {
            // System.out.println("File already exists.");
            // writer.print("");
            // }
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
        } catch (Exception e) {
            throw new MovidaFileException();
        }
    }

    public void clear() {
        dizionariTitle.clear();
        dizionariYear.clear();
        dizionariDirector.clear();
        dizionariCast.clear();
        dizionariVotes.clear();
    }

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
        if (dizionariTitle.delete(title)) {
            clearSubDictionaries();
            Movie[] movies = getAllMovies();
            for (Movie movie : movies) {
                dizionariYear.insert(movie, movie.getYear());
                dizionariVotes.insert(movie, movie.getVotes());
                loadCast(movie, movie.getCast());
                loadDirector(movie, movie.getDirector());
            }
            return true;
        }
        return false;
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
        Person myPerson = (Person)c;
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
        return dizionariDirector.searchMoviesByKey(new Person(name));
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
        return r.getAllMovies();
    }

    public Person[] searchMostActiveActors(Integer N) {
        Person[] attori = getAllActors();
        int[] keys = new int[attori.length];
        int i = 0;
        for(Person p : attori)
        {
            keys[i++] = dizionariCast.searchRecord(p).getCarico();
        }

        DizionarioFilm<Person, Integer> attività;
        attività = (selectedMap == MapImplementation.ListaNonOrdinata) ? new ListaNonOrdinata<Person, Integer>() : new TabellaHashAperta<Person, Integer>();

        for(i = 0; i<attori.length; i++)
        {
            attività.insert(attori[i], keys[i]);
        }

        attività.sort(0, false);
        Person[] NActors = new Person[N];
        NActors = attività.firstNActors(N);
        return NActors;
    }

    public static void main(String[] args) {
        Person p1 = new Person("Juri Fabbri");
        Person p2 = new Person("Donno");
        Person p3 = new Person("Di Iorio");
        Person[] p4 = { p2, p3 };
        Movie m = new Movie("Gianni", 2020, 69420, p4, p1);
        ListaNonOrdinata l = new ListaNonOrdinata();
        l.insert(m, m.getTitle());
        // l.stampa();

        MovidaCore mc = new MovidaCore();
        mc.setMap(MapImplementation.ListaNonOrdinata);
        mc.setSort(SortingAlgorithm.InsertionSort);
        File file = new File("src/movida/fabbridonno/test.txt");
        // File file2 = new File("src/movida/fabbridonno/test2.txt");
        mc.loadFromFile(file);
        //Movie c = mc.getMovieByTitle("Cape Fear");
        // System.out.println(c.getDirector().getName());
        System.out.println("Numero film: " + mc.countMovies());
        // mc.dizionariTitle[mc.getmapIndex()].clear();
        // mc.dizionariTitle[mc.getmapIndex()].insert(m, m.getTitle());
        // mc.dizionariTitle[mc.getmapIndex()].stampa();
        mc.dizionariTitle.sort(0, true);
        // System.out.println(mc.countMovies());
        // mc.dizionariTitle[mc.getmapIndex()].insert(m, m.getTitle());
        // mc.dizionariTitle[mc.getmapIndex()].stampa();
        // System.out.println("Suca Juri");
        //mc.deleteMovieByTitle("Cape Fear");
        //System.out.println(mc.countMovies());
        System.out.println("Numero film: " + mc.countMovies());
        // System.out.println(mc.getPersonByName("Juri"));
        mc.dizionariTitle.stampa();
        mc.searchMostActiveActors(3);
        // mc.dizionariDirector[mc.getmapIndex()].stampa();
        // System.out.println(mc.countMovies());
        File file2 = new File("src/movida/fabbridonno/test2.txt");
        //Person p = mc.getPersonByName("Jodie Foster");
        //System.out.println(p.getName());
        // Movie[] ms = mc.searchMoviesStarredBy("Robert De Niro");
        //Person[] ps = mc.getAllPeople();
        //int i;
        mc.saveToFile(file2);
    }

}