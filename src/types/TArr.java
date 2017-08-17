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
	
	@Override
	public String toString(){
		return ltype.toString() + " -> " + rtype.toString();
	}
	
	@Override
	public Type apply(Subst subst) {
		TArr tarr = (TArr)this;
		return new TArr(tarr.getLtype().apply(subst),
						tarr.getRtype().apply(subst));
	}
	
	public Set<TVar> ftv(){
		Type l = ((TArr)this).getLtype();
		Type r = ((TArr)this).getRtype();
		Set<TVar> s = new TreeSet<TVar>();
		
		s.addAll(l.ftv());
		s.addAll(r.ftv());
		return s;
	}
	
	public int compareTo(Type o) {
		if(o instanceof TArr) {
			int n = this.ltype.compareTo(((TArr)o).getLtype());
			return n != 0 ? n : this.rtype.compareTo(((TArr) o).getRtype()) ;
		}
		return super.compareTo(o);
	}
}
