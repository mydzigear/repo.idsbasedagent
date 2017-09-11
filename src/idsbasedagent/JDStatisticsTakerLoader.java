package JSniffer;
import java.util.*;

import JSniffer.stat.ApplicationProtocolStat;
import JSniffer.stat.JDStatisticsTaker;
import JSniffer.stat.NetworkProtocolStat;

import JSniffer.stat.TransportProtocolStat;

public class JDStatisticsTakerLoader
{
	static Vector stakers=new Vector();

	static void loadStatisticsTaker(){
		stakers.addElement(new NetworkProtocolStat());
		stakers.addElement(new TransportProtocolStat());
		stakers.addElement(new ApplicationProtocolStat());

	}

	public static JDStatisticsTaker[] getStatisticsTakers(){
		JDStatisticsTaker[] array=new JDStatisticsTaker[stakers.size()];

		for(int i=0;i<array.length;i++)
			array[i]=(JDStatisticsTaker)stakers.elementAt(i);

		return array;
	}

	public static JDStatisticsTaker getStatisticsTakerAt(int index){
		return (JDStatisticsTaker)stakers.get(index);
	}
}
