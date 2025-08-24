package flow.serverassignment.extension.repository;

import flow.serverassignment.extension.domain.ExtensionEntity;
import flow.serverassignment.extension.util.ExtensionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ExtensionJpaRepository extends JpaRepository<ExtensionEntity, Long> {
    @Query("""
            SELECT e.name
            FROM ExtensionEntity e
            WHERE e.type = :type
            """)
    Set<String> findNamesByType(@Param("type") ExtensionType type);

    @Query("""
            SELECT e
            FROM ExtensionEntity e
            WHERE e.name = :name
            AND e.type = :type
            """)
    Optional<ExtensionEntity> findByNameAndType(@Param("name") String name, @Param("type") ExtensionType type);

    @Query("""
            SELECT COUNT(e)
            FROM ExtensionEntity e
            WHERE e.type = :type
            """)
    long countByType(@Param("type") ExtensionType type);

    boolean existsByNameAndType(String name, ExtensionType type);
}
