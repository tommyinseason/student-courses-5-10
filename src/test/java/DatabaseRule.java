import org.sql2o.*;
import org.junit.rules.ExternalResource;

public class DatabaseRule extends ExternalResource {

  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/student", null, null);
  }


  @Override
  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteStudentsQuery = "DELETE FROM students *;";
      String deleteCoursesQuery = "DELETE FROM courses *;";
      String deleteStudentsCoursesQuery = "DELETE FROM students_courses *;";
      con.createQuery(deleteStudentsQuery).executeUpdate();
      con.createQuery(deleteCoursesQuery).executeUpdate();
      con.createQuery(deleteStudentsCoursesQuery).executeUpdate();
    }
  }
}
