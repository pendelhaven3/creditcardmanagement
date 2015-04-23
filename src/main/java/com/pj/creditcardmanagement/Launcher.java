package com.pj.creditcardmanagement;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @author PJ Miranda
 *
 */
public class Launcher {

	public void launch() {
		String[] configLocations = new String[] {"applicationContext.xml", "datasource.xml"};
		
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(configLocations);
		context.registerShutdownHook();
	}

}