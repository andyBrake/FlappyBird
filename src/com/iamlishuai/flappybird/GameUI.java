package com.iamlishuai.flappybird;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;

import javax.swing.JFrame;

/**
 * ��UI��
 * @author Dangerous
 *
 */

public class GameUI extends JFrame implements MouseListener, MouseMotionListener {

	/*
	 * ��Ϸ�������
	 * gamePanel : ��Ϸ�����������Ҳ�ǻ��壬��Ҫ������Ϸ����
	 * bird 	 : ��Ϸ�е����ǣ���С��
	 * pipe		 : ��Ϸ�е��ϰ�����ܵ�
	 * score 	 : ��Ϸ�÷�
	 */
	private MyPanel gamePanel;  
	private Bird bird;
	private Pipe[] pipe;
	private static int score;
	
	//�ܵ��з��� ��������pipe�ľ���
	public static final int w = 200;
	//�����ܵ�֮��Ŀ��,����pipe֮��ļ��
	public static final int d = 400;
	
	public static final int GAME_INIT = 0;
	public static final int GAME_RUNNING = 1;
	public static final int GAME_OVER = 2;
	/*
	 * ��Ϸ״̬��־
	 * 0 �� ��Ϸδ��ʼ
	 * 1 �� ��Ϸ������
	 * 2��  ��Ϸ����
	 */
	public static int flag = GameUI.GAME_INIT;
	
	//��Ϸ��ʼʱ��
	public static long start = 0;
	
	//����������(һ��ר�Ų����������߳�)
	private PlaySounds player;
	
	//��Ϸ״̬
	private GameStatus gs;
	
	//�ܵ�λ��
	private int x = 1000;
	private int[] ypoints = { new Random().nextInt(322 - GameUI.w) + GameUI.w + 10,
							  new Random().nextInt(322 - GameUI.w) + GameUI.w + 10,
							  new Random().nextInt(322 - GameUI.w) + GameUI.w + 10,
							  new Random().nextInt(322 - GameUI.w) + GameUI.w + 10  };
	
	public GameUI(){
		
		//��ʼ����Ϸ�÷�
		GameUI.score = 0;
		
		//��ʼ����Ϸ״̬
		gs = new GameStatus();
		
		//��ʼ����Ϸ��ʼʱ��
		GameUI.start = System.currentTimeMillis();
		
		//��ʼ������������
		player = new PlaySounds();
		
		//��ʼ��С��
		bird = new Bird(300,200,10,5,60);
		
		// ��ʼ���ܵ�
		pipe = new Pipe[4];
		for (int i = 0; i < ypoints.length; i++) {
			int randWid = new Random().nextInt(100);
			pipe[i] = new Pipe(x + i * GameUI.d + randWid, ypoints[i], 7);
		}
		
		// ��Ϸ����
		gamePanel = new MyPanel(bird, pipe, gs, player);

		// ���������
		gamePanel.addMouseListener(this);
		gamePanel.addMouseMotionListener(this);
		
		//�����߳�
		bird.start();
		player.start();
		for(int i = 0; i < pipe.length; i++){
			pipe[i].start();
		}
		/* mypanel �಻�ܶ�̳�thread�õ�ʵ��runnable�ӿڵķ�ʽ */
		Thread gp = new Thread(gamePanel);
		gp.start();
		//gamePanel.start();
		
		//���ô����������
		add(gamePanel);
		setResizable(false);
		setTitle("flappy bird");
		setVisible(true);
		setBounds(10,10,800,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	//��ȡ��Ϸ�÷�
	public static int getScore(){
		return GameUI.score;
	}

	//�ӷ�
	public static void addScore(){
		if(GameUI.flag == GameUI.GAME_RUNNING){
			GameUI.score++;
		}
	}
	
	//�ı���Ϸ״̬
	public void setStatus(int status){
		GameUI.flag = status;
	}
	
	public static synchronized int getGameFlag()
	{
		return GameUI.flag;
	}
	
	public static synchronized void setGameFlag(int flag)
	{
		GameUI.flag = flag;
	}

	@Override
	public void mouseClicked(MouseEvent e)  {
		
		int x = e.getX();
		int y = e.getY();
		
		//��С���״̬����Ϊ����״̬
		bird.setFlyStatus();
		
		if(GameUI.getGameFlag() == GameUI.GAME_RUNNING){
			return;
		}
		
		/*
		 * ����Ϸ�����󣬸��ݵ������λ��
		 * ���ж��ǲ��ǵ�������¿�ʼ��ť
		 */
		
		if(GameUI.getGameFlag() == GameUI.GAME_OVER){
			/*
			if(x > 225 && x < 381 && y > 400 && y < 493 ){
				GameUI.flag = GameUI.GAME_INIT;
			}
			*/
			System.out.println("rerererer");
			
			//GameUI.flag = GameUI.GAME_INIT;
			GameUI.setGameFlag(GameUI.GAME_INIT);
			
			GameUI.score = 0;
			GameUI.start = System.currentTimeMillis();
			return;
		} 
		System.out.println("runun");

		//����Ϸ״̬����Ϊ��Ϸ������
		GameUI.flag = GameUI.GAME_RUNNING;
		//����������������Ϊ������Ϸ��������ص�����
		player.setIsPlay(1);
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {

//		int x = e.getX();
//		int y = e.getY();
//		if(GameUI.flag == 2 && x > 225 && x < 381 && y > 400 && y < 493 ){
//			gs.setRestart();
//			//gamePanel.repaint();
//		}
		
	}
	

	
}
