Feature: Library API validation using Rest Assured API

  @AddBook @Regression
  Scenario: Verify if the user is able to add book correctly

    Given Add Book payload
    When User calls "AddBookAPI" with "POST" HTTP method
    Then Api call is success with status code 200 ok
    Then The "Msg" in response body is "successfully added" displayed
    And Verify the ID created above gets fetched with "GetBookAPI" using "GET" method

  @DeleteBook @Regression
  Scenario: Verify if delete book functionality is working fine or not

    Given Delete book payload
    When User calls "DeleteBookAPI" with "POST" HTTP method
    Then Api call is success with status code 200 ok
    #And Delete message is successful
    Then The "msg" in response body is "book is successfully deleted" displayed
    #And "Status" in response body is "OK"