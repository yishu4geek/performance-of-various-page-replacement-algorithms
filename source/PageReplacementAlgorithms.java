import java.util.Iterator;
import java.util.List;

public class PageReplacementAlgorithms {

	PageTable pageTable;
	private int clockLocation;

	public PageReplacementAlgorithms(PageTable pageTable){
		this.pageTable = pageTable;
	}

	/**
	 * First IN First Out Algorithm
	 * Selects the page table entry from the page table
	 * that was loaded in the distant past and returns it
	 * @return - PageTableEntry
	 */
	public PageTableEntry fifo(){
		PageTableEntry selectedPte = null;

		List<PageTableEntry> pteList = pageTable.getPageTableList();
		Iterator<PageTableEntry> iterator = pteList.iterator();
		selectedPte = iterator.next();
		int lowLoadTime = selectedPte.getLoadTime();

		while(iterator.hasNext()){
			//System.out.println("The low load time is "+lowLoadTime);
			PageTableEntry nextPte = iterator.next();
			
			if(nextPte.getLoadTime() < lowLoadTime){
				lowLoadTime = nextPte.getLoadTime();
				selectedPte = nextPte;
			}

		}

		return selectedPte;
	}

	/**
	 * Least Recently Used Algorithm
	 * Selects the page table entry from the page table that 
	 * was used in the most distant past and returns it
	 * @return - PageTableEntry
	 */
	public PageTableEntry lru(){
		PageTableEntry selectedPte = null;

		List<PageTableEntry> pteList = pageTable.getPageTableList();
		Iterator<PageTableEntry> iterator = pteList.iterator();
		selectedPte = iterator.next();
		int lowReference = selectedPte.getReferenced();

		while(iterator.hasNext()){
			PageTableEntry nextPte = iterator.next();
			if(nextPte.getReferenced() < lowReference){
				lowReference = nextPte.getReferenced();
				selectedPte = nextPte;
			}
		}	
		return selectedPte;
	}

	/**
	 * Clock Algorithm
	 * Selects the page table entry from the page table that
	 * was not referenced in the last clock tick and returns it
	 * @return - PageTableEntry
	 */
	public PageTableEntry clock(){

		PageTableEntry selectedPte = null;

		List<PageTableEntry> pteList = pageTable.getPageTableList();
		int listSize = pteList.size();
		
		do{
			clockLocation = (clockLocation+1)%listSize;
			selectedPte = pteList.get(clockLocation);
			if(selectedPte.getReferenced() < PageTable.lastClockTick){
				break;
			}
		}while(true);

		return selectedPte;
	}

}
