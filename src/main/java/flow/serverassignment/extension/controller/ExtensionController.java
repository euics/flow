package flow.serverassignment.extension.controller;

import flow.serverassignment.extension.service.ExtensionService;
import flow.serverassignment.extension.util.ExtensionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

@Controller
@RequiredArgsConstructor
public class ExtensionController {
    private final ExtensionService extensionService;

    @GetMapping("/")
    public String showForm(Model model) {
        Set<String> fixedExtensions = extensionService.getAllNamesByType(ExtensionType.FIXED);
        Set<String> customExtensions = extensionService.getAllNamesByType(ExtensionType.CUSTOM);

        model.addAttribute("fixedExtensions", fixedExtensions);
        model.addAttribute("customExtensions", customExtensions);

        return "index";
    }
}
