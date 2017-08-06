/**
 * 
 */
package types;

import inference.Subst;
import inference.Substitutable;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import syntax.Var;

/**
 * @author schkrabi
 *
 */
public class TypeEnv extends TreeMap<Var, Scheme> implements Substitutable<TypeEnv>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 726253031394133544L;
	
	public TypeEnv extend(Var var, Scheme scheme){
		TypeEnv env = new TypeEnv();
		env.putAll(this);
		env.put(var,  scheme);
		return env;
	}

	@Override
	public TypeEnv apply(Subst subst, TypeEnv t) {
		TypeEnv env = new TypeEnv();
		
		for(Map.Entry<Var, Scheme> e : t.entrySet()){
			Scheme s = e.getValue();
			env.put(e.getKey(), s.apply(subst, s));
		}
		
		return env;
	}

	@Override
	public Set<TVar> ftv(TypeEnv t) {
		Set<TVar> s = new TreeSet<TVar>();
		
		for(Map.Entry<Var, Scheme> e : t.entrySet()){
			Scheme sch = e.getValue();
			s.addAll(sch.ftv(sch));
		}
		
		return s;
	}
}
