package movida.fabbridonno;
import movida.commons.*;
public class MovidaCore implements IMovidaConfig {

    private final SortingAlgorithm SAs[] = {SortingAlgorithm.InsertionSort, SortingAlgorithm.QuickSort};
    private SortingAlgorithm selectedSort;

    public MovidaCore()
    {
        selectedSort = null;
    }

    public boolean setSort(SortingAlgorithm a)
    {
        if(selectedSort != a && (a == SAs[0] || a == SAs[1]))
        {
            selectedSort = a;
            return true;
        }

        return false;
    }

    public static void main(String[] args) {
        MovidaCore test = new MovidaCore();
        boolean b = test.setSort(SortingAlgorithm.QuickSort);
        b = test.setSort(SortingAlgorithm.QuickSort);
        System.out.println(b);
    }
}