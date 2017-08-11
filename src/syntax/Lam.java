/**
 * 
 */
package syntax;

import java.util.ArrayList;
import java.util.Map;

import inference.Subst;
import inference.Tuple;
import types.Scheme;
import types.TArr;
import types.TVar;
import types.Type;
import types.TypeEnv;
import types.VarName;

/**
 * @author schkrabi
 *
 */
public class Lam extends Expr {
	public final String name;
	public final Expr expr;
	
	public Lam(String name, Expr expr){
		this.name = name;
		this.expr = expr;
	}
	
	public String toString(){
		return "lambda " + this.name + ": " + this.expr.toString();
	}

	@Override
	public Tuple<Subst, Type> inferTuple(TypeEnv env) throws Exception {
		TVar tv = VarName.next();
		TypeEnv env1 = env.extend(new Var(this.name), new Scheme(new ArrayList<TVar>(), tv)); //Problem here?
		Tuple<Subst, Type> t = this.expr.inferTuple(env1);
		TArr tarr = new TArr(tv, t.y);			
		return new Tuple<Subst, Type>(t.x, tarr.apply(t.x));
	}

	@Override
	protected Type infer(TypeEnv env, Map<Type, Type> emit) throws Exception {
		TVar tv = VarName.next();
		Type t = this.expr.infer(env.inLocalEnv(this.name, new Scheme(new ArrayList<TVar>(), tv)), emit);
		return new TArr(tv, t);
	}
}
