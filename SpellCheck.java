import java.io.*;
import java.util.*;
import java.util.regex.*;

public class SpellCheck {
	
	HashMap<String, Integer> dict = new HashMap<String, Integer>();
	int flag =0, editflag=0, freq;
	String matched;
	
	public SpellCheck() throws Exception{
		BufferedReader br = new BufferedReader(new FileReader("big.txt"));
		Pattern p = Pattern.compile("\\p{Alpha}+");
		Matcher m;
		String word, line;
		int wordvalue;
		while((line = br.readLine())!=null){
			m = p.matcher(line);
			while(m.find()){
				word = m.group();
				word = word.toLowerCase();
				if(dict.containsKey(word)){
					wordvalue = dict.remove(word);
					dict.put(word, ++wordvalue);
				}
				else
					dict.put(word,1);					
			}
		}
	//	System.out.println(dict.keySet());
	//	System.out.println(dict.values());
	}

	public ArrayList<String> newwords(String s){
		ArrayList<String> words = new ArrayList<String>();
		int i, getfreq;
		String flagstring;
		//substitution
		for(i=0; i<s.length(); i++)
			for(char c = 'a'; c<='z'; c++){
				flagstring = s.substring(0,i) + c + s.substring(i+1);
				if(dict.containsKey(flagstring)){
					flag=1;
					getfreq = dict.get(flagstring);
					if(getfreq > freq){
						matched = flagstring;
						freq = getfreq;
					}
				}
				words.add(flagstring);
			}
		//insertion
		for(i=0; i<s.length(); i++)
			for(char c = 'a'; c<='z'; c++){
				flagstring = s.substring(0,i) + c + s.substring(i); 
				if(dict.containsKey(flagstring)){
					flag=1;
					getfreq = dict.get(flagstring);
					if(getfreq > freq){
						matched = flagstring;
						freq = getfreq;
					}
				}
				words.add(flagstring);
			}
		
		//deletion
		for(i=0; i<s.length(); i++){
			flagstring = s.substring(0,i) + s.substring(i+1);
			if(dict.containsKey(flagstring)){
				flag=1;
				getfreq = dict.get(flagstring);
				if(getfreq > freq){
					matched = flagstring;
					freq = getfreq;
				}
			}
			words.add(s);
		}
			
		
		//swapping
		for(i=0; i<s.length()-1; i++){
			flagstring = s.substring(0,i) + s.charAt(i+1) + s.charAt(i) + s.substring(i+2);
			if(dict.containsKey(flagstring)){
				flag=1;
				getfreq = dict.get(flagstring);
				if(getfreq > freq){
					matched = flagstring;
					freq = getfreq;
				}
			}
			words.add(s);
		}
		if(flag == 1 && editflag==0)
			System.out.println(matched);
		return words;		
	}
	
		
	public static void main(String[] args) throws Exception{
		Scanner sc = new Scanner(System.in);
		SpellCheck sp = new SpellCheck();
		String s;
		ArrayList<String> words;
		while((s = sc.nextLine()) != null){
		//	System.out.println(s);
			sp.editflag = 0;
			sp.flag = 0;
			sp.freq = 0;
			words = sp.newwords(s);
			if(sp.flag ==0){
				sp.editflag=1;
				sp.freq=0;
				for(String S : words){
					sp.newwords(S);
				}
				if(sp.freq == 0)
					System.out.println(s);
				else
					System.out.println(sp.matched);
			}
				
				
				
		}
		sc.close();
	}
}
