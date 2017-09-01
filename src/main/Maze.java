package main;
import java.util.ArrayList;
import java.util.List;

import datastruct.RC;

public class Maze {
	
	private int mapr, mapc;
	int[][] map;
	private final int[] moveR = {0, -1, 0, 1};
	private final int[] moveC = {-1, 0, 1, 0};
	private final int[] moveRDia = {-1, 1, -1, 1};
	private final int[] moveCDia = {-1, -1, 1, 1};
	private boolean[][] visited;
	private List<RC> deque;
	
	public int[][] gen(int r, int c) {
		this.mapr = r;
		this.mapc = c;
		map = new int[mapr][mapc];
		RC start = new RC((int)(Math.random()*mapr),(int)(Math.random()*mapc));
		visited = new boolean[mapr][mapc];
		deque = new ArrayList<RC>();
		deque.add(start);
		while(!deque.isEmpty()) {
			dfs(deque.remove((int)(Math.random() * deque.size())));
		}
		return map;
	}

	private void dfs(RC cur) {
		if(!visited[cur.r][cur.c]) {
			deque.add(cur);
			visited[cur.r][cur.c] = true;
			map[cur.r][cur.c] = 1;
		}
		int[] order = genOrder();
		for(int i : order) {
			int newR = cur.r + moveR[i];
			int newC = cur.c + moveC[i];
			RC next = new RC(newR, newC);
			if(
				newR > 0 && newR < mapr-1 &&
				newC > 0 && newC < mapc-1 &&
				!visited[newR][newC] &&
				check(next, cur, moveR[i], moveC[i])
			) {
				dfs(next);
				break;
			}
		}
	}

	private boolean check(RC child, RC parent, int dr, int dc) {
		RC a, b;
		if(dr != 0) {
			a = new RC(parent.r, parent.c-1);
			b = new RC(parent.r, parent.c+1);
		}else{
			a = new RC(parent.r-1, parent.c);
			b = new RC(parent.r+1, parent.c);
		}
		for(int i = 0; i < 4; i++) {
			RC newrc = new RC(child.r + moveR[i], child.c + moveC[i]);
			if(!newrc.equals(parent) && map[newrc.r][newrc.c] == 1)
				return false;
			newrc = new RC(child.r + moveRDia[i], child.c + moveCDia[i]);
			if(!newrc.equals(a) && !newrc.equals(b) && map[newrc.r][newrc.c] == 1)
				return false;
		}
		return true;
	}

	public static boolean chance(double c) {
		return (Math.random() < c / 100.0)?true:false;
	}

	private int[] genOrder() {
		int[] order = {0, 1, 2, 3};
		for(int i = 3; i >= 1; i--) {
			int swap = (int)(Math.random() * (i+1));
			int temp = order[i];
			order[i] = order[swap];
			order[swap] = temp;
		}
		return order;
	}
	
}
