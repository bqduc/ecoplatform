/**
 * 
 */
package net.brilliance.domain.entity.common;

import javax.persistence.Embeddable;

/**
 * @author ducbq
 *
 */
@Embeddable 
public class Phone {
	private String office;
  private String mobile;
  private String home; 
  private String others;

  public String getOffice() {
		return office;
	}
	public void setOffice(String office) {
		this.office = office;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getHome() {
		return home;
	}
	public void setHome(String home) {
		this.home = home;
	}
	public String getOthers() {
		return others;
	}
	public void setOthers(String others) {
		this.others = others;
	} 

}
