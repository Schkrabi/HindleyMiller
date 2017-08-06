/**
 * 
 */
package syntax;

/**
 * @author schkrabi
 *
 */
public class Fix extends Expr {
	private Expr expr;
	
	public Fix(Expr expr){
		this.expr = expr;
	}
	
	public String toString(){
		return "let rec " + expr;
	}
}
