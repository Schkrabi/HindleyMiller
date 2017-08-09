	/**
 * 
 */
package types;

import inference.Subst;
import inference.Substitutable;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author schkrabi
 *
 */
public class Scheme implements Substitutable<Scheme> {
	private List<TVar> types;
	private Type type;
	
	public Scheme(List<TVar> types, Type type){
		this.types = types;
		this.type = type;
	}

	public List<TVar> getTypes() {
		return types;
	}

	public Type getType() {
		return type;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Forall [");
		
		Iterator<TVar> i = types.iterator(); 
		while(i.hasNext()){
			TVar t = i.next();
			sb.append(t.toString());
			if(i.hasNext()){
				sb.append(", ");
			}
		}
		
		sb.append("] ");
		sb.append(type.toString());
		return sb.toString();
	}

	@Override
	public Scheme apply(Subst subst) {
		Subst s = new Subst();
		s.putAll(subst);
		for(TVar tvar : this.getTypes()){
			s.remove(tvar);
		}
		
		return new Scheme(this.getTypes(), this.getType().apply(s));
	}

	@Override
	public Set<TVar> ftv() {
		Type type = this.getType();
		Set<TVar> s = new TreeSet<TVar>();
		
		s.addAll(type.ftv());
		s.removeAll(this.getTypes());
		
		return s;
	}
}
