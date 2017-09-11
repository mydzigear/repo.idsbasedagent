/*
 * Created on Apr 4, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package JSniffer;

import java.io.File;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import JSniffer.stat.JDStatisticsTaker;
import JSniffer.ui.JDCaptureDialog;
import JSniffer.ui.JDContinuousStatFrame;
import JSniffer.ui.JDCumlativeStatFrame;
import JSniffer.ui.JDFrame;
import JSniffer.ui.JDStatFrame;

import jpcap.Jpcap;
import jpcap.JpcapHandler;
import jpcap.JpcapWriter;
import jpcap.Packet;
import jpcap.*;
import JSniffer.analyzer.*;

/**
 * @author kfujii
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class JDCaptor {
	long MAX_PACKETS_HOLD=10000;

	Vector packets = new Vector();
        Packet lastPacket;

         String objIP;
        boolean objflag=false;
        boolean isSuspend=false;
        boolean isContinue=false;

        public void setStatus()
        {
          if(objflag)
          {
            frame.stopobjButton.setEnabled(true);
            frame.stopobjMenu.setEnabled(true);
            frame.objstatus.setText("指定对象监听");
          }
          else
          {
            frame.stopobjButton.setEnabled(false);
            frame.stopobjMenu.setEnabled(false);
            frame.objstatus.setText("正在进行局域网监听");
          }
        }

        public void packetClear()
        {
          packets.clear();
          for(int i=0;i<sframes.size();i++)
          ((JDStatFrame)sframes.get(i)).clear();
        }

        public void  continueCaptorThread()
        {
          isContinue=true;
          isSuspend=false;
        }

        public void suspendCaptorThread()
        {
          isContinue=false;
          isSuspend=true;
        }

        public void setObjFlag(boolean flag)
        {
          objflag=flag;
        }

        public void setObjIP(String ip)
        {
          objIP=ip;
        }

        public void setLastPacket(Packet p)
        {
          lastPacket=p;
        }

        public Packet getLastPacket()
        {
          return lastPacket;
        }

	Jpcap jpcap=null,lastJpcap=null;

      boolean isLiveCapture;

       public void setIsLiveCapture(boolean flag)
       {
         isLiveCapture=flag;
       }
	boolean isSaved = false;

	public JDFrame frame;

	public void setJDFrame(JDFrame frame){
		this.frame=frame;
	}

	public Vector getPackets(){
		return packets;
	}


	public void capturePacketsFromDevice() {
		lastJpcap = jpcap = JDCaptureDialog.getJpcap(frame);

		clear();

		if (jpcap != null) {
                        frame.objstatus.setText("正在进行局域网监听");
                        frame.ipstatus.setText("本网段所有主机");
			isLiveCapture = true;
			frame.disableCapture();
			startCaptureThread();
		}
	}

	public void loadPacketsFromFile() {
		isLiveCapture = false;
		clear();

		int ret = JSniffer.chooser.showOpenDialog(frame);
		if (ret == JFileChooser.APPROVE_OPTION) {
			String path = JSniffer.chooser.getSelectedFile().getPath();
			String filename = JSniffer.chooser.getSelectedFile().getName();

			try {
				lastJpcap=jpcap = Jpcap.openFile(path);
			} catch (java.io.IOException e) {
				JOptionPane.showMessageDialog(
					frame,
					"Can't open file: " + path);
				return;
			}

			frame.disableCapture();

			startCaptureThread();
		}
	}

	public void clear(){
		packets.clear();
		frame.clear();

		for(int i=0;i<sframes.size();i++)
			((JDStatFrame)sframes.get(i)).clear();
	}

	public void saveToFile() {
		if (packets == null)
			return;

		int ret = JSniffer.chooser.showSaveDialog(frame);
		if (ret == JFileChooser.APPROVE_OPTION) {
			File file = JSniffer.chooser.getSelectedFile();

			if (file.exists()) {
				if (JOptionPane
					.showConfirmDialog(
						frame,
						"Overwrite " + file.getName() + "?",
						"Overwrite?",
						JOptionPane.YES_NO_OPTION)
					== JOptionPane.NO_OPTION) {
					return;
				}
			}

			try {
				//System.out.println("link:"+info.linktype);
				System.out.println(lastJpcap);
				JpcapWriter writer = new JpcapWriter(lastJpcap,file.getPath());

				for (int i = 0; i < packets.size(); i++) {
					writer.writeDumpFile((Packet) packets.elementAt(i));
				}

				writer.closeDumpFile();
				isSaved = true;
			} catch (java.io.IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(
					frame,
					"Can't save file: " + file.getPath());
			}
		}
	}

	public void stopCapture() {
                frame.disableCapture();
		stopCaptureThread();
	}

	public void saveIfNot() {
		if (isLiveCapture && !isSaved) {
			int ret =
				JOptionPane.showConfirmDialog(
					null,
					"Save this data?",
					"Save this data?",
					JOptionPane.YES_NO_OPTION);
			if (ret == JOptionPane.YES_OPTION)
				saveToFile();
		}
	}

	Vector sframes=new Vector();
	public void addCumulativeStatFrame(JDStatisticsTaker taker) {
		sframes.add(JDCumlativeStatFrame.openWindow(packets,taker.newInstance()));
	}

	public void addContinuousStatFrame(JDStatisticsTaker taker) {
		sframes.add(JDContinuousStatFrame.openWindow(packets,taker.newInstance()));
	}

	public void closeAllWindows(){
		for(int i=0;i<sframes.size();i++)
			((JDStatFrame)sframes.get(i)).dispose();
	}



	private Thread captureThread;

	public void startCaptureThread() {
		if (captureThread != null)
			return;

		captureThread = new Thread(new Runnable(){
			//body of capture thread
			public void run() {
				while (captureThread != null) {
                                    while(isSuspend==true && isContinue==false)
                                        {
                                          try{
                                                      captureThread.sleep(5);
                                              }
                                          catch(InterruptedException e){}
                                        }
					if (jpcap.processPacket(1, handler) == 0 && !isLiveCapture)
						stopCaptureThread();
					Thread.yield();
				}

				jpcap.close();
				jpcap = null;
				frame.enableCapture();
			}
		});
		captureThread.setPriority(Thread.MIN_PRIORITY);

		frame.startUpdating();
		for(int i=0;i<sframes.size();i++){
			((JDStatFrame)sframes.get(i)).startUpdating();
		}

		captureThread.start();
	}

	public void stopCaptureThread() {
		captureThread = null;
		frame.stopUpdating();
		for(int i=0;i<sframes.size();i++){
			((JDStatFrame)sframes.get(i)).stopUpdating();
		}
	}


	private JpcapHandler handler=new JpcapHandler(){
		public void handlePacket(Packet packet) {
//                        setLastPacket(packet);
                  if(objflag)
                  {

                          IPPacket ip;
                       if((new IPv4Analyzer()).isAnalyzable(packet))
                          {
                            ip=(IPPacket)packet;
//                            System.out.println(ip.src_ip.getHostAddress()+"——"+objIP+"=="+ip.src_ip.getHostAddress().equals(objIP));
                         if(ip.src_ip.getHostAddress().equals(objIP))
                            {
                                packets.addElement(packet);
                            while (packets.size() > MAX_PACKETS_HOLD) {
                                packets.removeElementAt(0);
                               }
                            if (!sframes.isEmpty()) {
                            for (int i = 0; i < sframes.size(); i++)
                                      ( (JDStatFrame) sframes.get(i)).addPacket(packet);
                                }
                                isSaved = false;

                             }
                          }
                  }
                  else
                  {
                    packets.addElement(packet);
                    while (packets.size() > MAX_PACKETS_HOLD) {
                      packets.removeElementAt(0);
                    }
                    if (!sframes.isEmpty()) {
                      for (int i = 0; i < sframes.size(); i++)
                        ( (JDStatFrame) sframes.get(i)).addPacket(packet);
                    }
                    isSaved = false;
                  }
		}
	};

}
