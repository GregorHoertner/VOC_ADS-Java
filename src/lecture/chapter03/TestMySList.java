package lecture.chapter03;

import lecture.chapter03.tests.Student;

public class TestMySList {

    public static void main(String[] args) {

        MySList liste = new MySList();

        // Students anlegen
        Student s1 = new Student("Anna", "Müller", "1234");
        Student s2 = new Student("Max", "Huber", "2345");
        Student s3 = new Student("Lena", "Koch", "3456");
        Student s4 = new Student("Tim", "Huber", "4567");   // zweiter Huber für searchAll

        // Aufgabe 1: append testen
        liste.append(s1);
        liste.append(s3);
        liste.append(s4);

        System.out.println("Liste nach append:");
        printList(liste);
        System.out.println();

        // Aufgabe 2: insert hinter s1
        boolean ok = liste.insert(s1, s2);
        System.out.println("insert(s1, s2) = " + ok);
        System.out.println("Studentenliste nach insert:");
        printList(liste);
        System.out.println();

        // Aufgabe 3: searchAll für Studenten (Huber)
        StudentSurnameKey studentKey = new StudentSurnameKey("Huber");
        SList hubers = liste.searchAll(studentKey);

        System.out.println("Treffer Studenten für Nachname Huber");
        printList(hubers);
        System.out.println();

        // Aufgabe 4: Telefonbuch
        MySList telList = new MySList();

        TelephoneBookEntry t1 = new TelephoneBookEntry("Anna", "Müller", "0664 111111");
        TelephoneBookEntry t2 = new TelephoneBookEntry("Bernd", "Müller", "0664 222222");
        TelephoneBookEntry t3 = new TelephoneBookEntry("Clara", "Huber", "0676 333333");
        TelephoneBookEntry t4 = new TelephoneBookEntry("David", "Schmidt", "0650 444444");

        telList.append(t1);
        telList.append(t2);
        telList.append(t3);
        telList.append(t4);

        System.out.println("Telefonbuch gesamt:");
        printList(telList);
        System.out.println();

        TelephoneBookEntryKey telKey = new TelephoneBookEntryKey("Müller");
        SList muellers = telList.searchAll(telKey);

        System.out.println("Telefonbuch: Treffer für Müller");
        printList(muellers);
        System.out.println();
    }

    private static void printList(SList liste) {
        IFIterator it = liste.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
            System.out.println("-----");
        }
    }
}