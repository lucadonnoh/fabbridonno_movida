package movida.fabbridonno;
import movida.commons.*;
//public class MovidaCore implements IMovidaConfig,IMovidaDB,IMovidaSearch,IMovidaCollaborations {

public class MovidaCore implements IMovidaConfig {
    private final SortingAlgorithm SAs[] = {SortingAlgorithm.InsertionSort, SortingAlgorithm.QuickSort};
    private SortingAlgorithm selectedSort;
    private final MapImplementation Maps[] = {MapImplementation.ListaNonOrdinata, MapImplementation.HashIndirizzamentoAperto};
    private MapImplementation selectedMap;

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
            for(MapImplementation SM : Maps )
            {
                if(m == SM)
                {
                    selectedMap = m;
                    return true;
                }
            }
        }
        return false;
    }

    // public boolean deleteMovieByTitle(String title)
    // {
    //     if(selectedMap == MapImplementation.ListaNonOrdinata)
    //     {
    //         lista.delete(title);
    //     }

    //     return false;
    // }

    // public Movie getMovieByTitle(String title)
    // {
    //     if(selectedMap == MapImplementation.ListaNonOrdinata)
    //     {
    //         lista.setKey(Field.Title);
    //         lista.search(title);
    //     }
    //     return null;
    // }

    public static void main(String[] args) {
        Person p1 = new Person("Juri Fabbri");
        Person p2 = new Person("Donno");
        Person p3 = new Person("Di Iorio");
        Person[] p4 = {p2, p3};
        Movie m = new Movie("Gianni", 2020, 69420, p4, p1);
        ListaNonOrdinata l = new ListaNonOrdinata();
        l.insert(m, m.getTitle());
        l.stampa();
    }
}