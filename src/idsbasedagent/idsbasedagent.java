


public class idsbasedagent{
	JDCaptor captor ;
	
	public idsbasedagent(){
		captor=new JDCaptor();
	}
	
	public static void main(String[] args){
 		System.out.println("Memulai Ethereal");
		idsbasedagent agent=new idsbasedagent();
		agent.captor.capturesFromDevice();
	}
}