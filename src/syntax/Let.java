/**
 * 
 */
package syntax;

import java.util.Map;

import inference.Inference;
import inference.Subst;
import inference.Tuple;
import types.Scheme;
import types.Type;
import types.TypeEnv;

/**
 * @author schkrabi
 *
 */
public class Let extends Expr {
	public final String name;
	public final Expr	bind;
	public final Expr	expr;
	
	public Let(String name, Expr bind, Expr expr){
		this.name = name;
		this.bind = bind;
		this.expr = expr;
	}
	
	public String toString(){
		return "let " + this.name + " (" + this.bind.toString() + ") " + this.expr.toString();
	}

	@Override
	public Tuple<Subst, Type> inferTuple(TypeEnv env) throws Exception {
		Tuple<Subst, Type> t1 = this.bind.inferTuple(env);
		TypeEnv env1 = env.apply(t1.x);
		Scheme sch = Inference.generalize(env1, t1.y);
		Tuple<Subst, Type> t2 = this.expr.inferTuple(env1.extend(new Var(this.name), sch));
		return new Tuple<Subst, Type>(t1.x.compose(t2.x), t2.y);
	}

	@Override
	protected Type infer(TypeEnv env, Map<Type, Type> emit) throws Exception {
		Type t1 = this.bind.infer(env, emit);
		Scheme sch = Inference.generalize(env, t1);
		Type t2 = this.expr.infer(env.inLocalEnv(this.name, sch), emit);
		return t2;
	}
}
