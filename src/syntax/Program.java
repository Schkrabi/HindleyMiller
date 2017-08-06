/**
 * 
 */
package syntax;

import java.util.List;

/**
 * @author schkrabi
 *
 */
public class Program {
	private List<Decl> decls;
	private Expr expr;
	
	public Program(List<Decl> decls, Expr expr){
		this.decls = decls;
		this.expr = expr;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		for(Decl d : decls){
			sb.append(d.toString());
			sb.append('\n');
		}
		
		sb.append(expr.toString());
		return sb.toString();
	}
}
