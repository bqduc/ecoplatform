/**
 * 
 */
package net.brilliance.domain.model;

import lombok.Builder;

/**
 * @author ducbq
 *
 */
@Builder
public class DataInterfaceModel {
	private DataSourceType dataSourceType;
	private String componentSeparator;
	private boolean processColumnHeaders;
	private boolean concatinationCRLF;
	
	public boolean isProcessColumnHeaders() {
		return processColumnHeaders;
	}
	public void setProcessColumnHeaders(boolean processColumnHeaders) {
		this.processColumnHeaders = processColumnHeaders;
	}
	public boolean isConcatinationCRLF() {
		return concatinationCRLF;
	}
	public void setConcatinationCRLF(boolean concatinationCRLF) {
		this.concatinationCRLF = concatinationCRLF;
	}
	public DataSourceType getDataSourceType() {
		return dataSourceType;
	}
	public void setDataSourceType(DataSourceType dataSourceType) {
		this.dataSourceType = dataSourceType;
	}
	public String getComponentSeparator() {
		return componentSeparator;
	}
	public void setComponentSeparator(String componentSeparator) {
		this.componentSeparator = componentSeparator;
	}

}
