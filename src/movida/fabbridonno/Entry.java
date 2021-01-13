package movida.fabbridonno;

import java.util.AbstractMap;
import movida.commons.Person;

public class Entry extends AbstractMap.SimpleEntry<Person, Double> {

    private static final long serialVersionUID = 1L;

    public Entry(Person key, Double value) {
        super(key, value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj instanceof Entry)) {
            Entry other = (Entry) obj;
            return this.getKey().equals(other.getKey());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.getKey().hashCode();
    }
}
