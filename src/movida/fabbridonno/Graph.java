package movida.fabbridonno;

import movida.commons.Collaboration;
import movida.commons.Movie;
import movida.commons.Person;

import java.util.*;

public class Graph {
    private HashMap<Person, Set<Collaboration>> graph;

    public Graph() {
        graph = new HashMap<Person, Set<Collaboration>>();
    }

    public Set<Collaboration> getCollabs(Person p) {
        return graph.get(p);
    }

    public void addCollab(Person p, Collaboration c) {
        graph.get(p).add(c);
    }

    public boolean addActor(Person p) {
        if(graph.containsKey(p)) return false;
        graph.put(p, new HashSet<Collaboration>());
        return true;
    }

    public boolean deleteMovie(Movie m) {
        for(Set<Collaboration> collabs : graph.values()) {
            for(Collaboration c : collabs) {
                if(c.getMovies().contains(m)) {
                    if(c.getMovies().size() == 1) {
                        collabs.remove(c);
                        return true;
                    }
                    c.getMovies().remove(m);
                    return true;
                }
            }
        }
        return false;
    }
}
