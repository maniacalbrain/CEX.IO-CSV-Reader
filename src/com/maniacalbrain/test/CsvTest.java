package com.maniacalbrain.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

public class CsvTest {
	Map<String, BigDecimal> btcMiningMap; 					//Map to hold date and mining value
	Map<String, BigDecimal> btcMaintenanceMap;				//Map to hold date and maintenance value
	
	public BigDecimal btcMining = new BigDecimal(0);		//Variable to hold mining value
	public BigDecimal btcMaintenance = new BigDecimal(0); 	//Variable to hold maintenance value
	
	String date = "";										//Variable to hold date while parsing through csv
	//ArrayList<String> dateList;

	
	public static void main(String[] args){
		
		CsvTest obj = new CsvTest();
		obj.sortCSV();
	}
	
	public void sortCSV(){
		String csv = "C:/Users/Nagle/Desktop/transactions.csv";
		
		String line = "";
		String cvsSplitBy = ","; 							
		
		//dateList = new ArrayList<String>();
		btcMiningMap = new TreeMap<String, BigDecimal>();
		btcMaintenanceMap = new TreeMap<String, BigDecimal>();
		
		try(BufferedReader br = new BufferedReader(new FileReader(csv))){
			while((line = br.readLine()) != null){
				String[] cex = line.split(cvsSplitBy);		//Split line based on position of commas
				

				if(cex[0].equals("DateUTC")){
					continue;								//Skip the first line of .CSV file
				}
				String tempDate = cex[0].substring(0, 10);	//tempDate holds figure to compare it to the currently assigned date
					if(date.equals("")){ 					//If date is unassigned, assign what is in tempDate, happens first time through loop
						date=tempDate;
						
					}
					
					if(tempDate.equals(date)){			//If tempDate is equal to assigned date it is the same day and continue to next if statement
					
					}						
					else {										//If tempDate represents a new days figures
						btcMiningMap.put(date, btcMining);		//put the previous days date and the mining figure into Mining TreeMap
						btcMining = BigDecimal.ZERO;			//set mining value to 0
						
						btcMaintenanceMap.put(date, btcMaintenance); //put the previous days date and maintenance figure into Maintenance TreeMap
						btcMaintenance = BigDecimal.ZERO;			//set maintenance value to 0
						
						date=tempDate;								//set date to the new date	
						
						
					}
					
					if(cex[2].equals("BTC")){		
						if(cex[4].equals("mining")){
							BigDecimal minTemp = new BigDecimal(cex[1]);
							btcMining = btcMining.add(minTemp); //add the current mining figure to the daily total mining figure
						}
						
						else if(cex[4].equals("maintenance")){
							BigDecimal mainTemp = new BigDecimal(cex[1]);
							btcMaintenance = btcMaintenance.add(mainTemp); //add the current maintenance figure to the daily total maintenance figure
						}
					}
					
			}
			btcMiningMap.put(date, btcMining); //When the loop ends the last date and figues will not have been put into TreeMaps, this is done below (replace with method when this becomes longer)
			btcMining = BigDecimal.ZERO;
			
			btcMaintenanceMap.put(date, btcMaintenance);
			btcMaintenance = BigDecimal.ZERO;	
			
		}catch(FileNotFoundException fnfe){
			fnfe.printStackTrace();
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
		

		System.out.println(btcMining + "     " + btcMaintenance);
		System.out.println(btcMining.add(btcMaintenance));
		System.out.println("");
		
		//System.out.println(dateList.size());
		//for(String dates: dateList){
			//System.out.println(dates);
			
		//}
		for(Map.Entry<String, BigDecimal> dataSet : btcMiningMap.entrySet()){
			System.out.print(dataSet.getKey());
			System.out.print("   ");
			System.out.print(dataSet.getValue());
			System.out.println("");
		}
		System.out.println(btcMiningMap.get("2014-05-17"));
		System.out.println(btcMaintenanceMap.get("2014-05-17"));
	}
}
