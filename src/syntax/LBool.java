/**
 * 
 */
package syntax;

import java.util.Set;

import inference.Subst;
import inference.Tuple;
import types.TCon;
import types.Type;
import types.TypeEnv;

/**
 * @author schkrabi
 *
 */
public class LBool extends Lit {
	public final boolean value;
	
	public LBool(boolean value){
		this.value = value;
	}
	
	public String toString(){
		return Boolean.toString(this.value);
	}

	@Override
	public Tuple<Subst, Type> inferTuple(TypeEnv env) throws Exception {
		return new Tuple<Subst, Type>(Subst.nullSubst(), TCon.typeBool);
	}

	@Override
	protected Type infer(TypeEnv env, Set<Tuple<Type, Type>> emit) {
		return TCon.typeBool;
	}
}
