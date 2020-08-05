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

        Record tmp = record, prev = null;
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
        if(searchRecord(k) != null)
            return searchRecord(k).getMovie();
        else return null;
    }

    private Record searchRecord(Comparable k)
    {
        if (record == null) return null;
        Record p = record;
        while(p != null){
            if (p.getKey().equals(k)) return p;
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

    public void clear(){
        record = null;
    }


    private Movie[] nNextMovies(Record p, int n){
        Movie[] movies = new Movie[n];
        for(int i = 0; i<n; i++)
        {
            movies[i] = p.getMovie();
            p = p.next;
        }
        return movies;
    }

    public Movie[] searchMoviesByKey(Comparable k)
    {
        Record p = searchRecord(k);
        Record tmp = p;
        int n = 0;
        while(tmp.next.getKey() == k)
        {
            n++;
            tmp = tmp.next;
        }
        Movie[] movies = new Movie[n];
        movies = nNextMovies(p, n);
        return movies;
    }

    public Movie[] firstNMovies(int n){
        Movie[] movies = new Movie[n];
        Record p = record;
        movies = nNextMovies(p, n);
        return movies;
    }

    public Movie[] stringInTitle(String title){
        Record p = record;
        int i=0;    
        while(p!=null){
            if(p.getMovie().getTitle().contains(title)){
                i++;
            }
            p=p.next;
        }
        p=record;
        i=0;
        Movie[] movies = new Movie[i];
        while(p!=null){
            if(p.getMovie().getTitle().contains(title)){
                movies[i]=p.getMovie();
                i++;
            }
        }
        return movies;
    }

    private Record sorted; // se lo dichiaro dentro al sort va tutto a troie :<, però dato che lo usano entrambi i osrt ci sta che sia attributo Pog
public void insertionSort()  
    { 

        // Initialize sorted linked list 
        sorted = null;
        Record current = record; 
        // Traverse the given linked list and insert every 
        // Record to sorted 
        while (current != null)  
        { 
            // Store next for next iteration 
            Record next = current.next; 
            // insert current in sorted linked list 
            sortedInsert(current); 
            // Update current 
            current = next; 
        } 
        // Update head_ref to point to sorted linked list 
        record = sorted; 
    } 
  
    /* 
     * function to insert a new_Record in a list. Note that  
     * this function expects a pointer to head_ref as this 
     * can modify the head of the input linked list  
     * (similar to push()) 
     */
    private void sortedInsert(Record newRecord)  
    { 
        
        /* Special case for the head end */
        if (sorted == null || sorted.getKey().compareTo(newRecord.getKey()) >= 0 )  
        { 
            newRecord.next = sorted; 
            sorted = newRecord; 
        } 
        else 
        { 
            Record current = sorted; 
            /* Locate the Record before the point of insertion */
            while (current.next != null && current.next.getKey().compareTo(newRecord.getKey()) < 0 )  
            { 
                current = current.next; 
            } 
            newRecord.next = current.next; 
            current.next = newRecord; 
        } 
    } 
}