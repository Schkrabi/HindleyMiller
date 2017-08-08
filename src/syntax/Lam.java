/**
 * 
 */
package syntax;

/**
 * @author schkrabi
 *
 */
public class Lam extends Expr {
	public final String name;
	public final Expr expr;
	
	public Lam(String name, Expr expr){
		this.name = name;
		this.expr = expr;
	}
	
	public String toString(){
		return "lambda " + this.name + ": " + this.expr.toString();
	}
}
