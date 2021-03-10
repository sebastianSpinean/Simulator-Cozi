package simulare;



public class Client implements Comparable<Client> {
	private int arrivalTime;                        //timpul de sosire in coada
	private int processingTime;                     //timpul de procesare  
	private int id;                                 //id  
	private boolean procesat;                       //marcheaza momentul cand clientul incepe sa fie procesat
	private boolean terminat;                       //marcheaza momentul cand clientul a fost terminat de procesat
	private int waitTime;                           //timpul petrecut de client in coada
	
	public Client() {
		
	}
	
    public Client(int id, int arrivalTime, int processingTime) {
    	this.arrivalTime=arrivalTime;
    	this.processingTime=processingTime;
    	this.id=id;
    	procesat=false;
    	waitTime=0;
    	terminat=false;
    	
		
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getProcessingTime() {
		return processingTime;
	}

	public void setProcessingTime(int processingTime) {
		this.processingTime = processingTime;
	}

	
	public int compareTo(Client o) {
		return this.arrivalTime-o.arrivalTime;
	}
	
	public String toString() {
		return "("+id+","+arrivalTime+","+processingTime+")";
	}
	
	public synchronized void decrementProcessingTime() {                  //decrementeaza timpul de procesare
		processingTime--;
	}

	public boolean isProcesat() {
		return procesat;
	}

	public void setProcesat(boolean procesat) {
		this.procesat = procesat;
	}

	public int getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}

	public boolean isTerminat() {
		return terminat;
	}

	public void setTerminat(boolean terminat) {
		this.terminat = terminat;
	}
	
	public int getId() {
		return id;
	}
	
    
    
	
	

}