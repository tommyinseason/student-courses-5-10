import org.sql2o.*;
import java.util.*;

public class Course {
  private int id;
  private String course_name;
  private String course_number;

  public Course (String course_course_name, String course_number) {
    this.course_name = course_name;
    this.course_number = course_number;
  }

  public int getId() {
    return id;
  }

  public String getCourseName() {
    return course_name;
  }

  public String getCourseNumber() {
    return course_number;
  }

  public static List<Course>all() {
    String sql = "SELECT id, course_name, course_number FROM courses";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Course.class);
    }
  }
  @Override
  public boolean equals(Object otherCourse) {
    if (!(otherCourse instanceof Course)) {
      return false;
    } else {
      Course newCourse =  (Course) otherCourse;
      return this.getCourseName().equals(newCourse.getCourseName()) &&
      this.getId() == newCourse.getId();
    }
  }
  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO courses (course_name, course_number) VALUES (:course_name, :course_number);";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("course_name", this.course_name)
      .addParameter("course_number", this.course_number)
      .executeUpdate()
      .getKey();
    }
  }

  public static Course find (int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM courses where id=:id";
      Course student = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Course.class);
      return student;
    }
  }
}
