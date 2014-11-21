package controllers;


/**
 * Singleton object used to create instances of AbastractControl objects.
 * @author jsoto
 *
 */
public class AbstractControlFactory {
	
	// singleton instance
	private static AbstractControlFactory abstractControlFactory;
		
	private AbstractControlFactory() {
		// TODO Auto-generated constructor stub
	}
	
	public static synchronized AbstractControlFactory getInstance() {
		if(abstractControlFactory == null) {
			abstractControlFactory = new AbstractControlFactory();
		}
		return abstractControlFactory;
	}

}
