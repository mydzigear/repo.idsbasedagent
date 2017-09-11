package idsbasedagent.analyzer;
import jpcap.*;

public abstract class JDPacketAnalyzer
{
	//Signature perjanjian kerja untuk analisis yang lapisan dalam jaringan
	public int layer=DATALINK_LAYER;
	//Network lapisan label khusus
	public static int DATALINK_LAYER=0;
	public static int NETWORK_LAYER=1;
	public static int TRANSPORT_LAYER=2;
	public static int APPLICATION_LAYER=3;
	
	//Tentukan apakah paket paket protokol tipe yang dipilih
	public abstract boolean isAnalyzable(Packet packet);
	//Tergantung pada paket protokol untuk analisis, mengekstraksi bidang yang relevan di header dengan (nama item, nilai item) dari hashtable disimpan
	public abstract void analyze(Packet packet);
	//Mengembalikan nama protokol untuk analisis
	public abstract String getProtocolName();
	//Kembali ke item entri untuk analisis
	public abstract String[] getValueNames();
	//Kembali kata kunci ValueName adalah objek tertentu di dalam HashMap
	public abstract Object getValue(String valueName);
	abstract Object getValueAt(int index);
	public abstract Object[] getValues();
}
