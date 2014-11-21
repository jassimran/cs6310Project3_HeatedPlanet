package controllers;


/**
 * Singleton object used to create instances of AbastractControl objects.
 * @author jsoto
 *
 */
public class AbstractControlFactory {
	
	// singleton instance
	private static AbstractControlFactory abstractControlFactory;
	
	// control types/ids
	public static final int MAB = 0; // master-consumer-producer initiative
	public static final int AB 	= 1; // producer-consumer initiative
	public static final int BA 	= 2; // consumer-producer initiative
	
		
	private AbstractControlFactory() {
		// TODO Auto-generated constructor stub
	}
	
	public static synchronized AbstractControlFactory getInstance() {
		if(abstractControlFactory == null) {
			abstractControlFactory = new AbstractControlFactory();
		}
		return abstractControlFactory;
	}
	
	/**
	 * Factory method for creating controls with different initiatives.
	 * @param controlId the id of the control type to create
	 * @return an abstract control
	 */
	public AbstractControl createControl(int controlId) {
		AbstractControl control = null;
		
		if(controlId == MAB) {
			control = new MasterControl();
		}
		
		if(controlId == AB) {
			control = new SimulationControl();
		}
		
		if(controlId == BA) {
			control = new PresentationControl();
		}
		
		return control;
	}

}
