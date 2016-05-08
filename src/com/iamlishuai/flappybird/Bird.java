package com.iamlishuai.flappybird;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

/*
 *  小鸟类
 *  拥有自由落体、飞翔能力
 */

public class Bird extends Thread {

	// 鸟的位置
	private int x;
	private int y;
	// 保持初始位置
	private int oldx;
	private int oldy;
	//private int ooldx;
	private int ooldy;
	// 鸟的图片
	private Image[] bird = {
			new ImageIcon(getClass().getResource("/Birds_01.png")).getImage(),
			new ImageIcon(getClass().getResource("/Birds_02.png")).getImage(),
			new ImageIcon(getClass().getResource("/Birds_03.png")).getImage() };
	
	private int imageIndex = 0;
	// 鸟下落的速度 、上升的速度
	private int downv;
	private int upv;
	private int upvn = 0;   /* 上升的默认速度 */
	// 鸟上升的大小
	//private int up;
	// 下降或上升
	private static int DOWN = 0;
	private static int FLY = 1;
	// 下降或上升的标志
	private int flag = Bird.FLY;

	// 自由落体的重力加速度
	private double g = 0.0003;

	// 构造方法
	public Bird(int x, int y, int downv, int upv, int up) {
		this.x = x;
		this.y = y;
		this.oldx = x;
		this.oldy = y;
		this.downv = downv;
		this.upv = upv;
		//this.up = up;
		//this.ooldx = x;
		this.ooldy = y;
		
		this.upvn = upv;
	}

	// 设置图片索引
	public void setImageIndex() {
		if (imageIndex == 2) {
			imageIndex = 0;
		} else {
			imageIndex++;
		}
	}

	// 绘制小鸟图片
	public void drawSelf(Graphics g) {
		
		//让画笔倾斜一定角度，绘制鸟的倾斜效果
		//Graphics2D g2 = (Graphics2D) g;
		/*
		double a = Math.atan((y - ooldy + 0.000001) / 50);
		//double a = Math.atan((y - oldy + 0.000001) / 50);
		if(a > Math.atan(2)){
			a = Math.atan(2);
		}else if(a < Math.atan(-2)){
			a = Math.atan(-2);
		}
		*/
		/*
		if (GameUI.flag == GameUI.GAME_RUNNING) {
			g2.rotate(a, x + 17, y + 17);
		}*/

		g.drawImage(bird[imageIndex], x, y, bird[imageIndex].getWidth(null),
				bird[imageIndex].getHeight(null), null);
		/*
		//将画笔反向倾斜来回正
		if (GameUI.flag == GameUI.GAME_RUNNING) {
			g2.rotate(-a, x + 17, y + 17);
		}
		*/

	}

	// 切换上升和下降状态
	public void setStatus() {
		if (flag == Bird.DOWN) {
			flag = Bird.FLY;
		} else {  /* 当前状态为flay则切换为down */
			flag = Bird.DOWN;
			upv = upvn;
		}
		
		//ooldx = x;
		ooldy = y;
		GameUI.start = System.currentTimeMillis();
	}

	public void setFlyStatus() {
		flag = Bird.FLY;
		//ooldx = x;
		ooldy = y;
		GameUI.start = System.currentTimeMillis();
		upv = upvn;
	}


	// 获取小鸟的位置
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	// 重新开始
	public void reStart() {

		x = oldx;
		y = oldy;
		//ooldx = x;
		//ooldy = y;

	}

	// 运动方法
	public void move() {

		/* 游戏还没开始时，只更新小鸟的动画 */
		if (GameUI.flag == GameUI.GAME_INIT) {
			setImageIndex();
			return;
		}
		/* 游戏结束则无动作 */
		else if (GameUI.flag == GameUI.GAME_OVER) {
			return;
		}

		if (flag == Bird.DOWN) {
			long end = System.currentTimeMillis();
		    long t = (end - GameUI.start);
		    /* 下降情况下模拟gt平方算出y上的位移 */
			int oy = (int) (ooldy + 0.8 * g * t * t);
			//int oy = (int) (oldy + 0.8 * g * t * t);
			y = oy;
		}
		/* 上升情况下y上升1个单位 */
		else {
			y--;
			
			long end = System.currentTimeMillis();
		    long t = (end - GameUI.start);
		    /* 递增上升的速度，模拟产生向上加速度的效果 */
			upv += 20 * g * t;
			/* 如果速度抵消不了引力则切换状态 */
			if((upvn - 60 * g * t) <= 0){
				setStatus();
			}
			/* 小鸟飞到上边界时，防止越界 */
			if(y <= 0){
				y = 0;
			}
			
		}

		// 改变小鸟的图片
		setImageIndex();

	}

	// 线程，让鸟一直运动
	@Override
	public void run() {
		while (true) {
			move();
			try {
				if (flag == Bird.FLY) {
					sleep(upv);
					//sleep(5);
				} else {
					sleep(downv);
					//sleep(5);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
