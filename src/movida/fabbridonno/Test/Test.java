package movida.fabbridonno.Test;
import movida.commons.*;
import java.io.File;
import movida.fabbridonno.MovidaCore;

public class Test {

    private MovidaCore mc = new MovidaCore();

    public Test(MapImplementation map, SortingAlgorithm algo){
        mc.setMap(MapImplementation.ListaNonOrdinata);
        mc.setSort(SortingAlgorithm.QuickSort);
    }

    public void loadFile(){
        File file = new File("src/movida/fabbridonno/Test/Filetest/test.txt");
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

    public void printDirector(){
        mc.printDirector();
    }

    public void printCast(){
        mc.printCast();
    }

    public void test_Find_and_Delete(String s){
        Movie m = mc.getMovieByTitle(s);
        if(m == null){
            System.out.println("Il film non è stato trovato");
            return;
        }
        System.out.println("\nTrovato " + m.getTitle() + " procedo ad eliminarlo");
        mc.deleteMovieByTitle(s);
        return;
    }

    public boolean findPerson(String s){
        if(mc.getPersonByName(s) != null) return true;
        return false;
    }

    public void test_Salvataggio(){
        File file = new File("src/movida/fabbridonno/Test/FileTest/salvati.txt");
        mc.saveToFile(file);
        System.out.println("Salvataggio su nuovo file completato");
    }

    public void test_FilmAndPersone(){
        System.out.println("In totale in ci sono " + mc.countMovies() + " film");
        System.out.println("In totale in ci sono " + mc.countPeople() + " persone");
        System.out.println("\nI film sono: ");
        for(Movie m : mc.getAllMovies()) System.out.println(m.toString());
        System.out.println("\nLe persone sono: ");
        for(Person p : mc.getAllPeople()) System.out.println(p.toString());
    }

    public void test_MoviesinYear(Integer year){
        System.out.println("I film usciti nell'anno " + year + " sono:" );
        for(Movie m: mc.searchMoviesInYear(year)){
            System.out.println(m.toString());
        }
    }

    public void test_MoviesDirectedBy(String name){
        System.out.println("I film usciti diretti da " + name + " sono:" );
        for(Movie m: mc.searchMoviesDirectedBy(name)){
            System.out.println(m.toString());
        }
    }

    public void test_MoviesbyTitle(String s){
        System.out.println("I film usciti contenenti la stringa " + s + " sono:" );
        for(Movie m: mc.searchMoviesByTitle(s)){
            System.out.println(m.toString());
        }
    }

    public void test_MostVotedMovies(Integer n){
        System.out.println("Gli " + n + " film più votati sono:" );
        for(Movie m: mc.searchMostVotedMovies(n)){
            System.out.println(m.toString());
        }
    }

    //TODO: testa
    public void test_MostRecentMovies(Integer n){
        System.out.println("I " + n + " film più recenti sono:" );
        for(Movie m: mc.searchMostRecentMovies(n)){
            System.out.println(m.toString());
        }
    }

    public void test_MoviesStarredBy(String name){
        System.out.println("I film in cui ha partecipato " + name + " sono:");
        for(Movie m: mc.searchMoviesStarredBy(name)){
            System.out.println(m.toString());
        }
    }

    //TODO: testa
    public void test_MostActiveActors(Integer n){
        System.out.println("I " + n + " attori più attivi sono:");
        for(Person p: mc.searchMostActiveActors(n)){
            System.out.println(p.toString());
        }
    }

    public void loadWrongFile(){
            //File f = new File("src/movida/fabbridonno/Test/Filetest/wrongMissingColon");      //Va
            //File f = new File("src/movida/fabbridonno/Test/FileTest/wrongMissingColumn");
            //File f = new File("src/movida/fabbridonno/Test/FileTest/wrongMissingSpace");      //Va
            //File f = new File("src/movida/fabbridonno/Test/FileTest/wrongExtraSpace");        //Va
            //TODO: l'unica cosa sarebbe un doppio spazio a metà di un nome tipo "Cape  Fear" però figa
            //File f = new File("src/movida/fabbridonno/Test/FileTest/wrongLabel");
            File f = new File("src/movida/fabbridonno/Test/FileTest/wrongDoubleNewLine");
            mc.loadFromFile(f);
    }


    public static void main(String[] args) {
            Test t=new Test(MapImplementation.ListaNonOrdinata, SortingAlgorithm.InsertionSort);
            //t.loadFile();
            t.loadWrongFile();
            t.test_Salvataggio();
    }
}
