package flow.serverassignment.extension.repository;

import flow.serverassignment.extension.domain.ExtensionEntity;
import flow.serverassignment.extension.util.ExtensionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class ExtensionRepositoryImpl implements ExtensionRepository {
    private final ExtensionJpaRepository repository;

    @Override
    public ExtensionEntity saveExtension(ExtensionEntity extensionEntity) {
        return repository.save(extensionEntity);
    }

    @Override
    public Set<String> findNamesByType(ExtensionType type) {
        return repository.findNamesByType(type);
    }

    @Override
    public Optional<ExtensionEntity> findByNameAndType(String name, ExtensionType type) {
        return repository.findByNameAndType(name, type);
    }

    @Override
    public void deleteExtension(ExtensionEntity extensionEntity) {
        repository.delete(extensionEntity);
    }

    @Override
    public long countByType(ExtensionType type) {
        return repository.countByType(type);
    }

    @Override
    public boolean existsByNameAndType(String name, ExtensionType type) {
        return repository.existsByNameAndType(name, type);
    }
}
