package com.hoffi.ai.ragdemo.rest;

import static io.restassured.RestAssured.given;

import com.hoffi.ai.ragdemo.testing.cucumber.CucumberBaseScenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class BaseControllerScenario extends CucumberBaseScenario {
  private final String ROOT_ENDPOINT = "http://localhost:8080";

  @Given("a running application - BaseController")
  public void a_running_application() {
  }

  @When("I call to the root path of the application - BaseController")
  public void I_call_to_the_root_path_of_the_application() {
    String path = ROOT_ENDPOINT + "/";
    cucumberContextHolder.setResponse(
        given()
            .when()
            .get(path));
  }

  @Then("the response should be fine - BaseController")
  public void the_BaseController_response_should_be_fine() {
    cucumberContextHolder.getResponse()
        .then().body(org.hamcrest.Matchers.equalTo("The Base Controller works!"));
  }
}
