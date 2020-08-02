package movida.fabbridonno;
import movida.commons.*;

public class ListaNonOrdinata implements DizionarioFilm{

    private Record record = null; //TODO: forse da mettere private

    public Record getRecord()
    {
        return record;
    }

    public void insert(Movie e, Comparable k) {

        Record p = new Record(e, k);

        if (record == null)
            record = p;
        else {
            p.next = record;
            record = p;
        }
    }

    public boolean delete(Comparable k) {

        Record  tmp = record, prev = null;
        if(tmp != null && tmp.getKey().equals(k)){
            record=record.next;
            return true;
        }
        while(tmp != null && tmp.getKey() != k){
            prev=tmp;
            tmp=tmp.next;
        }

         if(tmp==null)
             return false;
        prev.next=tmp.next;
        return true;
    }

    public Movie search(Comparable k) {
        if (record == null) return null;
        Record p = record;
        while(p != null){
            if (p.getKey().equals(k)) return p.getMovie();
            p=p.next;
        }
        return null;
    }

    public void stampa()
    {
        Record p = record;
        while(p!=null)
        {
            System.out.println(p.getMovie().getTitle());
            p=p.next;
        }
    }

}