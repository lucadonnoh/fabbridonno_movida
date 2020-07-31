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

    public void delete(Comparable k) {

        Record  tmp = record, prev = null;

        if(tmp != null && tmp.key == k){
            record=record.next;
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
        if (record == null) return null;
        Record p = record;
        while(p.next != null){
            if (p.key.equals(k)) return p.movie;
        }
        return null;
    }

    public void stampa()
    {
        Record p = record;
        while(p!=null)
        {
            System.out.println(p.movie.getTitle());
            p=p.next;
        }
    }

}