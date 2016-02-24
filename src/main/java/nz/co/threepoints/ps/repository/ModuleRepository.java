package nz.co.threepoints.ps.repository;

import nz.co.threepoints.ps.domain.Module;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Module entity.
 */
public interface ModuleRepository extends JpaRepository<Module,Long> {

}
