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
        //Set<Collaboration> collabs = graph.get(p);
        for(Map.Entry<Person,Set<Collaboration>> entry : graph.entrySet()){
                if(entry.getKey().toString().equals(p.toString())){
                    return entry.getValue();
                }
      }
      return null;
    }

    public void addCollab(Person p, Collaboration c) {
        graph.get(p).add(c);
    }

    public boolean addActor(Person p) {
        for(Person k : graph.keySet()){
            if(k.toString().equals(p.toString())){
                return false;
            }
        }
        graph.put(p, new HashSet<Collaboration>());
        return true;
    }

    public void deleteMovie(Movie m) {
        for(Iterator<Person> pIter = graph.keySet().iterator(); pIter.hasNext();) {
            Person p = pIter.next();
            Set<Collaboration> collabs = getCollabs(p);
            for (Iterator<Collaboration> cIter = collabs.iterator(); cIter.hasNext();) {
                Collaboration c=cIter.next();
                if(c.getMovies().contains(m)) {
                    if(c.getMovies().size() == 1) {
                        cIter.remove();
                    }else{
                        c.getMovies().remove(m);
                    }
                }
            }
            if(getCollabs(p).isEmpty()) {
                pIter.remove();
            }
        }
    }


    public void printGraph(){
        Person p;
        for(Map.Entry<Person,Set<Collaboration>> entry : graph.entrySet()){
            for(Collaboration c : entry.getValue()) {
                if(c.getActorA().equals(entry.getKey())){
                    p = c.getActorB();
                }else{
                    p=c.getActorA();
                }
                System.out.println(entry.getKey() + " " + p.toString() + " " + c.getMovies());
            }
            System.out.println();
        }
    }
}
