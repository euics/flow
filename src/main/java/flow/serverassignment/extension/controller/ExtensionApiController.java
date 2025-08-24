package flow.serverassignment.extension.controller;

import flow.serverassignment.extension.service.ExtensionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static flow.serverassignment.extension.controller.request.ExtensionApiRequest.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/extensions")
public class ExtensionApiController {
    private final ExtensionService extensionService;

    @PostMapping("/add")
    public ResponseEntity<Void> addExtension(@RequestBody ExtensionRequest request) {
        extensionService.addExtension(request);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> deleteExtension(@RequestBody ExtensionRequest request) {
        extensionService.deleteExtension(request);

        return ResponseEntity.ok().build();
    }
}
