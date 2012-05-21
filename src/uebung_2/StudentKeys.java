package uebung_2;

import kapitel_3.vl.ds.IKey;

public class StudentKeys {
	public static class SurNameKey implements IKey {
		String surName;
		SurNameKey(String surName) {
			this.surName = surName;
		}
		public boolean equals(Object data) {
			return surName == ((Student) data).surName;
		}
	}
	
	public static class MatrNrKey implements IKey {
		String matrNr;
		MatrNrKey(String matrNr) {
			this.matrNr = matrNr;
		}
		public boolean equals(Object data) {
			return matrNr == ((Student) data).matrNr;
		}
	}
}
