import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jsat.ARFFLoader;
import jsat.DataSet;
import jsat.classifiers.CategoricalResults;
import jsat.classifiers.ClassificationDataSet;
import jsat.classifiers.Classifier;
import jsat.classifiers.DataPoint;
import jsat.classifiers.MajorityVote;
import jsat.classifiers.Rocchio;
import jsat.classifiers.bayesian.NaiveBayes;
import jsat.classifiers.neuralnetwork.BackPropagationNet;
import jsat.classifiers.neuralnetwork.DReDNetSimple;
import jsat.classifiers.neuralnetwork.LVQ;
import jsat.classifiers.neuralnetwork.LVQLLC;
import jsat.classifiers.neuralnetwork.Perceptron;
import jsat.classifiers.neuralnetwork.RBFNet;
import jsat.linear.distancemetrics.DistanceMetric;

public class main {

	public static void main(String[] args) {
        File file = new File("test.arff");
        DataSet dataSet = ARFFLoader.loadArffFile(file);
        
        File file1 = new File("idk.arff");
        DataSet dataSet1 = ARFFLoader.loadArffFile(file1);
        
        
        //We specify '0' as the class we would like to make the target class. 
        ClassificationDataSet cDataSet = new ClassificationDataSet(dataSet, 0);
        
        ClassificationDataSet cDataSet1 = new ClassificationDataSet(dataSet1, 0);
        
        int errors = 0;
        List<Classifier> voters = new ArrayList<>();
        voters.add(new NaiveBayes());
        voters.add(new Perceptron());
        voters.add(new Rocchio());
        voters.add(new RBFNet());
        
        
        Classifier classifier = new BackPropagationNet(1);//MajorityVote(voters);
        classifier.trainC(cDataSet);
        
        for(int i = 0; i < dataSet1.getSampleSize(); i++)
        {
            DataPoint dataPoint = cDataSet.getDataPoint(i);//It is important not to mix these up, the class has been removed from data points in 'cDataSet' 
            int truth = cDataSet1.getDataPointCategory(i);//We can grab the true category from the data set
            
            //Categorical Results contains the probability estimates for each possible target class value. 
            //Classifiers that do not support probability estimates will mark its prediction with total confidence. 
            CategoricalResults predictionResults = classifier.classify(dataPoint);
            int predicted = predictionResults.mostLikely();
            if(predicted != truth)
                errors++;
            System.out.println( i + "| True Class: " + truth + ", Predicted: " + predicted + ", Confidence: " + predictionResults.getProb(predicted) );
        }
        
        System.out.println(errors + " errors were made, " + 100.0*errors/dataSet.getSampleSize() + "% error rate" );
	}
	
	
	

}
