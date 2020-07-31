package movida.fabbridonno;
import movida.commons.*;

public class ListaNonOrdinata implements DizionarioFilm{

    private Record list = null;

    
    public void insert(Movie e, Comparable k) {
        Record p = new Record(e, k);
        if (list == null)
            list = p;
        else {
            p.next = list;
            list = p;
        }
    }

    public void delete(Comparable k) {

        Record  tmp = list, prev = null; 
        
        if(tmp != null && tmp.key == k){
            list=list.next;
            return;
        }

        while(tmp != null && tmp.key != k){
            prev=tmp;
            tmp=tmp.next;
        }
        
        if(tmp==null)
            return;
        prev.next=tmp.next;
        
    }
	
    public Movie search(Comparable k) {
        if (list == null) return null;
        Record p = list;
        while(p.next != null){
            if (p.key.equals(k)) return p.movie;
        }
        return null;
    }
}