package JSniffer.ui;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import JSniffer.JDCaptor;
import JSniffer.JDStatisticsTakerLoader;
import JSniffer.JSniffer;
import JSniffer.stat.JDStatisticsTaker;
import jpcap.*;
import JSniffer.analyzer.*;

public class JDFrame extends JFrame implements ActionListener
{
	public JDCaptor captor;

	public JLabel statusLabel;
	public JMenuItem openMenu,saveMenu,captureMenu,stopMenu,suspendMenu,continueMenu,objMenu,stopobjMenu;
	public JMenu statMenu;
	public JButton newButton,openButton,saveButton,captureButton,stopButton,suspendButton,continueButton,objButton,stopobjButton,exitButton;

       public JLabel objstatus,label,ipstatus;

	public JDTablePane tablePane;


	public static JDFrame openNewWindow(JDCaptor captor){
		JDFrame frame=new JDFrame(captor);
		frame.setVisible(true);

		return frame;
	}

	public JDFrame(JDCaptor captor){
		this.captor=captor;
		tablePane=new JDTablePane(captor);
		captor.setJDFrame(this);

		setTitle("JSniffer �������");

		// Create Menu
		JMenuBar menuBar=new JMenuBar();
		setJMenuBar(menuBar);

    		//File Menu
		JMenu menu=new JMenu("�ļ�");
                menuBar.add(menu);
                JMenuItem item=new JMenuItem("�½�����");
                item.setIcon(getImageIcon("/image/window.gif"));
                item.setActionCommand("NewWin");
                item.addActionListener(this);
                menu.add(item);

		openMenu=new JMenuItem("���ļ�");
		openMenu.setIcon(getImageIcon("/image/open.gif"));
		openMenu.setActionCommand("Open");
		openMenu.addActionListener(this);
		menu.add(openMenu);
		saveMenu=new JMenuItem("�����ļ�");
		saveMenu.setIcon(getImageIcon("/image/save.gif"));
		saveMenu.setActionCommand("Save");
		saveMenu.addActionListener(this);
		saveMenu.setEnabled(false);
                menu.add(saveMenu);
                item=new JMenuItem("�˳�");
                item.setIcon(getImageIcon("/image/exit.gif"));
                item.setActionCommand("Exit");
                item.addActionListener(this);
		menu.add(item);

		//Capture Menu
		menu=new JMenu("����");
		menuBar.add(menu);
		captureMenu=new JMenuItem("��ʼ����");
		captureMenu.setIcon(getImageIcon("/image/Find.gif"));
		captureMenu.setActionCommand("Start");
		captureMenu.addActionListener(this);
		menu.add(captureMenu);

                continueMenu=new JMenuItem("��������");
                continueMenu.setIcon(getImageIcon("/image/continue.gif"));
                continueMenu.setActionCommand("continue");
                continueMenu.addActionListener(this);
                continueMenu.setEnabled(false);
                menu.add(continueMenu);

                suspendMenu=new JMenuItem("��ͣ����");
                suspendMenu.setIcon(getImageIcon("/image/suspend.gif"));
                suspendMenu.setActionCommand("suspend");
                suspendMenu.addActionListener(this);
                suspendMenu.setEnabled(false);
                menu.add(suspendMenu);

                objMenu=new JMenuItem("�������");
                objMenu.setIcon(getImageIcon("/image/object.gif"));
                objMenu.setActionCommand("object");
                objMenu.addActionListener(this);
                objMenu.setEnabled(false);
                menu.add(objMenu);

                stopobjMenu=new JMenuItem("ֹͣ�������");
                stopobjMenu.setIcon(getImageIcon("/image/stopobj.gif"));
                stopobjMenu.setActionCommand("stopobj");
                stopobjMenu.addActionListener(this);
                stopobjMenu.setEnabled(false);
                menu.add(stopobjMenu);

                stopMenu=new JMenuItem("��������");
                stopMenu.setIcon(getImageIcon("/image/stop.gif"));
                stopMenu.setActionCommand("Stop");
                stopMenu.addActionListener(this);
                stopMenu.setEnabled(false);
                menu.add(stopMenu);



                //View menu
                menu=new JMenu("Э��鿴");
                menuBar.add(menu);
		tablePane.setTableViewMenu(menu);

               //Stat Menu
		statMenu=new JMenu("���ݰ�ͳ��");
		menuBar.add(statMenu);
		menu=new JMenu("��״ͼ");
		statMenu.add(menu);
		JDStatisticsTaker[] stakers=JDStatisticsTakerLoader.getStatisticsTakers();
		for(int i=0;i<stakers.length;i++){
			item=new JMenuItem(stakers[i].getName());
			item.setActionCommand("CUMSTAT"+i);
			item.addActionListener(this);
			menu.add(item);
		}

		//L&F Menu
		menu=new JMenu("���Խ���");
		menuBar.add(menu);
		item=createLaFMenuItem("Metal","javax.swing.plaf.metal.MetalLookAndFeel");
		menu.add(item);
		item.setSelected(true);
		menu.add(createLaFMenuItem("Windows","com.sun.java.swing.plaf.windows.WindowsLookAndFeel"));
		menu.add(createLaFMenuItem("Motif","com.sun.java.swing.plaf.motif.MotifLookAndFeel"));
		menu.add(createLaFMenuItem("Mac","com.sun.java.swing.plaf.mac.MacLookAndFeel"));

		//Create Toolbar
		JToolBar toolbar=new JToolBar();
		toolbar.setFloatable(false);

                newButton=new JButton(getImageIcon("/image/window.gif"));
                newButton.setToolTipText("�½�����");
                newButton.setActionCommand("NewWin");
                newButton.addActionListener(this);
                toolbar.add(newButton);
		openButton=new JButton(getImageIcon("/image/open.gif"));
                openButton.setToolTipText("�򿪼�¼");
		openButton.setActionCommand("Open");
		openButton.addActionListener(this);
		toolbar.add(openButton);
		saveButton=new JButton(getImageIcon("/image/save.gif"));
                saveButton.setToolTipText("�����¼");
		saveButton.setActionCommand("Save");
		saveButton.addActionListener(this);
		saveButton.setEnabled(false);
		toolbar.add(saveButton);
		toolbar.addSeparator();
		captureButton=new JButton(getImageIcon("/image/Find.gif"));
                captureButton.setToolTipText("��ʼ����");
		captureButton.setActionCommand("Start");
		captureButton.addActionListener(this);
		toolbar.add(captureButton);


                continueButton=new JButton(getImageIcon("/image/continue.gif"));
                continueButton.setToolTipText("��������");
                continueButton.setActionCommand("continue");
                continueButton.addActionListener(this);
                continueButton.setEnabled(false);
                toolbar.add(continueButton);
                suspendButton=new JButton(getImageIcon("/image/suspend.gif"));
                suspendButton.setToolTipText("��ͣ����");
                suspendButton.setActionCommand("suspend");
                suspendButton.addActionListener(this);
                suspendButton.setEnabled(false);
                toolbar.add(suspendButton);




                objButton=new JButton(getImageIcon("/image/object.gif"));
                objButton.setToolTipText("�������");
                objButton.setActionCommand("object");
                objButton.addActionListener(this);
                objButton.setEnabled(false);
                toolbar.add(objButton);
                stopobjButton=new JButton(getImageIcon("/image/stopobj.gif"));
                stopobjButton.setToolTipText("ֹͣ�������");
                stopobjButton.setActionCommand("stopobj");
                stopobjButton.addActionListener(this);
                stopobjButton.setEnabled(false);
                toolbar.add(stopobjButton);

                stopButton=new JButton(getImageIcon("/image/stop.gif"));
                stopButton.setToolTipText("�������μ���");
                stopButton.setActionCommand("Stop");
                stopButton.addActionListener(this);
                stopButton.setEnabled(false);
                toolbar.add(stopButton);

                exitButton=new JButton(getImageIcon("/image/exit.gif"));
                exitButton.setToolTipText("�˳�");
                exitButton.setActionCommand("Exit");
                exitButton.addActionListener(this);
                toolbar.add(exitButton);


                JLabel lab=new JLabel("      ����״̬��");
                toolbar.add(lab);
                objstatus=new JLabel("δ���м���        ");
                toolbar.add(objstatus);
                label=new JLabel("    ��������: ");
                toolbar.add(label);
                ipstatus=new JLabel("  ��");
                toolbar.add(ipstatus);

		statusLabel=new JLabel("JSniffer �Ѿ���.");

		getContentPane().setLayout(new BorderLayout());
		//getContentPane().add(desktop,BorderLayout.CENTER);
		getContentPane().add(statusLabel,BorderLayout.SOUTH);
		getContentPane().add(tablePane,BorderLayout.CENTER);
		getContentPane().add(toolbar,BorderLayout.NORTH);

		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent evt){
				saveProperty();
				JSniffer.closeWindow((JDFrame)evt.getSource());
			}
		});

		loadProperty();
		//pack();
	}

	public void actionPerformed(ActionEvent evt){
		String cmd=evt.getActionCommand();

		if(cmd.equals("Open")){
			captor.loadPacketsFromFile();
		}else if(cmd.equals("Save")){
			captor.saveToFile();
		}else if(cmd.equals("NewWin")){
			JSniffer.openNewWindow();
		}else if(cmd.equals("Exit")){
			saveProperty();
			System.exit(0);
		}else if(cmd.equals("Start")){
			captor.capturePacketsFromDevice();

		}else if(cmd.equals("Stop")){
                        captor.stopCapture();

		}
                else if(cmd.equals("suspend"))
                {
                  objstatus.setText("��ͣ����");
                  suspendButton.setEnabled(false);
                  suspendMenu.setEnabled(false);
                  continueButton.setEnabled(true);
                  continueMenu.setEnabled(true);
                  objButton.setEnabled(false);
                  objMenu.setEnabled(false);
                  stopButton.setEnabled(false);
                  stopobjButton.setEnabled(false);
                  stopMenu.setEnabled(false);
                  stopobjMenu.setEnabled(false);

                  captor.suspendCaptorThread();
                }
                else if(cmd.equals("continue"))
                {
                  continueButton.setEnabled(false);
                  continueMenu.setEnabled(false);
                  suspendButton.setEnabled(true);
                  suspendMenu.setEnabled(true);
                  objButton.setEnabled(true);
                  objMenu.setEnabled(true);
                  stopButton.setEnabled(true);
                  stopMenu.setEnabled(true);
                  captor.setStatus();
                  captor.continueCaptorThread();
                }
                else if(cmd.equals("object"))
                {
                  (new JSourceDialog(this)).setVisible(true);
                }
                else if(cmd.equals("stopobj"))
                {
                  stopobjButton.setEnabled(false);
                  stopobjMenu.setEnabled(false);
                  objstatus.setText("���ڽ��о���������");
                  ipstatus.setText("����������������");
                this.captor.setObjFlag(false);
                  captor.packetClear();
                }
                else if(cmd.startsWith("CUMSTAT")){
			int index=Integer.parseInt(cmd.substring(7));
			captor.addCumulativeStatFrame(JDStatisticsTakerLoader.getStatisticsTakerAt(index));
		}else if(cmd.startsWith("CONSTAT")){
			int index=Integer.parseInt(cmd.substring(7));
			captor.addContinuousStatFrame(JDStatisticsTakerLoader.getStatisticsTakerAt(index));
		}else if(cmd.startsWith("LaF")){
			try{
				UIManager.setLookAndFeel(cmd.substring(3));
				SwingUtilities.updateComponentTreeUI(this);
				SwingUtilities.updateComponentTreeUI(JSniffer.chooser);
			}catch(Exception e){}
		}
	}

	public void clear(){
		tablePane.clear();
	}


	public void startUpdating(){
		JDFrameUpdater.setRepeats(true);
		JDFrameUpdater.start();
	}

	public void stopUpdating(){
		JDFrameUpdater.stop();
		JDFrameUpdater.setRepeats(false);
		JDFrameUpdater.start();
	}

	javax.swing.Timer JDFrameUpdater=new javax.swing.Timer(500,new ActionListener(){
		public void actionPerformed(ActionEvent evt){

                          tablePane.fireTableChanged();
                          statusLabel.setText("�Ѳ������ݰ�������" + captor.getPackets().size());

			repaint();
		}
	});

	void loadProperty(){
		setSize(Integer.parseInt(JSniffer.JDProperty.getProperty("WinWidth","400")),
		        Integer.parseInt(JSniffer.JDProperty.getProperty("WinHeight","400")));
		setLocation(Integer.parseInt(JSniffer.JDProperty.getProperty("WinX","0")),
			Integer.parseInt(JSniffer.JDProperty.getProperty("WinY","0")));
	}

	void saveProperty(){
        	JSniffer.JDProperty.put("WinWidth",String.valueOf(getBounds().width));
		JSniffer.JDProperty.put("WinHeight",String.valueOf(getBounds().height));
		JSniffer.JDProperty.put("WinX",String.valueOf(getBounds().x));
		JSniffer.JDProperty.put("WinY",String.valueOf(getBounds().y));

		tablePane.saveProperty();

		JSniffer.saveProperty();
	}

	public void enableCapture(){
		openMenu.setEnabled(true);
		openButton.setEnabled(true);
		saveMenu.setEnabled(true);
		saveButton.setEnabled(true);
		captureMenu.setEnabled(true);
		captureButton.setEnabled(true);
                continueButton.setEnabled(false);
                continueMenu.setEnabled(false);
                suspendButton.setEnabled(false);
                suspendMenu.setEnabled(false);
		stopMenu.setEnabled(false);
		stopButton.setEnabled(false);
                objMenu.setEnabled(false);
                objButton.setEnabled(false);
                stopobjButton.setEnabled(false);
                stopobjMenu.setEnabled(false);
	}

	public void disableCapture(){
		openMenu.setEnabled(false);
		openButton.setEnabled(false);
		captureMenu.setEnabled(false);
		captureButton.setEnabled(false);
		saveMenu.setEnabled(true);
		saveButton.setEnabled(true);
		stopMenu.setEnabled(true);
		stopButton.setEnabled(true);
                suspendButton.setEnabled(true);
                suspendMenu.setEnabled(true);
                continueButton.setEnabled(false);
                continueMenu.setEnabled(false);
                objMenu.setEnabled(true);
                objButton.setEnabled(true);
                stopobjMenu.setEnabled(false);
                stopobjButton.setEnabled(false);
	}

	private ImageIcon getImageIcon(String path){
		return new ImageIcon(this.getClass().getResource(path));
	}

	ButtonGroup lafGroup=new ButtonGroup();
	private JRadioButtonMenuItem createLaFMenuItem(String name,String lafName){
		JRadioButtonMenuItem item=new JRadioButtonMenuItem(name);
		item.setActionCommand("LaF"+lafName);
		item.addActionListener(this);
		lafGroup.add(item);

		try {
			Class lnfClass = Class.forName(lafName);
			LookAndFeel newLAF = (LookAndFeel)(lnfClass.newInstance());
			if(!newLAF.isSupportedLookAndFeel()) item.setEnabled(false);
		} catch(Exception e) {
			item.setEnabled(false);
		}

		return item;
	}
}
