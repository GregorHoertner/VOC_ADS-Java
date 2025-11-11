package lecture.chapter03;

import lecture.chapter03.tests.Student;

public class StudentSurnameKey implements IKey {

    private String surname;

    public StudentSurnameKey(String surname) {
        this.surname = surname;
    }

    @Override
    public boolean matches(Object data) {
        if (!(data instanceof Student)) {
            return false;
        }
        Student s = (Student) data;
        return surname.equals(s.getSurName());
    }
}