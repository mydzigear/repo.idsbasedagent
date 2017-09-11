
import java.util.*;

import idsbasedagent.analyzer.*;

public class JDPacketAnalyzerLoader
{
	static Vector analyzers=new Vector();


	//Analisis persetujuan ke dalam kategori default
	static void loadDefaultAnalyzer(){
		analyzers.addElement(new PacketAnalyzer());
		analyzers.addElement(new EthernetAnalyzer());
		analyzers.addElement(new IPv4Analyzer());
		analyzers.addElement(new IPv6Analyzer());
		analyzers.addElement(new TCPAnalyzer());
		analyzers.addElement(new UDPAnalyzer());
		analyzers.addElement(new ICMPAnalyzer());
		analyzers.addElement(new HTTPAnalyzer());
		analyzers.addElement(new FTPAnalyzer());
		analyzers.addElement(new TelnetAnalyzer());
		analyzers.addElement(new SSHAnalyzer());
		analyzers.addElement(new SMTPAnalyzer());
		analyzers.addElement(new POP3Analyzer());
		analyzers.addElement(new ARPAnalyzer());
	}
	
	//Analisis persetujuan untuk mengembalikan array
	public static JDPacketAnalyzer[] getAnalyzers(){
		JDPacketAnalyzer[] array=new JDPacketAnalyzer[analyzers.size()];
		
		for(int i=0;i<array.length;i++)
			array[i]=(JDPacketAnalyzer)analyzers.elementAt(i);
			
		return array;
	}
	
	public static JDPacketAnalyzer[] getAnalyzersOf(int layer){
		Vector v=new Vector();
		
		for(int i=0;i<analyzers.size();i++)
			if(((JDPacketAnalyzer)analyzers.elementAt(i)).layer==layer)
				v.addElement(analyzers.elementAt(i));
		
		JDPacketAnalyzer[] res=new JDPacketAnalyzer[v.size()];
		for(int i=0;i<res.length;i++)
			res[i]=(JDPacketAnalyzer)v.elementAt(i);
		
		return res;
	}
}
