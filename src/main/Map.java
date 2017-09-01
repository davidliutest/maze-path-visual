package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import datastruct.RC;
import io.Button;
import io.Click;

public class Map {
	
	private Application app;
	public static BufferedImage block, open, star, red;
	private int mapr, mapc;
	private float scale;
	private float co, ro;
	private int td;
	private Maze maze;
	private Pathfinder path;
	private List<Button> b;
	private boolean[][] press;
	private Button noPath;
	
	private int[][] map;
	
	public Map(Application app) {
		this.app = app;
		BufferedImage sheet;
		try {
			sheet = ImageIO.read(Map.class.getResource("/tiles.png"));
			block = sheet.getSubimage(0, 0, 16, 16);
			open = sheet.getSubimage(16, 0, 16, 16);
			star = sheet.getSubimage(32, 0, 16, 16);
			red = sheet.getSubimage(48, 0, 16, 16);
		} catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		mapr = 100;
		mapc = 100;
		setScale(1.0f);
		maze = new Maze();
		map = maze.gen(mapr, mapc);
		path = new Pathfinder(this);
		press = new boolean[mapr][mapc];
		b = new ArrayList<Button>();
		b.add(
			new Button(
				app, block, 0.9f, 0.1f, 160, 60, "Pathfind",
				new Click() {public void click() {path.seek();}}
			)
		);
		b.add(
			new Button(
				app, block, 0.9f, 0.2f, 160, 60, "New Maze",
				new Click() {
					public void click() { 
						path.clearStars();
						path.clear();
						map = maze.gen(mapr, mapc); 
					}
				}
			)
		);
		noPath = new Button(app, red, 0.9f, 0.3f, 160, 60, "No path", new Click() {public void click() {}});
		b.add(noPath);
		noPath.hide = true;
	}
	
	public void render(Graphics g) {
		int cs = (int)Math.max(0,co/td);
		int ce = (int)Math.min(mapc,(co+app.getWidth()*0.8f)/td+1);
		int rs = (int)Math.max(0,ro/td);
		int re = (int)Math.min(mapr,(ro+app.getHeight())/td+1);
		for(int r = rs; r < re; r++) {
			for(int c = cs; c < ce; c++) {			
				Rectangle b = new Rectangle((int)(c*td-co),(int)(r*td-ro),(int)(td),(int)(td));
				if(!app.mouse().left() && !app.mouse().right() && press[r][c])
					press[r][c] = false;
				if(!press[r][c] && b.x < app.getWidth()*0.8f) {
					if(app.mouse().left() && b.contains(app.mouse().mx(), app.mouse().my()) && (map[r][c] == 1 || map[r][c] == 3)) {
						map[r][c] = 2;
						path.starSwitch = !path.starSwitch;
						if(!path.starSwitch) {
							if(path.stara != null) map[path.stara.r][path.stara.c] = 1;
							path.stara = new RC(r, c);
						}else{
							if(path.starb != null) map[path.starb.r][path.starb.c] = 1;
							path.starb = new RC(r, c);
						}
						path.clear();
						press[r][c] = true;
					}
					if(app.mouse().right() && b.contains(app.mouse().mx(), app.mouse().my()) && (map[r][c] == 0 || map[r][c] == 1)) {
						map[r][c] = (map[r][c]==0)?1:0;
						press[r][c] = true;
					}
				}
				BufferedImage tile;
				if(map[r][c] == 0)
					tile = block;
				else if(map[r][c] == 1)
					tile = open;
				else if(map[r][c] == 2)
					tile = star;
				else
					tile = red;
				g.drawImage(tile, (int)(c*td-co),(int)(r*td-ro),(int)(td),(int)(td),null);	
			}
		}
		g.setColor(Color.lightGray);
		g.fillRect((int)(app.getWidth()*0.8f), 0, (int)(app.getWidth()*0.2f), app.getHeight());
		g.setColor(Color.black);
		g.setFont(new Font("Arial", Font.BOLD, 24));
		Iterator<Button> it = b.iterator();
		while(it.hasNext()) {it.next().render(g);}
		if(path.pathStatus == 2)
			noPath.hide = false;
		else
			noPath.hide = true;
		cam();
	}
	
	public void cam() {
		if(!app.mouse().left() && !app.mouse().right()) {
			if(app.keys().l)
				co-=5;
			if(app.keys().r)
				co+=5;
			if(app.keys().u)
				ro-=5;
			if(app.keys().d)
				ro+=5;
			int wheel = app.mouse().wheel();
			if(wheel != 0) {
				float ratio = 0.1f * wheel;
				float scalen = scale + ratio;
				if(scalen > 0.5f && scalen <= 15.0f) {
					float tdo = td;
					setScale(scalen);
					float mx = app.mouse().mx();
					float my = app.mouse().my();
					co = (co + mx) /  tdo * td - mx;
					ro = (ro + my) / tdo * td - my;
				}
				app.mouse().wheelReset();
			}
		}else{
			app.mouse().wheelReset();
		}
	}
	
	public void setScale(float n) {scale = n; td = (int)(16 * scale);}
	
	public int[][] map() {return map;}
	public int mapr() {return mapr;}
	public int mapc() {return mapc;}
	
}
