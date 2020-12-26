package movida.fabbridonno;
import movida.commons.*;
import java.io.File;

public class Test {
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
        mc.deleteMovieByTitle("Cape Fear");
        //System.out.println(mc.countMovies());
        //System.out.println("Numero film: " + mc.countMovies());
        // System.out.println(mc.getPersonByName("Juri"));
        mc.dizionariTitle.stampa();
        mc.dizionariDirector.stampa();
        //mc.dizionariCast.stampa();
        //mc.dizionariYear.stampa();
        //mc.dizionariVotes.stampa();
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
