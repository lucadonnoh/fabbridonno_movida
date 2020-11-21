package movida.fabbridonno;

public class ScansioneQuadratica {

    public int s(int i, int hk, int m) {
        double c1 = 0.5, c2 = 0.5;
		return (int)Math.floor(hk + c1*i + c2*i*i) % m;
	}
}
