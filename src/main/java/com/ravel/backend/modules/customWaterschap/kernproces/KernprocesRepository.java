package com.ravel.backend.modules.customWaterschap.kernproces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface KernprocesRepository extends JpaRepository<Kernproces, Long> {
	@Query("select k from Kernproces k where upper(k.kernProcesName) = upper(?1)")
	Kernproces findByKernProcesName(String kernProcesName);

	@Query(
		"select (count(k) > 0) from Kernproces k where upper(k.kernProcesName) = upper(?1)"
	)
	boolean existsByKernProcesName(String kernProcesName);
}
