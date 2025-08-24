package flow.serverassignment.extension.controller.request;

import flow.serverassignment.extension.util.ExtensionType;

public class ExtensionApiRequest {
    public record ExtensionRequest(
            String name,
            ExtensionType extensionType
    ) {
    }
}
