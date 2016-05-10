import org.sql2o.*;
import java.util.*;


public class Student {
  private int id;
  private String name;
  private String date_of_enrollment;

  public Student (String name, String date_of_enrollment) {
    this.name = name;
    this.date_of_enrollment = date_of_enrollment;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDateOfEnrollment() {
    return date_of_enrollment;
  }

  public static List<Student>all() {
    String sql = "SELECT id, name, date_of_enrollment FROM students";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Student.class);
    }
  }
  @Override
  public boolean equals(Object otherStudent) {
    if (!(otherStudent instanceof Student)) {
      return false;
    } else {
      Student newStudent =  (Student) otherStudent;
      return this.getName().equals(newStudent.getName()) &&
      this.getId() == newStudent.getId();
    }
  }
  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO students (name, date_of_enrollment) VALUES (:name, :date_of_enrollment);";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.name)
      .addParameter("date_of_enrollment", this.date_of_enrollment)
      .executeUpdate()
      .getKey();
    }
  }

  public static Student find (int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM students where id=:id";
      Student student = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Student.class);
      return student;
    }
  }
  public List<Course> getCourse() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT student_id FROM students_courses WHERE student_id= :student_id";
      List<Integer> courseIds = con.createQuery(joinQuery)
        .addParameter("student_id", this.id)
        .executeAndFetch(Integer.class);

        List<Course> courses = new ArrayList<Course>();

        for (Integer courseId : courseIds) {
          String courseQuery = "SELECT * FROM courses WHERE id = :courseId";
          Course course = con.createQuery(courseQuery)
          .addParameter("courseId", courseId)
          .executeAndFetchFirst(Course.class);
          courses.add(course);
        }
        return courses;
    }
  }


}
