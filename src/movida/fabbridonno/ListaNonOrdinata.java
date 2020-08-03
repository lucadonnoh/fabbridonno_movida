package movida.fabbridonno;
import movida.commons.*;

public class ListaNonOrdinata implements DizionarioFilm{

    private Record record; //TODO: forse da mettere private
    private int carico;

    public ListaNonOrdinata()
    {
        carico = 0;
        record = null;
    }

    public Record getRecord()
    {
        return record;
    }

    public int getCarico()
    {
        return carico;
    }

    public void insert(Movie e, Comparable k) {//TODO meglio inserimento in coda?

        Record p = new Record(e, k);

        if (record == null)
            record = p;
        else {
            p.next = record;
            record = p;
        }

        carico++;
    }

    public boolean delete(Comparable k) {

        Record  tmp = record, prev = null;
        if(tmp != null && tmp.getKey().equals(k)){
            record=record.next;
            carico--;
            return true;
        }
        while(tmp != null && !tmp.getKey().equals(k)){
            prev=tmp;
            tmp=tmp.next;
        }

         if(tmp==null)
             return false;
        prev.next=tmp.next;
        carico--;
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

    public Movie[] export()
    {
        Movie[] movies = new Movie[carico];
        int i = 0;

        Record p = record;
        while(p!=null)
        {
            movies[i++] = p.getMovie();
            p=p.next;
        }
        return movies;
    }

    public void clean(){
        record = null;
    }

}