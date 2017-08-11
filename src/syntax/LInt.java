/**
 * 
 */
package syntax;

import java.util.Map;

import inference.Subst;
import inference.Tuple;
import types.TCon;
import types.Type;
import types.TypeEnv;

/**
 * @author schkrabi
 *
 */
public class LInt extends Lit {
	public final int value;
	
	public LInt(int value){
		this.value = value;
	}
	
	public String toString(){
		return Integer.toString(this.value);
	}

	@Override
	public Tuple<Subst, Type> inferTuple(TypeEnv env) throws Exception {
		return new Tuple<Subst, Type>(Subst.nullSubst(), TCon.typeInt);
	}

	@Override
	protected Type infer(TypeEnv env, Map<Type, Type> emit) {
		return TCon.typeInt;
	}
}
