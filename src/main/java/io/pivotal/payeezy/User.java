package io.pivotal.payeezy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


	@Entity
	@Table(name = "user")
	public class User {

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Long id;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		@Column(name = "name")
		private String name;

		@Column(name = "email")
		private String email;

		@Column(name = "type")
		private String type;
	
		@Column(name = "cc")
		private String cc;
		
		public String getCc() {
			return cc;
		}

		public void setCc(String cc) {
			this.cc = cc;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}


		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}


		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

	}

