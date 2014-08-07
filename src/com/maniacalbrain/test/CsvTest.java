package com.maniacalbrain.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CsvTest {
	public float btcMaintenance;
	public float btcMining;
	public float btcElse;
	public float ixcMining;
	public float ixcElse;
	public float nmcMining;
	public float nmcElse;
	public float dvcMining;
	public float dvcElse;
	public float ghsBuy;
	public float ghsSell;
	public float bleh;

	
	public static void main(String[] args){
		
		CsvTest obj = new CsvTest();
		obj.sortCSV();
	}
	
	public void sortCSV(){
		String csv = "C:/Users/Nagle/Desktop/transactions.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		
		try{
			br= new BufferedReader(new FileReader(csv));
			while((line = br.readLine()) != null){
				String[] cex = line.split(cvsSplitBy);
				
				if(!(cex[2].equals("Symbol"))){
				switch(cex[2]){
				case "BTC":					
					if(cex[4].equals("maintenance")){
						btcMaintenance += Float.parseFloat(cex[1]);
					}
					else if(cex[4].equals("mining")){
						btcMining += Float.parseFloat(cex[1]);
					}
					else{					
						btcElse += Float.parseFloat(cex[1]);													
					}
					break;
				case "IXC":
					if(cex[4].equals("mining")){
						ixcMining += Float.parseFloat(cex[1]);
					}else{
						ixcElse += Float.parseFloat(cex[1]);						
					}
					break;
				case "NMC":
					if(cex[4].equals("mining")){
						nmcMining += Float.parseFloat(cex[1]);
					}else{
						nmcElse += Float.parseFloat(cex[1]);						
					}
					break;
				case "DVC":
					if(cex[4].equals("mining")){
						dvcMining += Float.parseFloat(cex[1]);
					}else{
						dvcElse += Float.parseFloat(cex[1]);						
					}
					break;
				case "GHS":
					if(cex[4].equals("buy")){
						ghsBuy += Float.parseFloat(cex[1]);
					}else{
						ghsSell += Float.parseFloat(cex[1]);
					}
					break;					
					default:
						bleh += Float.parseFloat(cex[1]);
				}		
				
				}
			}
		}catch(FileNotFoundException fnfe){
			fnfe.printStackTrace();
		}catch(IOException ioe){
			ioe.printStackTrace();
		}finally{
			if(br != null){
				try{
					br.close();
				}catch(IOException ioe){
					ioe.printStackTrace();
				}
			}
		}
		
		System.out.println(btcMining + "     " + btcMaintenance +"     " + btcElse);
		System.out.println(nmcMining + "     " + nmcElse);
		System.out.println(ixcMining + "     " + ixcElse);
		System.out.println(dvcMining + "     " + dvcElse);
		System.out.println(ghsBuy + "     " + ghsSell);
		System.out.println(bleh);

	}
	


}
