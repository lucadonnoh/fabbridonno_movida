package movida.fabbridonno;

import movida.commons.*;
import java.io.File;
import java.util.Scanner; // Import the Scanner class to read text files
import java.io.PrintWriter;
//public class MovidaCore implements IMovidaConfig,IMovidaDB,IMovidaSearch,IMovidaCollaborations {

public class MovidaCore implements IMovidaConfig, IMovidaDB {
    private final SortingAlgorithm SAs[] = { SortingAlgorithm.InsertionSort, SortingAlgorithm.QuickSort };
    private SortingAlgorithm selectedSort;
    private final MapImplementation Maps[] = { MapImplementation.ListaNonOrdinata,
            MapImplementation.HashIndirizzamentoAperto };
    private MapImplementation selectedMap;

    public DizionarioFilm dizionariTitle[] = { new ListaNonOrdinata(), new TabellaHashAperta() };
    public DizionarioFilm dizionariYear[] = { new ListaNonOrdinata(), new TabellaHashAperta() };
    public DizionarioFilm dizionariDirector[] = { new ListaNonOrdinata(), new TabellaHashAperta() };
    public DizionarioFilm dizionariVotes[] = { new ListaNonOrdinata(), new TabellaHashAperta() };
    public DizionarioFilm dizionariCast[] = { new ListaNonOrdinata(), new TabellaHashAperta() };

    private int mapIndex; // indice della struttura selezionata
    private int sortIndex;

    public MovidaCore() {
        selectedSort = null;
        selectedMap = null;
    }

    public int getmapIndex() {
        return mapIndex;
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
            for (int i = 0; i < Maps.length; i++) {
                if (m == Maps[i]) {
                    mapIndex = i;
                    selectedMap = m;
                    return true;
                }
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

    public void loadCast(Movie m, Person[] cast) {
        for (int i = 0; i < cast.length; i++) {
            Record attore = dizionariCast[mapIndex].searchRecord(cast[i]);
            if (attore == null) {
                System.out.println(i);
                dizionariCast[mapIndex].insert(m, cast[i]);
            } else {
                attore.addMovie(m);
            }
        }
    }

    public void loadFromFile(File f) {
        try {
            Scanner myReader = new Scanner(f);
            while (myReader.hasNextLine()) {
                String title = format(myReader.nextLine(), "Title");
                if ((dizionariTitle[mapIndex].search(title) != null))
                    dizionariTitle[mapIndex].delete(title);
                int year = Integer.parseInt(format(myReader.nextLine(), "Year"));
                Person director = new Person(format(myReader.nextLine(), "Director"));
                Person[] cast = formatCast(myReader.nextLine());
                int votes = Integer.parseInt(format(myReader.nextLine(), "Votes"));
                Movie m = new Movie(title, year, votes, cast, director);
                dizionariTitle[mapIndex].insert(m, title);
                dizionariDirector[mapIndex].insert(m, director.getName());
                dizionariVotes[mapIndex].insert(m, votes);
                dizionariYear[mapIndex].insert(m, year);
                loadCast(m, cast);
                if (myReader.hasNextLine())
                    myReader.nextLine();
            }
            myReader.close();
        } catch (LabelException e) {
            System.out.println(e.getMessage());
            throw new MovidaFileException();
        } catch (Exception e) {
            throw new MovidaFileException();
        }

    }

    private String printCast(Person[] cast) {
        int i = 0;
        String s = cast[i].getName();
        for (i = 1; i < cast.length - 2; i++) {
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
            for (Movie m : dizionariTitle[mapIndex].export()) {
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
        dizionariTitle[mapIndex].clear();
        dizionariYear[mapIndex].clear();
        dizionariDirector[mapIndex].clear();
        dizionariCast[mapIndex].clear();
        dizionariVotes[mapIndex].clear();
    }

    public void clearSubDictionaries() {
        dizionariYear[mapIndex].clear();
        dizionariDirector[mapIndex].clear();
        dizionariCast[mapIndex].clear();
        dizionariVotes[mapIndex].clear();
    }

    public int countMovies() {
        return dizionariTitle[mapIndex].getCarico();
    }

    public int countPeople() { // TODO: da fare
        return dizionariCast[mapIndex].getCarico();
    }

    public boolean deleteMovieByTitle(String title) {
        if (dizionariTitle[mapIndex].delete(title)) {
            clearSubDictionaries();
            Movie[] movies = getAllMovies();
            for (Movie movie : movies) {
                dizionariYear[mapIndex].insert(movie, movie.getYear());
                dizionariVotes[mapIndex].insert(movie, movie.getVotes());
                // dizionariCast[mapIndex].insert(movie, movie.getCast());
                dizionariDirector[mapIndex].insert(movie, movie.getDirector().getName());
            }
            return true;
        }
        return false;
    }

    public Movie getMovieByTitle(String title) {
        return dizionariTitle[mapIndex].search(title);
    }

    public Person getPersonByName(String name) { // TODO: da fare
        return null;
    }

    public Movie[] getAllMovies() {
        return dizionariTitle[mapIndex].export();
    }

    public Person[] getAllPeople() { // TODO: da fare
        return null;
    }

    public Movie[] searchMoviesInYear(Integer year) {
        return dizionariYear[mapIndex].searchMoviesByKey(year);
    }

    public Movie[] searchMoviesDirectedBy(String name) {
        return dizionariDirector[mapIndex].searchMoviesByKey(name);
    }

    public Movie[] searchMoviesByTitle(String title) {
        return dizionariTitle[mapIndex].stringInTitle(title);
    }

    public Movie[] searchMostVotedMovies(Integer N) {
        return dizionariVotes[mapIndex].firstNMovies(N);
    }

    public Movie[] searchMostRecentMovies(Integer N) {
        return dizionariYear[mapIndex].firstNMovies(N);
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
        File file = new File("src/movida/fabbridonno/test.txt");
        File file2 = new File("src/movida/fabbridonno/test2.txt");
        mc.loadFromFile(file);
        Movie c = mc.getMovieByTitle("Cape Fear");
        System.out.println(c.getDirector().getName());
        System.out.println(mc.countMovies());
        // mc.dizionariTitle[mc.getmapIndex()].clear();
        // mc.dizionariTitle[mc.getmapIndex()].insert(m, m.getTitle());
        // mc.dizionariTitle[mc.getmapIndex()].stampa();
        // mc.dizionariTitle[mc.getmapIndex()].sort(0, true);
        // System.out.println(mc.countMovies());
        // mc.dizionariTitle[mc.getmapIndex()].insert(m, m.getTitle());
        // mc.dizionariTitle[mc.getmapIndex()].stampa();
        System.out.println("Suca Juri");
        mc.deleteMovieByTitle("Cape Fear");
        System.out.println(mc.countMovies());
        System.out.println("numero persone: "+mc.countPeople());
        mc.dizionariDirector[mc.getmapIndex()].stampa();
        // System.out.println(mc.countMovies());
        // File file2 = new File("movida/fabbridonno/test2.txt");
        mc.saveToFile(file2);
    }
}