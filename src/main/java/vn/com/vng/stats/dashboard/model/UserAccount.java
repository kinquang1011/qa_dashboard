package vn.com.vng.stats.dashboard.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public class UserAccount implements UserDetails {
	private static final long serialVersionUID = -4702588701492716672L;

	private Long id;
	private String username;
	private String password;
	private boolean accountExpired;
	private boolean accountLocked;	
	private boolean accountEnabled;
	private boolean credentialsExpired;

	private Set<RolePermission> roles;

	public UserAccount(Long id, String username, String password,
                       boolean accountExpired, boolean accountLocked,
                       boolean credentialsExpired, boolean accountEnabled, Set<RolePermission> roles) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.accountExpired = accountExpired;
		this.accountLocked = accountLocked;
		this.credentialsExpired = credentialsExpired;
		this.accountEnabled = accountEnabled;
		this.roles = roles;
	}

	public void setRolePermissions(Set<RolePermission> roles) {
		this.roles = roles;
	}
	
	public Set<RolePermission> getRoles() {
		return roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return !accountExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !accountLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !credentialsExpired;
	}

	@Override
	public boolean isEnabled() {
		return accountEnabled;
	}

	@Override
	public String toString() {
		return "UserAccount{" +
				"id=" + id +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", accountExpired=" + accountExpired +
				", accountLocked=" + accountLocked +
				", accountEnabled=" + accountEnabled +
				", credentialsExpired=" + credentialsExpired +
				", roles=" + roles +
				'}';
	}
}
