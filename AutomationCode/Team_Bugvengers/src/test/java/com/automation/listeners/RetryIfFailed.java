package com.automation.listeners;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

@Retention(RUNTIME)
public @interface RetryIfFailed {
	
	int value() default 0;

}
