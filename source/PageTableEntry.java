/**
 * Page table entry class contains the attributes like
 * page frame number, virtual page number, referenced bit
 * modified bit and page load time in to the page table
 * @author rerupaka
 *
 */
public class PageTableEntry {
	
	private int pfn;
	private int vpn;
	private int referenced;
	private int modified;
	private int loadTime;
	
	// Getters and setters for the attributes
	public int getPfn() {
		return pfn;
	}
	public void setPfn(int pfn) {
		this.pfn = pfn;
	}
	public int getVpn() {
		return vpn;
	}
	public void setVpn(int vpn) {
		this.vpn = vpn;
	}
	public int getReferenced() {
		return referenced;
	}
	public void setReferenced(int referenced) {
		this.referenced = referenced;
	}
	public int getModified() {
		return modified;
	}
	public void setModified(int modified) {
		this.modified = modified;
	}
	public int getLoadTime() {
		return loadTime;
	}
	public void setLoadTime(int loadTime) {
		this.loadTime = loadTime;
	}

}
