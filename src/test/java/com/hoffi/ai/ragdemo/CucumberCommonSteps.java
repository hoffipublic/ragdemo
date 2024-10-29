package com.hoffi.ai.ragdemo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

public class CucumberCommonSteps {

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  private CucumberContextHolder cucumberContext;

  @Then("common status 200")
  public void i_receive_a_correct_response() {
    System.out.println(cucumberContext.getResponse().asPrettyString());
    assertEquals(200, cucumberContext.getResponse().getStatusCode());
  }

  @Then("common status NOT 200")
  public void i_receive_an_error() {
    System.out.println(cucumberContext.getResponse().asPrettyString());
    assertNotEquals(200, cucumberContext.getResponse().getStatusCode());
  }
}
