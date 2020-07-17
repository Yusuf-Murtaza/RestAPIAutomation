package StepDefinition;

import io.cucumber.java.Before;

import java.io.IOException;

public class Hooks {
    @Before("@DeleteBook")
    public void beforeScenario() throws IOException {

        StepDefForLibraryApi m = new StepDefForLibraryApi();
        if(StepDefForLibraryApi.fetchedID ==null){

            m.add_book_payload();
            m.user_calls_with_http_method("AddBookAPI", "POST");
            m.verify_the_id_created_above_gets_fetched_with_using_method("GetBookAPI", "GET");

        }
    }
}
