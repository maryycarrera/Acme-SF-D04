
package acme.features.administrator.banner;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.banners.Banner;

@Repository
public interface AdministratorBannerRepository extends AbstractRepository {

	@Query("SELECT b FROM Banner b WHERE b.id = :id")
	Banner findOneBannerById(int id);

	@Query("SELECT b FROM Banner b ORDER BY b.id")
	Collection<Banner> findBanners();

}
