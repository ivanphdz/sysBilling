package com.billing.demo.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="invoices")
public class Invoice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	private String description;
	
	private String observation;
	@Column(name = "createdAt")
	@Temporal(value = TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createdAt;
	@ManyToOne(fetch = FetchType.LAZY)
	private Client client;
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="invoice_id")
	private List<Item> lstItem;

	@PrePersist
	public void prePersist() {
		this.createdAt = new Date();
	}
	
	public Invoice() {
		this.lstItem = new ArrayList<Item>();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<Item> getLstItem() {
		return lstItem;
	}

	public void setLstItem(List<Item> lstItem) {
		this.lstItem = lstItem;
	}
	
	public void addItem(Item item) {
		this.lstItem.add(item);
	}
	
	public Double getTotal() {
		Double total = 0.0;
		for(Item item : lstItem) {
			total += item.calculateCharge();
		}
		return total;
	}

}
