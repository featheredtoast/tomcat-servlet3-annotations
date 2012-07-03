package com.test;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

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

/**
 * Servlet implementation class Init
 */
@WebServlet(name="Init",
		urlPatterns={"/url"},
initParams = {
@WebInitParam(name= "log4jFile", value="WEB-INF/properties/log4j.properties")},
loadOnStartup=1)
public class Init extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private TestThread thread;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Init() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    /**
	 * @see Servlet#init(ServletConfig)
	 */
    @Override
	public void init(ServletConfig config) throws ServletException {
    	super.init(config);
		String prefix =  getServletContext().getRealPath("/");
		String file = getInitParameter("log4jFile");
        PropertyConfigurator.configure(prefix+file);
        this.thread = new TestThread();
        new Thread(this.thread).start();
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		thread.stop();
	}

}
