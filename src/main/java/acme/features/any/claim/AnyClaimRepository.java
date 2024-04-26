
package acme.features.any.claim;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.claims.Claim;

@Repository
public interface AnyClaimRepository extends AbstractRepository {

	@Query("select claim from Claim claim where claim.id = :claimId")
	Claim findClaimById(int claimId);

	@Query("select claim from Claim claim")
	Collection<Claim> findAllClaims();

	@Query("select claim from Claim claim where claim.code = :code")
	Claim findClaimByCode(String code);

}
