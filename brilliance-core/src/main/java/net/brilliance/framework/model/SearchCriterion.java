/**
 * 
 */
package net.brilliance.framework.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * @author ducbq
 *
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriterion {
  private String key;
  private SearchOperand operand;
  private Object value;
  private Object valueHigh;

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public SearchOperand getOperand() {
		return operand;
	}
	public void setOperand(SearchOperand operand) {
		this.operand = operand;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public Object getValueHigh() {
		return valueHigh;
	}
	public void setValueHigh(Object valueHigh) {
		this.valueHigh = valueHigh;
	}

}
