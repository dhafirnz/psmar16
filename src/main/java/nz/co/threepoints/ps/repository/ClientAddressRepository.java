package nz.co.threepoints.ps.repository;

import nz.co.threepoints.ps.domain.ClientAddress;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ClientAddress entity.
 */
public interface ClientAddressRepository extends JpaRepository<ClientAddress,Long> {

}
