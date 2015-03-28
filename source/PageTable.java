import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author rerupaka
 * PageTable contains a list of PageTableEntries
 * including a global timer and last clock tick
 *
 */
public class PageTable {

	static int globalTimer;
	static int lastClockTick;
	List<PageTableEntry> pageTableList; 

	/**
	 * PageTable constructor which contains a static global timer 
	 * and static last clock tick timer
	 */
	public PageTable(){
		globalTimer = 0;
		lastClockTick = 0;
		pageTableList = new ArrayList<PageTableEntry>();
	}

	public List<PageTableEntry> getPageTableList() {
		return pageTableList;
	}

	public void setPageTableList(List<PageTableEntry> list) {
		this.pageTableList = list;
	}

	public void addPageTableEntry(PageTableEntry pageTableEntry){
		pageTableList.add(pageTableEntry);
	}

	/**
	 * Adds a page table entry to page table
	 * @param vpn
	 * @param pfn
	 * @param operation
	 */
	public void addPageTableEntry(int vpn, int pfn, String operation){
		PageTableEntry newPageTableEntry = new PageTableEntry();
		newPageTableEntry.setVpn(vpn);
		newPageTableEntry.setPfn(pfn);
		newPageTableEntry.setLoadTime(PageTable.globalTimer);
		if(operation.equalsIgnoreCase("READ")){
			newPageTableEntry.setReferenced(PageTable.globalTimer);
			newPageTableEntry.setModified(0);
		}
		else{
			newPageTableEntry.setReferenced(PageTable.globalTimer);
			newPageTableEntry.setModified(PageTable.globalTimer);
		}
		pageTableList.add(newPageTableEntry);
	}
	
	/**
	 * Modifies page table entry in a page table
	 * @param pte
	 * @param operation
	 * @param vpn
	 * @param changeLoadTime
	 */
	public void modifyPageTableEntry(PageTableEntry pte, String operation, int vpn, boolean changeLoadTime){
		
		if(vpn != 0){
			pte.setVpn(vpn);
		}
		
		if(changeLoadTime){
			pte.setLoadTime(PageTable.globalTimer);
		}
		
		// If the operation is a READ operation, set the referenced bit to global timer
		// and set the modified bit to zero
		if(operation.equalsIgnoreCase("READ")){
			pte.setReferenced(PageTable.globalTimer);
			pte.setModified(0);
		}
		else{
			// If the operation is a WRITE operation, set the referenced bit to global timer
			// and set the modified bit also to the global timer
			pte.setReferenced(PageTable.globalTimer);
			pte.setModified(PageTable.globalTimer);
		}
	}
	
	/**
	 * Performs clock tick
	 */
	public void performClockTick(){
		//System.out.println("Performing clock tick");
		PageTable.lastClockTick = PageTable.globalTimer;	
	}
}
