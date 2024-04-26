
package acme.features.authenticated.developer;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.data.accounts.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.roles.Developer;

@Repository
public interface AuthenticatedDeveloperRepository extends AbstractRepository {

	@Query("select developer from Developer developer where developer.userAccount.id = :id")
	Developer findDeveloperByUserAccountId(int id);

	@Query("select userAccount from UserAccount userAccount where userAccount.id = :id")
	UserAccount findUserAccountById(int id);

}
