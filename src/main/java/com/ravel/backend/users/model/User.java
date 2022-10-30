package com.ravel.backend.users.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.Email;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

@Entity
@Table(name = "user_account")
public class User {

	@Id
	@GeneratedValue(generator = "UUIDGenerator")
	@GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
	private UUID userUUID;

	@Length(max = 50)
	@Column(name = "first_name", length = 50)
	private String firstName;

	@Length(max = 50)
	@Column(name = "last_name", length = 50)
	private String lastName;

	@Email
	@Column(unique = true, length = 100)
	private String email;

	@JsonIgnore
	private String password;

	@Column(name = "is_active")
	private boolean isActive;

	@Column(name = "is_not_locked")
	private boolean isNotLocked;

	@URL
	@Column(name = "avatar_url")
	private String avatarUrl;
	@URL
	@Column(name = "avatar_url_fullBody")
	private String avatarUrlFullBody;

	public String getAvatarUrlFullBody() {
		return avatarUrlFullBody;
	}

	public void setAvatarUrlFullBody(String avatarUrlFullBody) {
		this.avatarUrlFullBody = avatarUrlFullBody;
	}

	@URL
	@Column(name = "profile_image_url")
	private String profileImageUrl;

	private String role;

	private String[] authorities;

	@JsonIgnore
	@Column(name = "created_at")
	private OffsetDateTime created_at;

	@JsonIgnore
	@Column(name = "updated_at")
	private OffsetDateTime updatedAt;

	@JsonIgnore
	@OneToMany(
		fetch = FetchType.EAGER,
		mappedBy = "sender",
		cascade = CascadeType.REMOVE,
		orphanRemoval = true
	)
	private Set<Connection> senders;

	@JsonIgnore
	@OneToMany(
		fetch = FetchType.EAGER,
		mappedBy = "receiver",
		cascade = CascadeType.REMOVE,
		orphanRemoval = true
	)
	private Set<Connection> receivers;

	public Set<Connection> getSenders() {
		return senders;
	}

	public void setSenders(Set<Connection> friends) {
		this.senders = friends;
	}

	public Set<Connection> getReceivers() {
		return receivers;
	}

	public void setReceivers(Set<Connection> connections) {
		this.receivers = connections;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String[] getAuthorities() {
		return authorities;
	}

	public void setAuthorities(String[] authorities) {
		this.authorities = authorities;
	}

	public boolean isActive() {
		return isActive;
	}

	public boolean isNotLocked() {
		return isNotLocked;
	}

	public UUID getUserUUID() {
		return userUUID;
	}

	public void setUserUUID(UUID userUUID) {
		this.userUUID = userUUID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getActive() {
		return isActive;
	}

	public void setActive(boolean active) {
		isActive = active;
	}

	public void setActive(Boolean active) {
		isActive = active;
	}

	public Boolean getNotLocked() {
		return isNotLocked;
	}

	public void setNotLocked(boolean notLocked) {
		isNotLocked = notLocked;
	}

	public void setNotLocked(Boolean notLocked) {
		isNotLocked = notLocked;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public OffsetDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(OffsetDateTime created_at) {
		this.created_at = created_at;
	}

	public OffsetDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(OffsetDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
}
