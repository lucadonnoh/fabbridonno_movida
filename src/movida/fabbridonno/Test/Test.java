package movida.fabbridonno.Test;

import movida.commons.*;
import java.io.File;
import java.util.Set;

import movida.fabbridonno.MovidaCore;

public class Test {

    // SETUP AND SAVE

    private MovidaCore mc = new MovidaCore();

    public Test(MapImplementation map, SortingAlgorithm algo) {
        mc.setMap(map);
        mc.setSort(algo);
    }

    public boolean setMap(MapImplementation map) {
        return mc.setMap(map);
    }

    public boolean setSort(SortingAlgorithm algo) {
        return mc.setSort(algo);
    }

    public void loadFile() {
        File file = new File("src/movida/fabbridonno/Test/Filetest/test.txt");
        mc.loadFromFile(file);
        System.out.println("\nCaricamento dei dati completati");
    }

    public void sort() {
        mc.sortAll();
    }

    public void test_Salvataggio() {
        File file = new File("src/movida/fabbridonno/Test/FileTest/salvati.txt");
        mc.saveToFile(file);
        System.out.println("\nSalvataggio su nuovo file completato");
    }

    public void reset() {
        mc.clear();
        return;
    }

    // PRINT

    public void print() {
        mc.print();
    }

    public void printYear() {
        mc.printYear();
    }

    public void printVotes() {
        mc.printVotes();
    }

    public void printDirector() {
        mc.printDirector();
    }

    public void printCast() {
        mc.printCast();
    }

    public void printAll() {
        print();
        printYear();
        printVotes();
        printDirector();
        printCast();
    }

    // IMovidaDB and IMovidaSearch

    public boolean findPerson(String s) {
        if (mc.getPersonByName(s) != null) {
            System.out.println("\nLa persona " + s + " è presente");
            return true;
        }
        System.out.println("\nLa persona " + s + " non è stata trovata");
        return false;
    }

    public void test_Find_and_Delete(String s) {
        Movie m = mc.getMovieByTitle(s);
        if (m == null) {
            System.out.println("\nIl film " + s + " non è stato trovato");
            return;
        }
        System.out.println("\nTrovato " + m.getTitle() + " procedo ad eliminarlo");
        mc.deleteMovieByTitle(s);
        return;
    }

    public void test_FilmAndPersone() {
        System.out.println("\nIn totale in ci sono " + mc.countMovies() + " film");
        System.out.println("In totale in ci sono " + mc.countPeople() + " persone");
        System.out.println("\nI film sono: ");
        for (Movie m : mc.getAllMovies())
            System.out.println(m.toString());
        System.out.println("\nLe persone sono: ");
        for (Person p : mc.getAllPeople()) {
            System.out.println(p.toString());
        }
    }

    public void test_MoviesinYear(Integer year) {
        Movie[] films = mc.searchMoviesInYear(year);
        if (films.length == 0) {
            System.out.println("\nNon sono presenti film usciti nell'anno " + year);
            return;
        }
        System.out.println("\nI film usciti nell'anno " + year + " sono:");
        for (Movie m : films) {
            System.out.println(m.toString());
        }
    }

    public void test_MoviesDirectedBy(String name) {
        Movie[] films = mc.searchMoviesDirectedBy(name);
        if (films.length == 0) {
            System.out.println("\nIl regista " + name + " non ha diretto in nessun film");
            return;
        }
        System.out.println("\nI film diretti da " + name + " sono:");
        for (Movie m : films) {
            System.out.println(m.toString());
        }
    }

    public void test_MoviesbyTitle(String s) {
        Movie[] films = mc.searchMoviesByTitle(s);
        if (films.length == 0) {
            System.out.println("\nNon ci sono film contenenti la stringa " + s);
            return;
        }
        System.out.println("\nI film contenenti la stringa " + s + " sono:");
        for (Movie m : films) {
            System.out.println(m.toString());
        }
    }

    public void test_MoviesStarredBy(String name) {
        Movie[] films = mc.searchMoviesStarredBy(name);
        if (films.length == 0) {
            System.out.println("\nL'attore " + name + " non ha partecipato in nessun film");
            return;
        }
        System.out.println("\nI film in cui ha partecipato " + name + " sono:");
        for (Movie m : films) {
            System.out.println(m.toString());
        }
    }

    public void test_MostVotedMovies(Integer n) {
        System.out.println("\nI " + n + " film più votati sono:");
        for (Movie m : mc.searchMostVotedMovies(n)) {
            System.out.println(m.toString());
        }
    }

    public void test_MostRecentMovies(Integer n) {
        System.out.println("\nI " + n + " film più recenti sono:");
        for (Movie m : mc.searchMostRecentMovies(n)) {
            System.out.println(m.toString());
        }
    }

    public void test_MostActiveActors(Integer n) {
        Person[] actors = mc.searchMostActiveActors(n);
        System.out.println("\nI " + n + " attori più attivi sono:");
        for (Person p : actors) {
            System.out.println(p.toString());
        }
    }

    // IMovida Collaborations

    public void test_GetCollabs(Person p) {
        Set<Collaboration> collabs = mc.getGraph().getCollabs(p);
        System.out.println("\n");
        if (collabs == null) {
            System.out.println("La persona non è nel grafo");
            return;
        }
        System.out.println("Le collaborazioni di " + p.toString() + " sono:");
        for (Collaboration c : collabs) {
            if (p.equals(c.getActorA()))
                System.out.println(c.getActorA().toString() + " " + c.getActorB().toString());
            else
                System.out.println(c.getActorB().toString() + " " + c.getActorA().toString());
        }
        return;
    }

    public void test_getDirectCollaboratorsOf(Person p) {
        System.out.println("\n");
        Person[] collabs = mc.getDirectCollaboratorsOf(p);
        if (collabs.length == 0)
            System.out.println("La persona non è nel grafo");
        System.out.println("I collaboratori diretti di " + p.toString() + " sono:");
        for (Person pers : collabs) {
            System.out.println(pers.toString());
        }
    }

    public void test_getTeamOf(Person p) {
        System.out.println("\n");
        Person[] collabs = mc.getTeamOf(p);
        if (collabs.length == 0)
            System.out.println("La persona non è nel grafo");
        System.out.println("Il team di " + p.toString() + " è:");
        for (Person pers : collabs) {
            System.out.println(pers.toString());
        }
    }

    public void test_maximizeCollaborationsInTheTeamOf(Person p) {
        System.out.println("\n");
        Collaboration[] collabs = mc.maximizeCollaborationsInTheTeamOf(p);
        if (collabs.length == 0)
            System.out.println("La persona non è nel grafo");
        for (Collaboration c : collabs) {
            System.out.println(c.getActorA().toString() + " " + c.getActorB().toString() + " " + c.getScore());
        }
    }

    public void test_printGraph() {
        mc.getGraph().printGraph();
    }

    // Metodo per testare tutte le funzioni riguaerdante le collaborazioni di una
    // singola persona
    public void test_FullCollaborations(Person p) {
        test_GetCollabs(p);
        test_getDirectCollaboratorsOf(p);
        test_getTeamOf(p);
        test_maximizeCollaborationsInTheTeamOf(p);
    }

    // Test completo per testare tutti i metodi con dati in input i film in movida
    // commons

    public void test_FullTest() {
        Person p = new Person("Harrison Ford");
        test_MostVotedMovies(5);
        test_MostActiveActors(5);
        test_MostRecentMovies(5);
        test_MoviesStarredBy("Al Pacino");
        findPerson("Al Pacino");
        test_MoviesbyTitle("AR");
        test_MoviesDirectedBy("Brian De Palma");
        test_MoviesinYear(1983);
        test_Find_and_Delete("Cape Fear");
        test_Find_and_Delete("Taxi Driver");
        test_Find_and_Delete("Scarface");
        printAll();
        test_MostVotedMovies(5);
        test_MostActiveActors(5);
        test_MostVotedMovies(5);
        test_MoviesStarredBy("Al Pacino");
        findPerson("Al Pacino");
        test_MoviesbyTitle("aR");
        test_MoviesDirectedBy("Brian De Palma");
        test_MoviesinYear(1983);
        test_FullCollaborations(p);
    }

    public static void main(String[] args) {
        Test t = new Test(MapImplementation.ListaNonOrdinata, SortingAlgorithm.QuickSort);
        t.loadFile();
        t.test_FullTest();
        t.test_Salvataggio();
    }
}
