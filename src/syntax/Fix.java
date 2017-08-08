/**
 * 
 */
package syntax;

/**
 * @author schkrabi
 *
 */
public class Fix extends Expr {
	public final Expr expr;
	
	public Fix(Expr expr){
		this.expr = expr;
	}
	
	public String toString(){
		return "let rec " + expr;
	}
}
