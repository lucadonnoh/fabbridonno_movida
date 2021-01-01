package movida.fabbridonno.Test;
import movida.commons.*;
import java.io.File;
import movida.fabbridonno.MovidaCore;

public class Test {

    private MovidaCore mc = new MovidaCore();

    public Test(MapImplementation map, SortingAlgorithm algo){
        mc.setMap(MapImplementation.ListaNonOrdinata);
        mc.setSort(SortingAlgorithm.QuickSort);
        File file = new File("src/movida/fabbridonno/test.txt");
        mc.loadFromFile(file);
        System.out.println("Caricamento dei dati completati");
    }

    public void reset(){
        mc.clear();
        return;
    }

    public void print(){
        mc.print();
    }

    public void test_Find_and_Delete(String s){
        Movie m = mc.getMovieByTitle(s);
        if(m == null){
            System.out.println("Il film non è stato trovato\n");
            return;
        }
        System.out.println("Trovato " + m.getTitle() + " procedo ad eliminarlo\n");
        mc.deleteMovieByTitle(s);
        mc.print();
        return;
    }

    public boolean findPerson(String s){
        if(mc.getPersonByName(s) != null) return true;
        return false;
    }

    public void test_Salvataggio(){
        File file = new File("src/movida/fabbridonno/salvati.txt");
        mc.saveToFile(file);
        System.out.println("Salvataggio su nuovo file completato");
    }


    public static void main(String[] args) {
            Test t=new Test(MapImplementation.ListaNonOrdinata, SortingAlgorithm.InsertionSort);
            t.print();
            System.out.println(t.findPerson("Steven Bauer"));
            t.test_Find_and_Delete("Scarface");
            System.out.println(t.findPerson("Steven Bauer"));


    //     MovidaCore mc = new MovidaCore();
    //     mc.setMap(MapImplementation.ListaNonOrdinata);
    //     mc.setSort(SortingAlgorithm.QuickSort);
    //     File file = new File("src/movida/fabbridonno/test.txt");
    //     // File file2 = new File("src/movida/fabbridonno/test2.txt");
    //     mc.loadFromFile(file);
    //     //Movie c = mc.getMovieByTitle("Cape Fear");
    //     // System.out.println(c.getDirector().getName());
    //     System.out.println("Numero film: " + mc.countMovies());
    //     // mc.dizionariTitle[mc.getmapIndex()].clear();
    //     // mc.dizionariTitle[mc.getmapIndex()].insert(m, m.getTitle());
    //     // mc.dizionariTitle[mc.getmapIndex()].stampa();
    //     // mc.dizionariTitle.sort(1, true);
    //     // System.out.println(mc.countMovies());
    //     // mc.dizionariTitle[mc.getmapIndex()].insert(m, m.getTitle());
    //     // mc.dizionariTitle[mc.getmapIndex()].stampa();
    //     // System.out.println("Suca Juri");
    //     //mc.deleteMovieByTitle("Cape Fear");
    //     Person[] ms = mc.getAllPeople();
    //     System.out.println(ms.length);
    //     for(Person m:ms){
    //         System.out.println(m.toString());
    //     }
    //     //System.out.println(mc.countMovies());
    //     //System.out.println("Numero film: " + mc.countMovies());
    //     // System.out.println(mc.getPersonByName("Juri"));
    //     //mc.dizionariTitle.stampa();
    //     //mc.dizionariDirector.stampa();
    //     //mc.dizionariCast.stampa();
    //     //mc.dizionariYear.stampa();
    //     //mc.dizionariVotes.stampa();
    //     //Person[] NActors = mc.searchMostActiveActors(3);
    //     // mc.dizionariDirector[mc.getmapIndex()].stampa();
    //     // System.out.println(mc.countMovies());
    //     File file2 = new File("src/movida/fabbridonno/test2.txt");
    //     //Person p = mc.getPersonByName("Jodie Foster");
    //     //System.out.println(p.getName());
    //     // Movie[] ms = mc.searchMoviesStarredBy("Robert De Niro");
    //     //Person[] ps = mc.getAllPeople();
    //     //int i;
    //     mc.saveToFile(file2);
    // }
    }
}
