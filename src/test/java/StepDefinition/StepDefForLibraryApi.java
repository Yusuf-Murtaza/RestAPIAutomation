package StepDefinition;

import Pojo.AddBook;
import Resources.APIResources;
import Resources.TestDataBuild;
import Resources.Utils;
import io.cucumber.java.en.*;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
//import org.junit.Assert;

import static org.junit.Assert.*;
import java.io.IOException;

import static io.restassured.RestAssured.given;


public class StepDefForLibraryApi extends Utils {

    RequestSpecification res;
    ResponseSpecification resspec;
    Response response;
    TestDataBuild data = new TestDataBuild();
    static String fetchedID;
    @Given("^Add Book payload$")
    public void add_book_payload() throws IOException {

     //resspec= new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

       res= given().spec(requestSpecification()).body(data.addBookPayload());

        System.out.println("Assert passed for --> Add Book payload");
    }

    @When("User calls {string} with {string} HTTP method")
    public void user_calls_with_http_method(String resource, String httpMethod) {

        APIResources resourceAPI = APIResources.valueOf(resource);  //Constructor will be called from APIResources when valueOf is invoked
        System.out.println(resourceAPI.getResource());
        resspec= new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        if (httpMethod.equalsIgnoreCase("POST") && resourceAPI.getResource().contains("Addbook")) {
            System.out.println("I am in Add book POST method");
            response = res.when().post(resourceAPI.getResource());
            //      .then().spec(resspec).extract().response();
        }
        else if (httpMethod.equalsIgnoreCase("GET") && resourceAPI.getResource().contains("GetBook")) {
            System.out.println("I am in Get book GET method");
            response = res.when().get(resourceAPI.getResource());
            //     .then().spec(resspec).extract().response();
        }
        else if (httpMethod.equalsIgnoreCase("POST") && resourceAPI.getResource().contains("DeleteBook")) {
            System.out.println("I am in Delete book POST method");
            response = res.when().post(resourceAPI.getResource())
                .then().spec(resspec).extract().response();
        }
    }

    @Then("Api call is success with status code 200 ok")
    public void api_call_is_success_with_status_code() {
        assertEquals(response.getStatusCode(),200);

        //assertEquals(response.getStatusCode(),200);
        System.out.println("Assert passed for --> Api call is success with status code");

    }

    @Then("The {string} in response body is {string} displayed")
    public void the_in_response_body_is_displayed(String key, String value) {

        System.out.println("Response key-msg is --> "+getJsonPath(response,key));
        assertEquals(getJsonPath(response,key),value);
        System.out.println("Assert passed for --> Key and Value matched");
    }

    @Then("Verify the ID created above gets fetched with {string} using {string} method")
    public void verify_the_id_created_above_gets_fetched_with_using_method(String resp, String method) throws IOException {

        fetchedID = getJsonPath(response,"ID");
        System.out.println("Fetched ID is --> "+fetchedID);
        res= given().spec(requestSpecification()).queryParam("ID", fetchedID);
        user_calls_with_http_method(resp,method);

        String bookName = getJsonPath(response,"book_name");
        System.out.println("Book name is --> "+bookName);
        assertTrue(bookName.contains("Rest Assured Automation"));
        //assertEquals(bookName,"[Rest Assured Automation]");
        System.out.println("Assert passed and Book Name matched");

    }

    @Given("Delete book payload")
    public void delete_book_payload() throws IOException {

        res=given().spec(requestSpecification()).body(data.deleteBookPayload(fetchedID));

        System.out.println("I am in Delete Book payload");

    }

    @And("Delete message is successful")
    public void delete_message_is_successful() {

        String resp= response.asString();
        //JsonPath js = new JsonPath(resp);
        System.out.println("Response delete-msg is --> "+getJsonPath(response,"msg"));
        assertEquals(getJsonPath(response,"msg"),"book is successfully deleted");
        System.out.println("Assert passed for --> Status is response body is --> book is successfully deleted");

    }

}
