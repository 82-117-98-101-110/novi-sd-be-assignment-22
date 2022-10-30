package com.ravel.backend.spacePro.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.ravel.backend.spacePro.model.Portal;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PortalRepository extends JpaRepository<Portal, Long> {
    @Transactional
    @Modifying
    @Query("delete from Portal p where p.portalUuid = ?1")
    void deleteByPortalUuid(UUID portalUuid);

    @Transactional
    @Modifying
    @Query("update Portal p set p.metadata = ?1 where p.portalUuid = ?2")
    int updateMetadataByPortalUuid(JsonNode metadata, UUID portalUuid);

    @Query("select p from Portal p where p.spacePro.spaceUuid = ?1")
    List<Portal> findBySpacePro_SpaceUuid(UUID spaceUuid);








}
