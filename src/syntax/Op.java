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
public class Op extends Expr {
	public final Binop binop;
	public final Expr lexpr;
	public final Expr rexpr;
	
	public Op(Binop binop, Expr lexpr, Expr rexpr){
		this.binop = binop;
		this.lexpr = lexpr;
		this.rexpr = rexpr;
	}
	
	public String toString(){
		return "( " + lexpr.toString() + " " + binop.toString() + " " + rexpr.toString() + " )";
	}

	@Override
	public Tuple<Subst, Type> inferTuple(TypeEnv env) throws Exception {
		Tuple<Subst, Type> t1, t2;
		t1 = this.lexpr.inferTuple(env);
		t2 = this.rexpr.inferTuple(env);
		TVar tv = VarName.next();
		if(!Inference.ops.containsKey(this.binop)){
			throw new Exception("Invalid binop");
		}
		Type opt = Inference.ops.get(this.binop);
		Subst s3 = Inference.unify(new TArr(t1.y, new TArr(t2.y, tv)), opt);
		return new Tuple<Subst, Type>(t1.x.compose(t2.x.compose(s3)),tv.apply(s3));
	}
}
