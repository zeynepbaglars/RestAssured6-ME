package GoRest;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.baseURI;

public class _07_GoRestCommentTest {
    // GoRest Comment ı API testini yapınız.


    Faker randomUreteci=new Faker();
    int commentID=0;
    RequestSpecification reqSpec;

    @BeforeClass
    public void setup(){

        baseURI="https://gorest.co.in/public/v2/comments";

        reqSpec = new RequestSpecBuilder()
                .addHeader("Authorization","Bearer f92bf3f56439b631d9ed928b3540968e747c8e75309c876420fb349cbb420ed1")
                .setContentType(ContentType.JSON)
                .build();
    }

    @Test
    public void createComment(){
//        {
//                "post_id": 79177,
//                "name": "Omana Kaur",
//                "email": "omana_kaur@crooks.example",
//                "body": "Pariatur et consectetur."
//        }

        String fullName= randomUreteci.name().fullName();
        String email= randomUreteci.internet().emailAddress();
        String body= randomUreteci.lorem().paragraph();

        Map<String,String> newComment=new HashMap<>();
        newComment.put("post_id", "82477");
        newComment.put("name", fullName);
        newComment.put("email", email);
        newComment.put("body", body);

        commentID=
        given()
                .spec(reqSpec)
                .body(newComment)
                .when()
                .post("")

                .then()
                .log().body()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .extract().path("id");
        ;
    }

    @Test(dependsOnMethods ="createComment" )
    public void getCommentById(){

        given()
                .spec(reqSpec)

                .when()
                .get(""+commentID)

                .then()
                .log().body()
                .contentType(ContentType.JSON)
                .body("id", equalTo(commentID))
        ;
    }


    @Test(dependsOnMethods ="getCommentById" )
    public void commentUpdate(){

        Map<String,String> updComment=new HashMap<>();
        updComment.put("name", "İsmet Temur");

        given()
                .spec(reqSpec)
                .body(updComment)
                .when()
                .put(""+commentID)

                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(commentID))
                .body("name", equalTo("İsmet Temur"))
        ;
    }

    @Test(dependsOnMethods ="commentUpdate" )
    public void deleteComment(){

        given()
                .spec(reqSpec)
                .when()
                .delete(""+commentID)

                .then()
                .log().body()
                .statusCode(204)
        ;
    }

    @Test(dependsOnMethods ="deleteComment" )
    public void deleteCommentNegative(){

        given()
                .spec(reqSpec)
                .when()
                .delete(""+commentID)

                .then()
                .log().body()
                .statusCode(404)
        ;
    }

}
