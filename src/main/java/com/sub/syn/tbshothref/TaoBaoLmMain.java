package com.sub.syn.tbshothref;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.sub.syn.util.Util;

/**
 * 登陆淘宝联盟 输入URL搜索产品 抓取产品的短连接和淘口令
 * @author wuyb
 *
 */
public class TaoBaoLmMain {

	public static void main(String[] args){
		TaoBaoLmMain tblm=new TaoBaoLmMain();
		TbLogin tb=new TbLogin();
		WebDriver driver=tb.getLoginDriver();
		System.out.println("获取driver 登陆完成："+driver);
		
		//2 登陆页面点击登陆按钮后，请求阿里妈妈地址
        
        List<String> list=new ArrayList<String>();
        //list.add("https://detail.tmall.com/item.htm?id=543799986727&spm=a219t.7900221/10.1998910419.d30ccd691.uI4hJM&spm=a219t.7900221/10.1998910419.d30ccd691.uI4hJM");
        list.add("https://detail.tmall.com/item.htm?id=538033221802&spm=a219t.7900221/19.1998910419.d9a1dac8eqqhd.2qb1Br&sku_properties=5919063:6536025");
        //list.add("https://item.taobao.com/item.htm?spm=a219t.7900221/21.1998910419.d9a1dac8enzjh.0ywKvZ&id=545588026553");
        //list.add("https://item.taobao.com/item.htm?spm=a219t.7900221/21.1998910419.d9a1dac8enzjh.0ywKvZ&id=545903909175");
        for(String key:list){
        	//((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 0)");
        	tblm.getDatas(driver,key);
        }

        System.out.println(" ======over====== " );

        //Close the browser
        driver.quit();
	}
	
	/**
	 * 获取淘宝短连接等
	 * @param key
	 */
	public void getDatas(WebDriver driver1,String key){
		WebDriver driver=driver1;
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(tabs.get(0)); //switches to new tab 不同的网址显示在同一个页面 可以使用登陆的用户信息
        driver.get("http://pub.alimama.com/"); //阿里妈妈地址  淘宝联盟网址
        
        Util.sleep(4);
        //搜索输入框
        WebElement input = driver.findElement(By.cssSelector(".search-inp.input"));
		
		input.sendKeys(key);
        //查询事件
        WebElement search = driver.findElement(By.cssSelector(".btn.btn-brand.search-btn"));
        search.click();
    	
        Util.sleep(4);
        //////////////////////////////////////////////////////////////////////
        //=====================向下滚动页面 显示出立即推荐按钮===========================
        //移动到指定的坐标(相对当前的坐标移动) 使页面加载元素
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 600)");
        //结合上面的scrollBy语句，相当于移动到700+800=1600像素位置 
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 700)");
        //////////////////////////////////////////////////////////////////////
        
    	List<WebElement> webs=null;
    	try{
    		webs=driver.findElements(By.cssSelector("a.box-btn-left"));
    		
    	}catch(Exception e){
    		Util.sleep(4);
    		webs=driver.findElements(By.cssSelector("a.box-btn-left"));
    	}
    	try{
    		for(WebElement web:webs){
        		System.out.println("===立即推广："+web.getText());
        		web.click();
        		System.out.println("==a 连接="+web.getTagName());
        		Util.sleep(2);
        		try{
        			web=driver.findElement(By.cssSelector(".btn.btn-brand.w100.mr10"));
        		}catch(org.openqa.selenium.NoSuchElementException e){
        			web=driver.findElement(By.cssSelector(".btn.btn-brand.w100.mr10"));
        		}
        		System.out.println("按钮："+web.getText());
        		web.click();
        		System.out.println("===================点击完成===================");
        		//点击复制领券地址
        		driver.manage().window().maximize();
        		Util.sleep(2);
        		//web=driver.findElement(By.id("clipboard-target-1"));
        		web=Util.getWebElement(driver, By.id("clipboard-target-1"));
        		if(null!=web){
        			System.out.println("======商品短连接地址  ====="+web.getAttribute("value"));
        		}else{
        			System.out.println("=======不存在短连接====");
        		}
        		//web=driver.findElement(By.id("clipboard-target-2"));
        		web=Util.getWebElement(driver, By.id("clipboard-target-2"));
        		if(null!=web){
        			System.out.println("======领券地址 value  ====="+web.getAttribute("value"));
        		}else{
        			System.out.println("=======领券地址 value 不存在====");
        		}
        		
        		//淘口令
        		WebElement kl=driver.findElement(By.xpath("(//ul[@class='tab-nav clearfix']/li)[last()]"));
        		System.out.println("===li===="+kl.getText());
        		kl.click();
        		System.out.println("点击完成 淘口令连接");
        		web=Util.getWebElement(driver, By.id("clipboard-target-1"));
        		if(null!=web){
        			System.out.println("======商品口令  ====="+web.getAttribute("value"));
        		}else{
        			System.out.println("=======不存在口令====");
        		}
        		web=Util.getWebElement(driver, By.id("clipboard-target-2"));
        		if(null!=web){
        			System.out.println("======领券口令 value  ====="+web.getAttribute("value"));
        		}else{
        			System.out.println("=======领券口令value 不存在====");
        		}
        		web=Util.getWebElement(driver, By.cssSelector(".btn.btn-gray.w100"));
        		web.click();
        	}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
	}
	
}
