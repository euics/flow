package flow.serverassignment.extension.repository;

import flow.serverassignment.extension.domain.ExtensionEntity;
import flow.serverassignment.extension.util.ExtensionType;

import java.util.Optional;
import java.util.Set;

public interface ExtensionRepository {
    ExtensionEntity saveExtension(ExtensionEntity extensionEntity);

    Set<String> findNamesByType(ExtensionType type);

    Optional<ExtensionEntity> findByNameAndType(String name, ExtensionType type);

    void deleteExtension(ExtensionEntity extensionEntity);

    long countByType(ExtensionType type);

    boolean existsByNameAndType(String name, ExtensionType type);
}
