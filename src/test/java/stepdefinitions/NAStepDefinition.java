package stepdefinitions;
    import io.cucumber.java.en.*;
    import workflows.SeleniumWorkFlow;
    import org.junit.Assert;
    import common.Assertion;
    public class NAStepDefinition
	{
        SeleniumWorkFlow workFlow = new SeleniumWorkFlow();
        

             @Given("^I have access to application$")			
            public void GivenIHaveAccessToApplication()
            {
                workFlow.accessToPage();
                
            }
    }