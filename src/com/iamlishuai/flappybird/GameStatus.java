package com.iamlishuai.flappybird;

import java.awt.Graphics;
import java.awt.Image;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;

import javax.swing.ImageIcon;

public class GameStatus {

	//��Ϸ��ʼ����
	private Image ready;
	private Image begin;
	
	//��Ϸ����
	private Image[] score;
	
	//��Ϸ��������
	private Image gameOver;
	private Image scoreBoard;
	private Image[] endScore;
	private Image restart;
	private Image list;
	
	//���췽��
	public GameStatus(){
		
		ready = new ImageIcon(getClass().getResource("/ready.png")).getImage();
		begin = new ImageIcon(getClass().getResource("/begin.png")).getImage();
		
		score = new Image[4];
		score[0] = new ImageIcon(getClass().getResource("/0.png")).getImage();
		
		endScore = new Image[4];
		gameOver = new ImageIcon(getClass().getResource("/gameover.png")).getImage();
		scoreBoard = new ImageIcon(getClass().getResource("/ScoreBoard.png")).getImage();
		
		restart = new ImageIcon(getClass().getResource("/Buttons.png")).getImage();
		list = new ImageIcon(getClass().getResource("/Buttons_02.png")).getImage();
		
	}
	
	//�������¿�ʼͼƬ
	public void setRestart(){
		restart = new ImageIcon(getClass().getResource("/Buttons_01.png")).getImage();
	}
	
	//������Ϸδ��ʼ�������С������Ļ���
	public void drawSelf(Graphics g){
		
		if(GameUI.flag == GameUI.GAME_INIT){
			
			//��Ϸδ��ʼ
			g.drawImage(ready,246,80,ready.getWidth(null),ready.getHeight(null),null);
			g.drawImage(begin,246,200,begin.getWidth(null),begin.getHeight(null),null);
			
		}else if(GameUI.flag == GameUI.GAME_RUNNING){
			
			//��Ϸ������
			showStartScore(g);
			
		}else{
			//��Ϸ����
			g.drawImage(gameOver,396 - gameOver.getWidth(null) / 2 ,80,gameOver.getWidth(null),
																	gameOver.getHeight(null),null);
			g.drawImage(scoreBoard, 396 - scoreBoard.getWidth(null) / 2,200,scoreBoard.getWidth(null),
																	scoreBoard.getHeight(null),null);
			g.drawImage(restart,225,400,restart.getWidth(null),restart.getHeight(null),null);
			g.drawImage(list, 405, 400, list.getWidth(null), list.getHeight(null), null);
			showEndScore(g);
		}
		
	}
	
	//��ʾ��Ϸ�����еĵ÷�
	public void showStartScore(Graphics g){
		
		//���÷���ת��Ϊ�ַ�����
		String s = String.valueOf(GameUI.getScore());
		char[] ss = s.toCharArray();
		
		int start = 396 - ss.length * 20;
		
		//��ÿ���ַ������жϣ���ʾ��Ӧ��ͼƬ
		for(int i = 0; i < ss.length; i++){
		
			score[i] = new ImageIcon(getClass().getResource("/" + ss[i] + ".png")).getImage();
			g.drawImage(score[i], start + i * 40, 30, 40, 60, null);
			
		}
		
	}
	
	//��ʾ��Ϸ������ĵ÷ֺ���߷�
	public void showEndScore(Graphics g){
		
		//���ɼ����ļ��б���ĳɼ��Ƚϣ��������ļ��еĳɼ����Ž��ɼ�д���ļ�
		FileOutputStream fos;
		FileInputStream fis;
		int max = GameUI.getScore();
		int is = 0;
		try {
			
			fis = new FileInputStream("MaxScore.txt");
			byte[] b = new byte[100];
			int has = fis.read(b);
			String stringScore = new String(b,0,has);
			is = Integer.valueOf(stringScore);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(max <= is){
			scoreBoard = new ImageIcon(getClass().getResource("/ScoreBoard.png")).getImage();
			max = is;
		}else{
			scoreBoard = new ImageIcon(getClass().getResource("/NewScoreBoard.png")).getImage();
			try {
				
				fos = new FileOutputStream("MaxScore.txt");
				PrintStream ps = new PrintStream(fos);
				ps.print(max);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
		//���÷���ת��Ϊ�ַ�����
		String s = String.valueOf(GameUI.getScore());
		char[] ss = s.toCharArray();

		int start = 396 - ss.length * 12;
		
		//��ÿ���ַ������жϣ���ʾ��Ӧ��ͼƬ
		for (int i = 0; i < ss.length; i++) {

			endScore[i] = new ImageIcon(getClass().getResource("/mini" + ss[i] + ".png")).getImage();
			g.drawImage(endScore[i], start + i * 25, 253, 25, 32, null);

		}
		
		//��ʾ��߷֣��Ƚ���߷�ת�����ַ����飩
		String s1 = String.valueOf(max);
		char[] ss1 = s1.toCharArray();
		
		int start1 = 396 - ss1.length * 12;
		
		for (int i = 0; i < ss1.length; i++) {

			endScore[i] = new ImageIcon(getClass().getResource("/mini" + ss1[i] + ".png")).getImage();
			g.drawImage(endScore[i], start1 + i * 25, 320, 25, 32, null);

		}
		
	}
	
	
}
