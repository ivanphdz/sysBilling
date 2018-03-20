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
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="clients")
public class Client implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	private String name;
	@NotEmpty
	@Column(name = "lastName")
	private String lastName;
	@NotEmpty
	@Email
	private String email;
	
	private String photo;
	
	@Column(name = "createdAt")
	@Temporal(value = TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createdAt;
	
	@OneToMany(mappedBy="client", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	private List<Invoice> lstInvoice;
	
	@PrePersist
	public void prePersist() {
		this.createdAt = new Date();
	}
	
	public Client() {
		lstInvoice = new ArrayList<Invoice>();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public List<Invoice> getLstInvoice() {
		return lstInvoice;
	}
	public void setLstInvoice(List<Invoice> lstInvoice) {
		this.lstInvoice = lstInvoice;
	}
	public void addInvoice(Invoice invoice) {
		lstInvoice.add(invoice);
	}

	@Override
	public String toString() {
		return name + " " + lastName;
	}
	
}
