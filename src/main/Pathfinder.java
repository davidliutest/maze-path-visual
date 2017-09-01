package main;

import java.util.ArrayDeque;
import java.util.PriorityQueue;

import datastruct.RC;
import datastruct.RCN;
import datastruct.RCNCompare;

public class Pathfinder {
	
	private Map map;
	private RCNCompare rccCompare = new RCNCompare();
	private PriorityQueue<RCN> open = new PriorityQueue<RCN>(10, rccCompare);
	private ArrayDeque<RCN> closed = new ArrayDeque<RCN>();
	private ArrayDeque<RC> move;
	private int[] moveR = {-1, 0, 1, 0};
	private int[] moveC = {0, -1, 0, 1};
	public int pathStatus;
	public boolean starSwitch;
	public RC stara, starb;
	
	public Pathfinder(Map map) {
		this.map = map;
	}	
	
	public boolean check() {
		return (stara != null && starb != null)?true:false;
	}
	
	public void clear() {
		if(move != null) {
			while(!move.isEmpty()) {
				if(map.map()[move.peek().r][move.peek().c] == 3) {
					map.map()[move.peek().r][move.peek().c] = 1;
				}
				move.pop();
			}
			pathStatus = 0;
		}
	}
	
	public void seek() {
		if(check()) {
			clear();
			move = path();
			if(move != null) {
				pathStatus = 1;
				for(RC rc : move)
					map.map()[rc.r][rc.c] = 3;
			}else{
				pathStatus = 2;
			}
		}
	}
	
	public int heuristics(int r, int c) {
		return Math.abs(starb.c - c) + Math.abs(starb.r - r);
	}
	
	private boolean collision(int r, int c) {return map.map()[r][c] == 0;}
		
	private ArrayDeque<RC> path() {
		RCN[][] find = new RCN[map.mapr()][map.mapc()];
		for(int r = 0; r < map.mapr(); r++) {
			for(int c = 0; c < map.mapc(); c++) {
				find[r][c] = new RCN(r, c, 0, 0, null);
			}
		}
		closed.clear();
		open.clear();
		open.add(find[stara.r][stara.c]);
		find[stara.r][stara.c].visited = true;
		boolean found = false;
		
		while( !open.isEmpty() ) {
			RCN cur = open.poll();
			closed.add(cur);
			if(cur.r == starb.r && cur.c == starb.c) {
				found = true;
			}else{
				for(int i = 0; i < 4; i++) {
					int newR = cur.r + moveR[i];
					int newC = cur.c + moveC[i];
					if(
						newR >= 0 && newR < map.mapr() && newC >= 0 && newC < map.mapc() && !collision(newR, newC) 
					) {			
						int nextCost = cur.cost + 1;
						find[newR][newC].visited = true;	
						RCN neighbour = find[newR][newC];
						if(nextCost < neighbour.cost) {
							if(open.contains(neighbour))
								open.remove(neighbour);
							if(closed.contains(neighbour))
								closed.remove(neighbour);
						}
						if(!open.contains(neighbour) && !closed.contains(neighbour)) {
							neighbour.cost = nextCost;
							neighbour.heuristic = heuristics(neighbour.r, neighbour.c);
							neighbour.parent = cur;
							open.add(neighbour);
						}
					}
				}
			}
		}
		if(!found) return null;
		ArrayDeque<RC> path = new ArrayDeque<RC>();
		RCN target = find[starb.r][starb.c].parent;
		while(target != find[stara.r][stara.c]) {
			path.push(new RC(target.r, target.c));
			target = target.parent;
		}
		return path;
	}
	
	public void clearStars() {
		stara = null; starb = null;
	}
	
}
