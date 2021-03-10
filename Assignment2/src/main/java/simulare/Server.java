package simulare;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable,Comparable<Server>{

	private BlockingQueue<Client> clienti = new ArrayBlockingQueue<Client>(1000);          //coada de clienti
	private AtomicInteger waitingTime;                                                    //timpul de asteptare la coada
	private int id;                                                                       //id
	private boolean flag=true;                                                            //flag pentru metoda run    
	private Client firstClient = new Client(0,0,0);                                       //retine clientul din fruntea cozii
	private boolean empty=true;                                                           //ne spune daca coada este goala 
	 
	public Server(int id) {
		waitingTime= new AtomicInteger(0);
		this.id=id;
	}
	
	public void addClient(Client t) {
		
    	 waitingTime.set(t.getProcessingTime()+waitingTime.get());        //timul de asteptare la coada este egal cu cat a fost plus timpul de procesare al noului client
    	 t.setWaitTime(waitingTime.get());                            //se seteaza cat timp o sa astepte clientul la coada 
    	 clienti.add(t);                                   //adaugarea clientului in coada

	}
	
	public void run() {
		while(flag) {
			 

	
			try {
				firstClient = new Client(0,0,0);                //se initializeaza firstClient cu o valoare speciala de client
				empty=true;                                     //se seteaza empty pe true
				Client t = clienti.take();                      //se extrage primul element din coada. Daca coada este goala se pune threadul la somn pana o sa aiba un element
			
				t.setProcesat(true);                        //incepe procesarea clientului
				firstClient=t;                              //in firstClient se pune clientul extras 
				if(clienti.isEmpty()) {              //se verifica daca coade este acum goala. daca este empty devine false deoarece chiar daca structura de coada este goala mai avem un element memorat in firstClient
					empty=false;
				}
			
			
				
			    Thread.sleep(t.getProcessingTime()*1000); //punem threadul la somn pentru un timp egal cu timpul de procesare al clientului extras
			    t.setTerminat(true);                    //marcam clientul ca terminat de procesat
			  
			    
			    
			    
			    
			    
			    
			    
			    
			    
			    
			    
			    
			    
			    
			    
			    
			   
	
				
			} catch (InterruptedException e) {
				
			
			}
			
		}
		
	}

	
	public int compareTo(Server o) {
		int a;
		a=this.waitingTime.get()-o.waitingTime.get();
		if(a==0) {
			a=this.id-o.getId();
		}
		
		return a;
	}
	
	public String toString() {
		String r="";
		for(Client i: clienti) {
			r=r+i.toString()+" ";
		}
		return r;
	}
	
	public int getWaitingTime() {
		return waitingTime.get();
	}
	
	public int getId() {
		return id;
	}

	public BlockingQueue<Client> getClienti() {
		return clienti;
	}

	public void setClienti(BlockingQueue<Client> clienti) {
		this.clienti = clienti;
	}
	
	
	public int computeWaitingTime() {                       //calculeaza timpul de asteptare al cozii ca suma din timpii de procesare al clientilor din coada
		int time=0;
		for(Client i : clienti) {
			time=time+i.getProcessingTime();
		}
		time=time+firstClient.getProcessingTime();
		return time;
	}
	
	public Client getFirstClient() {
		return firstClient;
	}

	

	public void setWaitingTime() {                            //seteaza timpul de asteptare
		waitingTime.set(computeWaitingTime());
	}
	
	public void setFlag(boolean flag) {
		this.flag=flag;
	}

	public boolean isEmpty() {
		return empty;
	}

	
	
	
	
	
	
	
	
	
	
}