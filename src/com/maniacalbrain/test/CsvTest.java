package com.maniacalbrain.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class CsvTest {
	Map<String, BigDecimal> btcMiningMap; 					//Map to hold date and mining value
	Map<String, BigDecimal> btcMaintenanceMap;				//Map to hold date and maintenance value
	Map<String, BigDecimal> btcDepositMap;
	
	public BigDecimal btcMining = new BigDecimal(0);		//Variable to hold mining value
	public BigDecimal btcMaintenance = new BigDecimal(0); 	//Variable to hold maintenance value
	public BigDecimal btcDeposit = new BigDecimal(0);
	
	String date = "";										//Variable to hold date while parsing through csv
	private String tempDate;
	ArrayList<String> dateList;
	
	public BigDecimal btcTotalIn = new BigDecimal(0);
	public BigDecimal btcTotalOut = new BigDecimal(0);
	public BigDecimal btcTotalDeposit = new BigDecimal(0);
	
	public BigDecimal btcDefault = new BigDecimal(0);

	
	public static void main(String[] args){
		
		CsvTest obj = new CsvTest();
		obj.sortCSV();
	}
	
	public void sortCSV(){
		String csv = "C:/Users/Nagle/Desktop/transactionsTest.csv";
		
		String line = "";
		String cvsSplitBy = ","; 							
		
		dateList = new ArrayList<String>();
		btcMiningMap = new TreeMap<String, BigDecimal>();
		btcMaintenanceMap = new TreeMap<String, BigDecimal>();
		
		try(BufferedReader br = new BufferedReader(new FileReader(csv))){
			br.readLine();
			while((line = br.readLine()) != null){
				String[] cex = line.split(cvsSplitBy);		//Split line based on position of commas
				

				/*if(cex[0].equals("DateUTC")){
					continue;								//Skip the first line of .CSV file
				}*/
				tempDate = cex[0].substring(0, 10);	//tempDate holds figure to compare it to the currently assigned date
					if(date.equals("")){ 					//If date is unassigned, assign what is in tempDate, happens first time through loop
						date=tempDate;
						
					}
					
					if(tempDate.equals(date)){			//If tempDate is equal to assigned date it is the same day and continue to next if statement
					
					}						
					else {										//If tempDate represents a new days figures
						addDate();						
					}
					
					
					switch(cex[2]){
					case "BTC":
						switch(cex[4]){
						case "mining":
							BigDecimal minTemp = new BigDecimal(cex[1]);
							btcMining = btcMining.add(minTemp); //add the current mining figure to the daily total mining figure
							break;
						case "maintenance":
							BigDecimal mainTemp = new BigDecimal(cex[1]);
							btcMaintenance = btcMaintenance.add(mainTemp); //add the current maintenance figure to the daily total maintenance figure						
							break;
						case "deposit":
							BigDecimal depTemp = new BigDecimal(cex[1]);
							btcDeposit = btcDeposit.add(depTemp);
							break;
						default:
							BigDecimal defTemp = new BigDecimal(cex[1]);
							btcDefault = btcDefault.add(defTemp);
						}
					}
					
					/*if(cex[2].equals("BTC")){		
						if(cex[4].equals("mining")){
							BigDecimal minTemp = new BigDecimal(cex[1]);
							btcMining = btcMining.add(minTemp); //add the current mining figure to the daily total mining figure
						}
						
						else if(cex[4].equals("maintenance")){
							BigDecimal mainTemp = new BigDecimal(cex[1]);
							btcMaintenance = btcMaintenance.add(mainTemp); //add the current maintenance figure to the daily total maintenance figure
						}
					}*/
					
			}
			
		}catch(FileNotFoundException fnfe){
			fnfe.printStackTrace();
		}catch(IOException ioe){
			ioe.printStackTrace();
		}finally{
			//call addDate() to add the last date and value
			addDate();
		}
		
		
		for(String date : dateList){
			BigDecimal btcIn = btcMiningMap.get(date);
			BigDecimal btcOut = btcMaintenanceMap.get(date);
			
			System.out.print(date);
			System.out.print("   ");			
			System.out.print(btcIn);
			System.out.print("   ");
			System.out.print(btcOut);
			System.out.print("   ");
			System.out.print(btcIn.add(btcOut));
			
			System.out.println("");
		}
		System.out.println("");
		System.out.println("Total mined:  " + btcTotalIn);
		System.out.println("Total fees:   " + btcTotalOut);
		System.out.println("Total profit: " + btcTotalIn.add(btcTotalOut));
		System.out.println("Total deposit: "+ btcTotalDeposit);
		System.out.println("Total default: "+ btcDefault);
	}
	
	
	public void addDate(){
		dateList.add(date);
		btcTotalIn = btcTotalIn.add(btcMining);
		btcMiningMap.put(date, btcMining);		//put the previous days date and the mining figure into Mining TreeMap
		btcMining = BigDecimal.ZERO;			//set mining value to 0
		
		btcTotalOut = btcTotalOut.add(btcMaintenance);
		btcMaintenanceMap.put(date, btcMaintenance); //put the previous days date and maintenance figure into Maintenance TreeMap
		btcMaintenance = BigDecimal.ZERO;			//set maintenance value to 0
		
		/*BigDecimal zeroCheck = new BigDecimal(0);
		zeroCheck = BigDecimal.ZERO;
		if(btcDeposit.equals(zeroCheck)){
		
		}else{
			*/btcTotalDeposit = btcTotalDeposit.add(btcDeposit);/*
			btcDepositMap.put(date, btcDeposit);
			*/btcDeposit = BigDecimal.ZERO;	/*		
		}*/
		
		date=tempDate;								//set date to the new date	
		
	}
}
