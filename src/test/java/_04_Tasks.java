import Model.ToDo;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class _04_Tasks {

    /**
     * Task 1
     * create a request to https://jsonplaceholder.typicode.com/todos/2
     * expect status 200
     * expect content type JSON
     * expect title in response body to be "quis ut nam facilis et officia qui"
     */

    @Test
    public void task1() {

        given()

                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")

                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("title", equalTo("quis ut nam facilis et officia qui"))
        ;

    }

    /**
     * Task 2
     * create a request to https://jsonplaceholder.typicode.com/todos/2
     * expect status 200
     * expect content type JSON
     *a) expect response completed status to be false(hamcrest)
     *b) extract completed field and testNG assertion(testNG)
     */

    @Test
    public void task2(){
        // a)
        given()

                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")

                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("completed", equalTo(false))
        ;

        // b)
        boolean completed=
        given()

                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")

                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().path("completed")
        ;
        Assert.assertFalse(completed);
    }

    /** Task 3
     * create a request to https://jsonplaceholder.typicode.com/todos/2
     * expect status 200
     * Converting Into POJO
     */

    @Test
    public void task3() {

        ToDo todo=
        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")

                .then()
                .statusCode(200)
                .extract().body().as(ToDo.class)
        ;

        System.out.println("todo = " + todo);
    }





}
