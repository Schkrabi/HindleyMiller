package inference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import syntax.App;
import syntax.Expr;
import syntax.Fix;
import syntax.If;
import syntax.LBool;
import syntax.LInt;
import syntax.Lam;
import syntax.Let;
import syntax.Op;
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
	
	public static final Map<Binop, Type> ops;
	static {
		Map<Binop, Type> m = new HashMap<Binop, Type>();
		m.put(Binop.Add, new TArr(TCon.typeInt, new TArr(TCon.typeInt, TCon.typeInt)));
		m.put(Binop.Eql, new TArr(TCon.typeInt, new TArr(TCon.typeInt, TCon.typeInt)));
		m.put(Binop.Mul, new TArr(TCon.typeInt, new TArr(TCon.typeInt, TCon.typeInt)));
		m.put(Binop.Sub, new TArr(TCon.typeInt, new TArr(TCon.typeInt, TCon.typeInt)));
		ops = m;
	}
	
	public static Tuple<Subst, Type> infer(TypeEnv env, Expr ex) throws Exception{
		if(ex instanceof Var) {
			return lookupEnv(env, (Var)ex);
		}
		if(ex instanceof Lam) {
			TVar tv = VarName.next();
			Lam lam = (Lam)ex;
			TypeEnv env1 = env.extend(new Var(lam.name), new Scheme(new ArrayList<TVar>(), tv));
			Tuple<Subst, Type> t = infer(env1, lam.expr);
			TArr tarr = new TArr(tv, t.y);			
			return new Tuple<Subst, Type>(t.x, tarr.apply(t.x, tarr));
		}
		if(ex instanceof App) {
			Expr e1 = ((App)ex).lExpr;
			Expr e2 = ((App)ex).rExpr;
			TVar tv = VarName.next();
			Tuple<Subst, Type> t1 = infer(env, e1);
			Tuple<Subst, Type> t2 = infer(env.apply(t1.x, env), e2);
			Subst s3 = unify(t1.y.apply(t2.x, t1.y), new TArr(t2.y, tv));
			return new Tuple<Subst, Type>(s3.compose(t2.x.compose(t1.x)), tv.apply(s3, tv));
		}
		if(ex instanceof Let) {
			Let let = (Let)ex;
			Tuple<Subst, Type> t1 = infer(env, let.bind);
			TypeEnv env1 = env.apply(t1.x, env);
			Scheme sch = generalize(env1, t1.y);
			Tuple<Subst, Type> t2 = infer(env1.extend(new Var(let.name), sch), let.expr);
			return new Tuple<Subst, Type>(t1.x.compose(t2.x), t2.y);
		}
		if(ex instanceof If) {
			If e = (If)ex;
			Tuple<Subst, Type> t1, t2, t3;
			t1 = infer(env, e.cond);
			t2 = infer(env, e.tExpr);
			t3 = infer(env, e.fExpr);
			Subst s4, s5;
			s4 = unify(t1.y, TCon.typeBool);
			s5 = unify(t2.y, t3.y);
			return new Tuple<Subst, Type>(	s5.compose(s4.compose(t3.x.compose(t2.x.compose(t1.x)))),
											t2.y.apply(s5, t2.y));
		}
		if(ex instanceof Fix) {
			Fix fix = (Fix)ex;
			Tuple<Subst, Type> t = infer(env, fix.expr);
			TVar tv = VarName.next();
			Subst s2 = unify(new TArr(tv, tv), t.y);
			return new Tuple<Subst, Type>(s2, tv.apply(t.x, tv));
		}
		if(ex instanceof Op) {
			Op op = (Op)ex;
			Tuple<Subst, Type> t1, t2;
			t1 = infer(env, op.lexpr);
			t2 = infer(env, op.rexpr);
			TVar tv = VarName.next();
			if(!ops.containsKey(op.binop)){
				throw new Exception("Invalid binop");
			}
			Type opt = ops.get(op.binop);
			Subst s3 = unify(new TArr(t1.y, new TArr(t2.y, tv)), opt);
			return new Tuple<Subst, Type>(t1.x.compose(t2.x.compose(s3)),tv.apply(s3, tv));
		}
		if(ex instanceof LInt) {
			return new Tuple<Subst, Type>(Subst.nullSubst(), TCon.typeInt);
		}
		if(ex instanceof LBool) {
			return new Tuple<Subst, Type>(Subst.nullSubst(), TCon.typeBool);
		}
		
		throw new Exception("Unable to infer!");
	}
}
