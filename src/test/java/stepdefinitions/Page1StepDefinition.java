package stepdefinitions;
    import io.cucumber.java.en.*;
    import workflows.SeleniumWorkFlow;
    import org.junit.Assert;
    import common.Assertion;
    public class Page1StepDefinition
	{
        SeleniumWorkFlow workFlow = new SeleniumWorkFlow();
        

            @When("^I entered Brand name in login page as '(.*)'$")			
            public void WhenIEnteredBrandNameInLoginPageAsbrandName1(String  brandName1)
            {
                workFlow.enterText(brandName1,0,"Brand name","Page1.BrandnameTextBoxXPATH","XPATH");
                
            }

            @When("^I entered Product names in home screen as '(.*)'$")			
            public void WhenIEnteredProductNamesInHomeScreenAsproductNames2(String  productNames2)
            {
                workFlow.enterText(productNames2,0,"Product names","Page1.ProductnamesTextBoxXPATH","XPATH");
                
            }

             @Then("^verify displayed Product page in home screen$")			
            public void ThenVerifyDisplayedProductPageInHomeScreen()
            {
                Assertion.IsTrue(workFlow.verifyTextInLink(0,"Product page","Page1.ProductpageLabelXPATH","XPATH"), "Then verify displayed Product page in home screen");
                
            }

             @Then("^verify displayed Product list in product listing page$")			
            public void ThenVerifyDisplayedProductListInProductListingPage()
            {
                Assertion.IsTrue(workFlow.verifyTextInLink(0,"Product list","Page1.ProductlistLabelXPATH","XPATH"), "Then verify displayed Product list in product listing page");
                
            }

            @When("^I copied number Price in product listing page$")			
            public void WhenICopiedNumberPriceInProductListingPage()
            {
                workFlow.copiedNumber(0,"Price","Page1.PriceLabelXPATH","XPATH");
                
            }

            @When("^I clicked Product image in product listing page$")			
            public void WhenIClickedProductImageInProductListingPage()
            {
                workFlow.defaultWorkFlowMethod();
                
            }

             @Then("^verify copied number Discription price in discription page$")			
            public void ThenVerifyCopiedNumberDiscriptionPriceInDiscriptionPage()
            {
                Assertion.IsTrue(workFlow.verifyCopiedNumber(0,"Discription price","Page1.DiscriptionpriceLabelXPATH","XPATH"), "Then verify copied number Discription price in discription page");
                
            }

            @When("^I mousehover Sort by in discription page$")			
            public void WhenIMousehoverSortByInDiscriptionPage()
            {
                workFlow.mouseHover(0,"Sort by","Page1.SortbyMouseXPATH","XPATH");
                
            }

             @Then("^verify high to low Cost in discription page$")			
            public void ThenVerifyHighToLowCostInDiscriptionPage()
            {
                Assertion.IsTrue(workFlow.verifyHighToLow(0,"Cost","Page1.CostLabelXPATH","XPATH"), "Then verify high to low Cost in discription page");
                
            }

             @Then("^verify low to high low cost in discription page$")			
            public void ThenVerifyLowToHighLowCostInDiscriptionPage()
            {
                Assertion.IsTrue(workFlow.verifyLowToHigh(0,"low cost","Page1.lowcostLabelXPATH","XPATH"), "Then verify low to high low cost in discription page");
                
            }
    }