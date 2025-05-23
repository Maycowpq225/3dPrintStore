package maycow.printstore3d.aspects;

import maycow.printstore3d.annotations.RequiresAuthorization;
import maycow.printstore3d.models.enums.ProfileEnum;
import maycow.printstore3d.security.UserSpringSecurity;
import maycow.printstore3d.services.exceptions.AuthorizationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Aspect
@Component
public class AuthorizationAspect {

    @Around("@annotation(requiresAuthorization)")
    public Object checkAccess(ProceedingJoinPoint joinPoint, RequiresAuthorization requiresAuthorization)
            throws Throwable {
        Object[] args = joinPoint.getArgs();
        String userIdOrEmail = (String) args[0];

        UserSpringSecurity userSpringSecurity = isAuthorized();

        if (Objects.isNull(userSpringSecurity)
                || (!userSpringSecurity.hasRole(ProfileEnum.ADMIN)
                && !userIdOrEmail.equals(userSpringSecurity.getEmail())
                && !userIdOrEmail.equals(userSpringSecurity.getId()))) {
            throw new AuthorizationException("Acesso negado!");
        }

        return joinPoint.proceed(); // Continua a execução do método original
    }

    private UserSpringSecurity isAuthorized() {
        try {
            return (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }
}