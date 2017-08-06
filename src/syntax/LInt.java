/**
 * 
 */
package syntax;

/**
 * @author schkrabi
 *
 */
public class LInt extends Lit {
	private int value;
	
	public LInt(int value){
		this.setValue(value);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public String toString(){
		return Integer.toString(this.value);
	}
}
