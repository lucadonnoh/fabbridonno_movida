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

    private boolean addActor(Person p) {
        for (Person k : graph.keySet()) {
            if (k.equals(p)) {
                return false;
            }
        }
        graph.put(p, new HashSet<Collaboration>());
        return true;
    }

    public void addCollab(Person p1, Person p2, Movie m) {
        Person actorA, actorB;
        if (p1.compareTo(p2) <= 0) {
            actorA = p1;
            actorB = p2;
        } else {
            actorA = p2;
            actorB = p1;
        }
        addActor(actorA);
        addActor(actorB);
        Collaboration collab = new Collaboration(actorA, actorB);
        for (Collaboration c : getCollabs(p1)) {
            if (Collaboration.areEquivalent(c, collab)) {
                c.addMovie(m);
                return;
            }
        }
        collab.addMovie(m);
        getCollabs(p1).add(collab);
    }

    public void deleteMovie(Movie m) {
        for (Iterator<Person> pIter = graph.keySet().iterator(); pIter.hasNext();) {
            Person p = pIter.next();
            Set<Collaboration> collabs = getCollabs(p);
            for (Iterator<Collaboration> cIter = collabs.iterator(); cIter.hasNext();) {
                Collaboration c = cIter.next();
                if (c.getMovies().contains(m)) {
                    if (c.getMovies().size() == 1) {
                        cIter.remove();
                    } else {
                        c.getMovies().remove(m);
                    }
                }
            }
            if (getCollabs(p).isEmpty()) {
                pIter.remove();
            }
        }
    }

    public void printGraph() {
        Person p;
        for (Map.Entry<Person, Set<Collaboration>> entry : graph.entrySet()) {
            for (Collaboration c : entry.getValue()) {
                if (c.getActorA().equals(entry.getKey())) {
                    p = c.getActorB();
                } else {
                    p = c.getActorA();
                }
                System.out.println(entry.getKey() + " " + p.toString() + " " + c.getMovies());
            }
            System.out.println();
        }
    }
}
