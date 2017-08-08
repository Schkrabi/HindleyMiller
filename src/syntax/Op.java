/**
 * 
 */
package syntax;

/**
 * @author schkrabi
 *
 */
public class Op extends Expr {
	public final Binop binop;
	public final Expr lexpr;
	public final Expr rexpr;
	
	public Op(Binop binop, Expr lexpr, Expr rexpr){
		this.binop = binop;
		this.lexpr = lexpr;
		this.rexpr = rexpr;
	}
	
	public String toString(){
		return "( " + lexpr.toString() + " " + binop.toString() + " " + rexpr.toString() + " )";
	}
}
