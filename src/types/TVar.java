/**
 * 
 */
package types;

/**
 * @author schkrabi
 *
 */
public class TVar extends Type {
	private String name;
	
	public TVar(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public String toString(){
		return this.name;
	}
}
