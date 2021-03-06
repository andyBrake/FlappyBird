package com.iamlishuai.flappybird;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.geom.Area;
import java.util.Random;

import javax.swing.ImageIcon;

public class Pipe extends Thread {

	private static final int PIPE_WIDTH = 52;
	//位置
	private int x;
	private int y;
	
	//管道重置标志
	private int isReset = 0;
	
	//移动速度 
	private int v;
	
	//管道图片
	private Image top;
	private Image bottom;
	
	//保存初始化时的位置
	private int oldx;
	private int oldy;
	
	//构造方法
	public Pipe(int x, int y,int v){
		this.x = x;
		this.y = y;
		this.v = v;
		
		oldx = x;
		oldy = y;
		
		top = new ImageIcon(getClass().getResource("/Pipe_Top.png")).getImage();
		bottom = new ImageIcon(getClass().getResource("/Pipe_Bottom.png")).getImage();
	}
	
	//绘制管道
	public void drawSelf(Graphics g){
		g.drawImage(top, x, y - top.getHeight(null), top.getWidth(null), top.getHeight(null), null);
		g.drawImage(bottom, x, y + GameUI.w , bottom.getWidth(null), bottom.getHeight(null) , null);
	}
	
	//移动
	public void move(){
		
		if(GameUI.flag == GameUI.GAME_INIT){
			return;
		}
		if(x > -PIPE_WIDTH){  //-52
			x--;
		}
		
	}
	
	//重置管道位置
	public void resetPipe(){
		if(x <= -PIPE_WIDTH){
			x = 4 * GameUI.d - PIPE_WIDTH;
			y = new Random().nextInt(322 - GameUI.w) + GameUI.w + 10;
			isReset = 0;
		}
	}
	
	//是否撞到小鸟
	public boolean isBirdDied(Bird bird){
		
		//上面管道的位置,4个点分别是左上，左下，右下，右上
		int[] xpoints1 = {x, x, x + top.getWidth(null), x + top.getWidth(null)};
		int[] ypoints1 = {y - top.getHeight(null), y, y, y - top.getHeight(null)};
		
		//下面管道的位置 
		int[] xpoints2 = {x, x, x + bottom.getWidth(null), x + bottom.getWidth(null)};
		int[] ypoints2 = {y + GameUI.w, y + GameUI.w + bottom.getHeight(null),
							y + GameUI.w + bottom.getHeight(null), y + GameUI.w };
		
		/* 构造上下区间的二维多边形 */
		Polygon p1 = new Polygon(xpoints1, ypoints1, 4);
		Polygon p2 = new Polygon(xpoints2, ypoints2, 4);
		
		//Area a1 = new Area(p1);
		//Area a2 = new Area(p2);
		
		if(p1.intersects(bird.getX(), bird.getY(), 35, 30) 
		  || p2.intersects(bird.getX(), bird.getY(), 35, 35)
		  || bird.getY() > 450){
			return true;
		}else{
			return false;
		}
		
	}
	
	//小鸟是否通过
	public boolean isBirdPass(Bird bird){
		
		if(bird.getX() > x && isReset == 0){
			isReset = 1;
			return true;
		}else{
			return false;
		}
		
	}
	
	//重新开始
	public void reStart(){
		x = oldx;
		y = oldy;
		isReset = 0;
	}
	
	//线程
	@Override
	public void run(){
		
		while(true){
			move();
			resetPipe();
			try {
				sleep(v);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
