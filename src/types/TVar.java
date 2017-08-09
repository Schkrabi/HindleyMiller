/**
 * 
 */
package types;

import java.util.Set;
import java.util.TreeSet;

import inference.Subst;

/**
 * @author schkrabi
 *
 */
public class TVar extends Type implements Comparable<TVar> {
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

	@Override
	public int compareTo(TVar o) {
		return this.name.compareTo(o.name);
	}
	
	@Override
	public Type apply(Subst subst) {
		if(!subst.containsKey(this)){
			return this;
		}
		return subst.get(this);
	}
	
	@Override
	public Set<TVar> ftv(){
		Set<TVar> s = new TreeSet<TVar>();
		s.add((TVar)this);
		return s;
	}
}
