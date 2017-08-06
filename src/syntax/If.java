/**
 * 
 */
package syntax;

/**
 * @author schkrabi
 *
 */
public class If extends Expr {
	private Expr cond;
	private Expr tExpr;
	private Expr fExpr;
	
	public If(Expr cond, Expr tExpr, Expr fExpr){
		this.cond = cond;
		this.tExpr = tExpr;
		this.fExpr = fExpr;
	}
	
	public String toString(){
		return "if " + cond.toString() + " then " + tExpr.toString() + " else " + fExpr.toString();
	}
}
