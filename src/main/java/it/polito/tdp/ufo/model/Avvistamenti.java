package it.polito.tdp.ufo.model;


public class Avvistamenti implements Comparable <Avvistamenti> {
	private int avvistamenti;
	private int year;
	/**
	 * @param anno
	 * @param year
	 */
	public Avvistamenti(int avvistamenti, int year) {
		super();
		this.avvistamenti = avvistamenti;
		this.year = year;
	}
	public int getAvvistamenti() {
		return avvistamenti;
	}
	public void setAvvistamenti(int avvistamenti) {
		this.avvistamenti = avvistamenti;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	@Override
	public String toString() {
		return String.format("Anno: %d. # Avvistamenti: %d", year, avvistamenti);
	}
	@Override
	public int compareTo(Avvistamenti o) {
		return this.year-o.getYear();
	}
	
	

}
