/**
 * 
 */
package syntax;

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
}
