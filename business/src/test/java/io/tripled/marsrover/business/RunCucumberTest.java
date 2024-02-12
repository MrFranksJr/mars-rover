package io.tripled.marsrover.business;

import io.cucumber.core.options.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.core.options.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("io/tripled/marsrover")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "io.tripled.marsrover")
@ConfigurationParameter(key = Constants.FEATURES_PROPERTY_NAME, value = "./src/test/resources/io.tripled.marsrover")
public class RunCucumberTest {
}
