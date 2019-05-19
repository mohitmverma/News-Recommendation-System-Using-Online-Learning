/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

/**
 *
 * @author mohit
 */
public class Cluster {
    
    int size;
    double[] mean;
    private int[] sum;
    private int[] numberOfSelections;
    private int[] sumReward;
    private int nofFeatures;
    private int nofAds;
    int totalReward;
    int n;
    Cluster(int nofads,int noffeatures){                
        sumReward = new int[nofads];
        numberOfSelections = new int[nofads];
        this.nofFeatures = noffeatures;
        sum = new int[noffeatures];
        mean = new double[noffeatures];
        nofAds = nofads;
        size = 0;
        this.totalReward = 0;
    }
    
    public void addMember(double[] array){              // updating the cluster
        size += 1;
       
        for(int i=0;i<this.nofFeatures;i++){
            sum[i] += array[i];
            mean[i] = Math.round(((double)sum[i])/size); 
        }
        
    }
    
    // finding the best suitable article for user
    public int recommend(double[] array,int i){
        int ad;
        double avgReward;
        double delta_i;
        double upperBound,max;
        max= 0;
        ad = 0;
        for(int j=0;j<nofAds;j++){
            if(numberOfSelections[j]>0){
                avgReward = (double)sumReward[j]/numberOfSelections[j];
                delta_i = Math.sqrt(Math.log(n+1)/numberOfSelections[j]);
                upperBound = avgReward + 0.85*delta_i;
            }
            else{
                upperBound = 100000;
            }
            
            if(upperBound>max){
                max = upperBound;
                ad = j;
            }
                    
        }
        
        numberOfSelections[ad] += 1;
        sumReward[ad] += array[nofFeatures+ad];
        totalReward += array[nofFeatures+ad];
        n++;
        
        if(array[nofFeatures+ad]==1)
            return 1;
        else
            return 0;
    }
   
}
