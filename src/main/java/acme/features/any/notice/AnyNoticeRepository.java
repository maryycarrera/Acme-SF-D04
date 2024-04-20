
package acme.features.any.notice;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.notices.Notice;

@Repository
public interface AnyNoticeRepository extends AbstractRepository {

	@Query("select n from Notice n where n.instantiationMoment >= :oneMonthAgo")
	Collection<Notice> findNoticesWithinOneMonth(Date oneMonthAgo);

	@Query("select n from Notice n where n.id = :id")
	Notice findOneNoticeById(int id);
}
