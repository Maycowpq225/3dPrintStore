package maycow.PrintStore3D.annotations;

import maycow.PrintStore3D.constraints.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordConstraint {
    String message() default "Sua senha deve conter pelo menos uma letra maiúscula, uma letra minúscula, um caracter especial e um número.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

