package movida.fabbridonno;
import movida.commons.*;

public class ListaNonOrdinata {

    private Record list = null;

    public final class Record{
        private Movie movie;
        private Record next;
        private Record prev;

        public Record(Movie movie){
            this.movie = movie;
            this.next = null;
            this.prev = null;
        }
    }

    public void insert(Movie m){
        Record p = new Record(m);
        if (list == null)
            list = p;
        else {
            p.next = list;
            list.prev=p;
            list=p;
        }
    }

    public void delete(Comparable k) {
        Record p = null;
        while(list != null){
            if (p.chiave.equals(k)) break;
            if (p == list)
                p = null; break;
        if (list != null)
            for (p = list.next; ; p = p.next){
                if (p.chiave.equals(k)) break;
                if (p == list) { p = null; break; }
            }
        if (p == null)
            throw new EccezioneChiaveNonValida();
        if (p.next == p) list = null;
        else {
            if (list == p) list = p.next;
            p.next.prev = p.prev;
            p.prev.next = p.next;
         }
        }
    }

    public Object search(Comparable k) {
        if (list == null) return null;
        for (Record p = list.next; ; p = p.next){
            if (p.chiave.equals(k)) return p.elem;
            if (p == list) return null;
        }
    }

    public void setKey(Field f)
    {
        if(f == Field.Title)
        {
            setKeyAsTitle();
        }
    }

}