import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.Random;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
public class TicTacToe extends JPanel
implements ActionListener,ItemListener{

	//The game can be played in two versions: Human against computer, which is a single player game and Human against Human which will be 2 player game. Choice the version from the first two checkboxes available. You can further choose to configure your game from other checkboxes that are enabled. Note: First chance will always be taken by player with 'X'
	String helpMessage="The game can be played in two versions:" +
					"\n Human against computer, which is a single player game and Human against Human which will be 2 player game." +
					"\n Choice the version from the first two checkboxes available." +
					"\n You can further choose to configure your game from other checkboxes that are enabled. " +
					"\nNote: First chance will always be taken by player with 'X'" +
					"\n The Arrow Button is the 'Start Game' Button, after configuring your choice press it" +
					"\n The last is Restart button to restart the game. After pressing restart Button" +
					"\nyou will need to press Go button to start the game.";
	
	String aboutMessage="TIC-TAC-TOE version 1.2.3	- Harsh" +
			"\nUndo and Redo facilities are yet unavailable." +
			"\nThey will be added by next version.";
	
	boolean gameOver=false;
	boolean gameIsOn=false;
	boolean gameVsMachineOn=false;	
	boolean soundOn=true;
	JFrame f;
	Container container;
	JMenu fileMenu;
	JMenu editMenu;
	JMenu helpMenu;
	JMenu aboutMenu;
	JMenuBar menubar;
	JCheckBox enableSound;
	JCheckBox vsHumanChoice1;
	JCheckBox vsHumanChoice2;
	JCheckBox mainChoice1;
	JCheckBox mainChoice2;
	JCheckBox vsMachineChoice1;
	JCheckBox vsMachineChoice2;
	ImageIcon currentIcon;
	ImageIcon cross;
	ImageIcon questionMark;
	ImageIcon circle;
	ImageIcon go;
	ImageIcon restart;	
	JButton b00;
	JButton b01;
	JButton b02;
	JButton b10;
	JButton b11;
	JButton b12;	
	JButton b20;
	JButton b21;
	JButton b22;
	JButton goButton;
	JButton restartButton;
	TextField statusWindow=new TextField();
	File goSoundFile ; 		AudioClip goSound;
	File turnTakenSoundFile;AudioClip turnTakenSound;
	File invalidMoveSoundFile;	AudioClip	invalidMoveSound;
	File drawGameSoundFile;	AudioClip drawGameSound;
	File winSoundFile;		AudioClip winSound;
	File restartSoundFile;	AudioClip restartSound;
	String grid[][]=new String[3][3];
	int usedRowIndex[]=new int[15];		int usedColIndex[]=new int[15];		
	static int PlayerID=1,x;
	int i=0;
	int machineTurn=0;
	@SuppressWarnings("deprecation")
	TicTacToe(){
		goSoundFile= new File("bin\\audio\\go.wav");
		turnTakenSoundFile= new File("bin\\audio\\turnTaken.wav");
		invalidMoveSoundFile= new File("bin\\audio\\invalidMove.wav");
		drawGameSoundFile= new File("bin\\audio\\drawGame.wav");
		winSoundFile= new File("bin\\audio\\win.wav");
		restartSoundFile= new File("bin\\audio\\restart.wav");
	    try{
	    	goSound = Applet.newAudioClip(goSoundFile.toURL());
	    	turnTakenSound = Applet.newAudioClip(turnTakenSoundFile.toURL());
	    	invalidMoveSound = Applet.newAudioClip(invalidMoveSoundFile.toURL());
	    	drawGameSound = Applet.newAudioClip(drawGameSoundFile.toURL());
	    	winSound = Applet.newAudioClip(winSoundFile.toURL());
	    	restartSound = Applet.newAudioClip(restartSoundFile.toURL());
	    }  
	    catch(Exception e){e.printStackTrace();}  
		f=new JFrame("TicTacToe Game (Harsh Trivedi) - version 1.2.3");
		container=f.getContentPane();
		container.setLayout(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		f.setSize(670, 700);		
		f.setLocationRelativeTo(null);
		f.setVisible(true);

		f.setResizable(false);
		
		JLabel background=new JLabel(new ImageIcon("bin\\images\\backgroundFinal.png"));
		background.setLocation(0, 0);	background.setSize(1000,1000);
		fileMenu=new JMenu("File");
		fileMenu.setMnemonic('F');
		
		JMenuItem restartGame=new JMenuItem("Restart Game");
		restartGame.setMnemonic('r');
		fileMenu.add(restartGame);
		JMenuItem changeIcons=new JMenuItem("Change Icons");
		fileMenu.add(changeIcons);	
		JMenuItem exit=new JMenuItem("Exit");
		exit.setMnemonic('e');
		fileMenu.add(exit);
		enableSound=new JCheckBox("Enable Sound",true);
		fileMenu.add(enableSound);
		enableSound.setMnemonic('s');
		
		restartGame.addActionListener(this);
		changeIcons.addActionListener(this);
		exit.addActionListener(this);
		enableSound.addItemListener(this);
		
		editMenu=new JMenu("Edit");
		editMenu.setMnemonic('E');
		JMenuItem undo=new JMenuItem("Undo");
		undo.setMnemonic('z');
		editMenu.add(new JMenuItem("Undo"));
		JMenuItem redo=new JMenuItem("Redo");
		redo.setMnemonic('y');
		editMenu.add(redo);
		
		helpMenu=new JMenu("Help");
		helpMenu.setMnemonic('h');
		JMenuItem help=new JMenuItem("Help");
		help.setMnemonic('h');
		helpMenu.add(help);
		help.addActionListener(this);
		
		aboutMenu=new JMenu("About");
		aboutMenu.setMnemonic('a');		
		JMenuItem about=new JMenuItem("About");
		about.setMnemonic('a');
		aboutMenu.add(about);
		about.addActionListener(this);
		
		menubar=new JMenuBar();
		
		menubar.add(fileMenu);
		menubar.add(editMenu);
		menubar.add(helpMenu);
		menubar.add(aboutMenu);
		f.setJMenuBar(menubar);
		mainChoice1=new JCheckBox("vs Machine");
		mainChoice2=new JCheckBox("vs Human",true);
		vsMachineChoice1=new JCheckBox("Human takes 1st turn'X'",true);
		vsMachineChoice2=new JCheckBox("Machine takes 1st turn'X'");
		vsHumanChoice1=new JCheckBox("Player A'X' takes first turn.",true);
		vsHumanChoice2=new JCheckBox("Player B'O' takes first turn.");
		Color c = new Color(0, 0, 0, 0);
		Color c1 = new Color(1f,1f, 1f, 0.99f);
		mainChoice1.setBackground(c);		mainChoice1.setForeground(c1);
		mainChoice2.setBackground(c);		mainChoice2.setForeground(c1);
		vsHumanChoice1.setBackground(c);	vsHumanChoice1.setForeground(c1);
		vsHumanChoice2.setBackground(c);	vsHumanChoice2.setForeground(c1);
		vsMachineChoice1.setBackground(c);	vsMachineChoice1.setForeground(c1);
		vsMachineChoice2.setBackground(c);	vsMachineChoice2.setForeground(c1);
		ButtonGroup mainGroup=new ButtonGroup();
		mainGroup.add(mainChoice1);
		mainGroup.add(mainChoice2);
		ButtonGroup playerGroup=new ButtonGroup();
		playerGroup.add(vsHumanChoice1);
		playerGroup.add(vsHumanChoice2);	
		ButtonGroup machineGroup1=new ButtonGroup();
		machineGroup1.add(vsMachineChoice1);
		machineGroup1.add(vsMachineChoice2);	
		mainChoice1.setLocation(10,20);	mainChoice1.setSize(90, 20);;
		mainChoice2.setLocation(10,40);	mainChoice2.setSize(90, 20);		
		vsHumanChoice1.setLocation(100, 20);	vsHumanChoice1.setSize(180, 20);
		vsHumanChoice2.setLocation(100, 40);	vsHumanChoice2.setSize(180, 20);		
		vsMachineChoice1.setLocation(280, 20);			vsMachineChoice1.setSize(170, 20);
		vsMachineChoice2.setLocation(280, 40);			vsMachineChoice2.setSize(170, 20);
		container.add(mainChoice1);
		container.add(mainChoice2);
		container.add(vsMachineChoice1);
		container.add(vsMachineChoice2);
		container.add(vsHumanChoice1);
		container.add(vsHumanChoice2);
		cross=new ImageIcon("bin\\images\\crossFinal.png");
		questionMark=new ImageIcon("bin\\images\\questionMarkFinal.png");
		circle=new ImageIcon("bin\\images\\circleFinal.png");		
		go=new ImageIcon("bin\\images\\goFinal-1.png");		
		restart=new ImageIcon("bin\\images\\refreshFinal.png");		
		b00=new JButton(questionMark);
		b01=new JButton(questionMark);
		b02=new JButton(questionMark);
		b10=new JButton(questionMark);
		b11=new JButton(questionMark);
		b12=new JButton(questionMark);
		b20=new JButton(questionMark);
		b21=new JButton(questionMark);
		b22=new JButton(questionMark);
		goButton=new JButton(go);
		restartButton=new JButton(restart);
		statusWindow=new TextField();
		Color c2 = new Color(0,0.3f, 0);
		statusWindow.setForeground(c2);
		statusWindow.setBackground(new Color(0.9f,0.9f,0.4f,0.0f));		
		Font f=new Font("adfsd",Font.BOLD,30);
		statusWindow.setFont(f);
		b00.setActionCommand("00");
		b01.setActionCommand("01");
		b02.setActionCommand("02");
		b10.setActionCommand("10");
		b11.setActionCommand("11");
		b12.setActionCommand("12");
		b20.setActionCommand("20");
		b21.setActionCommand("21");
		b22.setActionCommand("22");
		goButton.setActionCommand("go");
		restartButton.setActionCommand("restart");		
		vsHumanChoice1.addItemListener(this);
		vsHumanChoice2.addItemListener(this);
		mainChoice1.addItemListener(this);
		mainChoice2.addItemListener(this);
		vsMachineChoice1.addItemListener(this);
		vsMachineChoice2.addItemListener(this);
		b00.addActionListener(this);
		b01.addActionListener(this);
		b02.addActionListener(this);
		b10.addActionListener(this);
		b11.addActionListener(this);
		b12.addActionListener(this);
		b20.addActionListener(this);
		b21.addActionListener(this);
		b22.addActionListener(this);
		goButton.addActionListener(this);
		restartButton.addActionListener(this);
		b00.setLocation(100,100);	b00.setSize(150, 150);		
		b01.setLocation(250,100 );	b01.setSize(150, 150);	
		b02.setLocation(400,100);	b02.setSize(150, 150);	
		b10.setLocation(100,250);	b10.setSize(150, 150);	
		b11.setLocation(250,250);	b11.setSize(150, 150);	
		b12.setLocation(400,250);	b12.setSize(150, 150);	
		b20.setLocation(100,400);	b20.setSize(150, 150);	
		b21.setLocation(250,400);	b21.setSize(150, 150);	
		b22.setLocation(400,400);	b22.setSize(150, 150);	
		goButton.setLocation(470,20);	goButton.setSize(80,60);	
		restartButton.setLocation(570,20);restartButton.setSize(60,60);
		statusWindow.setLocation(100,570);	statusWindow.setSize(450,60);		
		container.add(b00);
		container.add(b01);
		container.add(b02);
		container.add(b10);
		container.add(b11);
		container.add(b12);
		container.add(b20);
		container.add(b21);
		container.add(b22);
		container.add(goButton);
		container.add(statusWindow);
		container.add(restartButton);
		for(int i=0;i<15;i++)	usedRowIndex[i]=-1;
		for(int i=0;i<15;i++)	usedColIndex[i]=-1;
		for(int i=0;i<3;i++)	for(int j=0;j<3;j++)	grid[i][j]="-";			
		goButton.setEnabled(false);
		b00.setEnabled(false);
		b01.setEnabled(false);
		b02.setEnabled(false);
		b10.setEnabled(false);
		b11.setEnabled(false);
		b12.setEnabled(false);
		b20.setEnabled(false);
		b21.setEnabled(false);
		b22.setEnabled(false);
		mainChoice1.setEnabled(false);			mainChoice1.setEnabled(true);
		mainChoice2.setEnabled(false);			mainChoice2.setEnabled(true);		
		vsMachineChoice1.setEnabled(false);//		vsMachineChoice1.setEnabled(true);
		vsMachineChoice2.setEnabled(false);//		vsMachineChoice2.setEnabled(true);
		vsHumanChoice1.setEnabled(false);		vsHumanChoice1.setEnabled(true);
		vsHumanChoice2.setEnabled(false);		vsHumanChoice2.setEnabled(true);
		goButton.setEnabled(true);
		restartButton.setEnabled(false);
		statusWindow.setEditable(false);
		background.setVisible(true);
		container.add(background);
		background.setEnabled(false);
		background.setEnabled(true);
		menubar.setVisible(false);
		menubar.setVisible(true);
	}
		
public static void main(String args[]){
	new TicTacToe();
}


public void actionPerformed(ActionEvent e) {
	if(e.getActionCommand().equalsIgnoreCase("exit")){
		System.exit(0);
	}
	if(e.getActionCommand().equalsIgnoreCase("help")){
		JOptionPane.showMessageDialog(this, helpMessage, "HELP!!", JOptionPane.INFORMATION_MESSAGE);
	
	}
	if(e.getActionCommand().equalsIgnoreCase("about")){
		JOptionPane.showMessageDialog(this, aboutMessage, "About!!", JOptionPane.INFORMATION_MESSAGE);
	
	}
	
	if(e.getActionCommand().equalsIgnoreCase("restart")		||	e.getActionCommand().equalsIgnoreCase("Restart Game")){	
		gameIsOn=false;
		if(soundOn){
			winSound.stop();
			restartSound.play();
		}
		machineTurn=1;
		statusWindow.setText("");
		gameOver=false;
		goButton.setEnabled(true);
		vsHumanChoice1.setEnabled(true);
		vsHumanChoice2.setEnabled(true);
		b00.setIcon(questionMark);
		b01.setIcon(questionMark);
		b02.setIcon(questionMark);
		b10.setIcon(questionMark);
		b11.setIcon(questionMark);
		b12.setIcon(questionMark);
		b20.setIcon(questionMark);
		b21.setIcon(questionMark);
		b22.setIcon(questionMark);
		mainChoice1.setEnabled(true);
		mainChoice2.setEnabled(true);
		if(mainChoice1.isSelected()){
			gameVsMachineOn=true;
			vsMachineChoice1.setEnabled(true);
			vsMachineChoice2.setEnabled(true);
			vsHumanChoice1.setEnabled(false);
			vsHumanChoice2.setEnabled(false);					
			if(vsMachineChoice1.isSelected()){
					PlayerID=1;
			}
			if(vsMachineChoice2.isSelected()){
					PlayerID=1;
			}
		}
		if(mainChoice2.isSelected()){
			vsMachineChoice1.setEnabled(false);
			vsMachineChoice2.setEnabled(false);
			vsHumanChoice1.setEnabled(true);
			vsHumanChoice2.setEnabled(true);
			gameVsMachineOn=false;
			vsHumanChoice1.setEnabled(true);
			vsHumanChoice2.setEnabled(true);
			vsMachineChoice1.setEnabled(false);
			vsMachineChoice2.setEnabled(false);
			if(vsHumanChoice1.isSelected()){	PlayerID=1;}
			else{	PlayerID=2;}
		}
		b00.setEnabled(false);
		b01.setEnabled(false);
		b02.setEnabled(false);
		b10.setEnabled(false);
		b11.setEnabled(false);
		b12.setEnabled(false);
		b20.setEnabled(false);
		b21.setEnabled(false);
		b22.setEnabled(false);
		i=0;
		for(int i=0;i<15;i++)	usedRowIndex[i]=-1;
		for(int i=0;i<15;i++)	usedColIndex[i]=-1;
		for(int i=0;i<3;i++)	for(int j=0;j<3;j++)	grid[i][j]="-";		
	}
	if(!gameOver){
	if(e.getActionCommand().equalsIgnoreCase("00")){		
		if(mainChoice2.isSelected())statusWindow.setText("Player "+PlayerID +" take chance.");else statusWindow.setText("Human take chance.");			
		if(humanTakeChance("00",b00)){
			if(soundOn)turnTakenSound.play();
			if(gameVsMachineOn){/*statusWindow.setText("\nMachine turn is" +machineTurn);*/	machineTakeChance(machineTurn);if(soundOn)turnTakenSound.play();machineTurn=machineTurn+1;}
		}
		else if(soundOn)invalidMoveSound.play();
	}
	if(e.getActionCommand().equalsIgnoreCase("01")){
		if(mainChoice2.isSelected())statusWindow.setText("Player "+PlayerID +" take chance.");else statusWindow.setText("Human take chance.");			
		if(humanTakeChance("01",b01)){
			if(soundOn)turnTakenSound.play();
			if(gameVsMachineOn){/*statusWindow.setText("\nMachine turn is" +machineTurn);*/	machineTakeChance(machineTurn);if(soundOn)turnTakenSound.play();machineTurn=machineTurn+1;}
		}
		else if(soundOn)invalidMoveSound.play();
	}
	if(e.getActionCommand().equalsIgnoreCase("02")){
		if(mainChoice2.isSelected())statusWindow.setText("Player "+PlayerID +" take chance.");else statusWindow.setText("Human take chance.");			
		if(humanTakeChance("02",b02)){

			if(soundOn)turnTakenSound.play();
			if(gameVsMachineOn){/*statusWindow.setText("\nMachine turn is" +machineTurn);*/	machineTakeChance(machineTurn);if(soundOn)turnTakenSound.play();machineTurn=machineTurn+1;}
		}
		else if(soundOn)invalidMoveSound.play();		
	}
	if(e.getActionCommand().equalsIgnoreCase("10")){
		if(mainChoice2.isSelected())statusWindow.setText("Player "+PlayerID +" take chance.");else statusWindow.setText("Human take chance.");			
		if(humanTakeChance("10",b10)){

			if(soundOn)turnTakenSound.play();
			if(gameVsMachineOn){/*statusWindow.setText("\nMachine turn is" +machineTurn);*/	machineTakeChance(machineTurn);if(soundOn)turnTakenSound.play();machineTurn=machineTurn+1;}
		}
		else if(soundOn)invalidMoveSound.play();
	}
	if(e.getActionCommand().equalsIgnoreCase("11")){
		if(mainChoice2.isSelected())statusWindow.setText("Player "+PlayerID +" take chance.");else statusWindow.setText("Human take chance.");			
		if(humanTakeChance("11",b11)){

			if(soundOn)turnTakenSound.play();
			if(gameVsMachineOn){/*statusWindow.setText("\nMachine turn is" +machineTurn);*/	machineTakeChance(machineTurn);if(soundOn)turnTakenSound.play();machineTurn=machineTurn+1;}
		}
		else if(soundOn)invalidMoveSound.play();
	}
	if(e.getActionCommand().equalsIgnoreCase("12")){
		if(mainChoice2.isSelected())statusWindow.setText("Player "+PlayerID +" take chance.");else statusWindow.setText("Human take chance.");			
		if(humanTakeChance("12",b12)){

			if(soundOn)turnTakenSound.play();
			if(gameVsMachineOn){/*statusWindow.setText("\nMachine turn is" +machineTurn);*/	machineTakeChance(machineTurn);if(soundOn)turnTakenSound.play();machineTurn=machineTurn+1;}
		}
		else if(soundOn)invalidMoveSound.play();
	}
	if(e.getActionCommand().equalsIgnoreCase("20")){
		if(mainChoice2.isSelected())statusWindow.setText("Player "+PlayerID +" take chance.");else statusWindow.setText("Human take chance.");			
		if(humanTakeChance("20",b20)){

			if(soundOn)turnTakenSound.play();
			if(gameVsMachineOn){/*statusWindow.setText("\nMachine turn is" +machineTurn);*/	machineTakeChance(machineTurn);if(soundOn)turnTakenSound.play();machineTurn=machineTurn+1;}
		}
		else if(soundOn)invalidMoveSound.play();
	}
	if(e.getActionCommand().equalsIgnoreCase("21")){
		if(mainChoice2.isSelected())statusWindow.setText("Player "+PlayerID +" take chance.");else statusWindow.setText("Human take chance.");					
		if(humanTakeChance("21",b21)){

			if(soundOn)turnTakenSound.play();
			if(gameVsMachineOn){/*statusWindow.setText("\nMachine turn is" +machineTurn);*/	machineTakeChance(machineTurn);if(soundOn)turnTakenSound.play();machineTurn=machineTurn+1;}
		}
		else if(soundOn)invalidMoveSound.play();
	}
	if(e.getActionCommand().equalsIgnoreCase("22")){
		if(mainChoice2.isSelected())statusWindow.setText("Player "+PlayerID +" take chance.");else statusWindow.setText("Human take chance.");			
		if(humanTakeChance("22",b22)){

			if(soundOn)turnTakenSound.play();
			if(gameVsMachineOn){/*statusWindow.setText("\nMachine turn is" +machineTurn);*/	machineTakeChance(machineTurn);if(soundOn)turnTakenSound.play();machineTurn=machineTurn+1;}
		}
		else if(soundOn)invalidMoveSound.play();
	}
	if(e.getActionCommand().equalsIgnoreCase("go")){	
		gameIsOn=true;
		if(mainChoice1.isSelected()){
			statusWindow.setText("Human Take chance.");
		}
		if(mainChoice2.isSelected()){
			statusWindow.setText("Player 1 Take chance.");
		}
		
		
		if(soundOn)goSound.play();		
		machineTurn=1;		
		goButton.setEnabled(false);
		restartButton.setEnabled(true);
		b00.setEnabled(true);
		b01.setEnabled(true);
		b02.setEnabled(true);
		b10.setEnabled(true);
		b11.setEnabled(true);
		b12.setEnabled(true);
		b20.setEnabled(true);
		b21.setEnabled(true);
		b22.setEnabled(true);
		mainChoice1.setEnabled(false);
		mainChoice2.setEnabled(false);
		vsHumanChoice1.setEnabled(false);
		vsHumanChoice2.setEnabled(false);
		vsMachineChoice1.setEnabled(false);
		vsMachineChoice2.setEnabled(false);
		if(vsMachineChoice2.isSelected())
			if(gameVsMachineOn){	machineTakeChance(machineTurn);machineTurn=+2;}		
	}
}
}

public void toggleIcon(JButton b,int playerID){
	if(playerID==1)	currentIcon=cross;
	if(playerID==2)	currentIcon=circle;
}


void flipPlayerID(){
	if(PlayerID==1) PlayerID=2;		else	PlayerID=1;	
}

String getPlayerSign(){
	if(PlayerID==1)	return "X";	else		return "O";	
	}

int getPossibleWinningLinesORgetIdOfWinningCell(int userID,int vsHumanChoice){
	int counter=0,x=1000,y=1000;
	for(int i=0;i<3;i++){	int count=0;
		if(grid[i][0].equalsIgnoreCase(grid[i][1]) && grid[i][1].equalsIgnoreCase(getPlayerSign()) && grid[i][2].equalsIgnoreCase("-")){count++;x=i ;y=2 ;}
		if(grid[i][1].equalsIgnoreCase(grid[i][2]) && grid[i][2].equalsIgnoreCase(getPlayerSign()) && grid[i][0].equalsIgnoreCase("-")){count++;x=i ;y=0 ;}
		if(grid[i][0].equalsIgnoreCase(grid[i][2]) && grid[i][2].equalsIgnoreCase(getPlayerSign()) && grid[i][1].equalsIgnoreCase("-")){count++;x=i ;y=1 ;}		
		if(count==1)	counter++;	
	}	for(int i=0;i<3;i++){	int count=0;
		if(grid[0][i].equalsIgnoreCase(grid[1][i]) && grid[1][i].equalsIgnoreCase(getPlayerSign()) && grid[2][i].equalsIgnoreCase("-")){count++;x=2 ;y=i ;}
		if(grid[1][i].equalsIgnoreCase(grid[2][i]) && grid[2][i].equalsIgnoreCase(getPlayerSign()) && grid[0][i].equalsIgnoreCase("-")){count++;x=0 ;y=i ;}
		if(grid[0][i].equalsIgnoreCase(grid[2][i]) && grid[2][i].equalsIgnoreCase(getPlayerSign()) && grid[1][i].equalsIgnoreCase("-")){count++;x=1 ;y=i ;}
		if(count==1)	counter++;	
	}{	int count=0;
		if(grid[0][0].equalsIgnoreCase(grid[1][1]) && grid[1][1].equalsIgnoreCase(getPlayerSign()) && grid[2][2].equalsIgnoreCase("-")){count++;x=2 ;y=2 ;}
		if(grid[1][1].equalsIgnoreCase(grid[2][2]) && grid[2][2].equalsIgnoreCase(getPlayerSign()) && grid[0][0].equalsIgnoreCase("-")){count++;x=0 ;y=0 ;}
		if(grid[0][0].equalsIgnoreCase(grid[2][2]) && grid[2][2].equalsIgnoreCase(getPlayerSign()) && grid[1][1].equalsIgnoreCase("-")){count++;x=1 ;y=1 ;}
		if(count==1)	counter++;	
	}{	int count=0;
		if(grid[0][2].equalsIgnoreCase(grid[1][1]) && grid[1][1].equalsIgnoreCase(getPlayerSign()) && grid[2][0].equalsIgnoreCase("-")){count++;x=2 ;y=0 ;}
		if(grid[1][1].equalsIgnoreCase(grid[2][0]) && grid[2][0].equalsIgnoreCase(getPlayerSign()) && grid[0][2].equalsIgnoreCase("-")){count++;x=0 ;y=2 ;}
		if(grid[0][2].equalsIgnoreCase(grid[2][0]) && grid[2][0].equalsIgnoreCase(getPlayerSign()) && grid[1][1].equalsIgnoreCase("-")){count++;x=1 ;y=1 ;}
		if(count==1)	counter++;	
	}
if(vsHumanChoice==1) return counter;
else	return getEquivalentNumberFromIndex(x,y);	}
int	onThisCellGetNumberOFWinningLines(int userID,int p,int q){
	grid[p][q]=getPlayerSign();
	int r=getPossibleWinningLinesORgetIdOfWinningCell(userID,1);
	grid[p][q]="-";
return r;
}

int checkGameResult(){
	if(		(grid[0][0].equalsIgnoreCase(grid[1][1]) && grid[1][1].equalsIgnoreCase(grid[2][2]) && grid[2][2].equalsIgnoreCase(getPlayerSign()))
		||	(grid[0][2].equalsIgnoreCase(grid[1][1]) && grid[1][1].equalsIgnoreCase(grid[2][0]) && grid[2][0].equalsIgnoreCase(getPlayerSign()))
		||	(grid[0][0].equalsIgnoreCase(grid[0][1]) && grid[0][1].equalsIgnoreCase(grid[0][2]) && grid[0][2].equalsIgnoreCase(getPlayerSign()))	
		||	(grid[2][0].equalsIgnoreCase(grid[2][1]) && grid[2][1].equalsIgnoreCase(grid[2][2]) && grid[2][2].equalsIgnoreCase(getPlayerSign()))
		||	(grid[0][0].equalsIgnoreCase(grid[1][0]) && grid[1][0].equalsIgnoreCase(grid[2][0]) && grid[2][0].equalsIgnoreCase(getPlayerSign()))
		||	(grid[0][2].equalsIgnoreCase(grid[1][2]) && grid[1][2].equalsIgnoreCase(grid[2][2]) && grid[2][2].equalsIgnoreCase(getPlayerSign()))
		||	(grid[0][1].equalsIgnoreCase(grid[1][1]) && grid[1][1].equalsIgnoreCase(grid[2][1]) && grid[2][1].equalsIgnoreCase(getPlayerSign()))
		||	(grid[1][0].equalsIgnoreCase(grid[1][1]) && grid[1][1].equalsIgnoreCase(grid[1][2]) && grid[1][2].equalsIgnoreCase(getPlayerSign()))																																					)
		{
		
		if(mainChoice2.isSelected()){
			statusWindow.setText("PLAYER "  +PlayerID      +"WINS!!!");	if(soundOn)winSound.play();
			return 0;
		}
		else{
			if(vsMachineChoice1.isSelected()){
				if(PlayerID==1){	statusWindow.setText("Congratulations, YOU WINS!!!");if(soundOn)	winSound.play();}
				else		{	statusWindow.setText("SORRY, U LOOSE!!!");if(soundOn)	drawGameSound.play();}
			}
			else{
				if(PlayerID==1){statusWindow.setText("SORRY, U LOOSE!!!");if(soundOn)	drawGameSound.play();}
				else		{	statusWindow.setText("Congratulations, YOU WINS!!!");if(soundOn)	winSound.play();	}				
			}			
			return 0;
		}
		
		
		}		
	else{int count=0;	
		for(int i=0;i<3;i++){	for(int j=0;j<3;j++)	if(grid[i][j].equalsIgnoreCase("-"))	count++;	}
		if(count==0){statusWindow.setText("Match draws");if(soundOn)	drawGameSound.play();	return 0;}		
	}	return 1;
}   

Boolean isValidToSetElementToThisCell(int p,int q){
	for(int k=0;k<9;k++)	if( (usedRowIndex[k]==p )&& (usedColIndex[k]==q)  )		return false;		
	return true;
	}

int getEquivalentNumberFromIndex(int x,int y){	
	if(x==0 && y==0 )	return 0;	if(x==0 && y==1 )	return 1;	if(x==0 && y==2 )	return 2;	if(x==1 && y==0 )	return 3;	if(x==1 && y==1 )	return 4;	if(x==1 && y==2 )	return 5;	if(x==2 && y==0 )	return 6;	if(x==2 && y==1 )	return 7;	if(x==2 && y==2 )	return 8;
else	return 1000;
	}
int getEquivalentColIndexFromNumber(int x){
	if(x==0 || x==3 || x==6)	return 0;	if(x==1 || x==4 || x==7)	return 1;	if(x==2 || x==5 || x==8)	return 2;	return 1000;
	}
int getEquivalentRowIndexFromNumber(int x){
	if(x==0 || x==1 || x==2)	return 0;	if(x==3 || x==4 || x==5)	return 1;	if(x==6 || x==7 || x==8)	return 2;	return 1000;
	}

void printGrid(){
	System.out.println("The current grid is:");
	for(int p=0;p<3;p++){	for(int q=0;q<3;q++)	System.out.print(grid[p][q]);	System.out.println();	}
}

void setAndPostSet_procedures(int i,int k,int l){
	grid[k][l]=getPlayerSign();
	usedRowIndex[i]=k;
	usedColIndex[i]=l;
	printGrid();
	if(checkGameResult()==0)	{/*	statusWindow.setText("\nGame Over!!!");*/ gameOver=true;gameIsOn=false;}
}

public void itemStateChanged(ItemEvent e) {	
	if(enableSound.isSelected())	soundOn=true;	else soundOn=false;
	if(!gameIsOn){		
			if(mainChoice1.isSelected()){	
				gameVsMachineOn=true;
				vsMachineChoice1.setEnabled(true);
				vsMachineChoice2.setEnabled(true);
				vsHumanChoice1.setEnabled(false);
				vsHumanChoice2.setEnabled(false);		
				if(vsMachineChoice1.isSelected()){
						PlayerID=1;
				}
				if(vsMachineChoice2.isSelected()){
						PlayerID=1;
				}
			}		
			if(mainChoice2.isSelected()){	
				gameVsMachineOn=false;
				vsHumanChoice1.setEnabled(true);
				vsHumanChoice2.setEnabled(true);
				vsMachineChoice1.setEnabled(false);
				vsMachineChoice2.setEnabled(false);
				if(vsHumanChoice1.isSelected()){	PlayerID=1;}
				else{	PlayerID=2;}		
			}
	}	
}

boolean humanTakeChance(String str,JButton b){
	int indexID=Integer.parseInt(str);
	int rowID=indexID/10;	int columnID=indexID%10;
	boolean B=isValidToSetElementToThisCell(rowID,columnID);
	if(B){		
		toggleIcon(b,PlayerID);
		b.setIcon(currentIcon);
		setAndPostSet_procedures(i,rowID,columnID);i++;flipPlayerID();	
		return true;
	}	
	else{	
		statusWindow.setText("You cannot overwrite!TRY AGAIN");
		return false;
	}
}


void setIcon(int p,int q){
	if(p==0&&q==0){
		toggleIcon(b00,PlayerID);
		b00.setIcon(currentIcon);
	}
	if(p==0&&q==1){
		toggleIcon(b01,PlayerID);
		b01.setIcon(currentIcon);
	}
	if(p==0&&q==2){
		toggleIcon(b02,PlayerID);
		b02.setIcon(currentIcon);
	}
	if(p==1&&q==0){
		toggleIcon(b10,PlayerID);
		b10.setIcon(currentIcon);
	}
	if(p==1&&q==1){
		toggleIcon(b11,PlayerID);
		b11.setIcon(currentIcon);
	}
	if(p==1&&q==2){
		toggleIcon(b12,PlayerID);
		b12.setIcon(currentIcon);
	}
	if(p==2&&q==0){
		toggleIcon(b20,PlayerID);
		b20.setIcon(currentIcon);
	}
	if(p==2&&q==1){
		toggleIcon(b21,PlayerID);
		b21.setIcon(currentIcon);
	}
	if(p==2&&q==2){
		toggleIcon(b22,PlayerID);
		b22.setIcon(currentIcon);
	}
	
}

void machineTakeChance(int turn){	//machine chance starts
	System.out.println("Its Machine's chance:");	
	x=0;			
	Random r=new Random();
	if(turn==1  && PlayerID==1){
								int o=getEquivalentRowIndexFromNumber(r.nextInt(8));int u=getEquivalentColIndexFromNumber(r.nextInt(8));								
								setIcon(o,u);setAndPostSet_procedures(i,o,u);setIcon(o,u);i++;turn++;x=1;flipPlayerID();		}
	if(turn==1  &&   PlayerID==2 ){
		if(grid[0][0].equalsIgnoreCase("X")||grid[2][2].equalsIgnoreCase("X")||grid[0][2].equalsIgnoreCase("X")||grid[2][0].equalsIgnoreCase("X")){
			if(grid[1][1]=="-"){
				setIcon(1,1);setAndPostSet_procedures(i,1,1);setIcon(1,1);i++;turn++;x=1;flipPlayerID();}}		}
	if(x!=1){
	for(int k=0;k<3;k++){	for(int l=0;l<3;l++){	if(grid[k][l]=="-"){
				if(getPossibleWinningLinesORgetIdOfWinningCell(PlayerID,2)!=1000  && x!=1){		// ie. if there exists an id where current player can win
					int m=getEquivalentRowIndexFromNumber(getPossibleWinningLinesORgetIdOfWinningCell(PlayerID,2));
					int n=getEquivalentColIndexFromNumber(getPossibleWinningLinesORgetIdOfWinningCell(PlayerID,2));													
					setIcon(m,n);setAndPostSet_procedures(i,m,n);setIcon(m,n);i++;turn++;x=1;flipPlayerID();		}}}}	}
	if(x!=1){
	for(int k=0;k<3;k++){	for(int l=0;l<3;l++){	if(grid[k][l]=="-"){
				flipPlayerID();
				if(getPossibleWinningLinesORgetIdOfWinningCell(PlayerID,2)!=1000  && x!=1){		// ie. if there exists an id where previous player can win										
					int m=getEquivalentRowIndexFromNumber(getPossibleWinningLinesORgetIdOfWinningCell(PlayerID,2));
					int n=getEquivalentColIndexFromNumber(getPossibleWinningLinesORgetIdOfWinningCell(PlayerID,2));													
					flipPlayerID();
					setIcon(m,n);setAndPostSet_procedures(i,m,n);setIcon(m,n);i++;turn++;x=1;		//System.out.println("B");
				}flipPlayerID();	}}}
	}
	if(isIDWhereNumberOfWinningLinesAre(2,turn)){
		int o=getEquivalentRowIndexFromNumber(getIDWhereNumberOfWinningLinesAre(2,turn));
		int u=getEquivalentColIndexFromNumber(getIDWhereNumberOfWinningLinesAre(2,turn));
		setIcon(o,u);setAndPostSet_procedures(i, o , u );setIcon(o,u);		
		i++;turn++;x=1;flipPlayerID();		}	
	int C=0;int z[]=new int[4];
	if(x!=1){	flipPlayerID();	
				for(int k=0;k<3;k++){	for(int l=0;l<3;l++){	if(grid[k][l]=="-"){		
					if(onThisCellGetNumberOFWinningLines(PlayerID,k,l)==2){
						z[C]=getEquivalentNumberFromIndex(k, l);
						C++;									
					}}}						//if previous player can make a double D modified
		}	flipPlayerID();		
	if(C>1){	for(int k=0;k<3;k++){	for(int l=0;l<3;l++){	if(grid[k][l]=="-"){		
				grid[k][l]=getPlayerSign();			
					if(getPossibleWinningLinesORgetIdOfWinningCell(PlayerID,2)!=1000){								
												int counterx=0;
												for(int v=0;v<C;v++)
													if(z[v]==getPossibleWinningLinesORgetIdOfWinningCell(PlayerID,2))		counterx++;												
												if(counterx==0 && x!=1){				/// change made && x!=1
													setIcon(k,l);setAndPostSet_procedures(i,k	,l);setIcon(k,l);
													i++;turn++;x=1;flipPlayerID();	}														
												else	grid[k][l]="-";
					}	else	grid[k][l]="-";
					}}}		//if previous player can make a double (D modified)
	}	
	if(C==1){	for(int k=0;k<3;k++){	for(int l=0;l<3;l++){	if(grid[k][l]=="-"){		
				flipPlayerID();
				if(onThisCellGetNumberOFWinningLines(PlayerID,k,l)==2  && x!=1){
					flipPlayerID();
					setIcon(k,l);setAndPostSet_procedures(i,k,l);setIcon(k,l);i++;
					turn++;x=1;		//					System.out.println("D");
				}	flipPlayerID();
			}}}}	
}	if(isIDWhereNumberOfWinningLinesAre(0,turn)){
		int k= getEquivalentRowIndexFromNumber(getIDWhereNumberOfWinningLinesAre(0,turn));
		int l=getEquivalentColIndexFromNumber(getIDWhereNumberOfWinningLinesAre(0,turn));
		setIcon(k,l);setAndPostSet_procedures(i,k , l );setIcon(k,l);		
		i++;turn++;x=1;flipPlayerID();	}
	if(isIDWhereNumberOfWinningLinesAre(1,turn)){
		int k= getEquivalentRowIndexFromNumber(getIDWhereNumberOfWinningLinesAre(1,turn));
		int l=getEquivalentColIndexFromNumber(getIDWhereNumberOfWinningLinesAre(1,turn)) ;
		setIcon(k,l);setAndPostSet_procedures(i,k , l);	setIcon(k,l);	
		i++;turn++;x=1;flipPlayerID();	}	
}//machine chance ends


Boolean isIDWhereNumberOfWinningLinesAre(int n,int turn){
	if(x!=1){
			for(int k=0;k<3;k++){
				for(int l=0;l<3;l++){if(grid[k][l]=="-")
									if(onThisCellGetNumberOFWinningLines(PlayerID,k,l)==n)	return	true;
					}}	return	false;
			}else return false;	
	}
int getIDWhereNumberOfWinningLinesAre(int n,int turn){
		for(int k=0;k<3;k++){	for(int l=0;l<3;l++)	if(grid[k][l]=="-")	if(onThisCellGetNumberOFWinningLines(PlayerID,k,l)==n  && x!=1)	return getEquivalentNumberFromIndex(k,l);}
		return 1000;
	}

}//class ends
