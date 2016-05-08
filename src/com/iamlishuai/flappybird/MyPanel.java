package com.iamlishuai.flappybird;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MyPanel extends JPanel implements Runnable {

	//С�����
	private Bird bird;	
	
	//�ܵ�����
	private Pipe[] pipe;
	
	//����������
	private PlaySounds player;
	
	//����ͼƬ
	private Image background;	
	private Image ground;
	
	//��Ϸ״̬
	private GameStatus gs;
	private boolean rp = true;
	
	//���췽��
	public MyPanel(Bird bird,Pipe[] pipe,GameStatus gs,PlaySounds player){
		
		//��ʼ������
		background = new ImageIcon(getClass().getResource("/Background.png")).getImage();
		ground = new ImageIcon(getClass().getResource("/Ground.png")).getImage();
		
		//��ʼ��С��
		this.bird = bird;
		
		
		//��ʼ������������
		this.player = player;
		
		//��ʼ���ܵ� 
		this.pipe = new Pipe[4];
		for(int i = 0; i < this.pipe.length; i++){
			this.pipe[i] = pipe[i];
		}
		
		this.gs = gs;
		
	}
	

	@Override
	public void paint(Graphics g){
		super.paint(g);			
		
		if(GameUI.flag == GameUI.GAME_INIT){
			bird.reStart();
		}
		for(int i = 0; i < pipe.length; i++){
			if(GameUI.flag == GameUI.GAME_INIT){
				pipe[i].reStart();
			}
		}
		//���Ʊ���
		g.drawImage(background,0,0,800,600,null);
		//���ƹܵ� 
		for(int i = 0; i < pipe.length; i++){
			pipe[i].drawSelf(g);
			
			if(pipe[i].isBirdDied(bird)){
				player.setIsPlay(3);
				GameUI.flag = GameUI.GAME_OVER;
			};
			
			if(pipe[i].isBirdPass(bird)){
				player.setIsPlay(2);
				GameUI.addScore();
			}
		}
		//���Ʊ���
		g.drawImage(ground, 0, 480, 800, 112, null);
		
		
		//����С��
		bird.drawSelf(g);
		
		gs.drawSelf(g);
	}

	@Override
	public void run() {

		while(true){
			
			long start = System.currentTimeMillis();
			//����Ϸ״̬��Ϊ��Ϸ����ʱ��ֻ�ػ�һ��(Ϊ����ʾ��Ϸ�÷ְ�)
			if(GameUI.getGameFlag() == GameUI.GAME_OVER){
				/* ������Ҫ�ö�һ���̣߳�����flag����һ���߳��޸ģ�����߳��޷�ͬ����
				 * ��Game flag��ͬ����������Ҳ���Խ��������� */
				/*
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}*/
				
				if(rp){
					repaint();
					rp = false;
				}
				continue;
			}
			repaint();
			rp = true;
			
			long end = System.currentTimeMillis();
			
			//ȷ����Ϸ֡��Ϊ60
			if(end - start < (1000 / 60)){
				try {
					Thread.sleep(1000 / 60 - end + start);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}
	
}
