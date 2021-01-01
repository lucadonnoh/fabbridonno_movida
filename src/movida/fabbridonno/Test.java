package movida.fabbridonno;
import movida.commons.*;
import java.io.File;

public class Test {
    public static void main(String[] args) {

        MovidaCore mc = new MovidaCore();
        mc.setMap(MapImplementation.ListaNonOrdinata);
        mc.setSort(SortingAlgorithm.QuickSort);
        File file = new File("src/movida/fabbridonno/test.txt");
        // File file2 = new File("src/movida/fabbridonno/test2.txt");
        mc.loadFromFile(file);
        //Movie c = mc.getMovieByTitle("Cape Fear");
        // System.out.println(c.getDirector().getName());
        System.out.println("Numero film: " + mc.countMovies());
        // mc.dizionariTitle[mc.getmapIndex()].clear();
        // mc.dizionariTitle[mc.getmapIndex()].insert(m, m.getTitle());
        // mc.dizionariTitle[mc.getmapIndex()].stampa();
        // mc.dizionariTitle.sort(1, true);
        // System.out.println(mc.countMovies());
        // mc.dizionariTitle[mc.getmapIndex()].insert(m, m.getTitle());
        // mc.dizionariTitle[mc.getmapIndex()].stampa();
        // System.out.println("Suca Juri");
        //mc.deleteMovieByTitle("Cape Fear");
        Person[] ms = mc.getAllPeople();
        System.out.println(ms.length);
        for(Person m:ms){
            System.out.println(m.toString());
        }
        //System.out.println(mc.countMovies());
        //System.out.println("Numero film: " + mc.countMovies());
        // System.out.println(mc.getPersonByName("Juri"));
        //mc.dizionariTitle.stampa();
        //mc.dizionariDirector.stampa();
        //mc.dizionariCast.stampa();
        //mc.dizionariYear.stampa();
        //mc.dizionariVotes.stampa();
        //Person[] NActors = mc.searchMostActiveActors(3);
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
