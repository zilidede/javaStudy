package zl.zlClass;

import edu.princeton.cs.algs4.StdOut;

public class zlDate {

	private final int month;
	private final int day;
	private final int year;

	public zlDate(int m, int d, int y) {
		month = m;
		day = d;
		year = y;
	}
	public zlDate(){
		month=11;
		day =11;
		year=2016;
	}

	/**
	 * Exercise 1.2.19
	 * @param date
	 */

	public zlDate(String date) {
		String[] fields = date.split("/");
		month = Integer.parseInt(fields[0]);
		day = Integer.parseInt(fields[1]);
		year = Integer.parseInt(fields[2]);
	}

	public int month() {
		return month;
	}

	public int day() {
		return day;
	}

	public int year() {
		return year;
	}

	@Override
	public String toString() {
		return month() + "/" + day() + "/" + year();
	}

	@Override
	public boolean equals(Object x) {
		if (this == x) {
			return true;
		}
		if (x == null) {
			return false;
		}
		if (this.getClass() != x.getClass()) {
			return false;
		}
		zlDate that = (zlDate) x;
		if (this.day != that.day) {
			return false;
		}
		if (this.month != that.month) {
			return false;
		}
		if (this.year != that.year) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		int m = Integer.parseInt(args[0]);
		int d = Integer.parseInt(args[1]);
		int y = Integer.parseInt(args[2]);
		zlDate date = new zlDate(m, d, y);
		StdOut.println(date);
	}
}
