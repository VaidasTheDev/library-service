import configuration.SpringIntegration;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/integration/resources/features")
public class IntegrationTests extends SpringIntegration {

}
