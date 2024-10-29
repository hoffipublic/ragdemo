package com.hoffi.ai.ragdemo;

import org.springframework.beans.factory.annotation.Autowired;

public class CucumberBaseScenario {
  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  protected CucumberContextHolder cucumberContextHolder;
}
