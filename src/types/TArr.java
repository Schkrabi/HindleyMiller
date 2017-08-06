/**
 * 
 */
package types;

/**
 * @author schkrabi
 *
 */
public class TArr extends Type {
	private Type ltype;
	private Type rtype;
	
	public TArr(Type ltype, Type rtype){
		this.ltype = ltype;
		this.rtype = rtype;
	}

	public Type getLtype() {
		return ltype;
	}

	public Type getRtype() {
		return rtype;
	}
	
	public String toString(){
		return ltype.toString() + " -> " + rtype.toString();
	}
}
