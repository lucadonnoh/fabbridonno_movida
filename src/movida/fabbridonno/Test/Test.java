package movida.fabbridonno.Test;

import movida.commons.*;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

import movida.fabbridonno.MovidaCore;

public class Test {

    private MovidaCore mc = new MovidaCore();

    public Test(MapImplementation map, SortingAlgorithm algo){
        mc.setMap(map);
        mc.setSort(algo);
    }

    public boolean setMap(MapImplementation map){
        return mc.setMap(map);
    }

    public boolean setSort(SortingAlgorithm algo){
        return mc.setSort(algo);
    }

    public void sort(){
        mc.sortAll();
    }

    public void loadFile(){
        File file = new File("src/movida/fabbridonno/Test/Filetest/test.txt");
        mc.loadFromFile(file);
        System.out.println("\nCaricamento dei dati completati");
    }

    public void reset(){
        mc.clear();
        return;
    }

    public void print(){
        mc.print();
    }

    public void printYear(){
        mc.printYear();
    }

    public void printVotes(){
        mc.printVotes();
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
            System.out.println("\nIl film " + s + " non è stato trovato");
            return;
        }
        System.out.println("\nTrovato " + m.getTitle() + " procedo ad eliminarlo");
        mc.deleteMovieByTitle(s);
        return;
    }

    public boolean findPerson(String s){
        if(mc.getPersonByName(s) != null){
            System.out.println("\nLa persona " + s + " è presente");
            return true;
        }
        System.out.println("\nLa persona " + s + " non è stata trovata");
        return false;
    }

    public void test_Salvataggio(){
        File file = new File("src/movida/fabbridonno/Test/FileTest/salvati.txt");
        mc.saveToFile(file);
        System.out.println("\nSalvataggio su nuovo file completato");
    }

    public void test_FilmAndPersone(){
        System.out.println("\nIn totale in ci sono " + mc.countMovies() + " film");
        System.out.println("In totale in ci sono " + mc.countPeople() + " persone");
        System.out.println("\nI film sono: ");
        for(Movie m : mc.getAllMovies()) System.out.println(m.toString());
        System.out.println("\nLe persone sono: ");
        for(Person p : mc.getAllPeople()){
            System.out.println(p.toString());
        }
    }

    public void test_MoviesinYear(Integer year){
        Movie[] films = mc.searchMoviesInYear(year);
        if(films.length == 0){
            System.out.println("\nNon sono presenti film usciti nell'anno " + year);
            return;
        }
        System.out.println("\nI film usciti nell'anno " + year + " sono:" );
        for(Movie m: films){
            System.out.println(m.toString());
        }
    }

    public void test_MoviesDirectedBy(String name){
        Movie[] films = mc.searchMoviesDirectedBy(name);
        if(films.length == 0){
            System.out.println("\nIl regista " + name + " non ha diretto in nessun film");
            return;
        }
        System.out.println("\nI film diretti da " + name + " sono:" );
        for(Movie m: films){
            System.out.println(m.toString());
        }
    }

    public void test_MoviesbyTitle(String s){
        Movie[] films = mc.searchMoviesByTitle(s);
        if(films.length == 0){
            System.out.println("\nNon ci sono film contenenti la stringa " + s);
            return;
        }
        System.out.println("\nI film contenenti la stringa " + s + " sono:" );
        for(Movie m: films){
            System.out.println(m.toString());
        }
    }

    public void test_MoviesStarredBy(String name){
        Movie[] films = mc.searchMoviesStarredBy(name);
        if(films.length == 0){
            System.out.println("\nL'attore " + name + " non ha partecipato in nessun film");
            return;
        }
        System.out.println("\nI film in cui ha partecipato " + name + " sono:");
        for(Movie m: films ){
            System.out.println(m.toString());
        }
    }

    public void test_MostVotedMovies(Integer n){
        System.out.println("\nI " + n + " film più votati sono:" );
        for(Movie m: mc.searchMostVotedMovies(n)){
            System.out.println(m.toString());
        }
    }

    public void test_MostRecentMovies(Integer n){
        System.out.println("\nI " + n + " film più recenti sono:" );
        for(Movie m: mc.searchMostRecentMovies(n)){
            System.out.println(m.toString());
        }
    }

    public void test_MostActiveActors(Integer n){
        Person[] actors = mc.searchMostActiveActors(n);
        System.out.println("\nI " + n + " attori più attivi sono:");
        for(Person p: actors){
            System.out.println(p.toString());
        }
    }

    public void test_GetCollabs(Person p){
        Set<Collaboration> collabs = mc.getGraph().getCollabs(p);
        System.out.println("\n");
        if(collabs == null){
            System.out.println("La persona non è nel grafo");
            return;
        }

        for(Collaboration c : collabs){
            System.out.println(c.getActorA().toString() + " " + c.getActorB().toString());
        }
        return;
    }

    public void test_getDirectCollaboratorsOf(Person p){
        System.out.println("\n");
        Person[] collabs = mc.getDirectCollaboratorsOf(p);
        if(collabs.length == 0) System.out.println("La persona non è nel grafo");
        for(Person pers:collabs){
            System.out.println(pers.toString());
        }
    }

    public void test_getTeamOf(Person p){
        System.out.println("\n");
        Person[] collabs = mc.getTeamOf(p);
        if(collabs.length == 0) System.out.println("La persona non è nel grafo");
        for(Person pers:collabs){
            System.out.println(pers.toString());
        }
    }

    public void test_maximizeCollaborationsInTheTeamOf(Person p){
        System.out.println("\n");
        Collaboration[] collabs = mc.maximizeCollaborationsInTheTeamOf(p);
        if(collabs.length == 0) System.out.println("La persona non è nel grafo");
        for(Collaboration c:collabs){
            System.out.println(c.getActorA().toString() + " " + c.getActorB().toString() + " " + c.getScore());
        }
    }

    public void test_printGraph(){
        mc.getGraph().printGraph();
    }

    public void loadWrongFile(){
            File f = new File("src/movida/fabbridonno/Test/Filetest/wrongMissingColon");
            //File f = new File("src/movida/fabbridonno/Test/FileTest/wrongMissingColumn");
            //File f = new File("src/movida/fabbridonno/Test/FileTest/wrongMissingSpace");
            //File f = new File("src/movida/fabbridonno/Test/FileTest/wrongExtraSpace");
            //File f = new File("src/movida/fabbridonno/Test/FileTest/wrongLabel");
            //File f = new File("src/movida/fabbridonno/Test/FileTest/wrongDoubleNewLine");
            mc.loadFromFile(f);
    }


    public static void main(String[] args) {
            Test t=new Test(MapImplementation.HashIndirizzamentoAperto, SortingAlgorithm.QuickSort);
            t.loadFile();
            Person p = new Person("Harrison Ford");
            t.printCast();
            // t.test_FilmAndPersone();
            //t.test_printGraph();
            //t.test_GetCollabs(p);
            // t.test_getDirectCollaboratorsOf(p);
            // t.test_getTeamOf(p);
            // t.test_maximizeCollaborationsInTheTeamOf(p);
            // t.test_MoviesinYear(1991);
            //t.findPerson("Juliette Lewis");
            t.test_Find_and_Delete("Scarface");
            t.test_Find_and_Delete("Cape Fear");
            t.test_Find_and_Delete("The Sixth Sense");
            t.test_Find_and_Delete("Air Force One");
            t.test_Find_and_Delete("What Lies Beneath");
            t.test_Find_and_Delete("Taxi Driver");
            t.test_Find_and_Delete("The Fugitives");
            t.test_Find_and_Delete("The Fugitive");
            t.test_Find_and_Delete("The Fugitivessss");
            t.printCast();
            // t.test_printGraph();
            //t.test_GetCollabs(p);
            // t.test_getDirectCollaboratorsOf(p);
            // t.test_getTeamOf(p);
            // t.test_maximizeCollaborationsInTheTeamOf(p);
            //t.test_MoviesinYear(1991);
            //t.test_FilmAndPersone();
            // System.out.println(t.setMap(MapImplementation.HashIndirizzamentoAperto));
            // System.out.println(t.setSort(SortingAlgorithm.InsertionSort));
            // t.sort();
            t.test_Salvataggio();
    }
}
