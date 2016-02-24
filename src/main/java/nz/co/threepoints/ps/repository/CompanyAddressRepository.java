package nz.co.threepoints.ps.repository;

import nz.co.threepoints.ps.domain.CompanyAddress;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CompanyAddress entity.
 */
public interface CompanyAddressRepository extends JpaRepository<CompanyAddress,Long> {

}
