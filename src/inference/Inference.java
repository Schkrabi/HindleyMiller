package inference;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import syntax.Expr;
import syntax.Lam;
import syntax.Var;
import types.Scheme;
import types.TArr;
import types.TCon;
import types.TVar;
import types.Type;
import types.TypeEnv;
import types.VarName;

public class Inference {
	@SuppressWarnings("unchecked")
	public static <T> boolean occursCheck(TVar a, Substitutable<T> t) {
		return t.ftv((T)t).contains(a);
	}
	
	public static  Subst bind(TVar a, Type t) throws Exception{
		if(t == a) { //TODO check
			return Subst.nullSubst();
		}
		if(occursCheck(a, t)) {
			throw new Exception("Infinite Type");
		}
		Subst s = new Subst();
		s.put(a, t);
		return s;
	}
	
	public static Subst unify(Type ltype, Type rtype) throws Exception {
		if(		ltype instanceof TArr
			&&	rtype instanceof TArr) {
			TArr l = (TArr)ltype;
			TArr r = (TArr)rtype;
			Subst s1 = Inference.unify(l.getLtype(), r.getLtype());
			Type rltype = l.getRtype();
			Type rrtype = r.getRtype();
			Subst s2 = Inference.unify(rltype.apply(s1, rltype), rrtype.apply(s1, rrtype));
			return s1.compose(s2);			
		}
		else if(ltype instanceof TVar) {
			return Inference.bind((TVar)ltype, rtype);
		}
		else if(rtype instanceof TVar) {
			return Inference.bind((TVar)rtype, ltype);
		}
		else if(		(ltype instanceof TCon
					&&	 rtype instanceof TCon)
				 ||	ltype == rtype) {
			return Subst.nullSubst();
		}
		throw new Exception("Unification Fail");
	}
	
	public static Type instantiate(Scheme sch) {
		Subst s = new Subst();
		for(TVar tvar : sch.getTypes()) {
			s.put(tvar, VarName.next());
		}
		return sch.getType().apply(s, sch.getType());
	}
	
	public static Scheme generalize(TypeEnv env, Type type) {
		Set<TVar> s = type.ftv(type);
		s.removeAll(env.ftv(env));
		List<TVar> l = new ArrayList<TVar>();
		l.addAll(s);
		return new Scheme(l, type);
	}
	
	public static Tuple<Subst, Type> lookupEnv(TypeEnv env, Var x) throws Exception{
		if(env.containsKey(x)) {
			Type t = instantiate(env.get(x));
			return new Tuple<Subst, Type>(Subst.nullSubst(), t);
		}
		throw new Exception("Unbound Variable");
	}
	
	public static Tuple<Subst, Type> infer(TypeEnv env, Expr ex) throws Exception{
		if(ex instanceof Var) {
			return lookupEnv(env, (Var)ex);
		}
		if(ex instanceof Lam) {
			TVar tv = VarName.next();
			Lam lam = (Lam)ex;
			
		}
		return null;
	}
}
