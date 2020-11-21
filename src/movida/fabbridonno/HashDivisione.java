package movida.fabbridonno;

public class HashDivisione {

    public int h(Object k, int m) {
		return Math.abs(k.hashCode()) % m;
    }
}
