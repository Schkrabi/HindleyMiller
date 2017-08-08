/**
 * 
 */
package syntax;

/**
 * @author schkrabi
 *
 */
public class App extends Expr {
	public final Expr lExpr;
	public final Expr rExpr;
	
	public App(Expr lExpr, Expr rExpr){
		this.lExpr = lExpr;
		this.rExpr = rExpr;
	}
	
	public String toString()
	{
		return lExpr.toString() + " " + rExpr.toString();
	}
}
