package Driverfactory;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import CommonFunLibrary.CommonFunction;
import Utilities.ExcelFileUtil;

public class DriverScript {
WebDriver driver;
ExtentReports report;
ExtentTest test;
String inputpth="E:\\OjtProject\\ERP_StockMaven\\TestInput\\HybridFramework.xlsx";
String outputpath="E:\\OjtProject\\ERP_StockMaven\\TestOutput\\HybridResults.xlsx";
public void startTest() throws Throwable
{
	//access excel methods
	ExcelFileUtil xl=new ExcelFileUtil(inputpth);
	//iterating all row in mastertestcase sheet
	for(int i=1;i<=xl.rowCount("MasterTestCase");i++)
	{
		if(xl.getCellData("MasterTestCase", i, 2).equalsIgnoreCase("Y"))
		{
			//Store module name into TCModule
			String TCModule=xl.getCellData("MasterTestCase", i, 1);
			//Generate report under project
			report=new ExtentReports("./ExtentsReports//"+TCModule+CommonFunction.generateDate()+".html");
			//Iterate all the Rows in TCModule sheet
			for(int j=1;j<=xl.rowCount(TCModule);j++)
			{
				//start test 
				test=report.startTest(TCModule);
				//Read all cell from TCModule
				String Description=xl.getCellData(TCModule, j, 0);
				String Function_Nmae=xl.getCellData(TCModule, j, 1);
				String Location_Type=xl.getCellData(TCModule, j, 2);
				String Locator_Value=xl.getCellData(TCModule, j, 3);
				String Test_Data=xl.getCellData(TCModule, j, 4);
				try {
					if(Function_Nmae.equalsIgnoreCase("startBrowser"))
					{
						driver=CommonFunction.startBrowser();
						test.log(LogStatus.INFO, Description);
					}
					else if(Function_Nmae.equalsIgnoreCase("openApplication"))
					{
						CommonFunction.openApplication(driver);
						test.log(LogStatus.INFO, Description);
					}
					else if(Function_Nmae.equalsIgnoreCase("waitforElement"))
					{
						CommonFunction.waitforelement(driver, Location_Type, Locator_Value, Test_Data);
						test.log(LogStatus.INFO, Description);
					}
					else if(Function_Nmae.equalsIgnoreCase("typeAction"))
					{
						CommonFunction.typeAction(driver, Location_Type, Locator_Value, Test_Data);
						test.log(LogStatus.INFO, Description);
					}
					else if(Function_Nmae.equalsIgnoreCase("clickAction"))
					{
						CommonFunction.clickAction(driver, Location_Type, Locator_Value);
						test.log(LogStatus.INFO, Description);
					}
					else if(Function_Nmae.equalsIgnoreCase("closeBrowser")) 
					{
						CommonFunction.closeBrowser(driver);
						test.log(LogStatus.INFO, Description);
					}
					else if(Function_Nmae.equalsIgnoreCase("captureData"))
					{
						CommonFunction.captureData(driver, Location_Type, Locator_Value);
						test.log(LogStatus.INFO, Description);
					}
					else if(Function_Nmae.equalsIgnoreCase("stableValidation"))
					{
						CommonFunction.stableValidation(driver, Test_Data);
						test.log(LogStatus.INFO, Description);
					}
					else if(Function_Nmae.equalsIgnoreCase("stockCategory"))
					{
						CommonFunction.stockCategory(driver);
						test.log(LogStatus.INFO,Description);
					}
					else if(Function_Nmae.equalsIgnoreCase("stockTable"))
					{
						CommonFunction.stockTable(driver, Test_Data);
						test.log(LogStatus.INFO, Description);
					}
					//For customer
					else if(Function_Nmae.equalsIgnoreCase("CaptureDatac"))
					{
						CommonFunction.captureDatac(driver, Location_Type, Locator_Value);
						test.log(LogStatus.INFO, Description);
					}
					else if(Function_Nmae.equalsIgnoreCase("stableValidationc"))
					{
						CommonFunction.stableValidationc(driver, Test_Data);
						test.log(LogStatus.INFO, Description);
					}
					else if(Function_Nmae.equalsIgnoreCase("SelectAction"))
					{
						CommonFunction.SelectAction(driver);
						test.log(LogStatus.INFO, Description);
					}
					else if(Function_Nmae.equalsIgnoreCase("captureDatap"))
					{
						CommonFunction.captureDatap(driver, Location_Type, Locator_Value);
						test.log(LogStatus.INFO, Description);
					}
					else if(Function_Nmae.equalsIgnoreCase("stableValidationp"))
					{
						CommonFunction.stableValidation(driver, Test_Data);
						test.log(LogStatus.INFO, Description);
					}
					//write as pass into Status column TCModule.
					xl.setCelldata(TCModule, j, 5, "Pass", outputpath);
					test.log(LogStatus.PASS, Description);
					//write as Pass into MasterTestcase sheet.
					xl.setCelldata("MasterTestcase", i, 3, "PASS", outputpath);
				} catch (Exception e) {
			
				{
				//Write as fail into status column TCModule
					xl.setCelldata(TCModule, j, 5, "Fail", outputpath);
					test.log(LogStatus.FAIL, Description);
					//Write as fail into MastertestCase sheet
					xl.setCelldata("MasterTestcase", i, 3, "fail", outputpath);
				}
			}
				report.endTest(test);
				report.flush();
		}
	}
			else 
			{
				//write as blocked into status cell in MasterTestCase sheet
				xl.setCelldata("MasterTestcase", i, 3, "Blocked", outputpath);
			}
	}
}
}

