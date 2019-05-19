/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author mohit
 */
public class Main {
    
    private double[][] myArray;
    private Cluster[] clusters;
    private int nofAds;
    private int nofFeatures;
    Main(String location,int nofads,int noffeatures){
          myArray = new double[10000][nofads+noffeatures];
          this.nofAds = nofads;
          this.nofFeatures = noffeatures;
          clusters = new Cluster[nofads];
          Scanner scanIn = null;
          String inputLine = "";
          int row = 0;
          try{
              scanIn = new Scanner(new BufferedReader(new FileReader(location)));
              while(scanIn.hasNextLine()){
                  inputLine = scanIn.nextLine();
                  String[] inArray = inputLine.split(",");
                  for(int x = 0;x<inArray.length;x++){
                      
                      myArray[row][x] = Integer.parseInt(inArray[x]);
                  }
                  row++;
              }
          }catch(Exception e){
              System.out.println("File not found");
              System.out.println(e);
          }

    }
    
    public void init(){
        Random random = new Random();
        
        // Randomly choose the intial clusters positions 
        
        for(int i=0;i<this.nofAds;i++){
            clusters[i] = new Cluster(this.nofAds,this.nofFeatures);
            clusters[i].addMember(Arrays.copyOfRange(myArray[random.nextInt(10000)],0,nofFeatures));  
        }
    }
    
    public double distance(double[] a1,double a2[]){  
       
        int a=0;
        double D;
        
        for(int i=0;i<nofFeatures;i++){
            if(a1[i]==a2[i])
                a++;
        }
        D = (a)/(10);
        return 1-D;         // returns distance b/w cluster and new record
    }
    
    public int start(){
        double min = 10000;
        int mind = 0;
        double d = 0;
        int reward = 0;
        
        for(int i=0;i<10000;i++){
            min = 10000.0;
            for(int j=0;j<nofAds;j++){
              d = distance(myArray[i],clusters[j].mean);//Math.sqrt(1.5*Math.log(clusters[j].size)/(i+1));
              if(d<min){
                  mind = j;
                  min = d;
              }
            }
             clusters[mind].addMember(myArray[i]);
             reward += clusters[mind].recommend(myArray[i],i);
          
            // System.out.println(Integer.toString(i+1)+" "+reward);
        }
       
        return reward;
    }
}
