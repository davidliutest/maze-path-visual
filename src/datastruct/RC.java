package datastruct;

public class RC {
	
	public int r, c;
	
	public RC(int r, int c) {
		this.r = r;
		this.c = c;
	}
	
	public boolean equals(RC rc) {return(this.r == rc.r && this.c == rc.c)?true:false;}
	
	public double dist(RC rc) {return Math.sqrt(Math.pow(this.c - rc.c, 2) + Math.pow(this.r - rc.r, 2));}
	
	public boolean isNeighbor(RC rc) {
		 int dc = Math.abs(this.c - rc.c);
		 int dr = Math.abs(this.r - rc.r);
		 return(dc == 1 && dr == 0)||(dc == 0 && dr == 1)?true:false;
	}
	
}
