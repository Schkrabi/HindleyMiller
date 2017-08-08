/**
 * 
 */
package syntax;

/**
 * @author schkrabi
 *
 */
public class If extends Expr {
	public final Expr cond;
	public final Expr tExpr;
	public final Expr fExpr;
	
	public If(Expr cond, Expr tExpr, Expr fExpr){
		this.cond = cond;
		this.tExpr = tExpr;
		this.fExpr = fExpr;
	}
	
	public String toString(){
		return "if " + cond.toString() + " then " + tExpr.toString() + " else " + fExpr.toString();
	}
}
