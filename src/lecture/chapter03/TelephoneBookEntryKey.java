package lecture.chapter03;

public class TelephoneBookEntryKey implements IKey {

    private String surName;

    public TelephoneBookEntryKey(String surName) {
        this.surName = surName;
    }

    @Override
    public boolean matches(Object data) {
        if (!(data instanceof TelephoneBookEntry)) {
            return false;
        }
        TelephoneBookEntry entry = (TelephoneBookEntry) data;
        
        return surName != null && surName.equals(entry.getSurName());
    }
}