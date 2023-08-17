package stepdefinitions;
    import io.cucumber.java.en.*;
    import workflows.SeleniumWorkFlow;
    import org.junit.Assert;
    import common.Assertion;
    public class CurrentScreenStepDefinition
	{
        SeleniumWorkFlow workFlow = new SeleniumWorkFlow();
        

            @When("^I clicked Search in login page$")			
            public void WhenIClickedSearchInLoginPage()
            {
                workFlow.clickedElement(0,"Search","Current Screen.SearchButtonXPATH","XPATH");
                
            }

            @When("^I clicked Seach icon in home screen$")			
            public void WhenIClickedSeachIconInHomeScreen()
            {
                workFlow.clickedElement(0,"Seach icon","Current Screen.SeachiconButtonXPATH","XPATH");
                
            }

             @Then("^'(.*)' is displayed with '(.*)'$")			
            public void ThenpageIsDisplayedWithcontent(String  page, String content)
            {
                Assertion.IsTrue(workFlow.VerifyDefaultpageIsdisplayed(page), "Then '<page>' is displayed with '<content>'");
                Assertion.IsTrue(workFlow.VerifymessageIsDisplayed(content), "");;
                Assertion.assertAll();
            }

            @When("^I clicked Price high to low in discription page$")			
            public void WhenIClickedPriceHighToLowInDiscriptionPage()
            {
                workFlow.clickedElement(0,"Price high to low","Current Screen.PricehightolowButtonXPATH","XPATH");
                
            }

            @When("^I clicked Price low to high in discription page$")			
            public void WhenIClickedPriceLowToHighInDiscriptionPage()
            {
                workFlow.clickedElement(0,"Price low to high","Current Screen.PricelowtohighButtonXPATH","XPATH");
                
            }
    }