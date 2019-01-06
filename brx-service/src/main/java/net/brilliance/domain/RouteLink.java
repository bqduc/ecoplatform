package net.brilliance.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.brilliance.framework.entity.BizObjectBase;

/**
 * An region or CRX.
 */
@Builder
@NoArgsConstructor 
@AllArgsConstructor
@Entity
@Table(name = "brx_route_link")
@EqualsAndHashCode(callSuper=false)
public class RouteLink extends BizObjectBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8705677251681714775L;

	@ManyToOne
	@JoinColumn(name = "route_id")
	private Route route;

	@ManyToOne
	@JoinColumn(name = "station_id")
	private Station station;

	@Column(name = "sort_order")
	private Byte sortOrder;

	@Column(name = "stop_duration")
	private Integer stopDuration;

	public Byte getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Byte sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public Integer getStopDuration() {
		return stopDuration;
	}

	public void setStopDuration(Integer stopDuration) {
		this.stopDuration = stopDuration;
	}
}
