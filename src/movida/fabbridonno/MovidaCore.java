package movida.fabbridonno;
import movida.commons.*;
public class MovidaCore implements IMovidaConfig,IMovidaDB,IMovidaSearch,IMovidaCollaborations {

    private final SortingAlgorithm SAs[] = {SortingAlgorithm.InsertionSort, SortingAlgorithm.QuickSort};
    private SortingAlgorithm selectedSort;
    private final MapImplementation Maps[] = {MapImplementation.ListaNonOrdinata, MapImplementation.HashIndirizzamentoAperto};
    private MapImplementation selectedMap;

    private ListaNonOrdinata lista;
    private TabellaHashAperta tabella;


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

    public boolean deleteMovieByTitle(String title)
    {
        if(selectedMap == MapImplementation.ListaNonOrdinata)
        {
            lista.delete(title);
        }

        return false;
    }

    public Movie getMovieByTitle(String title)
    {
        if(selectedMap == MapImplementation.ListaNonOrdinata)
        {
            lista.setKey(Field.Title);
            lista.search(title);
        }
        return null;
    }

    public static void main(String[] args) {
        MovidaCore test = new MovidaCore();
        boolean b = test.setMap(MapImplementation.BTree);
        b = test.setMap(MapImplementation.ListaNonOrdinata);
        System.out.println(b);
    }
}