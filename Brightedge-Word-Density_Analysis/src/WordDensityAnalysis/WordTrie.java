package WordDensityAnalysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

public class WordTrie {
	
	private WordNode root;
	private HashMap<String, WordNode> hm;
	private HashMap<String, Integer> keyWordsMap;
	
	public WordTrie(){
		hm=new HashMap<String,WordNode>();
		keyWordsMap=new HashMap<String, Integer>();
		root=new WordNode("*");
	}
	
	/*
	 * add one word to tree*/
	public void addWord(String word){
		if(!hm.containsKey(word)) {
			hm.put(word,new WordNode(word));
			root.getChildren().add(hm.get(word));
		}else{
			WordNode node=hm.get(word);
			node.setDegree(node.getDegree()+1);
		}
	}
	
	/*
	 * add multiple words to tree*/
	public void addWord(String[] words){
		WordNode pre=root;
		for(int i=0;i<words.length;i++){
			WordNode cur=scan(words[i],pre);
			if(cur==null){
				cur=new WordNode(words[i]);
				pre.getChildren().add(cur);
			}
			pre=cur;
		}
	}
	
	/*
	 * get top k most frequencies of keywords
	 * the default number of result is five*/
	public List<String> getKeyWordsOfTopK(int k){
		for(WordNode startNode:root.getChildren()){
			getKeyWordWithMultipleWords(startNode,"");
		}
		
		TreeSet<Integer> ts=new TreeSet<Integer>();
		HashMap<Integer, List<String>> map=new HashMap<Integer, List<String>>();
		for(String key:keyWordsMap.keySet()){
			int frequency=keyWordsMap.get(key);
			if(!map.containsKey(frequency)) map.put(frequency, new ArrayList<String>());
			map.get(frequency).add(key);
			ts.add(frequency);
			if(ts.size()>k) ts.pollFirst();
		}
		
		List<String> list=new ArrayList<String>();
		while(!ts.isEmpty()){
			int frequency=ts.pollLast();
			System.out.println("keyword:"+map.get(frequency)+"  fre: "+frequency);
			list.addAll(map.get(frequency));
		}
		return list;
	}
	
	private WordNode scan(String word,WordNode root){
		for(WordNode node:root.getChildren()){
			if(node.getVal().equals(word)){
				return node;
			}
		}
		return null;
	}
	
	private void getKeyWordWithMultipleWords(WordNode cur,String s){
		if(s.length()==0){
			keyWordsMap.put(s+cur.getVal(), cur.getDegree());
		}else{
			keyWordsMap.put(s+" "+cur.getVal(), cur.getDegree());
		}
		for(WordNode child:cur.getChildren()){
			if(s.length()==0) getKeyWordWithMultipleWords(child,s+cur.getVal());
			else getKeyWordWithMultipleWords(child,s+" "+cur.getVal());
		}
	}
	
	public void display(){
		for(WordNode cur:root.getChildren()){
			if(cur.getChildren().size()==0) System.out.println(cur.getVal()+" "+cur.getVal().length());
			for(WordNode child:cur.getChildren()){
				System.out.println(cur.getVal()+"-->"+child.getVal());
			}
		}
	}
}
