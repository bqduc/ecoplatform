package net.brilliance.domain.entity.epos;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.util.StringUtils;

import lombok.EqualsAndHashCode;
import net.brilliance.domain.entity.epos.base.BaseMenuItemSize;

@Entity
@Table(name = "vpos_menu_item_size")
@EqualsAndHashCode(callSuper = true)
public class MenuItemSize extends BaseMenuItemSize {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public MenuItemSize () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public MenuItemSize (java.lang.Long id) {
		super(id);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	@Override
	public String getTranslatedName() {
		String translatedName = super.getTranslatedName();
		if(StringUtils.isEmpty(translatedName)) {
			return getName();
		}
		
		return translatedName;
	}

	@Override
	public String toString() {
		return getName();
	}
}