/**
 * This class calculates the hit ratio and
 * miss ratio of the given input data
 * @author rerupaka
 *
 */
public class PageStatistics {
	
	public static int hit = -1;
	
	/**
	 * Calculates the hit ratio
	 * @return
	 */
	public static double getHitRatio(){
		return ((double)hit/PageTable.globalTimer);
	}
	
	/**
	 * Calculates the miss ratio
	 * @return
	 */
	public static double getMissRatio(){
		return (1- ((double)hit/PageTable.globalTimer));
	}

}
