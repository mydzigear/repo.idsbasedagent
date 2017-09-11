package JSniffer.analyzer;
import jpcap.*;
import java.util.*;

public class IPv6Analyzer extends JDPacketAnalyzer
{
	private static final String[] valueNames={
            "(IPv6)Version",
            "(IPv6)Class",
            "(IPv6)Flow Label",
            "(IPv6)Length",
            "(IPv6)Protocol",
            "(IPv6)Hop Limit",
            "(IPv6)Source IP",
            "(IPv6)Destination IP",
            "(IPv6)Source Host Name",
            "(IPv6)Destination Host Name"};


	Hashtable values=new Hashtable();

	public IPv6Analyzer(){
		layer=NETWORK_LAYER;
	}

	public boolean isAnalyzable(Packet p){
		if(p instanceof IPPacket && ((IPPacket)p).version==6) return true;
		else return false;
	}

	public String getProtocolName(){
		return "IPv6";
	}

	public String[] getValueNames(){
		return valueNames;
	}

	public void analyze(Packet packet){
		values.clear();
		if(!isAnalyzable(packet))	return;
		IPPacket ip=(IPPacket)packet;
		values.put(valueNames[0],new Integer(6));
		values.put(valueNames[1],new Integer(ip.priority));
		values.put(valueNames[2],new Integer(ip.flow_label));
		values.put(valueNames[3],new Integer(ip.length));
		values.put(valueNames[4],new Integer(ip.protocol));
		values.put(valueNames[5],new Integer(ip.hop_limit));
		values.put(valueNames[6],ip.src_ip.getHostAddress());
		values.put(valueNames[7],ip.dst_ip.getHostAddress());
		try{
			values.put(valueNames[8],ip.src_ip.getHostName());
		}catch(java.net.UnknownHostException e){
			values.put(valueNames[8],ip.src_ip.getHostAddress());
		}
		try{
			values.put(valueNames[9],ip.dst_ip.getHostName());
		}catch(java.net.UnknownHostException e){
			values.put(valueNames[9],ip.dst_ip.getHostAddress());
		}
	}

	public Object getValue(String valueName){
		return values.get(valueName);
	}

	Object getValueAt(int index){
		if(index<0 || index>=valueNames.length) return null;
		return values.get(valueNames[index]);
	}

	public Object[] getValues(){
		Object[] v=new Object[valueNames.length];

		for(int i=0;i<valueNames.length;i++)
			v[i]=values.get(valueNames[i]);

		return v;
	}
}
