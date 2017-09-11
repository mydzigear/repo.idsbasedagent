

import java.io.File;
import java.util.Vector;

/*import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import idsbasedagent.stat.JDStatisticsTaker;
import idsbasedagent.ui.JDCaptureDialog;
import idsbasedagent.ui.JDContinuousStatFrame;
import idsbasedagent.ui.JDCumlativeStatFrame;
import idsbasedagent.ui.JDFrame;
import idsbasedagent.ui.JDStatFrame;*/

import jpcap.Jpcap;
import jpcap.JpcapHandler;
import jpcap.JpcapWriter;
import jpcap.Packet;

/**
 * @author kfujii
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class JDCaptor {
	long MAX_PACKETS_HOLD=10000;

	//Vector Packet Grup
	Vector packets = new Vector();

	//????
	Jpcap jpcap=null,lastJpcap=null;

	boolean isLiveCapture;
	boolean isSaved = false;

	//JDFrame frame;

	/*public void setJDFrame(JDFrame frame){
		this.frame=frame;
	}

	//Kembali ke intercept paket
	public Vector getPackets(){
		return packets;
	}


	//Memilih perangkat jaringan dari awal Ethereal user
	public void capturePacketsFromDevice() {
		//Jpcap Sangat ringan,JpcapHandle Tindakan terhadap paket yg di Capture
		lastJpcap = jpcap = 
                Jpcap.openDevice(Jpcap.getDeviceList()[0], 1000, false, 20);
		clear();
		
		if (jpcap != null) {
			isLiveCapture = true;
			//Set opsi terkait di window capture packet
			//frame.disableCapture();
			
			//Mulai thread di kartu capture paket tertentu (Class dalam anonim)
			startCaptureThread();
		}
	}

	//Dari file yang ditentukan pengguna ke dalam paket, dan kemudian memulai capture paket baru
	public void loadPacketsFromFile() {

		//Stop Ethereal
		isLiveCapture = false;
		clear();

		int ret = JpcapDumper.chooser.showOpenDialog(frame);
		if (ret == JFileChooser.APPROVE_OPTION) {
			String path = JpcapDumper.chooser.getSelectedFile().getPath();
			String filename = JpcapDumper.chooser.getSelectedFile().getName();

			try {
				lastJpcap=jpcap = Jpcap.openFile(path);
			} catch (java.io.IOException e) {
				JOptionPane.showMessageDialog(
					frame,
					"Can't open file: " + path);
				return;
			}

			//Setel opsi yang relevan di window capture paket
			frame.disableCapture();

			//Mulai kartu paket capture di thread tertentu (class dalam)
			startCaptureThread();
		}
	}

	private void clear(){
		packets.clear();
		frame.clear();

		for(int i=0;i<sframes.size();i++)
			((JDStatFrame)sframes.get(i)).clear();
	}


	//Capture paket baru disimpan dalam file
	public void saveToFile() {
		if (packets == null)
			return;

		int ret = JpcapDumper.chooser.showSaveDialog(frame);
		if (ret == JFileChooser.APPROVE_OPTION) {
			File file = JpcapDumper.chooser.getSelectedFile();

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


	//Menghentikan Ethereal
	public void stopCapture() {
		//Menghentikan capture paket dari baris tersebut
		stopCaptureThread();
	}


	//Ditanya apakah packets diambil ke file
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

	//sframes Apakah sistem untuk jaringan yang berbeda capture paket perangkat untuk membuka jendela dialog vektor
	Vector sframes=new Vector();
	
	//???
	public void addCumulativeStatFrame(JDStatisticsTaker taker) {
		sframes.add(JDCumlativeStatFrame.openWindow(packets,taker.newInstance()));
	}

	
	//???
	public void addContinuousStatFrame(JDStatisticsTaker taker) {
		sframes.add(JDContinuousStatFrame.openWindow(packets,taker.newInstance()));
	}

	//Tutup semua perangkat loop terbuka dialog jaringan Ethereal
	public void closeAllWindows(){
		for(int i=0;i<sframes.size();i++)
			((JDStatFrame)sframes.get(i)).dispose();
	}*/



	private Thread captureThread;
	
	//Fungsi 'capture packet Awal
	 public void startCaptureThread() {
		//Baris Ethereal mengatakan telah mulai
		if (captureThread != null)       
			return;

		//captureThread Thread implisit adalah class anonim, berfungsi spesifik untuk menyelesaikan Ethereal
		captureThread = new Thread(new Runnable(){
			//body of capture thread
			public void run() {
				//????
				while (captureThread != null) {

					//jpcap.processPacket(1,handler)=0 packets tidak tertangkap
					if (jpcap.processPacket(1, handler) == 0 && !isLiveCapture)
						stopCaptureThread();
					//Menghentikan thread
					Thread.yield();
				}

				jpcap.close();
				jpcap = null;
				//frame.enableCapture();
			}
		});
		captureThread.setPriority(Thread.MIN_PRIORITY);
		
		/*frame.startUpdating();
		for(int i=0;i<sframes.size();i++){
			((JDStatFrame)sframes.get(i)).startUpdating();
		}*/
		
		//Mulai capture packet Thread
		captureThread.start();
	}

	void stopCaptureThread() {
		captureThread = null;
		//frame.stopUpdating();
		/*for(int i=0;i<sframes.size();i++){
			((JDStatFrame)sframes.get(i)).stopUpdating();
		}*/
	}


	//Anonymous class inner untuk mengimplementasikan JpcapHandler Interface,JpcapDenganhandlePacket Pemrosesan packets diambil;
	//Digunakan untuk menerapkan class inner anonim JpcapHandler Interface, dan mengembalikan referensi nya;
	//Yang berarti Spesifik: menghasilkan suatu objek class anonim, pewarisan class ke anonim JpcapHandle?new References ini akan dikembalikan otomatis upCast ke JpcapHandle Kutipan?
	private JpcapHandler handler=new JpcapHandler(){
		public void handlePacket(Packet packet) {
			packets.addElement(packet);
			while (packets.size() > MAX_PACKETS_HOLD) {
				packets.removeElementAt(0);
			}
			/*if (!sframes.isEmpty()) {
				for (int i = 0; i < sframes.size(); i++)
					((JDStatFrame)sframes.get(i)).addPacket(packet);
			}*/
			isSaved = false;
		}
	};

}
