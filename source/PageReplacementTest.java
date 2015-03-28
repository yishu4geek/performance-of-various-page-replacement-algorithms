import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;

public class PageReplacementTest {

	public static void main(String[] args) {
		int noOfPageFrames = 0;
		String repAlgorithm = null;
		PageTable pageTable = new PageTable();
		List<PageTableEntry> pageTableEntries = pageTable.getPageTableList();

		File file = new File(args[0]);
		FileInputStream fileInputStream;
		try {
			String fileLine;
			fileInputStream = new FileInputStream(file);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

			// Read a single line from the input file
			fileLine = bufferedReader.readLine();
			String[] pages = fileLine.split("\\t", -1);
			
			// Set the number of frames
			noOfPageFrames = Integer.parseInt(pages[1]);

			fileLine = bufferedReader.readLine();
			
			// Set the page replacement algorithm
			String[] algos = fileLine.split("\\t", -1);
			repAlgorithm = algos[1];

			while((fileLine = bufferedReader.readLine()) != null){
				String[] values = fileLine.split("\\t", -1);
				String operation = values[0];
				if(operation.equalsIgnoreCase("START")){
					continue;
				}
				else if(operation.equalsIgnoreCase("END")){
					break;
				}
				else if(operation.equalsIgnoreCase("CLOCK_TICK"))
				{
					pageTable.performClockTick();
					continue;
				}

				// Increment the global timer
				PageTable.globalTimer++;
				boolean pageHitFlag = false;
				int requestVpn = Integer.parseInt(values[1]);
				//System.out.println("First field "+ values[0] + " Second field "+ values[1]);
				Iterator<PageTableEntry> iterator = pageTableEntries.iterator();
				if(pageTableEntries.size() > 0){
					// Search the inverted page table for the VPN
					while(iterator.hasNext()){
						PageTableEntry pageTableEntry = iterator.next();

						// If the record is found
						if(pageTableEntry.getVpn() == requestVpn){
							pageHitFlag = true;

							// Modify the R and/or M values
							pageTable.modifyPageTableEntry(pageTableEntry, operation, 0, false);

							// Record the page hit
							PageStatistics.hit++;
							System.out.println(""+ requestVpn+"\t"+operation+ "\t"+"PAGE_HIT");
							break;
						}
					}

					// If the record is not found
					if(!pageHitFlag){

						// Record the page fault
						System.out.println(""+ requestVpn+"\t"+operation+ "\t"+"PAGE_FAULT");

						// If the page table has some empty slots, add the new record to page table
						if(pageTableEntries.size() < noOfPageFrames){
							pageTable.addPageTableEntry(requestVpn, pageTableEntries.size(), operation);
						}
						else{
							PageTableEntry pte = null;

							// Run the page replacement algorithm
							PageReplacementAlgorithms algorithms = new PageReplacementAlgorithms(pageTable);
							if(repAlgorithm.equalsIgnoreCase("FIFO")){
								pte = algorithms.fifo();
							}
							else if(repAlgorithm.equalsIgnoreCase("LRU")){
								pte = algorithms.lru();
							}
							else{
								pte = algorithms.clock();
							}

							if(pte.getModified() > 0){
								// Record the page eviction
								System.out.println(""+ pte.getVpn()+"\t"+pte.getPfn()+ "\t"+"PAGE_EVICT");
							}

							// Add the details of new VPN to the PFN
							pageTable.modifyPageTableEntry(pte, operation, requestVpn, true);

							// Record the page load
							System.out.println(""+ requestVpn+"\t"+pte.getPfn()+ "\t"+"PAGE_LOAD");
						}
					}
				}
				else{
					System.out.println(""+ requestVpn+"\t"+operation+ "\t"+"PAGE_FAULT");
					pageTable.addPageTableEntry(requestVpn, 0, operation);
				}

			}
			bufferedReader.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}		
		catch(IOException ioe){
			ioe.printStackTrace();
		}

		System.out.println("");
		System.out.println("Statistics");
		System.out.println("----------");
		System.out.println("Number of requests "+"\t"+PageTable.globalTimer);
		System.out.println("Number of hits "+"\t"+"\t"+PageStatistics.hit);
		System.out.println("Hit Ratio "+"\t"+"\t"+BigDecimal.valueOf(PageStatistics.getHitRatio()).setScale(3, RoundingMode.CEILING));
	}

}
