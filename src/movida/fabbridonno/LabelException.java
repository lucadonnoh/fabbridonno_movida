package movida.fabbridonno;

public class LabelException extends RuntimeException {

    private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "Le etichette delle proprieta di un film non sono corrette";
    }

}