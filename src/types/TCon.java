/**
 * 
 */
package types;

/**
 * @author schkrabi
 * Concrete type
 *
 */
public class TCon extends Type {
	private String name;
	
	public TCon(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public String toString(){
		return this.name;
	}
	
	public static final Type typeInt = new TCon("Int");
	public static final Type typeBool = new TCon("Bool");
}
