package datastruct;
import java.util.Comparator;

public class RCNCompare implements Comparator<RCN>{
	public int compare(RCN rcn1, RCN rcn2) {return rcn1.heuristic - rcn2.heuristic;}
}