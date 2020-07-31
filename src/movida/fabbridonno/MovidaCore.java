package movida.fabbridonno;
import movida.commons.*;
import java.io.File;
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
//public class MovidaCore implements IMovidaConfig,IMovidaDB,IMovidaSearch,IMovidaCollaborations {

public class MovidaCore implements IMovidaConfig, IMovidaDB {
    private final SortingAlgorithm SAs[] = {SortingAlgorithm.InsertionSort, SortingAlgorithm.QuickSort};
    private SortingAlgorithm selectedSort;
    private final MapImplementation Maps[] = {MapImplementation.ListaNonOrdinata, MapImplementation.HashIndirizzamentoAperto};
    private MapImplementation selectedMap;

    private DizionarioFilm dizionariTitle[] = {new ListaNonOrdinata(), new TabellaHashAperta()};
    private int index; //indice della struttura selezionata

    public MovidaCore()
    {
        selectedSort = null;
        selectedMap  = null;
    }

    /**
     * L'algoritmo è implementato in modo tale da supportare qualsiasi collezione di SortingAlgorithm di cui è fornita l'implementazione.
     * Il costo è O(n) se selectedSort != a, altrimenti O(1).
     * @param a l'algoritmo da selezionare
     */
    public boolean setSort(SortingAlgorithm a)
    {
        if(selectedSort != a)
        {
            for(SortingAlgorithm SA : SAs)
            {
                if(a == SA)
                {
                    selectedSort = a;
                    return true;
                }
            }
        }

        return false;
    }

    public boolean setMap(MapImplementation m)
    {
        if(selectedMap != m)
        {
            for(int i = 0; i<Maps.length; i++)
            {
                if(m == Maps[i])
                {
                    index = i;
                    selectedMap = m;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkFormato()
    {
        return true;
    }

    private String format(String nf)
    {
        return nf.split(": ")[1];
    }

    private Person[] formatCast(String nf)
    {
        String s = format(nf);
        String[] a = s.split(", ");
        Person[] cast = new Person[a.length];
        for(int i = 0; i<a.length; i++)
        {
            cast[i] = new Person(a[i]);
        }

        return cast;
    }

    public void loadFromFile(File f) //TODO: bisogna verificare che il formato sia giusto
    {
        if (!checkFormato()) throw new MovidaFileException();
        try {
            Scanner myReader = new Scanner(f);
            while (myReader.hasNextLine()) {
              String title = format(myReader.nextLine());
              int year = Integer.parseInt(format(myReader.nextLine()));
              Person director = new Person(format(myReader.nextLine()));
              Person[] cast = formatCast(myReader.nextLine());
              int votes = Integer.parseInt(format(myReader.nextLine()));
              dizionariTitle[index].insert(new Movie(title,year,votes,cast,director), title);
            }
            myReader.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }

    public void saveToFile(File f)
    {

    }

    public void clear()
    {

    }

    public int countMovies()
    {
        return -1;
    }

    public int countPeople()
    {
        return -1;
    }

    public boolean deleteMovieByTitle(String title)
    {
        return false;
    }

    public Movie getMovieByTitle(String title)
    {
        return null;
    }

    public Person getPersonByName(String name)
    {
        return null;
    }

    public Movie[] getAllMovies()
    {
        return null;
    }

    public Person[] getAllPeople()
    {
        return null;
    }

    public static void main(String[] args) {
        Person p1 = new Person("Juri Fabbri");
        Person p2 = new Person("Donno");
        Person p3 = new Person("Di Iorio");
        Person[] p4 = {p2, p3};
        Movie m = new Movie("Gianni", 2020, 69420, p4, p1);
        ListaNonOrdinata l = new ListaNonOrdinata();
        l.insert(m, m.getTitle());
        l.stampa();

        MovidaCore mc = new MovidaCore();
        File file = new File("src/movida/fabbridonno/test.txt");
        mc.loadFromFile(file);
    }
}