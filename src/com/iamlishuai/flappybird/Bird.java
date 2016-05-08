package com.iamlishuai.flappybird;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

/*
 *  С����
 *  ӵ���������塢��������
 */

public class Bird extends Thread {

	// ���λ��
	private int x;
	private int y;
	// ���ֳ�ʼλ��
	private int oldx;
	private int oldy;
	//private int ooldx;
	private int ooldy;
	// ���ͼƬ
	private Image[] bird = {
			new ImageIcon(getClass().getResource("/Birds_01.png")).getImage(),
			new ImageIcon(getClass().getResource("/Birds_02.png")).getImage(),
			new ImageIcon(getClass().getResource("/Birds_03.png")).getImage() };
	
	private int imageIndex = 0;
	// ��������ٶ� ���������ٶ�
	private int downv;
	private int upv;
	private int upvn = 0;   /* ������Ĭ���ٶ� */
	// �������Ĵ�С
	//private int up;
	// �½�������
	private static int DOWN = 0;
	private static int FLY = 1;
	// �½��������ı�־
	private int flag = Bird.FLY;

	// ����������������ٶ�
	private double g = 0.0003;

	// ���췽��
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

	// ����ͼƬ����
	public void setImageIndex() {
		if (imageIndex == 2) {
			imageIndex = 0;
		} else {
			imageIndex++;
		}
	}

	// ����С��ͼƬ
	public void drawSelf(Graphics g) {
		
		//�û�����бһ���Ƕȣ����������бЧ��
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
		//�����ʷ�����б������
		if (GameUI.flag == GameUI.GAME_RUNNING) {
			g2.rotate(-a, x + 17, y + 17);
		}
		*/

	}

	// �л��������½�״̬
	public void setStatus() {
		if (flag == Bird.DOWN) {
			flag = Bird.FLY;
		} else {  /* ��ǰ״̬Ϊflay���л�Ϊdown */
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


	// ��ȡС���λ��
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	// ���¿�ʼ
	public void reStart() {

		x = oldx;
		y = oldy;
		//ooldx = x;
		//ooldy = y;

	}

	// �˶�����
	public void move() {

		/* ��Ϸ��û��ʼʱ��ֻ����С��Ķ��� */
		if (GameUI.flag == GameUI.GAME_INIT) {
			setImageIndex();
			return;
		}
		/* ��Ϸ�������޶��� */
		else if (GameUI.flag == GameUI.GAME_OVER) {
			return;
		}

		if (flag == Bird.DOWN) {
			long end = System.currentTimeMillis();
		    long t = (end - GameUI.start);
		    /* �½������ģ��gtƽ�����y�ϵ�λ�� */
			int oy = (int) (ooldy + 0.8 * g * t * t);
			//int oy = (int) (oldy + 0.8 * g * t * t);
			y = oy;
		}
		/* ���������y����1����λ */
		else {
			y--;
			
			long end = System.currentTimeMillis();
		    long t = (end - GameUI.start);
		    /* �����������ٶȣ�ģ��������ϼ��ٶȵ�Ч�� */
			upv += 20 * g * t;
			/* ����ٶȵ��������������л�״̬ */
			if((upvn - 60 * g * t) <= 0){
				setStatus();
			}
			/* С��ɵ��ϱ߽�ʱ����ֹԽ�� */
			if(y <= 0){
				y = 0;
			}
			
		}

		// �ı�С���ͼƬ
		setImageIndex();

	}

	// �̣߳�����һֱ�˶�
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
