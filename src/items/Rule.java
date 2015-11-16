package items;

public class Rule implements Comparable{
	
	public String rule="";
	public double conf=0;
	
	public Rule(String rule, double conf){
		this.rule=rule;
		this.conf=conf;
	}

	@Override
	public int compareTo(Object o) {
		Rule oRule=(Rule)o;
		double res=oRule.conf-this.conf;
		if(res==0){
			return 0;
		}else if(res>0){
			return 1;
		}else {
			return -1;
		}
	}
}
