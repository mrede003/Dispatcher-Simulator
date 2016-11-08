
public class dispatcherSim {

	
	
	public static void main(String[] args)
    {
		simulator sim=new simulator(1000);
		sim.addProcessReady(1);						//test data
		sim.addProcessReady(3);
		sim.addProcessReady(2);
		sim.addProcessReady(7);
		sim.addProcessReady(4);
		sim.addProcessBlocked(1);
		sim.addProcessBlocked(3);
		sim.addProcessBlocked(2);
		sim.addProcessBlocked(7);
		sim.addProcessBlocked(4);
		GUI gui=new GUI(sim);
		gui.setVisible(true);
		
    }
}
