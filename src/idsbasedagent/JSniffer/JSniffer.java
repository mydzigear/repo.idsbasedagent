/**
 * JSniffer Õ¯¬Áº‡Ã˝≥Ã–Ú
 * @authorπ˘√Ù’‹,Œ‚≈Ù
 * @version1.0 ,2004ƒÍ‘¬8»’
 * @see JSniffer#JSniffer
 * @since JSniffer v1.0
 */


package JSniffer;
import java.util.*;
import java.io.*;
import JSniffer.ui.JDFrame;

public class JSniffer
{
	public static Properties JDProperty;

	public static javax.swing.JFileChooser chooser=new javax.swing.JFileChooser();

	static Vector frames=new Vector();

	public static void main(String[] args){
		JDPacketAnalyzerLoader.loadDefaultAnalyzer();
		JDStatisticsTakerLoader.loadStatisticsTaker();
		loadProperty();

		openNewWindow();
	}

	public static void saveProperty(){
		if(JDProperty==null) return;
		try{
			JDProperty.store((OutputStream)new FileOutputStream("JSniffer.property"),"JSniffer");
			//JDProperty.store(new FileOutputStream("JSniffer.property"),"JSniffer");
		}catch(IOException e){
		}catch(ClassCastException e){
		}
	}

	static void loadProperty(){
		try{
			JDProperty=new Properties();
			JDProperty.load((InputStream)new FileInputStream("JSniffer.property"));
		}catch(IOException e){
		}
	}

	public static void openNewWindow(){
		JDCaptor captor=new JDCaptor();
		frames.add(JDFrame.openNewWindow(captor));
	}

	public static void closeWindow(JDFrame frame){
		frame.captor.stopCapture();
		frame.captor.saveIfNot();
		frame.captor.closeAllWindows();
		frames.remove(frame);
		frame.dispose();
		if(frames.isEmpty()){
			saveProperty();
			System.exit(0);
		}
	}

	protected void finalize() throws Throwable{
		saveProperty();
	}
}
