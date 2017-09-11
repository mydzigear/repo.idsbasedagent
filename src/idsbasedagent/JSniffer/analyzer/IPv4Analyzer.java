package JSniffer.analyzer;
import jpcap.*;
import java.util.*;

public class IPv4Analyzer extends JDPacketAnalyzer
{
	private static final String[] valueNames={"(IPv4)Version",
            "(IPv4)TOS: Priority",
            "(IPv4)TOS: Throughput",
            "(IPv4)TOS: Reliability",
            "(IPv4)Length",
            "(IPv4)Identification",
            "(IPv4)Fragment: Don't Fragment",
            "(IPv4)Fragment: More Fragment",
            "(IPv4)Fragment Offset",
            "(IPv4)Time To Live",
            "(IPv4)Protocol",
            "(IPv4)Source IP",
            "(IPv4)Destination IP",
            "(IPv4)Source Host Name",
            "(IPv4)Destination Host Name"};
	private Hashtable values=new Hashtable();

	public IPv4Analyzer(){
		layer=NETWORK_LAYER;
	}

	public boolean isAnalyzable(Packet p){
		if(p instanceof IPPacket && ((IPPacket)p).version==4) return true;
		else return false;
	}

	public String getProtocolName(){
		return "IPv4";
	}

	public String[] getValueNames(){
		return valueNames;
	}

	public void analyze(Packet packet){
		values.clear();
		if(!isAnalyzable(packet))	return;
		IPPacket ip=(IPPacket)packet;
		values.put(valueNames[0],new Integer(4));
		values.put(valueNames[1],new Integer(ip.priority));
		values.put(valueNames[2],new Boolean(ip.t_flag));
		values.put(valueNames[3],new Boolean(ip.r_flag));
		values.put(valueNames[4],new Integer(ip.length));
		values.put(valueNames[5],new Integer(ip.ident));
		values.put(valueNames[6],new Boolean(ip.dont_frag));
		values.put(valueNames[7],new Boolean(ip.more_frag));
		values.put(valueNames[8],new Integer(ip.offset));
		values.put(valueNames[9],new Integer(ip.hop_limit));
		values.put(valueNames[10],new Integer(ip.protocol));
		values.put(valueNames[11],ip.src_ip.getHostAddress());
		values.put(valueNames[12],ip.dst_ip.getHostAddress());
		values.put(valueNames[13],ip.src_ip);
		values.put(valueNames[14],ip.dst_ip);
	}

	public Object getValue(String valueName){
		if((valueNames[13].equals(valueName) && values.get(valueName) instanceof IPAddress) ||
		   (valueNames[14].equals(valueName) && values.get(valueName) instanceof IPAddress)){

			try{
				values.put(valueName,((IPAddress)values.get(valueName)).getHostName());
			}catch(java.net.UnknownHostException e){
				values.put(valueName,((IPAddress)values.get(valueName)).getHostAddress());
			}
		}

		return values.get(valueName);
	}

	Object getValueAt(int index){
		if(index<0 || index>=valueNames.length) return null;

		if((index==13 && values.get(valueNames[index]) instanceof IPAddress) ||
		   (index==14 && values.get(valueNames[index]) instanceof IPAddress)){
			try{
				values.put(valueNames[index],((IPAddress)values.get(valueNames[index])).getHostName());
			}catch(java.net.UnknownHostException e){
				values.put(valueNames[index],((IPAddress)values.get(valueNames[index])).getHostAddress());
			}
		}

		return values.get(valueNames[index]);
	}

	public Object[] getValues(){
		Object[] v=new Object[valueNames.length];

		for(int i=0;i<valueNames.length;i++)
			v[i]=getValueAt(i);

		return v;
	}
}
