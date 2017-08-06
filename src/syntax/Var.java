/**
 * 
 */
package syntax;

/**
 * @author schkrabi
 *
 */
public class Var extends Expr {
	private String name;
	
	public Var(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public String toString(){
		return name;
	}
}
