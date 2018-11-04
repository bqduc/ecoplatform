/**
 * 
 */
package net.brilliance.common;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author ducbq
 *
 */
public class EcosysCollection<E> extends ArrayList<E> implements Collection<E>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7919769844003238231L;

	public EcosysCollection(){
		super();
	}

	public EcosysCollection(E e){
		this.add(e);
	}

	public EcosysCollection<E> addObject(E e){
		super.add(e);
		return this;
	}
}
