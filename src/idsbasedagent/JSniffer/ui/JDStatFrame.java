package JSniffer.ui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import jpcap.*;
import javax.swing.*;

public abstract class JDStatFrame extends JFrame
{
	JDStatFrame(String title){
		super(title);
		JDStatFrameUpdater.start();
		addWindowListener(new java.awt.event.WindowAdapter(){
			public void windowClosed(java.awt.event.WindowEvent evt){
				hide();
			}
		});
	}
	abstract void fireUpdate();
	public abstract void addPacket(Packet p);
	public abstract void clear();

	public void startUpdating(){
		JDStatFrameUpdater.setRepeats(true);
		JDStatFrameUpdater.start();
	}

	public void stopUpdating(){
		JDStatFrameUpdater.stop();
		JDStatFrameUpdater.setRepeats(false);
		JDStatFrameUpdater.start();
	}

	javax.swing.Timer JDStatFrameUpdater=new javax.swing.Timer(500,new ActionListener(){
		public void actionPerformed(ActionEvent evt){
			fireUpdate();
			repaint();
		}
	});

}
