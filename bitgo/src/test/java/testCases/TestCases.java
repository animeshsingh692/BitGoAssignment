package testCases;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.List;

public class TestCases {
	    private WebDriver driver;
	    private static final String BLOCK_URL = "https://blockstream.info/block/000000000000000000076c036ff5119e5a5a74df77abf64203473364509f7732";
	    private static final String EXPECTED_HEADING = "25 of 2875 Transactions";

	    @BeforeClass
	    public void setUp() {
	        driver = new ChromeDriver();
	        driver.manage().window().maximize();
	        driver.get(BLOCK_URL);
	    }

	    @Test(priority = 1)
	    public void validateTransactionHeading() {
	        try {
	            WebElement headingElement = driver.findElement(By.xpath("//div[contains(@class, 'transactions')]//h3"));
	            String actualHeading = headingElement.getText();

	            Assert.assertEquals(actualHeading, EXPECTED_HEADING, "Transaction heading does not match!");
	            System.out.println("Test Case 1 Passed: Transaction heading is correct.");
	        } catch (Exception e) {
	            System.err.println("Error in validateTransactionHeading: " + e.getMessage());
	            Assert.fail("Failed to validate transaction heading.");
	        }
	    }

	    @Test(priority = 2)
	    public void printTransactionHashesWithSpecificInputsOutputs() {
	        try {
	            List<WebElement> transactionRows = driver.findElements(By.xpath("//div[contains(@class, 'transactions')]//div[contains(@class, 'transaction-box')]"));

	            for (WebElement row : transactionRows) {
	                List<WebElement> inputs = row.findElements(By.xpath(".//div[contains(@class, 'vins')]//div[contains(@class, 'vin')]"));
	                List<WebElement> outputs = row.findElements(By.xpath(".//div[contains(@class, 'vouts')]//div[contains(@class, 'vout')]"));

	                if (inputs.size() == 1 && outputs.size() == 2) {
	                    // Print the transaction hash
	                    WebElement txnHashElement = row.findElement(By.xpath(".//div[contains(@class, 'txn font-p2')]//a"));
	                    String txnHash = txnHashElement.getText();
	                    System.out.println("Transaction Hash: " + txnHash);
	                }
	            }
	            System.out.println("Test Case 2 Passed: Transaction hashes with 1 input and 2 outputs printed.");
	        } catch (Exception e) {
	            System.err.println("Error in printTransactionHashesWithSpecificInputsOutputs: " + e.getMessage());
	            Assert.fail("Failed to process transactions.");
	        }
	    }

	    @AfterClass
	    public void tearDown() {
	        if (driver != null) {
	            driver.quit();
	        }
	    }
}
