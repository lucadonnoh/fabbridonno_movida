/*
 * Copyright (C) 2020 - Angelo Di Iorio
 *
 * Progetto Movida.
 * Corso di Algoritmi e Strutture Dati
 * Laurea in Informatica, UniBO, a.a. 2019/2020
 *
*/
package movida.commons;

/**
 * Classe usata per rappresentare una persona, attore o regista,
 * nell'applicazione Movida.
 *
 * Una persona � identificata in modo univoco dal nome
 * case-insensitive, senza spazi iniziali e finali, senza spazi doppi.
 *
 * Semplificazione: <code>name</code> � usato per memorizzare il nome completo (nome e cognome)
 *
 * La classe pu� essere modicata o estesa ma deve implementare il metodo getName().
 *
 */
public class Person implements Comparable<Person> {

	private String name;

	public Person(String name) {
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public int compareTo(Person p)
	{
		return this.name.compareTo(p.getName());
	}

	@Override
	public boolean equals(Object o)
	{
		if(o == null || !(o instanceof Person))
		{
			return false;
		}

		final Person p = (Person)o;
		if(this.name.compareTo(p.getName()) != 0) return false;
		return true;
	}

	@Override
	public int hashCode() {
		return this.name.hashCode();
	}

	@Override
	public String toString() {
		return this.name;
	}

}
