package com.usermanagement.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users_roles")
public class UsersRoles {
	
	    @Id
	    @Column(nullable = false, unique = true)
	    private Long user_id;
	    @Column(nullable = false, unique = true)
	    private Long role_id;
	   // @JoinColumn(name = "role_id", referencedColumnName = "id",updatable = false,insertable = false)
	    //private List<User> users = new ArrayList<>();

}
