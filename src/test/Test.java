/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author mohit
 */
class Reader{
    
    private int[][] myArray;
    
    Reader(String location){
          myArray = new int[10000][20];
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
              System.out.println("qq");
              System.out.println(e);
          }
        
          
    
    }
    public int[][] getArray(){
        return myArray;
    }
     
}
class UCB{
    
    private int[][] myArray;
    
    UCB(int[][] array){
        myArray = array;
        
    }
    
    public int Calculate(){              // UCB ALgorithm implementation
        
        int totalReward = 0;
        int[] numberOfSelections = new int[20];
        int[] sumReward = new int[20];
        int ad;
        double avgReward;
        double delta_i;
        
        double upperBound,max;
        for(int i=0;i<10000;i++){
            max= 0;
            ad = 0;
            for(int j=0;j<10;j++){
                if(numberOfSelections[j]>0){
                avgReward = (double)sumReward[j]/numberOfSelections[j];
                delta_i = Math.sqrt(1.5*Math.log(i+1)/numberOfSelections[j]);
                upperBound = avgReward + delta_i;
                }else{
                    upperBound = 100000;
                }
                if(upperBound>max){
                        max = upperBound;
                        ad = j;
                }
  
            }
            numberOfSelections[ad] += 1;
            sumReward[ad] += myArray[i][10+ad];
            totalReward += myArray[i][10+ad];
            // System.out.println(Integer.toString(i+1)+" "+totalReward);
        }
           return totalReward;
    }
  public int random(){
      int totalReward = 0;
        int ad;
        Random rand = new Random();
        for(int i=0;i<10000;i++){
            ad = rand.nextInt(10);
                totalReward += myArray[i][10+ad];
            //System.out.println(Integer.toString(i+1)+" "+totalReward);
        }
           return totalReward;
    }
}

public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        // TODO code application logic here
        /*PrintStream o = new PrintStream(new File("Cluster.dat")); 
        PrintStream console = System.out; 
        System.setOut(o);
        */
        
        
        String location = "/home/mohit/mod.csv";
        Reader reader = new Reader(location);             // To read the dataset file 
        UCB ucb = new UCB(reader.getArray());
       
        System.out.println("No of Clicks through random: " +ucb.random());
        System.out.println("No of Clicks through UCB only: " +ucb.Calculate());
        Main main = new Main(location,10,10);
       
       
      
        main.init();
        System.out.println("No of Clicks through UCB+K-means: "+main.start());
           
    }
        
         
    }
    
