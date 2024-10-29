package com.hoffi.ai.ragdemo;

import static io.cucumber.core.options.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.core.options.Constants.PLUGIN_PROPERTY_NAME;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasspathResource("cucumber") // test/resources/cucumber
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.hoffi.ai.ragdemo")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "html:build/cucumber-reports.html")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "usage")
@IncludeEngines("cucumber")
class RunCucumberTests {
  @Test
  void testSuite() {
    File bddResourcesDirectory = new File("src/test/resources/cucumber");
    assertTrue(bddResourcesDirectory.exists());
  }
}
