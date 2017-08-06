/**
 * 
 */
package syntax;

/**
 * @author schkrabi
 *
 */
public class Decl {
	private String name;
	private Expr expr;
	
	public Decl(String name, Expr expr){
		this.name = name;
		this.expr = expr;
	}
	
	public String toString(){
		return this.name + " := " + this.expr.toString();
	}
}
