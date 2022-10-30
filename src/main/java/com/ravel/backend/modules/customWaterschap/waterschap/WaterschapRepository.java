package com.ravel.backend.modules.customWaterschap.waterschap;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WaterschapRepository extends JpaRepository<Waterschap, Long> {
	@Query("select w from Waterschap w where upper(w.waterschapName) = upper(?1)")
	Waterschap findWaterschapByWaterschapName(String waterschapName);

	@Query("select w from Waterschap w where upper(w.waterschapName) = upper(?1)")
	Waterschap findByWaterschapName(String waterschapname);

	@Query(
		"select (count(w) > 0) from Waterschap w where upper(w.waterschapName) = upper(?1)"
	)
	boolean existsByWaterschapName(String waterschapName);
}
