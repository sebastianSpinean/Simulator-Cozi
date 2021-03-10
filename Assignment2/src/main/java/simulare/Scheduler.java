package simulare;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import java.util.concurrent.BlockingQueue;

public class Scheduler {

	private ArrayList<Server> servers = new ArrayList<Server>();      //lista de cozi
	private int maxNoServers=0;                                      //numar de cozi
	
	
	public Scheduler(int  maxNoServers) {
		this.maxNoServers= maxNoServers;
		
		
		for(int i=0;i<maxNoServers;i++) {                         // se genereaza cozile si se pornesc threadurile
			Server server = new Server(i+1);
			servers.add(server);
			Thread t = new Thread(server);
			t.start();
		}
		
	}
	 
	public void dispachClient(Client t) {            //se distribuie clientii la cozi.
		Collections.sort(servers);		  //se sorteaza crescator dupa timpul de asteptare
		servers.get(0).addClient(t);    //se adauga in coada cu cel mai mic timp de asteptare
		
		
	}
	
	public void afisare(FileWriter myWriter) {
		
		System.out.println(" ");
		try {
			myWriter.write("\n ");
			for(int k=1;k<=maxNoServers;k++) {
				for(Server i: servers) {
					if(i.getId()==k) {
					
						myWriter.write("Queue "+i.getId()+" ");             //se afiseaza numarul cozii
						BlockingQueue<Client> t=i.getClienti();
						if(t.isEmpty() && i.isEmpty()) {                   //daca coada este goala si nici in firstClient nu avem un client atunci coada este inchisa
					
						
							myWriter.write("closed ");
						} 
						else {
							if(i.getFirstClient().getId()!=0) {       //daca firstClient este diferit de clientul (0,0,0) atunci se afiseaza 
								
								Client cl = i.getFirstClient();
						
								myWriter.write(cl+""); 
								
							}
							for(Client l : t) {                    //se afiseaza si restul clientilor
		
								myWriter.write(l+""); 
							}
				     
						}
			
						myWriter.write("\n ");
				
				}
				
			}
		}
			
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	
	
	public void modifyWaitingTime() {     //se modifica timpul de asteptare pentru fiecare coada
		for(Server i : servers) {
			i.setWaitingTime();
		}
		
	}
	
	public void setFlag() {
		for(Server i : servers) {
			i.setFlag(false);
		}
	}
	
	public void intrerupere() {                         //se intrerup threadurile corespunzatoare fiecarei cozi
		
		setFlag();
	
		for(int i=0;i<maxNoServers;i++) {
			servers.get(i).addClient(new Client(1,1,1));
		}
	}
		
	
}

