package br.edu.example.api.core.generic.exception;

import br.edu.example.api.core.generic.model.PermissionFlag;

public final class ForbiddenException extends CoreException {
    public ForbiddenException(PermissionFlag permissionFlag) {
        super("permission.not.sufficient", generateMessage(permissionFlag));
    }

    private static String generateMessage(PermissionFlag permissionFlag) {
        return String.format(
                "The current user does not have the %s permission that is required to use the functionality",
                permissionFlag.name()
        );
    }
}
