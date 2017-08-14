package inference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import syntax.Var;
import syntax.Binop;
import types.Scheme;
import types.TArr;
import types.TCon;
import types.TVar;
import types.Type;
import types.TypeEnv;
import types.VarName;

public class Inference {
	public static <T> boolean occursCheck(TVar a, Substitutable<T> t) {
		return t.ftv().contains(a);
	}
	
	public static  Subst bind(TVar a, Type t) throws Exception{
		if(t == a) { 
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
			Subst s2 = Inference.unify(rltype.apply(s1), rrtype.apply(s1));
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
		return sch.getType().apply(s);
	}
	
	public static Scheme generalize(TypeEnv env, Type type) {
		Set<TVar> s = type.ftv();
		s.removeAll(env.ftv());
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
	
	public static final Map<Binop, Type> ops;
	static {
		Map<Binop, Type> m = new HashMap<Binop, Type>();
		m.put(Binop.Add, new TArr(TCon.typeInt, new TArr(TCon.typeInt, TCon.typeInt)));
		m.put(Binop.Eql, new TArr(TCon.typeInt, new TArr(TCon.typeInt, TCon.typeInt)));
		m.put(Binop.Mul, new TArr(TCon.typeInt, new TArr(TCon.typeInt, TCon.typeInt)));
		m.put(Binop.Sub, new TArr(TCon.typeInt, new TArr(TCon.typeInt, TCon.typeInt)));
		ops = m;
	}
	
	public static Tuple<Subst, Set<Tuple<Type, Type>>> unifies(Type t1, Type t2) throws Exception{
		if(t1 == t2){
			return new Tuple<Subst, Set<Tuple<Type,Type>>>(Subst.nullSubst(), new TreeSet<Tuple<Type, Type>>());
		}
		if(t1 instanceof TVar){
			TVar a = (TVar)t1;
			Subst su = bind(a, t2);
			return new Tuple<Subst, Set<Tuple<Type,Type>>>(su, new TreeSet<Tuple<Type, Type>>());
		}
		if(t2 instanceof TVar){
			TVar a = (TVar)t2;
			Subst su = bind(a, t1);
			return new Tuple<Subst, Set<Tuple<Type,Type>>>(su, new TreeSet<Tuple<Type, Type>>());
		}
		if(		t1 instanceof TArr
			&&	t2 instanceof TArr){
			TArr ta1 = (TArr)t1;
			TArr ta2 = (TArr)t2;
			List<Type> l1 = new LinkedList<Type>(); //TODO Not good
			l1.add(ta1.getLtype());
			l1.add(ta1.getRtype());
			List<Type> l2 = new LinkedList<Type>(); 
			l2.add(ta2.getLtype());
			l2.add(ta2.getRtype());
			
			return unifyMany(l1, l2); 
		}
		throw new Exception("Unification fail " + t1 + " " + t2);
	}
	
	public static Tuple<Subst, Set<Tuple<Type, Type>>> unifyMany(List<Type> l1, List<Type> l2) throws Exception{
		Subst subst = new Subst();
		Set<Tuple<Type, Type>> set = new TreeSet<Tuple<Type, Type>>();
		
		Iterator<Type> i1, i2;
		i1 = l1.iterator();
		i2 = l2.iterator();
		while(i1.hasNext() && i2.hasNext()){
			Type t1 = i1.next();
			Type t2 = i2.next();
			
			Tuple<Subst, Set<Tuple<Type, Type>>> t = unifies(t1, t2);
			i1.forEachRemaining((Type x) -> {x.apply(t.x);});
			i2.forEachRemaining((Type x) -> {x.apply(t.x);});
			
			subst = subst.compose(t.x);
			set.addAll(t.y);
		}
		
		return new Tuple<Subst, Set<Tuple<Type, Type>>>(subst, set);
	}
	
	public static Subst solveConstraints(Set<Tuple<Type, Type>> constraints) throws Exception{
		Subst subst = new Subst();
		Set<Tuple<Type, Type>> cstr = new TreeSet<Tuple<Type, Type>>();
		cstr.addAll(constraints);
		
		while(!cstr.isEmpty()) {
			Iterator<Tuple<Type, Type>> i = cstr.iterator();
			Tuple<Type, Type> t = i.next(); 
			Tuple<Subst, Set<Tuple<Type, Type>>> t1 = unifies(t.x, t.y);
			subst = subst.compose(t1.x);
			
			i.forEachRemaining((Tuple<Type, Type> x) -> {x.x.apply(t1.x); x.y.apply(t1.x);});
			cstr.remove(t);
			tmp.addAll(t1.y);
			
			
		}
	}
}
