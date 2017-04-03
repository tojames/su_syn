package com.sub.syn.tbshothref;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.sub.syn.util.Util;

/**
 * 淘宝 登陆
 * @author wuyb
 *
 */
public class TbLogin {
	
	private WebDriver driver=null;
	
	/***淘宝账号**/
	private String name="13911624198";
	
	/***密码***/
	private String password="wu060326";
	
	public WebDriver getLoginDriver(){
        
        System.setProperty("webdriver.chrome.driver", "E:/mavenpro/sub-syn/chromedriver.exe");
        driver = new ChromeDriver();
        
        //窗口最大化 防止出现找不到元素的错误信息 由于页面元素比较多，往往需要操作的元素不能全部显示出来，所以需要窗口最大化，也可以设定像素
        //元素在页面最下方的，可以通过页面滚动的形式，定位到元素所在位置
        driver.manage().window().maximize();
        // 设置登陆地址 淘宝阿里妈妈登陆地址
        driver.get("https://login.taobao.com/member/login.jhtml?style=mini&newMini2=true&css_style=alimama&from=alimama&redirectURL=http%3A%2F%2Fwww.alimama.com&full_redirect=true&disableQuickLogin=true");
        // Alternatively the same thing can be done like this
        // driver.navigate().to("http://www.google.com");

        // 1 找出切换到用户名和密码登录页面的按钮
        WebElement loginBtn = null;
        try{
        	loginBtn=driver.findElement(By.cssSelector(".iconfont.static"));
        }catch(Exception e){
        	loginBtn=driver.findElement(By.cssSelector(".iconfont.static"));
        	e.printStackTrace();
        }
        //点击切换按钮
        loginBtn.click();
        
        Util.sleep(1);
        //找出用户名和密码输入框
        WebElement TPL_username_1=driver.findElement(By.id("TPL_username_1"));
        WebElement TPL_password_1=driver.findElement(By.id("TPL_password_1"));
        Util.sleep(1);
        //输入用户名和密码
        TPL_username_1.sendKeys(name);
        TPL_password_1.sendKeys(password);
        Util.sleep(1);
        
        //找出登陆按钮
        WebElement tjbtn = driver.findElement(By.id("J_SubmitStatic"));
        //模拟点击登陆按钮
        tjbtn.click();
        //////////////////////////////////////
        //登陆完成后需要通过手机端确认登陆，此处要用手机确认登陆
      //等待手机验证
        while(true){
        	try{
        		tjbtn=driver.findElement(By.cssSelector(".menu-loginout"));
        		if(null!=tjbtn){
        			break;
        		}
        	}catch(Exception e){
        		System.out.println("等待手机端确认登陆。。。。。");
        		Util.sleep(2);
        	}
        }
        System.out.println("登陆完成");
        return driver;
	}

}
