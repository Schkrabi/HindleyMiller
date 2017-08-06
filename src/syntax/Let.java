/**
 * 
 */
package syntax;

/**
 * @author schkrabi
 *
 */
public class Let extends Expr {
	private String 	name;
	private Expr	bind;
	private Expr	expr;
	
	public Let(String name, Expr bind, Expr expr){
		this.name = name;
		this.bind = bind;
		this.expr = expr;
	}
	
	public String toString(){
		return "let " + this.name + " (" + this.bind.toString() + ") " + this.expr.toString();
	}
}
