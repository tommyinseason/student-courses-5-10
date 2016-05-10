import java.sql.Timestamp;
import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;

public class StudentTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/student_test", null, null);
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String deleteCourseQuery = "DELETE FROM courses *;";
      String deleteStudentQuery = "DELETE FROM students *;";
      con.createQuery(deleteCourseQuery).executeUpdate();
      con.createQuery(deleteStudentQuery).executeUpdate();
    }
  }

  @Test
  public void Student_instantiatesCorrectly_true() {
    Student myStudent = new Student("Tom", "07-06-1985");
    assertEquals(true, myStudent instanceof Student);
  }

  @Test
  public void getName_studentInstantiatesWithName_String() {
    Student myStudent = new Student("Tom", "07-06-1985");
    assertEquals("Tom", myStudent.getName());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Student.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Student firstStudent = new Student("Tom", "07-06-1985");
    Student secondStudent = new Student("Tom", "07-06-1985");
    assertTrue(firstStudent.equals(secondStudent));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Student myStudent = new Student("Tom", "07-06-1985");
    myStudent.save();
    assertTrue(Student.all().get(0).equals(myStudent));
  }
  @Test
  public void save_assignsIdToObject() {
    Student myStudent = new Student("Tom", "07-06-1985");
    myStudent.save();
    Student savedStudent = Student.all().get(0);
    assertEquals(myStudent.getId(), savedStudent.getId());
  }
  @Test
  public void find_findStudentInDatabase_true() {
    Student myStudent = new Student("Tom", "07-06-1985");
    myStudent.save();
    Student savedStudent = Student.find(myStudent.getId());
    assertTrue(myStudent.equals(savedStudent));
  }

  @Test
  public void addCourse_addsCourseToStudent_true() {
    Student myStudent = new Student("Tom", "07-06-1985");
    myStudent.save();
    Course myCourse = new Course("History", "100");
    myCourse.save();
    myStudent.addCourse(myCourse);
    Course savedCourse = myStudent.getCourses().get(0);
    assertTrue(myCourse.equals(savedCourse));
  }

  // @Test
  // public void getCourses_returnsAllCourses_List() {
  //   Student myStudent = new Student("Tom", "07-06-1985");
  //   myStudent.save();
  //   Course myCourse = new Course("History", "100");
  //   myCourse.save();
  //   myStudent.addCourse(myCourse);
  //   List savedCourses = myStudent.getCourses();
  //   assertEquals(1, savedCourses.size());
  // }
  //
  // @Test
  // public void delete_deletesAllCoursesAndCategoriesAssociations() {
  //   Student myStudent = new Student("Tom", "07-06-1985");
  //   myStudent.save();
  //   Course myCourse = new Course("History", "100");
  //   myCourse.save();
  //   myStudent.addCourse(myCourse);
  //   myStudent.delete();
  //   assertEquals(0, myCourse.getCategories().size());
  // }
}
