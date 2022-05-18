package com.automation.listeners;

import org.testng.asserts.IAssert;
import org.testng.asserts.SoftAssert;

import com.automation.util.ExtentReportManager;
import com.aventstack.extentreports.Status;

public class LogAssert extends SoftAssert {
	
	  @Override
	  public void onAssertSuccess(IAssert<?> assertCommand) {
	    System.err.println(assertCommand.getMessage() + " <PASSED> ");
	    ExtentReportManager.getTest().log(Status.PASS, assertCommand.getMessage() + " <PASSED> ");
	  }

	  @Override
	  public void onAssertFailure(IAssert<?> assertCommand, AssertionError ex) {
	    String suffix =
	        String.format(
	            "Expected [%s] but found [%s]",
	            assertCommand.getExpected().toString(), assertCommand.getActual().toString());
	    System.err.println(assertCommand.getMessage() + " <FAILED>. " + suffix);
	    ExtentReportManager.getTest().log(Status.FAIL, "Following assert failed:"+assertCommand.getMessage() + " <FAILED>. " + suffix);
	  }	

}
