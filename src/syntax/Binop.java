package syntax;

public enum Binop {
	Add("+"),
	Sub("-"),
	Mul("*"),
	Eql("=");
	
	private final String op;
	
	private Binop(String op){
		this.op = op;
	}
	
	public String toString(){
		return this.op;
	}
}
