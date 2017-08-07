/**
 * 
 */
package syntax;

/**
 * @author schkrabi
 *
 */
public class Lam extends Expr {
	private String name;
	private Expr expr;
	
	public Lam(String name, Expr expr){
		this.name = name;
		this.expr = expr;
	}
	
	public String toString(){
		return "lambda " + this.getName() + ": " + getExpr().toString();
	}

	public String getName() {
		return name;
	}

	public Expr getExpr() {
		return expr;
	}
}
