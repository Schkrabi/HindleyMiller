/**
 * 
 */
package syntax;

import java.util.Set;

import inference.Inference;
import inference.Subst;
import inference.Tuple;
import types.TArr;
import types.TVar;
import types.Type;
import types.TypeEnv;
import types.VarName;

/**
 * @author schkrabi
 *
 */
public class Fix extends Expr {
	public final Expr expr;
	
	public Fix(Expr expr){
		this.expr = expr;
	}
	
	public String toString(){
		return "let rec " + expr;
	}

	@Override
	public Tuple<Subst, Type> inferTuple(TypeEnv env) throws Exception {
		Tuple<Subst, Type> t = this.expr.inferTuple(env);
		TVar tv = VarName.next();
		Subst s2 = Inference.unify(new TArr(tv, tv), t.y);
		return new Tuple<Subst, Type>(s2, tv.apply(t.x));
	}

	@Override
	protected Type infer(TypeEnv env, Set<Tuple<Type, Type>> emit) throws Exception {
		Type t1 = this.expr.infer(env, emit);
		TVar tv = VarName.next();
		emit.add(new Tuple<Type, Type>(new TArr(tv, tv), t1));
		return tv;
	}
}
