package MainTest;

import java.util.ArrayList;
import java.util.List;
import io.restassured.path.json.JsonPath;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;

public class TestClass {
    @BeforeClass
    public static void setUp() {
        // First step - set up the base URI
        baseURI = "http://dummy.restapiexample.com";
    }

    @Test
    public void getAllEmployees() {
        // Option 1
        given(). // Not mandatory
                when().
                get("/api/v1/employees").
                then().
                assertThat().statusCode(200).extract().body().jsonPath().prettyPrint();

        // Option 2
//        String response = when().
//                                get("/api/v1/employees").
//                          then().
//                                assertThat().statusCode(200).extract().body().asString();
//
//        System.out.println(response);
    }

    @Test
    public void getAllEmployeesSalaries() {
        // Option 1

        final List<Object> employeeSalary = when().
                get("/api/v1/employees").
                then().
                assertThat().statusCode(200).
                extract().body().jsonPath().getList("employee_salary");

        List<Double> employeeSalariesDouble = new ArrayList<Double>();

        employeeSalary.forEach(System.out::println);

        employeeSalary.forEach(s -> employeeSalariesDouble.add(Double.parseDouble(s.toString())));

//        employeeSalariesDouble.forEach(System.out::println);
//
        // Print distinct values
//        employeeSalariesDouble.stream()
//                .distinct()
//                .forEach(System.out::println);


        // filter salaries < 1000 and distinct
        employeeSalariesDouble.stream()
                .distinct()
                .filter(s -> s < 15000 && s > 0)
                .forEach(System.out::println);

//         sum all employees salaries which is less than 1000
        double salarySum = employeeSalariesDouble.stream()
                .distinct()
                .filter(d -> d < 15000 && d > 0)
                .mapToDouble(Double::doubleValue).sum();

        System.out.println(salarySum);

//        List<Double> myList = new ArrayList<>();
//        myList.add(20000d);
//        myList.add(30000d);
//        myList.add(40000d);
//
//
//       double sum = myList.stream().
//               filter(s -> s >= 30000).
//               mapToDouble(Double::doubleValue).sum();
//        System.out.println(sum);
    }

    @Test
    public void getEmployeesById() {
        final JsonPath jsonPath = given().
                when()
                .get("/api/v1/employee/{id}", 4085).
                        then()
                .assertThat().statusCode(200)
                .extract().body().jsonPath();

        jsonPath.prettyPrint();
        String employeeSalary = jsonPath.get("employee_salary");
        System.out.println("The salary is " + employeeSalary);
    }

    @Test
    public void createAnEmployee() {
        Employee employee = new Employee("Yevhen", 15000.0, 30, 4087);
        final JsonPath jsonPath =
                given()
                        .body(employee).
                        when()
                        .post("api/v1/create").
                        then()
                        .assertThat().statusCode(200)
                        .extract().body().jsonPath();
       // jsonPath.prettyPrint();
    }

    @Test
    public void updateAnEmployee() {
        Employee employee = new Employee("YevhenT", 17000.0, 30, 4086);
        final JsonPath jsonPath =
                given()
                        .body(employee.toString()).
                        when().
                        put("api/v1/update/{id}", 4086).
                        then()
                        .assertThat().statusCode(200)
                        .extract().body().jsonPath();
        jsonPath.prettyPrint();
    }

    @Test
    public void deleteAnEmployee() {
        final JsonPath jsonPath =
                when().
                        get("/delete/{id}", 4086).
                        then()
                        .assertThat().statusCode(200)
                        .extract().body().jsonPath();
        jsonPath.prettyPrint();
    }

}
