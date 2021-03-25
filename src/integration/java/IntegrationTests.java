import configuration.SpringIntegration;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/integration/resources/features")
@ActiveProfiles("test")
public class IntegrationTests extends SpringIntegration {

}
