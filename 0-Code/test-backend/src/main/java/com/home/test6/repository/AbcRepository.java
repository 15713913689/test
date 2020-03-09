package com.home.test6.repository;

import com.home.test6.domain.Abc;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Abc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AbcRepository extends JpaRepository<Abc, Long>, JpaSpecificationExecutor<Abc> {

}
