package com.automation.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

	
	 int counter = 0;
	 private static int maxTry = 3;
	 
	@Override
	public boolean retry(ITestResult result) {
		
		//if this annotation is applied to method
	RetryIfFailed annotation=result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(RetryIfFailed.class);
		
		if((annotation!=null)&&(counter<annotation.value()))
		{
			counter++;
			return true;
		}
		
		return false;
		
	}

}
