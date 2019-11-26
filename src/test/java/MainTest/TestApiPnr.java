package MainTest;

import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class TestApiPnr {
    @BeforeClass
    public static void setUp() {
        // First step - set up the base URI
       // baseURI = "http://dummy.restapiexample.com";
        baseURI = "https://apiservice-qa.api.local.net:5002";
    }

    @Test
    public void getAllCountries() {
        // Option 1
        given(). // Not mandatory
                when().
                get("/api/dct/Countries/all").
                then().
                assertThat().statusCode(200).extract().body().jsonPath().prettyPrint();

        // Option 2
//        String response = when().
//                                get("/api/dct/Countries/all").
//                          then().
//                                assertThat().statusCode(200).extract().body().asString();
//
//        System.out.println(response);
    }

}
