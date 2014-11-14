package services;


/**
 * Singleton service used for calculating accuracy gap sizes. An accuracy gap size is the amount of samples 
 * between persisted and ignored samples in a sample set based on an accuracy value. For example, in a
 * sample set with 100 samples and an accuracy of 10% the gap size is 10; that is we are going to ignore
 * 10 samples between each persisted sample. Based on this example, the consumer of the service should
 * persist samples [1 11 22 33 44 55 66 77 88 99].
 * @author jsoto
 *
 */
public class AccuracyService {
	
	// singleton instance
	private static AccuracyService serviceInstance;
		
	private AccuracyService() {
		// TODO Auto-generated constructor stub
	}
	
	public static synchronized AccuracyService getInstance() {
		if(serviceInstance == null) {
			serviceInstance = new AccuracyService();
		}
		return serviceInstance;
	}
	
	/**
	 * Calculates the number of samples to leave between used samples in a sample set based on a given accuracy value.
	 * @param totalSamples the total number of samples in the sample set
	 * @param accuracy the desired accuracy to use ( between 1 and 100)
	 * @return the number of samples to leave between used samples
	 */
	public synchronized int calculateGapSize(int totalSamples, int accuracy) {
		int totalSamplesToUse = (int) (((double) accuracy / (double) 100.0) * totalSamples);
		int numberOfGaps = totalSamplesToUse - 1;
		return (numberOfGaps > 0)? (int) ((double) (totalSamples - totalSamplesToUse) / (double) numberOfGaps) : (totalSamples - totalSamplesToUse);
	}
	
	

}
