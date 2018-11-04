package net.brilliance.manager.sales;
/*package net.powertrain.manager.sales;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.powertrain.common.CommonUtility;
import net.powertrain.common.logging.GlobalLoggerFactory;
import net.powertrain.domain.entity.security.Authority;
import net.powertrain.domain.entity.security.UserProfile;
import net.powertrain.domain.model.AuthorityGroup;
import net.powertrain.domain.model.MasterUserGroup;
import net.powertrain.exceptions.AccountNotActivatedException;
import net.powertrain.exceptions.AuthenticationException;
import net.powertrain.manager.auth.AuthorityManager;
import net.powertrain.manager.auth.CustomUserDetailsService;
import net.powertrain.manager.security.SimpleEncryptionManager;
import net.powertrain.manager.security.VpxEncoder;
import net.powertrain.repository.auth.UserRepository;
import net.powertrain.service.helper.ClientServicesHelper;

@Service
@Transactional
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {
	private Logger log = GlobalLoggerFactory.getLogger(this.getClass());

	@Inject
	private Provider<UserRepository> userRepositoryProvider;

	@Inject
	private AuthorityManager authorityServiceManager;

	@Inject
	private SimpleEncryptionManager simpleEncryptor;

	@Inject
	private VpxPasswordEncoder vpxPasswordEncoder;

	@Inject
	private ClientServicesHelper clientServicesHelper;
	
	@Inject
	private VpxEncoder passwordEncoder;

	@Inject
	private SimpleEncryptionManager simpleEncryptionManager;

	@Override
	public UserDetails loadUserByUsername(final String login) throws UsernameNotFoundException {
		log.debug("Authenticating {}", login);
		String lowercaseLogin = login;//.toLowerCase();
		Optional<UserProfile> userFromDatabase = userRepositoryProvider.get().findOneByLogin(lowercaseLogin);
		if (false==userFromDatabase.isPresent())
			throw new UsernameNotFoundException(String.format("User with sso id %s was not found in the database", login));

		return this.buildUserDetails(userFromDatabase.get());
	}

	@Override
	public UserDetails loadUserByEmail(String email) {
		Optional<UserProfile> userFromDatabase = userRepositoryProvider.get().findOneByEmail(email);
		//TODO: Remove after then
		if (false==userFromDatabase.isPresent()) {
			throw new UsernameNotFoundException(String.format("User with email %s was not found in the database", email));
			//return this.buildDummyUser(email);
		}

		if (!userFromDatabase.get().isActivated())
				throw new AccountNotActivatedException(String.format("User with email %s is not activated", email));
		
		return this.buildUserDetails(userFromDatabase.get());
	}

	@Override
	public UserProfile save(UserProfile user) {
		userRepositoryProvider.get().save(user);
		return user;
	}

	private UserDetails buildDummyUser(String loginName) {
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		String dummyPassword = "";
		if ("user".equalsIgnoreCase(loginName)) {
			dummyPassword = "userpassword";
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		}else if ("admin".equalsIgnoreCase(loginName)) {
			dummyPassword = "adminpassword";
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		}else {//Maybe using email
			dummyPassword = "dummypassword";
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_CLIENT"));
		}
		return new org.springframework.security.core.userdetails.User(loginName, dummyPassword, grantedAuthorities);
	}

	@Override
	public UserProfile authenticate(String loginToken, String password) throws AuthenticationException {
		UserProfile authenticatedUser = null;
		UserDetails userDetails = null;
		Optional<UserProfile> repositoryUser = null;
		if (CommonUtility.isEmailAddreess(loginToken)){
			repositoryUser = userRepositoryProvider.get().findOneByEmail(loginToken);
		}else{
			repositoryUser = userRepositoryProvider.get().findOneByLogin(loginToken);
		}

		if (!repositoryUser.isPresent())
			throw new AuthenticationException(AuthenticationException.ERROR_INVALID_PRINCIPAL, "Could not get the user information base on [" + loginToken + "]");

		if (false==simpleEncryptor.comparePassword(password, repositoryUser.get().getPassword(), repositoryUser.get().getEncryptAlgorithm()))
			throw new AuthenticationException(AuthenticationException.ERROR_INVALID_CREDENTIAL, "Invalid password of the user information base on [" + loginToken + "]");

		if (false==repositoryUser.get().isActivated())
			throw new AuthenticationException(AuthenticationException.ERROR_INACTIVE, "Login information is fine but this account did not activated yet. ");

		userDetails = buildUserDetails(repositoryUser.get());
		authenticatedUser = repositoryUser.get();
		authenticatedUser.setUserDetails(userDetails);
		return authenticatedUser;
	}

	@Override
	public UserProfile getUser(String userToken) throws AuthenticationException {
		Optional<UserProfile> repositoryUser = null;
		if (CommonUtility.isEmailAddreess(userToken)){
			repositoryUser = userRepositoryProvider.get().findOneByEmail(userToken);
		}else{
			repositoryUser = userRepositoryProvider.get().findOneByLogin(userToken);
		}

		if (!repositoryUser.isPresent()){
			throw new AuthenticationException(AuthenticationException.ERROR_INVALID_PRINCIPAL, "Could not get the user information base on [" + userToken + "]");
		}

		return repositoryUser.get();
	}

	private UserDetails buildUserDetails(UserProfile repositoryUser){
		Set<GrantedAuthority> grantedAuthorities = getAuthorities(repositoryUser);
		return new org.springframework.security.core.userdetails.User(repositoryUser.getLogin(), repositoryUser.getPassword(), grantedAuthorities);
	}

  private Set<GrantedAuthority> getAuthorities(UserProfile user){
  	Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
  	for(Authority authority : user.getAuthorities()) {
      authorities.add(new SimpleGrantedAuthority(authority.getName()));
  	}
  	for(Role role : user.getRoles()) {
        authorities.add(new SimpleGrantedAuthority(role.getName()));
    }
    log.debug("User authorities are " + authorities.toString());
    return authorities;
  }

	@Override
	public void setupDefaultMasterData() throws AuthenticationException {
		UserProfile adminUser = null, clientUser = null, user = null;
		Authority clientRoleEntity = null, userRoleEntity = null, adminRoleEntity = null;
		//Setup authorities/roles
		try {
			Optional<Authority> clientRole = authorityServiceManager.getByName(AuthorityGroup.RoleClient.getCode());
			if (!clientRole.isPresent()){
				clientRoleEntity = Authority.createInstance(AuthorityGroup.RoleClient.getCode(), "Client activity. ");
				authorityServiceManager.save(clientRoleEntity);
				clientRole = Optional.of(clientRoleEntity);
			}

			Optional<Authority> userRole = authorityServiceManager.getByName(AuthorityGroup.RoleUser.getCode());
			if (!userRole.isPresent()){
				userRoleEntity = Authority.createInstance(AuthorityGroup.RoleUser.getCode(), "Common activity for normal user. ");
				authorityServiceManager.save(userRoleEntity);
				userRole = Optional.of(userRoleEntity);
			}

			Optional<Authority> adminRole = authorityServiceManager.getByName(AuthorityGroup.RoleAdmin.getCode());
			if (!adminRole.isPresent()){
				adminRoleEntity = Authority.createInstance(AuthorityGroup.RoleAdmin.getCode(), "System Administration. ");
				authorityServiceManager.save(adminRoleEntity);
				adminRole = Optional.of(adminRoleEntity);
			}

			Set<Authority> adminAuthorities = new HashSet<>();
			adminAuthorities.add(userRole.get());
			adminAuthorities.add(clientRole.get());
			adminAuthorities.add(adminRole.get());

			Set<Authority> clientAuthorities = new HashSet<>();
			clientAuthorities.add(clientRole.get());

			Set<Authority> userAuthorities = new HashSet<>();
			userAuthorities.add(userRole.get());

			if (!userRepositoryProvider.get().findOneByLogin(MasterUserGroup.Administrator.getLogin()).isPresent()){
				//adminUser = User.createInstance("administrator@gmail.com", "Administrator", "System", MasterUserGroup.Administrator.getLogin(), passwordEncoder.encode("P@dministr@t0r"), adminAuthorities);
				adminUser = UserProfile.createInstance(
						"administrator@gmail.com", 
						"Administrator", 
						"System", 
						MasterUserGroup.Administrator.getLogin(), 
						passwordEncoder.encode("P@dministr@t0r"), 
						adminAuthorities);
				adminUser.setActivated(true);
				userRepositoryProvider.get().save(adminUser);

				clientServicesHelper.registerBasicClient(adminUser);
			}

			if (!userRepositoryProvider.get().findOneByLogin(MasterUserGroup.VpexClient.getLogin()).isPresent()){
				//clientUser = User.createInstance("vpexcorpclient@gmail.com", "Corporate client", "Vpex", MasterUserGroup.VpexClient.getLogin(), passwordEncoder.encode("vP3@x5"), clientAuthorities);
				clientUser = UserProfile.createInstance(
						"vpexcorpclient@gmail.com", 
						"Corporate client", 
						"Vpex", 
						MasterUserGroup.VpexClient.getLogin(), 
						passwordEncoder.encode("vP3@x5"), 
						clientAuthorities);
				clientUser.setActivated(true); 
				userRepositoryProvider.get().save(clientUser);
				clientServicesHelper.registerBasicClient(clientUser);
			}

			if (!userRepositoryProvider.get().findOneByLogin(MasterUserGroup.User.getLogin()).isPresent()){
				//user = User.createInstance("normaluser@gmail.com", "User", "Application", MasterUserGroup.User.getLogin(), passwordEncoder.encode("u63r@x9"), userAuthorities);
				user = UserProfile.createInstance(
						"normaluser@gmail.com", 
						"User", 
						"Application", 
						MasterUserGroup.User.getLogin(), 
						passwordEncoder.encode("u63r@x9"), 
						userAuthorities);
				user.setActivated(true);
				userRepositoryProvider.get().save(user);
				clientServicesHelper.registerBasicClient(user);
			}
		} catch (Exception e) {
			throw new AuthenticationException(e);
		}
	}

	@Override
	public UserProfile confirm(String confirmedEmail) throws AuthenticationException {
		Optional<UserProfile> confirmUser = userRepositoryProvider.get().findOneByEmail(confirmedEmail);
		if (!confirmUser.isPresent())
			throw new AuthenticationException("The email not found in database: " + confirmedEmail);

		UserProfile fetchedUser = confirmUser.get();
		fetchedUser.setActivated(true);
		userRepositoryProvider.get().save(fetchedUser);
		return fetchedUser;
	}
}
*/