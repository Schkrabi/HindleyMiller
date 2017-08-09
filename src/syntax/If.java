/**
 * 
 */
package syntax;

import inference.Inference;
import inference.Subst;
import inference.Tuple;
import types.TCon;
import types.Type;
import types.TypeEnv;

/**
 * @author schkrabi
 *
 */
public class If extends Expr {
	public final Expr cond;
	public final Expr tExpr;
	public final Expr fExpr;
	
	public If(Expr cond, Expr tExpr, Expr fExpr){
		this.cond = cond;
		this.tExpr = tExpr;
		this.fExpr = fExpr;
	}
	
	public String toString(){
		return "if " + cond.toString() + " then " + tExpr.toString() + " else " + fExpr.toString();
	}

	@Override
	public Tuple<Subst, Type> inferTuple(TypeEnv env) throws Exception {
		Tuple<Subst, Type> t1, t2, t3;
		t1 = this.cond.inferTuple(env);
		t2 = this.tExpr.inferTuple(env);
		t3 = this.fExpr.inferTuple(env);
		Subst s4, s5;
		s4 = Inference.unify(t1.y, TCon.typeBool);
		s5 = Inference.unify(t2.y, t3.y);
		return new Tuple<Subst, Type>(	s5.compose(s4.compose(t3.x.compose(t2.x.compose(t1.x)))),
										t2.y.apply(s5));
	}
}
