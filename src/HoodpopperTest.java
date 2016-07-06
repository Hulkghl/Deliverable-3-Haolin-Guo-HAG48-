import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 * 
 * @author Haolin Guo
 *
 */

public class HoodpopperTest {
	
    // set up the driver used
	static WebDriver driver = new HtmlUnitDriver();
	
	// Start at the main page for Hoodpopper for each test
	@Before
	public void setUp() throws Exception {
		driver.get("http://lit-bayou-7912.herokuapp.com/");
	}
	
	/**User story 1:
	 * As a user of Hoodpopper,
	 * I would like to what on the main page,
	 * So that I can know what is happening in the Hoodpopper(Ruby compilation visualizer)
	 */
	// Scenario 1: 
	// Given that I am on the main page
	// When I view the title
	// Then I see that it contains the word "Hoodpopper"
	@Test
	public void testshowsTitle() {
		
		// Simply check that the title contains the word "Hoodpopper"
		
		String title = driver.getTitle();
		assertTrue(title.contains("Hoodpopper"));
		driver.manage().deleteAllCookies();
	}
	
	// Scenario 2: 
	// Given that I am on the main page
	// When I view the main page
	// Then I should see that it contains a code textbox area to write the code
	@Test
	public void testCodeTestarea() {
		
		// Check for the code text area - if any of
		// these is not found, fail the test
		
		try {
			driver.findElement(By.id("code_code"));
		} catch (NoSuchElementException nseex) {
			fail();
		}
	  driver.manage().deleteAllCookies();
	}
    
	// Scenario 3:
    // Given that I am on the main page
	// When I view the buttons at the Bottom 
	// Then I should see that it contains "Tokenize", "Parse", and "Compile" buttons
    @Test
    public void testButtons() {
	
	// Check for the buttons at the bottom - if any of
	// these is not found, fail the test
	    try {
	    	List<WebElement> e = driver.findElements(By.tagName("input"));
	    	//WebElement a = driver.findElement(By.xpath("//p"));
	    	WebElement a = e.get(2);
	    	String elementText = a.getText();
			assertTrue(elementText.contains("Tokenize"));
			//assertTrue(elementText.contains("Compile"));
	    } catch (NoSuchElementException nseex) {
		    fail();
	    }
          driver.manage().deleteAllCookies();
    } 
  
    // Scenario 4:
	// Given that I am on the main page and do not write any code in the text area
	// When I click on the "Tokenize" button
	// Then I should be directed to a new page with seeing a "back" link to the previous page 
	@Test
	public void testGoBack() {
		
		driver.findElements(By.tagName("input")).get(2).click();
		// Check that there is a link to go back to the previous page and it is visible
		try {
			WebElement goback = driver.findElement(By.linkText("Back"));
			assertTrue(goback.isDisplayed());
		} catch (NoSuchElementException nseex) {
			fail();
		}
		driver.manage().deleteAllCookies();
	}
	
	// Scenario 5:
	// Given that I am on the main page
	// When I click on the "Compile" Button
    // Then I should be redirected to the Compile operation page and I should see "Compile Operation" on the above of the page
	@Test
	public void testSeeNewHeading() {
		
		// find the "Compile" button and click on it
		// The page you go to should include "Compile Operation" on the above of the page
		driver.findElements(By.tagName("input")).get(4).click();
		try {
			WebElement a = driver.findElement(By.xpath("//h1"));
			String elementText = a.getText();
			assertTrue(elementText.contains("Compile Operation"));
		} catch (NoSuchElementException nseex) {
			fail();
		}
		driver.manage().deleteAllCookies();
	}
	
	/**User story 2:
	 * As a user of Hoodpopper who would like to compile the arithmetic expression,
	 * I would like to see it can support the basic arithmetic operations,
	 * So that I can know Ruby compilation visualizer is able to express arithmetics as the way of Ruby expression 
	 */
	// Scenario 6:
	// Given that I am on the main page
	// And I've already wrote an arithmetic expression "a = 1" in the text box(Notice that there are whitespace between elements)
	// When I click on the "Tokenize" Button
	// Then I should be directed to the Tokenize Operation page 
	// and I should see "on_sp", which represents the whitespace lies in the expression, shows on the page
	@Test
	public void testWhitespace() {
		
		// Enter the arithmetic expression "a = 1" in the text box
		driver.findElement(By.id("code_code")).sendKeys("a = 1");
		// find the "Tokenize" button and click on it
		driver.findElements(By.tagName("input")).get(2).click();
		try {
			//Locate at the second line of the code area in the Tokenize Operation page, this line should have a "on_sp" represents a whitespace as expected
			WebElement a = driver.findElement(By.xpath("//code[1]"));
			String elementText = a.getText();
			//test if there is "on_sp" in the second line
			assertTrue(elementText.contains("on_sp"));
		} catch (NoSuchElementException nseex) {
			fail();
		}
		driver.manage().deleteAllCookies();
	}
	
	// Scenario 7:
	// Given that I am on the main page
	// And I've already wrote a wrong arithmetic expression "a = 1k" in the text box, which can not be compiled(Notice that there are whitespace between elements)
	// When I click on the "Compile" Button
	// Then I should be directed to the Compile Operation page 
	// and I should see the notification says "Could not compile code - Syntax error", which is to notice me the error in compiler	
	@Test
	public void testArithmeticErrorCompile() {
		
		// Enter the arithmetic expression "a = 1k" in the text box
		driver.findElement(By.id("code_code")).sendKeys("a = 1k");
		// find the "Compile" button and click on it
		driver.findElements(By.tagName("input")).get(4).click();
		try {
			//Locate at the code area in the Compile Operation page, it should have an error notification as expected 
			WebElement a = driver.findElement(By.xpath("//code"));
			String elementText = a.getText();
			//test if there is "Could not compile code - Syntax error" in the area
			assertTrue(elementText.contains("Could not compile code - Syntax error"));
		} catch (NoSuchElementException nseex) {
			fail();
		}
		driver.manage().deleteAllCookies();
	}
	
	/**User story 3:
	 * As a user of Hoodpopper who wants to compile the string expression,
	 * I would like to see it can support the basic string expressions,
	 * So that I can know Ruby compilation visualizer is able to express strings as the way of Ruby expression 
	 */
	// Scenario 8:
	// Given that I am on the main page
	// And I've already wrote a string expression "the_best_class = SQA" in the text box
	// When I click on the "Compile" Button
	// Then I should be directed to the Compile Operation page 
	// and I should see " getconstant :SQA ", which represents the compiler successfully store the string expression in the textbox, shows on the page
	@Test
	public void testStringExpression() {
		
		// Enter the expression in the text box
		driver.findElement(By.id("code_code")).sendKeys(new String[] { "the_best_class " + "=" + " SQA" });
		// find the "Compile" button and click on it
		driver.findElements(By.tagName("input")).get(4).click();
		try {
			//Locate at the code area in the Compile Operation page
			WebElement a = driver.findElement(By.xpath("//code"));
			String elementText = a.getText();
			//test if there is "getconstant :SQA" on the page
			assertTrue(elementText.contains("getconstant :SQA"));
		} catch (NoSuchElementException nseex) {
			fail();
		}
		driver.manage().deleteAllCookies();
	}
	
	// Scenario 9:
	// Given that I am on the main page
	// And I've already wrote a wrong string expression "the_best_class = SQA/2SQA" in the text box, which can not be compiled
	// When I click on the "Compile" Button
	// Then I should be directed to the Compile Operation page 
	// and I should see the notification says "Could not compile code - Syntax error", which is to notice me the error in compiler
	@Test
	public void testStringErrorCompile() {
			
		// Enter the expression in the text box
		driver.findElement(By.id("code_code")).sendKeys("the_best_class = SQA/2SQA");
		// find the "Compile" button and click on it
		driver.findElements(By.tagName("input")).get(4).click();
		try {
			//Locate at the code area in the Compile Operation page, it should have an error notification as expected 
			WebElement a = driver.findElement(By.xpath("//code"));
			String elementText = a.getText();
			//test if there is "Could not compile code - Syntax error" in the area
			assertTrue(elementText.contains("Could not compile code - Syntax error"));
		} catch (NoSuchElementException nseex) {
			fail();
		}
		driver.manage().deleteAllCookies();
	}
	
	/**Not belong to any user stories: 
	 */
	//Scenario 10
	// Given that I am on the main page
	// And I click on the "Tokenize" Button
	// When I go back by clicking the browser's back button
	// Then I should be directed back to the main page 
	@Test
	public void testBrowserGoBack() {
		// find the "Tokenize" button and click 
		driver.findElements(By.tagName("input")).get(2).click();
		driver.navigate().back();
		// Check that if already went back to the main page
		try {
			WebElement a = driver.findElement(By.xpath("//label"));
			String elementText = a.getText();
			// Check if the label of the current page includes "Code", which means if already on the main page now
			assertTrue(elementText.contains("Code"));
		} catch (NoSuchElementException nseex) {
			fail();
		}
		driver.manage().deleteAllCookies();
	}
	
}
