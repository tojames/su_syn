package com.sub.syn.util;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Util {
	public static void sleep(long seconds){
    	try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
	
	/**
     * 获取元素
     * @param driver
     * @param by
     * @return
     */
    public static WebElement getWebElement(WebDriver driver,By by){
    	WebElement web=null;
    	try{
    		web=driver.findElement(by);
    	}catch(org.openqa.selenium.NoSuchElementException e){
    		return null;
    	}
    	return web;
    }
    
    /**
     * 获取元素
     * @param driver
     * @param by
     * @return
     */
    public static WebElement getWebElementByWebElement(WebElement ele,By by){
    	WebElement web=null;
    	try{
    		web=ele.findElement(by);
    	}catch(org.openqa.selenium.NoSuchElementException e){
    		return null;
    	}
    	return web;
    }
    
    /**
     * 鼠标滑动
     * @param targetElement
     * @param driver
     */
    public static void MouseHoverByJavaScript(WebElement targetElement,WebDriver driver){
 
        String mouseHoverjs = "var evObj = document.createEvent('MouseEvents');" +
                            "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);" +
                            "arguments[0].dispatchEvent(evObj);";
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript(mouseHoverjs, targetElement);
    }
    
    /**
     * 获取ID
     * @return
     */
    public static String getId(){
    	return UUID.randomUUID().toString().replaceAll("-", "");
    }
    
    /**
     * 获取谷歌浏览器的驱动
     * @return
     */
    public static WebDriver geChromeDriver(){
    	System.setProperty("webdriver.chrome.driver", "D:/学习文档/Selenium/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		return driver;
    }
}
