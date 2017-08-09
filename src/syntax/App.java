/**
 * 
 */
package syntax;

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
public class App extends Expr {
	public final Expr lExpr;
	public final Expr rExpr;
	
	public App(Expr lExpr, Expr rExpr){
		this.lExpr = lExpr;
		this.rExpr = rExpr;
	}
	
	public String toString()
	{
		return lExpr.toString() + " " + rExpr.toString();
	}

	@Override
	public Tuple<Subst, Type> inferTuple(TypeEnv env) throws Exception {
		Expr e1 = this.lExpr;
		Expr e2 = this.rExpr;
		TVar tv = VarName.next();
		Tuple<Subst, Type> t1 = e1.inferTuple(env);
		Tuple<Subst, Type> t2 = e2.inferTuple(env.apply(t1.x));
		Subst s3 = Inference.unify(t1.y.apply(t2.x), new TArr(t2.y, tv));
		return new Tuple<Subst, Type>(s3.compose(t2.x.compose(t1.x)), tv.apply(s3));
	}
}
