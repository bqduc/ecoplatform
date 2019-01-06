/**
 * 
 */
package net.brilliance.manager.dashboard;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import net.brilliance.domain.entity.contact.ClientProfile;
import net.brilliance.manager.catalog.CatalogManager;
import net.brilliance.manager.contact.ClientProfileManager;
import net.brilliance.manager.hc.EmployeeManager;
import net.brilliance.manager.vbb.VbbForumManager;
import net.brilliance.service.api.contact.ContactService;
import net.brilliance.service.api.inventory.ProductService;

/**
 * @author ducbq
 *
 */
//@Service
@Scope(value = "singleton")
@Component
public class DashboardManager {
	private final static long INITIAL_TOTAL = 0;

	@Inject 
	private ProductService productService;

	@Inject 
	private ContactService contactService;

	@Inject 
	private EmployeeManager employeeManager;

	@Inject 
	private ClientProfileManager clientProfileManager;

	@Inject 
	private VbbForumManager forumManager;

	@Inject 
	private CatalogManager catalogManager;

	private Map<String, Object> dashboardDataMap = new HashMap<>();

	public long getTotalForums(){
		return forumManager.count();
	}

	public long getTotalClientProfiles(){
		return clientProfileManager.count();
	}

	public long getTotalOrders(ClientProfile clientProfile){
		return INITIAL_TOTAL;
	}

	public Map<String, Object> getDashboardDataMap() {
		return dashboardDataMap;
	}

	public void setDashboardDataMap(Map<String, Object> dashboardDataMap) {
		this.dashboardDataMap = dashboardDataMap;
	}

	public Map<?, ?> syncData(){
		this.dashboardDataMap.put("totalClientProfiles", clientProfileManager.count());
		this.dashboardDataMap.put("contacts", contactService.count());
		this.dashboardDataMap.put("employees", employeeManager.count());
		this.dashboardDataMap.put("catalogues", catalogManager.count());
		this.dashboardDataMap.put("productCount", productService.count());
		return this.dashboardDataMap;
	}
}
