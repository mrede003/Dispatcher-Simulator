import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

public class simulator {

	private PriorityQueue<Process> readyQueue;
	private Process runningProcess;
	private ArrayList<Process> blockedList;
	private ArrayList<Process> allProcesses;
	private Random generator;

	public simulator(int queueSize) {
		generator = new Random(9999);
		Comparator<Process> comparator = new ProcessComparator();
		readyQueue = new PriorityQueue<Process>(queueSize, comparator);
		blockedList = new ArrayList<Process>(queueSize);
		allProcesses = new ArrayList<Process>(queueSize);
	}

	public void start() {
		runningProcess = removeProcess();
		runningProcess.setReady(false);
		runningProcess.setBlocked(false);
		runningProcess.setExec(true);
	}

	public void print() {
		while (readyQueue.size() != 0) {
			System.out.println(removeProcess().getID());
		}
	}

	public void addProcessReady(int pr) {
		Process p = makeNewProcess(pr);
		p.setReady(true);
		p.setBlocked(false);
		p.setExec(false);
		readyQueue.add(p);
	}

	public void addProcessBlocked(int pr) {
		Process p = makeNewProcess(pr);
		p.setReady(false);
		p.setBlocked(true);
		p.setExec(false);
		blockedList.add(p);
	}

	public Process makeNewProcess(int p) {
		String ran = Integer.toString(generator.nextInt(9999));
		Process i = new Process(p, ran);
		return i;
	}

	public Process removeProcess() {
		return readyQueue.remove();
	}

	public Process getRunningProcess() {
		return runningProcess;
	}

	public ArrayList<Process> getBlockedList() {
		return blockedList;
	}

	public ArrayList<Process> getAllProcesses() {
		return allProcesses;
	}

	public PriorityQueue<Process> getReadyQueue() {
		return readyQueue;
	}

	public String getStatus() {
		String init = "Running\n";
		if (runningProcess != null) {
			init = init + "\tProcess ID: " + runningProcess.getID() + "  "
					+ "Priority: " + runningProcess.getPriority() + "\n";
		} else {
			init = init + "\tProcess ID: " + "NULL" + "  " + "Priority: "
					+ "NULL" + "\n";
		}

		init = init + "Ready Queue\n";

		if (!readyQueue.isEmpty()) {
			Object[] tempP = getReadyQueue().toArray();
			for (int i = 0; i < tempP.length; i++) {
				Process tempProc = readyQueue.remove();
				init = init + "\tProcess ID: " + tempProc.getID() + "  "
						+ "Priority: " + tempProc.getPriority() + "\n";
			}
			for (int i = 0; i < tempP.length; i++) {
				readyQueue.add((Process) tempP[i]);
			}
		} else {
			init = init + "\tEmpty\n";
		}
		init = init + "BlockedList\n";
		if (!blockedList.isEmpty()) {
			for (int i = 0; i < blockedList.size(); i++) {
				init = init + "\tProcess ID: " + blockedList.get(i).getID()
						+ "  " + "Priority: "
						+ blockedList.get(i).getPriority() + "\n";
			}
		} else {
			init = init + "\tEmpty\n";
		}
		return init;
	}

	public void contextSwitch() {
		if (!readyQueue.isEmpty()) {
			runningProcess = readyQueue.remove();
		} else {
			runningProcess = null;
		}
	}

	private class ProcessComparator implements Comparator<Process> {

		public int compare(Process arg0, Process arg1) {
			if (arg0.getPriority() < arg1.getPriority()) {
				return -1;
			}
			if (arg0.getPriority() > arg1.getPriority()) {
				return 1;
			}
			return 0;
		}

	}
}