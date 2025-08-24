package flow.serverassignment.extension.service;

import flow.serverassignment.exception.BadRequestException;
import flow.serverassignment.extension.domain.ExtensionEntity;
import flow.serverassignment.extension.repository.ExtensionRepository;
import flow.serverassignment.extension.util.ExtensionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static flow.serverassignment.extension.controller.request.ExtensionApiRequest.*;

@Service
@RequiredArgsConstructor
public class ExtensionService {
    private final ExtensionRepository extensionRepository;
    private static final List<String> FIXED_EXTENSION_NAMES = List.of("bat", "cmd", "com", "cpl", "exe", "scr", "js");

    @Transactional(readOnly = true)
    public Set<String> getAllNamesByType(ExtensionType type) {
        return extensionRepository.findNamesByType(type);
    }

    @Transactional
    public void addExtension(ExtensionRequest request) {
        String name = request.name();
        ExtensionType type = request.extensionType();

        if (type.equals(ExtensionType.CUSTOM)) {
            /*
             * 1. 이름이 고정 확장자 목록에 포함되어 있는지 확인
             * 2. 글자 수 제한 확인
             * 3. 최대 개수 제한 확인
             * */
            if (FIXED_EXTENSION_NAMES.contains(name)) {
                throw new BadRequestException("'" + name + "'은(는) 고정 확장자이므로 커스텀으로 추가할 수 없습니다.");
            }
            if (name.length() > 20) {
                throw new BadRequestException("커스텀 확장자는 20자를 넘길 수 없습니다.");
            }
            if (extensionRepository.countByType(ExtensionType.CUSTOM) >= 200) {
                throw new BadRequestException("커스텀 확장자는 최대 200개까지 추가할 수 있습니다.");
            }
        } else {
            /*
             * 1. 추가하려는 이름이 실제 고정 확장자 목록에 있는지 확인
             * */
            if (!FIXED_EXTENSION_NAMES.contains(name)) {
                throw new BadRequestException("유효하지 않은 고정 확장자입니다: " + name);
            }
        }

        /*
        * 1. 이미 등록된 확장자 확인
        * */
        if (extensionRepository.existsByNameAndType(name, type)) {
            throw new BadRequestException("이미 등록된 확장자입니다.");
        }

        ExtensionEntity extensionEntity = ExtensionEntity.builder()
                .name(name)
                .type(type)
                .deleted(false)
                .build();

        extensionRepository.saveExtension(extensionEntity);
    }

    @Transactional
    public void deleteExtension(ExtensionRequest request) {
        ExtensionEntity extensionToDelete = extensionRepository.findByNameAndType(request.name(), request.extensionType())
                .orElseThrow(() -> new BadRequestException("삭제할 확장자를 찾을 수 없습니다."));

        extensionRepository.deleteExtension(extensionToDelete);
    }
}