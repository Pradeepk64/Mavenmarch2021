package CommonFunLibrary;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import Utilities.PropertyFileUtil;

public class CommonFunction {
public static WebDriver driver;
public static WebDriver startBrowser() throws Throwable
{
	if(PropertyFileUtil.getvalueForKey("Browser").equalsIgnoreCase("chrome"))
	{
		System.setProperty("webdriver.chrome.driver","E:\\OjtProject\\ERP_StockMaven\\CommonDriver\\chromedriver.exe");
		driver  = new ChromeDriver();
	}
		else if(PropertyFileUtil.getvalueForKey("Browser").equalsIgnoreCase("Firefox"))
		{
			System.setProperty("webdriver.gecko.driver", "E:\\OjtProject\\ERP_StockMaven\\CommonDriver\\geckodriver.exe");
			driver= new FirefoxDriver();
		}
		else
		{
	      System.out.println("Browser value not matching");
		}
		return driver;
	}
	public static void openApplication(WebDriver drive) throws Throwable
	{
		driver.get(PropertyFileUtil.getvalueForKey("url"));
		driver.manage().window().maximize();
	}
	public static void waitforelement(WebDriver driver,String locatortype,String locatorvalue,String waittime)
	{
		WebDriverWait mywait= new WebDriverWait(driver, Integer.parseInt(waittime));
		if(locatortype.equalsIgnoreCase("name"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorvalue)));
		}
		else if(locatortype.equalsIgnoreCase("id"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorvalue)));
		}
		else if(locatortype.equalsIgnoreCase("xpath"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorvalue)));
		}
	}
	public static void typeAction(WebDriver driver,String locatortype,String locatorvalue,String testData)
	{
		if (locatortype.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(locatorvalue)).clear();
			driver.findElement(By.name(locatorvalue)).sendKeys(testData);
		}
		else if(locatortype.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(locatorvalue)).clear();
			driver.findElement(By.id(locatorvalue)).sendKeys(testData);
		}
		else if(locatortype.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(locatorvalue)).clear();
			driver.findElement(By.xpath(locatorvalue)).sendKeys(testData);
		}
	}
	public static void clickAction(WebDriver driver,String locatortype,String locatorvalue)
	{
		if(locatortype.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(locatorvalue)).sendKeys(Keys.ENTER);
		}
		else if(locatortype.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(locatorvalue)).click();
		}
		else if(locatortype.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(locatorvalue)).click();
		}
	}
	public static void closeBrowser(WebDriver driver)
	{
		driver.close();
	}
	//Java time Stamp
	public static String generateDate()
	{
		Date date = new Date();
		SimpleDateFormat df=new SimpleDateFormat("YYYY_MM_DD_SS");
		return df.format(date);
	}

//Method for Capturing snumber into Notepad
public static void captureData(WebDriver driver,String locatortype, String locatorvalue) throws Throwable
{
	String Expectedsnumber=null;
	if(locatortype.equalsIgnoreCase("id"))
	{
		Expectedsnumber=driver.findElement(By.id(locatorvalue)).getAttribute("value");
	}
	else if (locatorvalue.equalsIgnoreCase("name"))
	{
		Expectedsnumber=driver.findElement(By.name(locatorvalue)).getAttribute("value");
	}
	//Write expsnumber into notepad
	FileWriter fw=new FileWriter("E:\\OjtProject\\ERP_StockMaven\\CaptureData\\Supplier.txt");
	BufferedWriter bw= new BufferedWriter(fw);
	bw.write(Expectedsnumber);
	bw.flush();
	bw.close();
	//Method for table validation
}
	public static void stableValidation(WebDriver driver,String columndata)throws Throwable
	{
		//Read data from Notepad
		FileReader fr= new FileReader("E:\\OjtProject\\ERP_StockMaven\\CaptureData\\Supplier.txt");
		BufferedReader br= new BufferedReader(fr);
		String Exp_Data= br.readLine();
		//Convert columndata into integer
		int column=Integer.parseInt(columndata);
		if(!driver.findElement(By.xpath(PropertyFileUtil.getvalueForKey("search.textbox"))).isDisplayed());
		//click on search Panel button
		driver.findElement(By.xpath(PropertyFileUtil.getvalueForKey("search.panel"))).click();
		Thread.sleep(5000);
		//clear searchbox
		WebElement searchtext=  driver.findElement(By.xpath(PropertyFileUtil.getvalueForKey("search.textbox")));
		searchtext.clear();
		searchtext.sendKeys(Exp_Data);
		Thread.sleep(5000);
		driver.findElement(By.xpath(PropertyFileUtil.getvalueForKey("search.button"))).click();
		Thread.sleep(5000);
		WebElement table=driver.findElement(By.xpath(PropertyFileUtil.getvalueForKey("webtable.path")));
		Thread.sleep(5000);
		//count no of Rows in a table
		List<WebElement>rows= table.findElements(By.tagName("tr"));
		for(int i=1;i<=rows.size();i++)
		{
			//capture six cell datafrom a table
			String act_Data=driver.findElement(By.xpath("//table[@id='tbl_a_supplierslist']/tbody/tr["+i+"]/td["+column+"]/div/span/span")).getText();
			Assert.assertEquals(act_Data, Exp_Data,"Column Data is not Matching");
			Reporter.log(Exp_Data+"   "+act_Data);
			break;
		}
	}
	public static void stockCategory(WebDriver driver) throws Throwable
	{
		Actions ac= new Actions(driver);
		ac.moveToElement(driver.findElement(By.linkText("Stock Items"))).perform();
		Thread.sleep(4000);
		ac.moveToElement(driver.findElement(By.xpath("(//a[text()='Stock Categories'])[2]"))).click().perform();
		Thread.sleep(4000);
	}
	public static void stockTable(WebDriver driver, String testData) throws Throwable
	{
		if(!driver.findElement(By.xpath(PropertyFileUtil.getvalueForKey("search.textbox"))).isDisplayed());
		driver.findElement(By.xpath(PropertyFileUtil.getvalueForKey("search.panel"))).click();
		Thread.sleep(4000);
		WebElement searchtext =driver.findElement(By.xpath(PropertyFileUtil.getvalueForKey("search.textbox")));
		searchtext.clear();
		Thread.sleep(5000);
		searchtext.sendKeys(testData);
		Thread.sleep(5000);
		driver.findElement(By.xpath(PropertyFileUtil.getvalueForKey("search.button"))).click();
		Thread.sleep(5000);
		WebElement table= driver.findElement(By.xpath(PropertyFileUtil.getvalueForKey("webtable.stock")));
		List<WebElement>rows=table.findElements(By.tagName("tr"));
		for(int i=1;i<=rows.size();i++)
		{
			String actdata=driver.findElement(By.xpath("//table[@id='tbl_a_stock_categorieslist']//td[4]")).getText();
			Assert.assertEquals(actdata, testData,"Category is not matching");
		}
	}


	
	
	//Method for Capture customerNumber
	public static void captureDatac(WebDriver driver, String locatortype,String locatorvalue) throws Throwable
	{
	String Expectedcnumber= null;
	if(locatortype.equalsIgnoreCase("id"))
	{
		Expectedcnumber=driver.findElement(By.id(locatorvalue)).getAttribute("value");
	}
	else if(locatortype.equalsIgnoreCase("name"))
	{
		Expectedcnumber=driver.findElement(By.name(locatorvalue)).getAttribute("value");
	}
	
	//Write  cnumber data in to notepad
	FileWriter fw= new FileWriter("E:\\OjtProject\\ERP_StockMaven\\CaptureData\\customer.txt");
	BufferedWriter bw= new BufferedWriter(fw);
	bw.write(Expectedcnumber);
	bw.flush();
	bw.close();
	}
	public static void stableValidationc(WebDriver driver,String columndata) throws Throwable
	{
		FileReader fr=new FileReader("E:\\OjtProject\\ERP_StockMaven\\CaptureData\\customer.txt");
		BufferedReader br=new BufferedReader(fr);
		String exp_data=br.readLine();
		//Convert column data into String
		int column= Integer.parseInt(columndata);
		if(!driver.findElement(By.xpath(PropertyFileUtil.getvalueForKey("search.ctextbox"))).isDisplayed());
		// Click on search panel
		driver.findElement(By.xpath(PropertyFileUtil.getvalueForKey("search.cpanel"))).click();
		Thread.sleep(5000);
		//clear searchbox
		WebElement searchtext=driver.findElement(By.xpath(PropertyFileUtil.getvalueForKey("search.ctextbox")));
		Thread.sleep(5000);
		searchtext.clear();
		searchtext.sendKeys(exp_data);
		Thread.sleep(4000);
		driver.findElement(By.xpath(PropertyFileUtil.getvalueForKey("search.cbutton"))).click();
		Thread.sleep(4000);
		WebElement Table=driver.findElement(By.xpath(PropertyFileUtil.getvalueForKey("webtable.customer")));
		Thread.sleep(4000);
		//Count no of rows in table
		List<WebElement>Rows= Table.findElements(By.tagName("tr"));
		for(int i=1;i<=Rows.size();i++)
		{
			// Capture fifth cell data from table
			String Act_data= driver.findElement(By.xpath("//table[@id='tbl_a_customerslist']//tbody/tr["+i+"]/td["+column+"]/div/span/span")).getText();
			Assert.assertEquals(Act_data, exp_data,"column data not matching");
			Reporter.log(exp_data+"   "+Act_data);
			break;
		}
			
		}
	//Method for Capture Purchasers Number
		public static void captureDatap(WebDriver driver, String locatortype,String locatorvalue) throws Throwable
		{
		String Expectedcnumber= null;
		if(locatortype.equalsIgnoreCase("id"))
		{
			Expectedcnumber=driver.findElement(By.id(locatorvalue)).getAttribute("value");
		}
		else if(locatortype.equalsIgnoreCase("name"))
		{
			Expectedcnumber=driver.findElement(By.name(locatorvalue)).getAttribute("value");
		}
		
		//Write  cnumber data in to notepad
		FileWriter fw= new FileWriter("E:\\OjtProject\\ERP_StockMaven\\CaptureData\\purchasers.txt");
		BufferedWriter bw= new BufferedWriter(fw);
		bw.write(Expectedcnumber);
		bw.flush();
		bw.close();
		}
		public static void stableValidationp(WebDriver driver,String columndata) throws Throwable
		{
			FileReader fr=new FileReader("E:\\OjtProject\\ERP_StockMaven\\CaptureData\\purchasers.txt");
			BufferedReader br=new BufferedReader(fr);
			String expt_data=br.readLine();
			//Convert column data into String
			int column= Integer.parseInt(columndata);
			if(!driver.findElement(By.xpath(PropertyFileUtil.getvalueForKey("search.ptextbox"))).isDisplayed());
			// Click on search panel
			driver.findElement(By.xpath(PropertyFileUtil.getvalueForKey("search.ppanel"))).click();
			Thread.sleep(5000);
			//clear searchbox
			WebElement searchtextb=driver.findElement(By.xpath(PropertyFileUtil.getvalueForKey("search.ptextbox")));
			Thread.sleep(5000);
			searchtextb.clear();
			searchtextb.sendKeys(expt_data);
			Thread.sleep(4000);
			driver.findElement(By.xpath(PropertyFileUtil.getvalueForKey("search.pbutton"))).click();
			Thread.sleep(4000);
			WebElement table=driver.findElement(By.xpath(PropertyFileUtil.getvalueForKey("webtable.purchaser")));
			Thread.sleep(4000);
			//Count no of rows in table
			List<WebElement>rows= table.findElements(By.tagName("tr"));
			for(int i=1;i<=rows.size();i++)
			{
				// Capture fifth cell data from table
				String Act_Data= driver.findElement(By.xpath("//table[@id='tbl_a_purchaseslist']//tbody/tr["+i+"]/td["+column+"]/div/span/span")).getText();
				Assert.assertEquals(Act_Data, expt_data,"column data not matching");
				Reporter.log(expt_data+"   "+Act_Data);
				break;
			}
				
			}
		public static void SelectAction(WebDriver driver) throws Throwable
		{
			Actions ac= new Actions(driver);
			Robot r=new Robot();
			ac.moveToElement(driver.findElement(By.xpath("//span[@class='glyphicon glyphicon-calendar']"))).click().perform();
			Thread.sleep(3000);
			ac.moveToElement(driver.findElement(By.xpath("//td[contains(text(),'18')]"))).click().perform();
			Thread.sleep(3000);
			ac.moveToElement(driver.findElement(By.xpath("//select[@id='x_Supplier_ID']"))).click().perform();
			Thread.sleep(3000);
			r.keyPress(KeyEvent.VK_DOWN);
			r.keyPress(KeyEvent.VK_DOWN);
			Thread.sleep(3000);
			r.keyPress(KeyEvent.VK_DOWN);
			Thread.sleep(3000);
			r.keyPress(KeyEvent.VK_ENTER);
			
			}
		
	

}



















