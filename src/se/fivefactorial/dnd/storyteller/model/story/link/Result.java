package se.fivefactorial.dnd.storyteller.model.story.link;

public class Result {
	private int roll;
	private int mod;
	private String check;
	private boolean sucess;
	
	public Result(int roll, int mod, String check, boolean sucess) {
		this.roll = roll;
		this.mod = mod;
		this.check = check;
		this.sucess = sucess;
	}
	
	public int getRoll() {
		return roll;
	}
	public int getMod() {
		return mod;
	}
	public String getCheck() {
		return check;
	}
	public boolean isSucess() {
		return sucess;
	}
	
}
