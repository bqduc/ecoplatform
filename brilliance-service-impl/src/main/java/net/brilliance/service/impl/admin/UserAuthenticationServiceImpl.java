package net.brilliance.service.impl.admin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import net.brilliance.common.CommonUtility;
import net.brilliance.domain.entity.admin.Authority;
import net.brilliance.domain.entity.admin.UserAccount;
import net.brilliance.domain.model.AuthorityGroup;
import net.brilliance.domain.model.MasterUserGroup;
import net.brilliance.exceptions.AccountNotActivatedException;
import net.brilliance.exceptions.AuthenticationException;
import net.brilliance.exceptions.UserAuthenticationException;
import net.brilliance.exceptions.UserAuthenticationException.AuthenticationCode;
import net.brilliance.framework.repository.BrillianceRepository;
import net.brilliance.framework.service.GenericServiceImpl;
import net.brilliance.manager.auth.AuthorityManager;
import net.brilliance.manager.security.SimpleEncryptionManager;
import net.brilliance.repository.admin.UserAccountRepository;
import net.brilliance.exceptions.ObjectNotFoundException;
import net.brilliance.service.api.admin.UserAuthenticationService;
import net.brilliance.service.helper.ClientServicesHelper;

@Service
public class UserAuthenticationServiceImpl extends GenericServiceImpl<UserAccount, Long> implements UserAuthenticationService, UserDetailsService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
	 * 
	 */
	private static final long serialVersionUID = 6033439932741319171L;

	@Inject
	private AuthorityManager authorityServiceManager;

	@Autowired
  private UserAccountRepository repository;

	@Inject
	private ClientServicesHelper clientServicesHelper;

	@Inject
	private SimpleEncryptionManager simpleEncryptor;

	@Override
  protected BrillianceRepository<UserAccount, Long> getRepository() {
      return repository;
  }

	@Override
	public UserAccount getOne(String username) throws ObjectNotFoundException {
		return repository.findBySsoId(username);
	}

	@Override
	public UserDetails loadUserByUsername(String login) throws AuthenticationException {
		logger.debug("Authenticating {}", login);
		String lowercaseLogin = login;//.toLowerCase();
		UserAccount userFromDatabase = repository.findBySsoId(login);

		if (null==userFromDatabase)
			throw new UserAuthenticationException(AuthenticationCode.INVALID_USER, String.format("User %s was not found in the database", lowercaseLogin));

		if (Boolean.FALSE.equals(userFromDatabase.isActivated()))
			throw new AccountNotActivatedException(String.format("User %s is not activated", lowercaseLogin));

		List<GrantedAuthority> grantedAuthorities = userFromDatabase.getAuthorities().stream()
				.map(authority -> new SimpleGrantedAuthority(authority.getName())).collect(Collectors.toList());

		return new org.springframework.security.core.userdetails.User(userFromDatabase.getSsoId(), userFromDatabase.getPassword(), grantedAuthorities);
	}

	@Override
	public UserDetails loadUserByEmail(String email) {
		UserAccount userFromDatabase = repository.findByEmail(email);
		//TODO: Remove after then
		if (null == userFromDatabase) {
			throw new UsernameNotFoundException(String.format("User with email %s was not found in the database", email));
			//return this.buildDummyUser(email);
		}

		if (!Boolean.TRUE.equals(userFromDatabase.isActivated()))
				throw new AccountNotActivatedException(String.format("User with email %s is not activated", email));
		
		return this.buildUserDetails(userFromDatabase);
	}

	@Override
	public void createUser(UserAccount user) {
		this.repository.saveAndFlush(user);
	}

	@Override
	public void updateUser(UserAccount user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUser(String username) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changePassword(String oldPassword, String newPassword) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean userExists(String username) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int countByLogin(String login) {
		return this.repository.countBySsoId(login).intValue();
	}

	@Override
	protected Page<UserAccount> performSearch(String keyword, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserAccount save(UserAccount user) {
		repository.save(user);
		return user;
	}

	@Override
	public UserAccount authenticate(String loginToken, String password) throws AuthenticationException {
		UserAccount authenticatedUser = null;
		UserDetails userDetails = null;
		UserAccount repositoryUser = null;
		if (CommonUtility.isEmailAddreess(loginToken)){
			repositoryUser = repository.findByEmail(loginToken);
		}else{
			repositoryUser = repository.findBySsoId(loginToken);
		}

		if (null == repositoryUser)
			throw new AuthenticationException(AuthenticationException.ERROR_INVALID_PRINCIPAL, "Could not get the user information base on [" + loginToken + "]");

		if (false==simpleEncryptor.comparePassword(password, repositoryUser.getPassword(), repositoryUser.getEncryptAlgorithm()))
			throw new AuthenticationException(AuthenticationException.ERROR_INVALID_CREDENTIAL, "Invalid password of the user information base on [" + loginToken + "]");

		if (!Boolean.TRUE.equals(repositoryUser.isActivated()))
			throw new AuthenticationException(AuthenticationException.ERROR_INACTIVE, "Login information is fine but this account did not activated yet. ");

		userDetails = buildUserDetails(repositoryUser);
		authenticatedUser = repositoryUser;
		authenticatedUser.setUserDetails(userDetails);
		return authenticatedUser;
	}

	@Override
	public UserAccount getUser(String userToken) throws AuthenticationException {
		UserAccount repositoryUser = null;
		if (CommonUtility.isEmailAddreess(userToken)){
			repositoryUser = repository.findByEmail(userToken);
		}else{
			repositoryUser = repository.findBySsoId(userToken);
		}

		if (null==repositoryUser){
			throw new AuthenticationException(AuthenticationException.ERROR_INVALID_PRINCIPAL, "Could not get the user information base on [" + userToken + "]");
		}

		return repositoryUser;
	}

	@Override
	public void initializeMasterData() throws AuthenticationException {
		UserAccount adminUser = null, clientUser = null, user = null;
		Authority clientRoleEntity = null, userRoleEntity = null, adminRoleEntity = null;
		//Setup authorities/roles
		try {
			clientRoleEntity = authorityServiceManager.getByName(AuthorityGroup.RoleClient.getCode());
			if (null==clientRoleEntity){
				clientRoleEntity = Authority.builder().name(AuthorityGroup.RoleClient.getCode()).displayName("Client activity. ").build();
				authorityServiceManager.save(clientRoleEntity);
			}

			userRoleEntity = authorityServiceManager.getByName(AuthorityGroup.RoleUser.getCode());
			if (null==userRoleEntity){
				userRoleEntity = Authority.builder().name(AuthorityGroup.RoleUser.getCode()).displayName("Common activity for normal user. ").build();
				authorityServiceManager.save(userRoleEntity);
			}

			adminRoleEntity = authorityServiceManager.getByName(AuthorityGroup.RoleAdmin.getCode());
			if (null==adminRoleEntity){
				adminRoleEntity = Authority.builder().name(AuthorityGroup.RoleAdmin.getCode()).displayName("System Administration. ").build();
				authorityServiceManager.save(adminRoleEntity);
			}

			Set<Authority> adminAuthorities = new HashSet<>();
			adminAuthorities.add(userRoleEntity);
			adminAuthorities.add(clientRoleEntity);
			adminAuthorities.add(adminRoleEntity);

			Set<Authority> clientAuthorities = new HashSet<>();
			clientAuthorities.add(clientRoleEntity);

			Set<Authority> userAuthorities = new HashSet<>();
			userAuthorities.add(userRoleEntity);

			if (null==repository.findBySsoId(MasterUserGroup.Administrator.getLogin())){
				//adminUser = User.createInstance("administrator@gmail.com", "Administrator", "System", MasterUserGroup.Administrator.getLogin(), passwordEncoder.encode("P@dministr@t0r"), adminAuthorities);
				adminUser = UserAccount.createInstance(
						"administrator@gmail.com", 
						"Administrator", 
						"System", 
						MasterUserGroup.Administrator.getLogin(), 
						simpleEncryptor.vpxEncode("P@dministr@t0r"), 
						null);
				adminUser.setActivated(true);
				repository.save(adminUser);

				adminUser.setAuthorities(adminAuthorities);
				repository.save(adminUser);

				clientServicesHelper.registerBasicClient(adminUser);
			}

			if (null==repository.findBySsoId(MasterUserGroup.VpexClient.getLogin())){
				//clientUser = User.createInstance("vpexcorpclient@gmail.com", "Corporate client", "Vpex", MasterUserGroup.VpexClient.getLogin(), passwordEncoder.encode("vP3@x5"), clientAuthorities);
				clientUser = UserAccount.createInstance(
						"vpexcorpclient@gmail.com", 
						"Corporate client", 
						"Vpex", 
						MasterUserGroup.VpexClient.getLogin(), 
						simpleEncryptor.vpxEncode("vP3@x5"), 
						null);
				clientUser.setActivated(true); 
				repository.save(clientUser);

				adminUser.setAuthorities(clientAuthorities);
				repository.save(clientUser);

				clientServicesHelper.registerBasicClient(clientUser);
			}

			if (null==repository.findBySsoId(MasterUserGroup.User.getLogin())){
				//user = User.createInstance("normaluser@gmail.com", "User", "Application", MasterUserGroup.User.getLogin(), passwordEncoder.encode("u63r@x9"), userAuthorities);
				user = UserAccount.createInstance(
						"normaluser@gmail.com", 
						"User", 
						"Application", 
						MasterUserGroup.User.getLogin(), 
						simpleEncryptor.vpxEncode("u63r@x9"), 
						null);
				user.setActivated(true);
				repository.save(user);

				adminUser.setAuthorities(userAuthorities);
				repository.save(user);

				clientServicesHelper.registerBasicClient(user);
			}
		} catch (Exception e) {
			throw new AuthenticationException(e);
		}
	}

	@Override
	public UserAccount confirm(String confirmedEmail) throws AuthenticationException {
		UserAccount confirmUser = repository.findByEmail(confirmedEmail);
		if (null == confirmUser)
			throw new AuthenticationException("The email not found in database: " + confirmedEmail);

		confirmUser.setActivated(true);
		repository.save(confirmUser);
		return confirmUser;
	}

	private UserDetails buildUserDetails(UserAccount userProfile){
		List<GrantedAuthority> grantedAuthorities = userProfile.getAuthorities().stream()
				.map(authority -> new SimpleGrantedAuthority(authority.getName())).collect(Collectors.toList());

		return new org.springframework.security.core.userdetails.User(userProfile.getSsoId(), userProfile.getPassword(), grantedAuthorities);
	}
}
