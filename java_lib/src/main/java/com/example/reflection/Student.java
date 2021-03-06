package com.example.reflection;

/**
 * 子类
 */
public class Student extends Person implements Examination {
    // 年级
    int mGrade;

    public Student(String aName) {
        super(aName);
    }

    public Student(int grade, String aName) {
        super(aName);
        mGrade = grade;
    }

    private void learn(String course) {
        System.out.println(mName + " learn " + course);
    }

    public void takeAnExamination() {
        System.out.println(" takeAnExamination ");
    }

    @Override
    public String toString() {
        return "Student{" +
                "mName=" + mName +
                "mGrade=" + mGrade +
                '}';
    }
}
