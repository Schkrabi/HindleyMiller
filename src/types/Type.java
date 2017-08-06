/**
 * 
 */
package types;

import java.util.Set;
import java.util.TreeSet;

import inference.Subst;
import inference.Substitutable;

/**
 * @author schkrabi
 *
 */
public abstract class Type implements Substitutable<Type> {
	
	public Type apply(Subst subst, Type t){
		if(t instanceof TCon){
			return t;
		}
		if(t instanceof TVar){
			if(!subst.containsKey(t)){
				return t;
			}
			return subst.get(t);
		}
		if(t instanceof TArr){
			TArr tarr = (TArr)t;
			return new TArr(tarr.getLtype().apply(subst, tarr.getLtype()),
							tarr.getRtype().apply(subst, tarr.getRtype()));
		}
		//TODO fix
		return null;
	}
	
	public Set<TVar> ftv(Type t){
		if(t instanceof TCon){
			return new TreeSet<TVar>();
		}
		if(t instanceof TVar){
			Set<TVar> s = new TreeSet<TVar>();
			s.add((TVar)t);
			return s;
		}
		if(t instanceof TArr){
			Type l = ((TArr)t).getLtype();
			Type r = ((TArr)t).getRtype();
			Set<TVar> s = new TreeSet<TVar>();
			
			s.addAll(l.ftv(l));
			s.addAll(r.ftv(r));
			return s;
		}
		//TODO fix
		return null;
	}
}
