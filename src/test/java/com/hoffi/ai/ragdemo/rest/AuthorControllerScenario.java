package com.hoffi.ai.ragdemo.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.hoffi.ai.ragdemo.dbmodel.Author;
import com.hoffi.ai.ragdemo.dbrepository.AuthorRepository;
import com.hoffi.ai.ragdemo.testing.cucumber.CucumberBaseScenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class AuthorControllerScenario extends CucumberBaseScenario {
  private String AUTHOR_ENDPOINT = "http://localhost:8080/authors";

  private Author.AuthorBuilder authorBuilder = Author.builder();

  @Autowired
  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  private AuthorRepository authorRepository;

  @Given("a username {string} - AuthorController")
  public void a_username(String username) {
    authorBuilder.username(username);
  }

  @Given("an email {string} - AuthorController")
  public void an_email(String email) {
    authorBuilder.email(email);
  }

  @Given("a bio {string} - AuthorController")
  public void a_bio(String bio) {
    authorBuilder.bio(bio);
  }

  @When("I submit this information to save a new user - AuthorController")
  public void i_submit_this_information_to_save_a_new_user() {
    String path = AUTHOR_ENDPOINT;
    Author toBeInsertedAuthor = authorBuilder.build();
    cucumberContextHolder.setResponse(
        given()
            .body(toBeInsertedAuthor)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .when().post(path));
  }

  @Then("I receive a correct response - AuthorController")
  public void I_receive_a_correct_response() {
    Author insertedAuthor = cucumberContextHolder.getResponse()
        .then().assertThat()
        .statusCode(HttpStatus.OK.value())
        .body("id", equalTo(5))
        .body("username", equalTo("federico"))
        .extract().as(Author.class);
    assertEquals(5, insertedAuthor.getId());
    // cleanup for next tests
    //cucumberContextHolder.reset();
    authorRepository.delete(insertedAuthor);
    authorBuilder = Author.builder();
    assertEquals(4, authorRepository.count());
  }
}
