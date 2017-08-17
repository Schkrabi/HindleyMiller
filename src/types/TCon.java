/**
 * 
 */
package types;

import java.util.Set;
import java.util.TreeSet;

import inference.Subst;

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
	
	public Type apply(Subst s) {
		return this;
	}
	
	public Set<TVar> ftv(){
		return new TreeSet<TVar>();
	}
	
	public int compareTo(Type o) {
		if(o instanceof TCon)
			return this.name.compareTo(((TCon)o).name);
		return super.compareTo(o);
	}
	
	public static final Type typeInt = new TCon("Int");
	public static final Type typeBool = new TCon("Bool");
}
