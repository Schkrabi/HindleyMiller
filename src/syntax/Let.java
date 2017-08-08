/**
 * 
 */
package syntax;

/**
 * @author schkrabi
 *
 */
public class Let extends Expr {
	public final String name;
	public final Expr	bind;
	public final Expr	expr;
	
	public Let(String name, Expr bind, Expr expr){
		this.name = name;
		this.bind = bind;
		this.expr = expr;
	}
	
	public String toString(){
		return "let " + this.name + " (" + this.bind.toString() + ") " + this.expr.toString();
	}
}
