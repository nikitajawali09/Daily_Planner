package com.usermanagement.entities;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String firstName;
	
	@Column(nullable = false)
	private String lastName;
	
	@Column(nullable = false, unique = true)
	@Email(message = "Email address should be valid")
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String confirmPassword;
	
	@Column(nullable = false)
	private String gender;
	
	private String profession;
	
	@Column(nullable = false)
	private String address;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(nullable = false)
	private Date createdDate;
	
	@Column(nullable = false, unique = true)
	private String userName;
	
	  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	    @JoinTable(name = "users_roles",
	        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
	            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
	    )
	    private Set<Role> roles;
}
