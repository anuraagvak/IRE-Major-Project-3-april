import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;


public class feature_vectors {
	public static ArrayList<String> ext= new ArrayList<String>();
	public static ArrayList<String> positive_words= new ArrayList<String>();
	public static ArrayList<String> negative_words= new ArrayList<String>();
	public static ArrayList<String> top_10000= new ArrayList<String>();
	public static ArrayList<String> top_20000= new ArrayList<String>();
	
 // public static  MaxentTagger tagger = new MaxentTagger("/home/navya/Downloads/stanford-postagger-2014-01-04/models/english-bidirectional-distsim.tagger");
	
	public static void  getfeature_vectors(String filename ) throws IOException
	{
		 ext= new ArrayList<String>();

	FileReader fr= new FileReader(filename);

	BufferedReader br= new BufferedReader(fr);
	String currentline="";
	while ((currentline=br.readLine())!=null)
	{
		if(!ext.contains(currentline))
		ext.add(currentline);
		
	}
	br.close();
	fr.close();
	 fr= new FileReader("/home/navya/Downloads/mypersonality_final/positive-words.txt");

	 br= new BufferedReader(fr);
	 while ((currentline=br.readLine())!=null)
		{
			if(!positive_words.contains(currentline))
				positive_words.add(currentline);
			
		}
	 br.close();
		fr.close();
		 fr= new FileReader("/home/navya/Downloads/mypersonality_final/negative-words.txt");

		 br= new BufferedReader(fr);
		 while ((currentline=br.readLine())!=null)
			{
				if(!negative_words.contains(currentline))
					negative_words.add(currentline);
				
			}
		 br.close();
			fr.close();
		 fr= new FileReader("/home/navya/Downloads/mypersonality_final/top-10000.txt");

		 br= new BufferedReader(fr);
		 while ((currentline=br.readLine())!=null)
			{
				if(!top_10000.contains(currentline))
					top_10000.add(currentline);
				
			}
		 br.close();
			fr.close();
			 fr= new FileReader("/home/navya/Downloads/mypersonality_final/top-20000.txt");

			 br= new BufferedReader(fr);
			 while ((currentline=br.readLine())!=null)
				{
					if(!top_20000.contains(currentline))
						top_20000.add(currentline);
					
				}
			 br.close();
				fr.close();
	}
public static int getDay(int year, int month ,int dd)
{
	 Date date= (new GregorianCalendar(year, month, dd)).getTime();
	   SimpleDateFormat f = new SimpleDateFormat("EEEE");
	     String day=f.format(date);
	    // System.out.println(day);
	     if(day.equals("Saturday") || day.equals("Sunday"))
	     {
	    	 return 1;
	     }
	     else return 0;
	     
//	     return day ;
}
	public static int[] meta_features(String text)
	
	{
		
		char[] punct={'.',',','!' ,'?','"',':',';',',','/','-'};
		int[] count={0,0};
		int punct_count=0;
		int case_count=0;
		java.util.List punct_list = Arrays.asList(punct);
		for(int i=0; i< text.length();i++)
		{
		if(punct_list.contains(text.charAt(i)))	
		{
			
			punct_count++;
		}
		if(Character.isUpperCase(text.charAt(i)))
		{
			
			case_count++;
		}
		}
 		count[0]= punct_count ;
 		count[1]= case_count ;

 		return count;
		
	}
	public static int[] count_negative(ArrayList<String> text)
	{
		int pos=0;
		int neg=0;
		int i=0;
		int[] count={0,0,0,0,0,0 , 0} ; 
				
		for(i=0;i<text.size();i++)
		{
			if(positive_words.contains(text.get(i)))
			  count[0]++;
			if(negative_words.contains(text.get(i)))
				 count[1]++;	
			int rank =top_10000.indexOf(text.get(i));
				if(!(rank ==-1))
				{
					if(rank<=5000) count[2]++;
					else count[3]++;
					
				}
				else
				{
				
					 rank =top_20000.indexOf(text.get(i));
						if(!(rank ==-1))
						{
							if(rank<=15000) count[4]++;
							else count[5]++;
							
						}
				}
					
			if(text.get(i).contains("est")) count[6]++;
			
		}
		//int[] count={pos,neg} ; 
		return count;
		
	}
	public static int[] tagText(String text) throws IOException
	{
		   MaxentTagger tagger = new MaxentTagger("/home/navya/Downloads/stanford-postagger-2014-01-04/models/english-bidirectional-distsim.tagger");
		
		StringReader sr= new StringReader(text); // wrap your String
		BufferedReader br= new BufferedReader(sr);
		System.out.println(text);
		 List<List<HasWord>> sentences =  tagger.tokenizeText(br);
		 String ans="";
		    for (List<HasWord> sentence : sentences) {
		      ArrayList<TaggedWord> tSentence = tagger.tagSentence(sentence);
		    ans =ans + (Sentence.listToString(tSentence, false));
		    }
		    //= Regex.Split(text, " +");
		    String[]toks = ans.split("\\s+") ; 
		    int[] count= new int[2];
		    count[0]=0;
		    count[1]=1;
		    for(int i=0; i< toks.length ; i++)
		    {
		    	String[] tags=toks[i].split("/");
		    	if(tags[1].equals("NNP")); 
		    	{
		    		count[0] +=1;
		    		
		    	}
		    	 if(tags[1].equals("PRP")); 
		    	{
		    		count[1] +=1;
		    		
		    	}
		    } 
	//	int[] count={0,0};
	
		    //System.out.println(count[0]+"  "+count[1]);
		    br.close();
		    sr.close();
		    return count;
		
		
	}
	
	
	public static void testdata()
	{
		
	}
	public static void traindata( String train , String test, int index) throws IOException
	{
		//FileWriter fr = new FileWriter("/home/navya/Downloads/mypersonality_final/train_data.csv" , false);
		FileWriter fr = new FileWriter(train , false);
		 BufferedWriter br = new BufferedWriter(fr);
		
		FileReader fr2= new FileReader("/home/navya/Downloads/mypersonality_final/mypersonality_final.csv");
		BufferedReader br2= new BufferedReader(fr2);
		String currentline="";
		currentline=br2.readLine() ; 
		String cur_user="" ; 
	int testflag=0;
	HashMap<String ,int[]> pos_count= new HashMap<String ,int[]>();
		HashMap<String , ArrayList<String>> users= new HashMap<String ,  ArrayList<String>> ();
		HashMap<String , ArrayList<String>> network_features= new HashMap<String ,  ArrayList<String>> ();
		HashMap<String , ArrayList<String>> time_features= new HashMap<String ,  ArrayList<String>> ();
		while ((currentline=br2.readLine())!=null)
		{
			
			
			
			
			//System.out.println(currentline);
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
		//	String[] tokens= currentline.split(" , ");
			String[] words = tokens[1].split(" ") ;
			if(users.get(tokens[0]) ==null)
			{
				 ArrayList<String> words_user= new ArrayList<String> ();
				 String label;
				 if(tokens[index].equals("y"))
				 {
					 
					 label="1";
				 }
				 else 
					 label="0";
				 words_user.add(label); 
				 for (int i=0; i< words.length ; i++)
				 {
					 words_user.add(words[i]);
					 
				 }
				 ArrayList<String> network= new ArrayList<String>();
				 for(int iter=13; iter<19; iter++ )
				 {
					 network.add(tokens[iter]); 
					 
				 }
				 
				 network_features.put(tokens[0],network ) ; 
				 ArrayList<String> time_user= new ArrayList<String> ();
				 //System.out.println( "tie"+tokens[12]);
				 String[] time_tokens=tokens[12].split(" ");
				 
				 time_user.add(time_tokens[0]+"&"+time_tokens[1]+"&"+time_tokens[2]); 
				 time_features.put(tokens[0], time_user  ) ; 
				users.put(tokens[0],  words_user) ; 
				
				
			}
			else
			{

				 ArrayList<String> words_user = users.get(tokens[0]);
				 for (int i=0; i< words.length ; i++)
				 {
					
			//System.out.println(words[i]);		 
words_user.add(words[i])	;				 
				 }
				 ArrayList<String> time_user= time_features.get(tokens[0]); 
				// System.out.println("time"+tokens[12]);
				 String[] time_tokens=tokens[12].split(" ");
				 //System.out.println("hell"+ time_tokens[2]);
				 time_user.add(time_tokens[0]+"&"+time_tokens[1]+"&"+time_tokens[2]); 
				 time_features.put(tokens[0], time_user  ) ; 
				 users.put(tokens[0],  words_user) ; 
			}
			
		}
		
		 int tot=0;
		 int flags=1;
		 for (Entry<String, ArrayList<String>> entry : users.entrySet())
		 {
			 
			
			// System.out.println(entry.getKey());
			 
			tot++;
				HashMap<String , Integer> count= new HashMap<String , Integer> ();
				String text="";
			for (int it=1 ; it < (entry.getValue()).size() ; it++)
			{
				text= text+entry.getValue().get(it)+" ";
				
				if(count.get(entry.getValue().get(it))==null)
				{
					count.put(entry.getValue().get(it),1);
					
				}
				else
				{
					count.put(entry.getValue().get(it),count.get(entry.getValue().get(it))+1);
				}
				
			}
			//int[] com= tagText(text);
			//System.out.println(com[0]+" "+com[1]);
			String line="";
			if(testflag==0)
			{
			 line=entry.getValue().get(0)+" ";
			}
			else 
			{
				  line="";
			}
			//int[] pos_count1=new int[2];
			
		//	 int[] sent_count= count_negative(entry.getValue()); 
		//	 int[] meta_count= meta_features(text);
			//ArrayList<String> network=  network_features.get(entry.getKey());
			
		//	ArrayList<String> time=  time_features.get(entry.getKey());
			int[] time_fre= {0 , 0, 0, 0};
			int [] day_fre={0,0};
		/*	for (int j=0; j<time.size(); j++ )
			{
			//
				//System.out.println(time.get(j));
				String[] tokens= time.get(j).split("&"); 
				String[] toks_day=tokens[0].split("/");
				int month= Integer.parseInt(toks_day[0]);
				int date= Integer.parseInt(toks_day[1]);
				int year =  Integer.parseInt("20"+toks_day[2]);
				int day = getDay(year , month , date) ; 
				if(day==1)
				{
					day_fre[1]++;
				}
				else  day_fre[0]++;
				if (tokens[2].equals("AM"))
				{
					
					String[]  tokens_hour= tokens[1].split(":") ; 
					if(Integer.parseInt(tokens_hour[0]) <= 6)
					{
						time_fre[0]=time_fre[0]+1 ;
						
					}
					if(Integer.parseInt(tokens_hour[0]) > 6)
					{
						time_fre[1]=time_fre[1]+1 ;
					}
					
				}
				if (tokens[2].equals("PM"))
				{
					
					String[]  tokens_hour= tokens[1].split(":") ; 
					if(Integer.parseInt(tokens_hour[0]) <= 6)
					{
						time_fre[2]=time_fre[2]+1 ;
						
						
					}
					if(Integer.parseInt(tokens_hour[0]) > 6)
					{
						time_fre[3]=time_fre[3]+1 ;
					}
						
				}
			}*/
			
			for(int it=0; it< ext.size(); it++)
			{
				if (count.get(ext.get(it))!=null)
				{
					
						line = line + (it+1)+":"+ count.get(ext.get(it))+" ";
					
					
				}
				else
				{
					
					
						line = line + (it+1)+":"+ 0 +" ";
					
			//		tot++;
					//line = line + "\""+ 0 +"\""+",";
				}
			}
			int it;
			/*for(it=0+ext.size();it <4+ext.size() ; it++)
			{
				line = line + (it+1)+":"+ time_fre[it-ext.size()]+" ";	
			}
			line = line + (it+1)+":"+ sent_count[0]+" "+(it+2)+":"+ sent_count[1]+" "+(it+3)+":"+ sent_count[0]+" "+(it+4)+":"+ sent_count[1]+" "+(it+5)+":"+ sent_count[0]+" "+(it+6)+":"+ sent_count[1]+" "+(it+7)+":"+ sent_count[6]+" ";	
			it=it+7;
			line = line + (it+1)+":"+ day_fre[0]+" "+(it+2)+":"+ day_fre[1]+" ";
			it=it+2;
			line = line + (it+1)+":"+ meta_count[0]+" "+(it+2)+":"+ meta_count[1]+" ";	
			for( it=15+ext.size();it <15+network.size()+ext.size() ; it++)
			{

				if(it== 15+network.size()+ext.size()-1)
				{
				line = line + (it+1)+":"+network.get(it - 15-ext.size());
				}
				else
				{
					line = line + (it+1)+":"+network.get(it-15-ext.size())+" ";
				}
			}*/
			
			//System.out.println(tot);
			
			br.write(line);
			br.newLine();	
			if(tot> 180 && flags==1)
			{
				//testflag=1;
				br.close();
				fr.close();
				
				 fr = new FileWriter(test , false);
				  br = new BufferedWriter(fr);
				  flags=0;
				  
			}
		 }
		br2.close();
		fr2.close();
		br.close();
		fr.close();
		
		
	}
	public static void main(String[] args) throws IOException
	{
		getfeature_vectors("/home/navya/Downloads/mypersonality_final/ext_features");
		traindata( "/home/navya/Downloads/mypersonality_final/ext_traindata","/home/navya/Downloads/mypersonality_final/ext_testdata" ,7);
		
	getfeature_vectors("/home/navya/Downloads/mypersonality_final/neu_features");
	traindata( "/home/navya/Downloads/mypersonality_final/neu_traindata","/home/navya/Downloads/mypersonality_final/neu_testdata" ,8);
	getfeature_vectors("/home/navya/Downloads/mypersonality_final/agr_features");
	traindata( "/home/navya/Downloads/mypersonality_final/agr_traindata","/home/navya/Downloads/mypersonality_final/agr_testdata" ,9);
	getfeature_vectors("/home/navya/Downloads/mypersonality_final/con_features");
	traindata( "/home/navya/Downloads/mypersonality_final/con_traindata","/home/navya/Downloads/mypersonality_final/con_testdata" ,10);
	getfeature_vectors("/home/navya/Downloads/mypersonality_final/opn_features");
	traindata( "/home/navya/Downloads/mypersonality_final/opn_traindata","/home/navya/Downloads/mypersonality_final/opn_testdata" ,11);
	
	}

}
