package edu.mum.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;

@Entity
public class Address {

    @Id
	@GeneratedValue(strategy=GenerationType.AUTO)
 	private Long id;

 	private String street;
	private String city;	
 	private String state;
  	private String zipCode;

  	@ManyToOne(fetch=FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable  //( name="Address_Member")
  	( name="Address_Member", joinColumns={@JoinColumn(name="ADDRESS", unique=true)},  
    inverseJoinColumns={ @JoinColumn(name="Member")} )  
 	private Member  member;
  	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	
	public void addMember(Member member) {
		this.member = member;
		this.member.getAddresses().add(this);
	}
	
	
}
