package com.test;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

class TestThread implements Runnable {

	private boolean active = true;
	private Logger log = Logger.getLogger(this.getClass());
	@Override
	public void run() {
		log.debug("I'm alive!");
		System.out.println("alive!");
		// TODO Auto-generated method stub
		while(active) {
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				active = false;
			}
			
			log.debug("I'm alive!");
			System.out.println("alive!");
			
			if(!active) {
				break;
			}
		}
	}
	
	public void stop() {
		active = false;
	}
}

@WebListener()
public class Init implements javax.servlet.ServletContextListener {
       
	private TestThread thread;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
        thread.stop();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		String prefix = arg0.getServletContext().getRealPath("/");
		String file = "WEB-INF/properties/log4j.properties";
        PropertyConfigurator.configure(prefix+file);
        this.thread = new TestThread();
        new Thread(this.thread).start();
	}

}
