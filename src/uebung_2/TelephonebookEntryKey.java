package uebung_2;

import kapitel_3.vl.ds.IKey;

public class TelephonebookEntryKey implements IKey {
	private String name = null;
	private String surName = null;
	
	public TelephonebookEntryKey(String name, String surName) {
		this.name = name;
		this.surName = surName;
	}
	
	public boolean equals(Object data) {
		TelephonebookEntry te = (TelephonebookEntry) data;
		return te.name().equals(name) && te.surName().equals(surName);
	}
}
