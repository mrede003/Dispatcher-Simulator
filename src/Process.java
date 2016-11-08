
public class Process {
	private int priority;
	private String id;
	private boolean executing;
	private boolean blocked;
	private boolean ready;
	
	
	public Process(int p, String i)
	{
		id=i;
		priority=p;
		executing=false;
		blocked=false;
		ready=false;
	}
	public String getID()
	{
		return id;
	}
	public int getPriority()
	{
		return priority;
	}
	public boolean isExecuting()
	{
		return executing;
	}
	public boolean isReady()
	{
		return ready;
	}
	public boolean isBlocked()
	{
		return blocked;
	}
	public void setExec(boolean e)
	{
		executing=e;
	}
	public void setBlocked(boolean b)
	{
		blocked=b;
	}
	public void setReady(boolean r)
	{
		ready=r;
	}
}
