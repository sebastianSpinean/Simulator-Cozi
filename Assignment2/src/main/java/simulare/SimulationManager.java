package simulare;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;


public class SimulationManager implements Runnable{

	
	private Scheduler scheduler;    //scheduler
	private ArrayList<Client> clienti = new ArrayList<Client>();         //clientii care asteappta sa fie trimisi la cozi
	private ArrayList<Client> deletedClients = new ArrayList<Client>(); //clientii care au fost deja trimisi la cozi
	private int N;
	private int Q;
	private int simulationTime;
	private int minArrival;
	private int maxArrival;
	private int minService;
	private int maxService;
	private String inputFile;
	private String outputFile;
	
	public SimulationManager(String inputFile, String outputFile) {
		this.inputFile=inputFile;
		this.outputFile=outputFile;
		File pf = new File(this.inputFile);                  //se citesc datele de intrare din fisier
		try {
			Scanner sc = new Scanner(pf);
			N=sc.nextInt();
			Q=sc.nextInt();
			simulationTime=sc.nextInt();
			String line = sc.next();
			String[] splitLine = line.split(",");
			minArrival = Integer.parseInt(splitLine[0]);
			maxArrival = Integer.parseInt(splitLine[1]);
			String line2 = sc.next();
			String[] splitLine2 = line2.split(",");
			minService = Integer.parseInt(splitLine2[0]);
			maxService = Integer.parseInt(splitLine2[1]);
			sc.close();
			
			
			
			
		}
		catch(FileNotFoundException e) {
			System.out.println("Not found input file"); 
			
		}
		scheduler = new Scheduler(Q);            //se instantiaza scheduler
		GenerateNRandomClients();           //se genereaza random clientii
	}
	
	private void GenerateNRandomClients() {
		
		Random rand = new Random();
		int i;
		for(i=1;i<=N;i++) {
			int tArrival = rand.nextInt((maxArrival-minArrival)+1)+minArrival;  //se genereaza un numar random intre minArrival si maxArrival
			int tService = rand.nextInt((maxService-minService)+1)+minService;  //se genereaza un numar random intre minService si maxService
			Client t = new Client(i,tArrival,tService);
	
			clienti.add(t);
		}
		
		Collections.sort(clienti);     // se sorteaza clientii in ordine crescatoare dupa timpul de sosire la cozi
			
	}
	 
	public int averageTime() {                 //se calculeaza suma timpilor de asteptare la coada pentru fiecare client
		int suma=0;
		for(Client i : deletedClients) {
			suma+=i.getWaitTime();
		}
		return suma;
	}
	
	public void deleteClients(int time) {                     //din lista de client se sterg clientii care au fost trimisi la cozi si se adauga in deletedClients
		while(true) {
			if(!clienti.isEmpty()) {
			    if(clienti.get(0).getArrivalTime()==time && clienti!=null) {
			    	deletedClients.add(clienti.get(0));
			    	clienti.remove(0);
				    
			}
			else break;
		}
			else break; 
			}
	}
	
	public boolean noMoreClients() {              //verifica daca mai sunt clienti care nu s-au terminat de procesat dar sunt inca in coada
		boolean flag=true;
		for(Client i : deletedClients) {
			if(i.isTerminat()==false) {
				flag=false;
			}
		}
		return flag;
	}
	
	//@Override
	public void run() {
		int time=0;
		try {
			
	    FileWriter myWriter = new FileWriter(outputFile);	
		
		while(time<=simulationTime) {             //cat timp nu s-a terminat simularea 
			
		
		
			
	
			myWriter.write("Time "+time+"\n");
			for(Client i : clienti) {
				if(i.getArrivalTime()==time) {           //clientii care au timpul de sosire egal cu timpul curent al simularii sunt trimisi intr-o coada
					scheduler.dispachClient(i);
					
				}
			}
			
		
			deleteClients(time);                    //se sterg clientii trimisi in coada
			
	
			myWriter.write("clients ");
			
			for(Client i : clienti) {               //se afiseaza clientii care inca mai asteapta
			
			
				    myWriter.write(i+" ");
			
			}
			scheduler.modifyWaitingTime();                //se modifica timpii de asteptare al cozilor
			scheduler.afisare(myWriter);
			
		
			
			for(Client i : deletedClients) {
					if(i.getArrivalTime()<=time && i.getArrivalTime()>0 && i.isProcesat()==true) {        //se decrementeaza timpul de procesare pentru clientii care sunt acum procesati
	
						i.decrementProcessingTime();
					}
				} 
			
			time++;
			if(noMoreClients() && clienti.isEmpty()) {    //se verifica daca nu mai sunt clienti in asteptare si daca nu mai sunt nici in cozi
				break;
			}
			
			try {
				Thread.sleep(1000);           //se pune la somn
				
			} catch (InterruptedException e) {
			}
		}
	
		myWriter.write("\n\n\nAverage waiting time is "+averageTime()/(float)N);  //se calculeaza timpul mediu de asteptare
	
		myWriter.close();
		
		
		
		scheduler.intrerupere(); //se intrerup threadurile cozilor
		
		}
		catch(IOException e) {
			
		}
		
	}
	
	
	
	public ArrayList<Client> getDeletedClients() {
		return deletedClients;
	}

	public void setDeletedTasks(ArrayList<Client> deletedClients) {
		this.deletedClients = deletedClients;
	}

	public static void main(String[] args) {
		
		
		File pf = new File(args[1]);
		try {
			pf.createNewFile();            //se creaza fisierul in care sa se afiseze datele
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		SimulationManager sim = new SimulationManager(args[0],args[1]);
		Thread t = new Thread(sim);
		t.start();
		
		
		
		
		
		
		
	}	

}
