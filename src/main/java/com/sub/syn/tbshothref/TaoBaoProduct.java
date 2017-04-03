package com.sub.syn.tbshothref;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.sub.syn.util.Util;


/**
 * 抓取淘宝联盟的商品
 * @author wuyb
 *
 */
public class TaoBaoProduct {

	public static void main(String[] args){
		WebDriver driver =Util.geChromeDriver();
		driver.get("http://pub.alimama.com/promo/search/index.htm?spm=a219t.7900221%2F1.1998910419.de727cf05.ZDRVJC&toPage=1&queryType=2&dpyhq=1");
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 100)");
		//List<WebElement> divs=driver.findElement(By.cssSelector("div.search-result-wrap.search-result-wrap-block.clearfix")).findElements(By.cssSelector("div.block-search-box.tag-wrap"));
		List<WebElement> divs=driver.findElements(By.xpath("//div[@class='search-result-wrap search-result-wrap-block clearfix']/div[@class='block-search-box tag-wrap']"));
		   
		System.out.println("====== divs size :"+divs.size());
	    for(WebElement div:divs){
	    	System.out.println("=============="+div.getText());
	    	WebElement ele=div.findElement(By.tagName("div"));
	    	System.out.println("=======二级===="+ele.getText());
//	    	ele=div.findElement(By.tagName("div")).findElement(By.tagName("a"));
//	    	System.out.println("=====商品URL====="+ele.getText());
//	    	WebElement child1=ele.findElement(By.tagName("div"));
//	    	System.out.println("========商品信息====="+child1.getText());
	    }
	    
	    driver.quit();
	}
}
