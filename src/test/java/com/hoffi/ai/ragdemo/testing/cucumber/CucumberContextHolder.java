package com.hoffi.ai.ragdemo.testing.cucumber;

import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/** used to keep the response from REST Assured calls */
@Component
@Getter
@Setter
public class CucumberContextHolder {
  private Response response;

  public void reset() {
    response = null;
  }
}
