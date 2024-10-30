package com.hoffi.ai.ragdemo.testing.cucumber;

import org.springframework.beans.factory.annotation.Autowired;

public class CucumberBaseScenario {
  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  protected CucumberContextHolder cucumberContextHolder;
}
