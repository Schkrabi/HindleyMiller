/**
 * 
 */
package syntax;

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
}
