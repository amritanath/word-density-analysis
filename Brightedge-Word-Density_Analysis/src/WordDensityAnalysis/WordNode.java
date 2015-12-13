package WordDensityAnalysis;

import java.util.ArrayList;
import java.util.List;

public class WordNode {
	
	private String val;
	private int degree;
	private List<WordNode> children;
	
	public WordNode(String val){
		this.val=val;
		this.degree=1;
		this.children=new ArrayList<WordNode>();
	}
	
	public void setVal(String val){
		this.val=val;
	}
	
	public void setDegree(int degree){
		this.degree=degree;
	}
	
	public String getVal(){
		return val;
	}
	
	public int getDegree(){
		return degree;
	}
	
	public List<WordNode> getChildren(){
		return children;
	}
}
