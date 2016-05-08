package com.iamlishuai.flappybird;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.geom.Area;
import java.util.Random;

import javax.swing.ImageIcon;

public class Pipe extends Thread {

	private static final int PIPE_WIDTH = 52;
	//λ��
	private int x;
	private int y;
	
	//�ܵ����ñ�־
	private int isReset = 0;
	
	//�ƶ��ٶ� 
	private int v;
	
	//�ܵ�ͼƬ
	private Image top;
	private Image bottom;
	
	//�����ʼ��ʱ��λ��
	private int oldx;
	private int oldy;
	
	//���췽��
	public Pipe(int x, int y,int v){
		this.x = x;
		this.y = y;
		this.v = v;
		
		oldx = x;
		oldy = y;
		
		top = new ImageIcon(getClass().getResource("/Pipe_Top.png")).getImage();
		bottom = new ImageIcon(getClass().getResource("/Pipe_Bottom.png")).getImage();
	}
	
	//���ƹܵ�
	public void drawSelf(Graphics g){
		g.drawImage(top, x, y - top.getHeight(null), top.getWidth(null), top.getHeight(null), null);
		g.drawImage(bottom, x, y + GameUI.w , bottom.getWidth(null), bottom.getHeight(null) , null);
	}
	
	//�ƶ�
	public void move(){
		
		if(GameUI.flag == GameUI.GAME_INIT){
			return;
		}
		if(x > -PIPE_WIDTH){  //-52
			x--;
		}
		
	}
	
	//���ùܵ�λ��
	public void resetPipe(){
		if(x <= -PIPE_WIDTH){
			x = 4 * GameUI.d - PIPE_WIDTH;
			y = new Random().nextInt(322 - GameUI.w) + GameUI.w + 10;
			isReset = 0;
		}
	}
	
	//�Ƿ�ײ��С��
	public boolean isBirdDied(Bird bird){
		
		//����ܵ���λ��,4����ֱ������ϣ����£����£�����
		int[] xpoints1 = {x, x, x + top.getWidth(null), x + top.getWidth(null)};
		int[] ypoints1 = {y - top.getHeight(null), y, y, y - top.getHeight(null)};
		
		//����ܵ���λ�� 
		int[] xpoints2 = {x, x, x + bottom.getWidth(null), x + bottom.getWidth(null)};
		int[] ypoints2 = {y + GameUI.w, y + GameUI.w + bottom.getHeight(null),
							y + GameUI.w + bottom.getHeight(null), y + GameUI.w };
		
		/* ������������Ķ�ά����� */
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
	
	//С���Ƿ�ͨ��
	public boolean isBirdPass(Bird bird){
		
		if(bird.getX() > x && isReset == 0){
			isReset = 1;
			return true;
		}else{
			return false;
		}
		
	}
	
	//���¿�ʼ
	public void reStart(){
		x = oldx;
		y = oldy;
		isReset = 0;
	}
	
	//�߳�
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
