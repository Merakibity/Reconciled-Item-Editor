package com.amazon.rie;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

//{@literal @RunWith(JUnit4.class)}
public class reconItemEdit extends App {

        private static ChromeDriverService service;
        private WebDriver driver;

        // {@literal @BeforeClass}
        public static void createAndStartService() throws IOException {
                service = new ChromeDriverService.Builder()
                                .usingDriverExecutable(new File("Resources/chromedriver.exe")).usingAnyFreePort()
                                .build();
                service.start();
        }

        // {@literal @AfterClass}
        public static void createAndStopService() {
                service.stop();
        }

        // {@literal @Before}
        public void createDriver() {
                driver = new RemoteWebDriver(service.getUrl(), DesiredCapabilities.chrome());
        }

        // {@literal @After}
        public void quitDriver() {
                driver.quit();
        }

        Sheet sheet;
        Cell cell;
        String ASIN, MP, attr, locale, nv, CID, MPID, reason, pathe;
        double c, m, r;

        public int ketData() throws IOException {

                FileInputStream finput = null;

                int k;

                finput = new FileInputStream(new File("Reconcileditemeditor.xlsm"));
                Workbook workbook = WorkbookFactory.create(finput);

                sheet = workbook.getSheetAt(0);

                k = sheet.getLastRowNum();

                driver.get("https://csi.amazon.com/diag/ReconciledItemEditor");

                return k;
        }

        public void getValues(int j) {
                cell = sheet.getRow(j).getCell(0); // cell A2
                ASIN = cell.getStringCellValue();
                cell = sheet.getRow(j).getCell(1);
                MP = cell.getStringCellValue();
                cell = sheet.getRow(j).getCell(2);
                c = cell.getNumericCellValue();
                CID = String.format("%d", (long) c);
                cell = sheet.getRow(j).getCell(3);
                attr = cell.getStringCellValue();
                try {
                        cell = sheet.getRow(j).getCell(4);
                        locale = cell.getStringCellValue();
                        cell = sheet.getRow(j).getCell(5);
                        m = cell.getNumericCellValue();
                        MPID = String.format("%d", (long) m);
                        cell = sheet.getRow(j).getCell(6);
                        nv = cell.getStringCellValue();
                } catch (NullPointerException ignNullPointerException) {
                }
                cell = sheet.getRow(j).getCell(7);
                r = cell.getNumericCellValue();
                reason = String.format("%d", (long) r);

        }

        // {@literal @Test}
        public void changeTheDumb() throws InterruptedException {
                WebDriverWait wait = new WebDriverWait(driver, 1000);
                // WebDriverWait w = new WebDriverWait(driver, clock, sleeper, timeOutInSeconds,
                // sleepTimeOut)
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                                "//body/div[@id='diagnosticAppBase']/div[2]/div[1]/div[1]/div[2]/div[1]/step[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/form[1]/div[7]/div[1]/button[1]")));
                driver.findElement(By.xpath(
                                "//body/div[@id='diagnosticAppBase']/div[2]/div[1]/div[1]/div[2]/div[1]/step[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/form[1]/div[1]/div[1]/div[1]/div[1]/input[1]"))
                                .clear();
                driver.findElement(By.xpath(
                                "//body/div[@id='diagnosticAppBase']/div[2]/div[1]/div[1]/div[2]/div[1]/step[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/form[1]/div[1]/div[1]/div[1]/div[1]/input[1]"))
                                .sendKeys(ASIN);
                driver.findElement(By.xpath(MP)).click();
                driver.findElement(By.xpath(
                                "//body/div[@id='diagnosticAppBase']/div[2]/div[1]/div[1]/div[2]/div[1]/step[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/form[1]/div[3]/div[1]/div[1]/div[1]/input[1]"))
                                .clear();
                driver.findElement(By.xpath(
                                "//body/div[@id='diagnosticAppBase']/div[2]/div[1]/div[1]/div[2]/div[1]/step[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/form[1]/div[3]/div[1]/div[1]/div[1]/input[1]"))
                                .sendKeys(CID);
                driver.findElement(By.xpath(
                                "//body/div[@id='diagnosticAppBase']/div[2]/div[1]/div[1]/div[2]/div[1]/step[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/form[1]/div[4]/div[1]/div[1]/div[1]/input[1]"))
                                .clear();
                driver.findElement(By.xpath(
                                "//body/div[@id='diagnosticAppBase']/div[2]/div[1]/div[1]/div[2]/div[1]/step[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/form[1]/div[4]/div[1]/div[1]/div[1]/input[1]"))
                                .sendKeys(ASIN);
                driver.findElement(By.id("submit")).click();

                wait.until(ExpectedConditions.presenceOfElementLocated(
                                By.xpath("//button[contains(text(),'Add Attribute to SKU')]")));
               if (nv != "nul") {
                driver.findElement(By.xpath("//button[contains(text(),'Add Attribute to SKU')]")).click();

                try {
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(
                                        "//body/div[@id='diagnosticAppBase']/div[2]/div[1]/div[1]/div[2]/div[1]/step[1]/div[1]/div[2]/div[2]/div[2]/div[1]/div[1]/form[1]/div[2]/div[1]/div[1]/div[1]/span[1]")));
                } catch (TimeoutException e) {
                        e.printStackTrace();
                }
                        try {
                                driver.findElement(By.xpath(
                                                "//body/div[@id='diagnosticAppBase']/div[2]/div[1]/div[1]/div[2]/div[1]/step[1]/div[1]/div[2]/div[2]/div[2]/div[1]/div[1]/form[1]/div[2]/div[1]/div[1]/div[1]/div[1]/input[1]"))
                                                .sendKeys(attr);
                                driver.findElement(By.xpath(
                                                "//body/div[@id='diagnosticAppBase']/div[2]/div[1]/div[1]/div[2]/div[1]/step[1]/div[1]/div[2]/div[2]/div[2]/div[1]/div[1]/form[1]/div[2]/div[1]/div[1]/div[1]/div[1]/button[1]"))
                                                .click();
                                try {
                                        driver.findElement(By.xpath(
                                                        "//body/div[@id='diagnosticAppBase']/div[2]/div[1]/div[1]/div[2]/div[1]/step[1]/div[1]/div[2]/div[2]/div[2]/div[1]/div[1]/form[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/input[1]"))
                                                        .sendKeys(locale);
                                        driver.findElement(By.xpath(
                                                        "//body/div[@id='diagnosticAppBase']/div[2]/div[1]/div[1]/div[2]/div[1]/step[1]/div[1]/div[2]/div[2]/div[2]/div[1]/div[1]/form[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[2]/div[1]/input[1]"))
                                                        .sendKeys(MPID);

                                        driver.findElement(By.xpath(
                                                        "//body/div[@id='diagnosticAppBase']/div[2]/div[1]/div[1]/div[2]/div[1]/step[1]/div[1]/div[2]/div[2]/div[2]/div[1]/div[1]/form[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[3]/div[1]/input[1]"))
                                                        .sendKeys(nv);

                                } catch (IllegalArgumentException e) {
                                        e.printStackTrace();
                                }
                        } catch (ElementNotVisibleException ignoreElementNotVisibleException) {
                        }
                } else {
                }
                List<WebElement> webel = driver.findElements(
                                By.xpath("//label[contains(@class,'col-md-2 control-label') and text()='" + attr
                                                + "']/ancestor::div[@class='form-group']//div[contains(@class,'col-md-3')]//input[contains(@type,'checkbox')]"));
                int w = webel.size();

                List<WebElement> webtxt = driver.findElements(
                                By.xpath("//label[contains(@class,'col-md-2 control-label') and text()='" + attr
                                                + "']/ancestor::div[@class='form-group']//div[contains(@class,'col-sm-3')]//input[contains(@type,'text')]"));
                System.out.println(w);
                for (int v = 0; v < w; v++) {
                        WebElement tim = webel.get(v);
                        WebElement xim = webtxt.get(v);
                        tim.click();
                        xim.sendKeys(reason);
                }
                List<WebElement> li = driver.findElements(By.cssSelector("#submit"));
                li.get(1).click();
                try {
                        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                                        "//body/div[@id='diagnosticAppBase']/div[2]/div[1]/div[1]/div[2]/div[1]/step[1]/div[1]/div[3]/div[2]/div[1]/h4[1]/a[1]")));
                } catch (NoSuchElementException e) {

                        li.get(1).click();
                }
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                                "//body/div[@id='diagnosticAppBase']/div[2]/div[1]/div[1]/div[2]/div[1]/step[1]/div[1]/div[3]/div[2]/div[1]/h4[1]/a[1]")));
                driver.navigate().to("https://csi.amazon.com/diag/ReconciledItemEditor");
                /*
                 * driver.findElement(By.xpath(
                 * "//body/div[@id='diagnosticAppBase']/div[2]/div[1]/div[1]/div[2]/div[1]/step[1]/div[1]/div[1]/div[2]/div[1]/h4[1]/a[1]"
                 * )) .click();
                 */
        }
}