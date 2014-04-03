import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Map.Entry;


public class feature_words {
	
	
	
	
	public static void  getFeatures (String filename , int index) throws IOException
	{
		FileReader fr= new FileReader("/home/navya/Downloads/mypersonality_final/mypersonality_final.csv");
		
		BufferedReader br2= new BufferedReader(fr);
		String currentline="";

		HashMap<String, ArrayList<String>> extrovert = new  HashMap<String, ArrayList<String>>(); // contains key as use id and value as status of thar user
		HashMap<String, Double> tf_extrovert = new  HashMap<String, Double >(); // term frequency of all the words used by the  extrovert users 
		HashMap <String , Double > total_freq= new HashMap <String , Double >(); // term frequency of all the words in the corpus 
		currentline=br2.readLine();
		int total_count=0;
		
		while((currentline=br2.readLine())!=null)
			
		 {
			total_count++;
			// String[] tokens= new String[20];
			 String[] temp_tokens= currentline.split(",");
				int z=19;
				String[] tokens= new String[20];
				int ite= temp_tokens.length-1;
				while(z>1)
				{
					tokens[z]= temp_tokens[ite] ;
					z--;
					ite--;
					
					
				}
				tokens[0]=temp_tokens[0];
				tokens[1]=temp_tokens[1];
				for(int i=2; i<=ite ;i++)
				{
				//	System.out.println(temp_tokens[i]);
					tokens[1] += temp_tokens[i] ; 
				}
			 tokens[1]=tokens[1].replaceAll("\"", "");
			 String[] words = tokens[1].split(" ");
			 for (int i=0;i<words.length;i++)
			 {
				 if (total_freq.get(words[i])==null )
				 {
					
					 total_freq.put((String)words[i], (double)1);
				 }
				 else
				 {
					
					 total_freq.put((String)words[i],total_freq.get(words[i])+1);
				 }
			 }
			
		 }
		System.out.println(total_count);
		fr.close();
		br2.close();
int count_extrovert =0;
FileReader fr2= new FileReader("/home/navya/Downloads/mypersonality_final/mypersonality_final.csv");
TreeMap<Double, String>Y_X_extrovert= new   TreeMap<Double, String>();


BufferedReader br= new BufferedReader(fr2);
currentline=br.readLine();
		while((currentline=br.readLine())!=null)
				
		 {
			String[] temp_tokens= currentline.split(",");
			int z=19;
			String[] tokens= new String[20];
			int ite= temp_tokens.length-1;
			//extracting the 20 columns of each line  into tokens[20]
			while(z>1)
			{
				tokens[z]= temp_tokens[ite] ;
				z--;
				ite--;
				
				
			}
			tokens[0]=temp_tokens[0];
			tokens[1]=temp_tokens[1];
			for(int i=2; i<=ite ;i++)
			{
		
				tokens[1] += temp_tokens[i] ; 
			}
			
			 if (extrovert.get(tokens[0])==null ) 
			 {
				 System.out.println(tokens[index]);
				
				 if (tokens[index].equals("y"))
				 {
					 tokens[1]=tokens[1].replaceAll("\"","") ; 
				 String[]  words= tokens[1].split(" ");
				 ArrayList<String> words_user= new ArrayList<String> ();
				 for (int i=0; i< words.length ; i++)
				 {
					 words_user.add(words[i]);
					 
				 }
				 extrovert.put(tokens[0], words_user) ; 
				 }
				 count_extrovert ++;
			 }
			 else
			 {
				 if (tokens[index].equals("y"))
				 {
										 tokens[1]=tokens[1].replaceAll("\"","") ; 
							 String[]  words= tokens[1].split(" ");
					 
				 
				 ArrayList<String> words_user = extrovert.get(tokens[0]);
				 for (int i=0; i< words.length ; i++)
				 {
					
				 
words_user.add(words[i])	;				 
				 }
				 extrovert.put(tokens[0], words_user) ; 
				 }
			 }
				 
			 
		 }
		// clalculates term frequency in the extroverts corpus 
		 for (Entry<String, ArrayList<String>> entry : extrovert.entrySet())
		 {
			
ArrayList words= entry.getValue() ; 
for (int i=0; i< words.size() ; i++)
{
	if (tf_extrovert.get(words.get(i))==null)
	{
		tf_extrovert.put((String) words.get(i), (double) 1);
		
	}
	else
	{
		tf_extrovert.put((String) words.get(i), tf_extrovert.get((String) words.get(i))+1);
	}
	
}

			 
		 }
		 //used TreeMap x to sort the terms based on the value of x 
		 TreeMap< Double,String> X = new  TreeMap< Double,String> ();
		 
		 for (Entry<String, Double> entry : tf_extrovert.entrySet())
		 {
			 X.put(-(entry.getValue()), entry.getKey());
		 }
		
		 int cout=0;
		 //take the first 200 values from x and sort them in the increasing order of y-x 
		 for (Entry<Double, String> entry : X.entrySet())
		 {
			 if(cout>200)
			 {
				 break;
			 }
			
				 cout++;
			
			 Double val = total_freq.get(entry.getValue());
			Y_X_extrovert.put( (val-entry.getKey()), entry.getValue());
			
			  
		 }
		 int count=0;
		 FileWriter fw= new FileWriter(filename, false);
		
			
			BufferedWriter bre= new BufferedWriter(fw);
		 for (Entry<Double, String> entry :Y_X_extrovert.entrySet()) 
		 {
			 bre.write(entry.getValue());
			 bre.newLine();
			 count++;
			 if(count>=50)
			 {
				 break; 
			 }
		 }
	
		 bre.close();
		 fw.close();
	}
	public static void main(String[] args) throws IOException
	{
		
		getFeatures("/home/navya/Downloads/mypersonality_final/ext_features" ,7) ;
		getFeatures("/home/navya/Downloads/mypersonality_final/neu_features" ,8) ;
		getFeatures("/home/navya/Downloads/mypersonality_final/agr_features" ,9) ;
		getFeatures("/home/navya/Downloads/mypersonality_final/con_features" ,10) ;
		getFeatures("/home/navya/Downloads/mypersonality_final/opn_features" ,11) ; 
	
		
	}
	
}
