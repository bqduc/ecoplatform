/**
 * 
 */
package net.brilliance.framework.model;

import java.io.Serializable;
import java.util.Map;

import org.springframework.data.domain.Pageable;

/**
 * @author ducbq
 *
 */
public class SearchCondition implements Serializable{
	private static final long serialVersionUID = -4657754053265985498L;

	private Pageable pageable;
	private Map<Object, Object> parameters;

	public static SearchCondition getInstance(Pageable pageable, Map parameters){
		SearchCondition searchCondition = new SearchCondition();
		return searchCondition
				.setPageable(pageable)
				.setParameters(parameters);
	}

	public Pageable getPageable() {
		return pageable;
	}
	public SearchCondition setPageable(Pageable pageable) {
		this.pageable = pageable;
		return this;
	}
	public Map<Object, Object> getParameters() {
		return parameters;
	}
	public SearchCondition setParameters(Map<Object, Object> parameters) {
		this.parameters = parameters;
		return this;
	}
	public SearchCondition putParameter(Object key, Object value){
		this.parameters.put(key, value);
		return this;
	}
}
