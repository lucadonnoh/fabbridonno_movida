package movida.commons;

import java.util.ArrayList;

public class Collaboration {

	Person actorA;
	Person actorB;
	ArrayList<Movie> movies;

	static public boolean areEquivalent(Collaboration c1, Collaboration c2) {
		return (c1.getActorA().toString().equals(c2.getActorA().toString()) && c1.getActorB().toString().equals(c2.getActorB().toString()));
	}

	public Collaboration(Person actorA, Person actorB) {
		this.actorA = actorA;
		this.actorB = actorB;
		this.movies = new ArrayList<Movie>();
	}

	public void addMovie(Movie m) {
		movies.add(m);
	}

	public Person getActorA() {
		return actorA;
	}

	public Person getActorB() {
		return actorB;
	}

	public Double getScore(){

		Double score = 0.0;

		for (Movie m : movies)
			score += m.getVotes();

		return score / movies.size();
	}

	public ArrayList<Movie> getMovies() {
		return movies;
	}

}
