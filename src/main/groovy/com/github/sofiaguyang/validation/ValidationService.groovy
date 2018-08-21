package com.github.sofiaguyang.validation

import org.springframework.util.Assert
import org.springframework.validation.*

class ValidationService {

    private List<Validator> validators

    private Validator defaultValidator

    private MessageCodesResolver messageCodesResolver

    private Map<Class<?>, Validator[]> classToValidatorsCache

    ValidationService(List<Validator> validators = [],
                      Validator defaultValidator,
                      MessageCodesResolver messageCodesResolver = makeDefaultResolver()) {

        Assert.notNull(validators, "List of validators is required")
        Assert.notNull(defaultValidator, 'Default validator is required!')
        Assert.notNull(messageCodesResolver, 'Message codes resolver is required!')

        this.validators = new ArrayList<>(validators)
        this.defaultValidator = defaultValidator
        this.messageCodesResolver = messageCodesResolver
        this.classToValidatorsCache = new HashMap<>()
    }

    void validate(Object object, Object... validationHints) throws ValidationException {
        def dataBinder = new DataBinder(object, object.class.simpleName)
        dataBinder.addValidators(findValidatorFor(object.class))
        dataBinder.setMessageCodesResolver(this.messageCodesResolver)
        dataBinder.validate(validationHints)
        if (dataBinder.bindingResult.hasErrors()) {
            throw new ValidationException(dataBinder.bindingResult)
        }
    }

    protected Validator[] findValidatorFor(Class<?> clazz) {
        Validator[] foundValidators = classToValidatorsCache.get(clazz)
        if (!foundValidators) {
            foundValidators = this.validators.findAll { it.supports(clazz) } ?: [defaultValidator]
            classToValidatorsCache.put(clazz, foundValidators)
        }
        return foundValidators
    }

    private static DefaultMessageCodesResolver makeDefaultResolver() {
        new DefaultMessageCodesResolver(formatter: DefaultMessageCodesResolver.Format.POSTFIX_ERROR_CODE)
    }

    static class ValidationException extends Exception {
        final Errors errors

        ValidationException(Errors errors) {
            this(messageFor(errors), errors)
        }

        ValidationException(String message, Errors errors) {
            super(message)
            this.errors = errors
        }

        @Override
        String toString() {
            return messageFor(errors)
        }

        /**
         * Creates a message out of the default error codes for each field
         */
        private static String messageFor(Errors errors) {
            def message = new StringBuffer("Validation exception with error codes: ")
            message.append(errors.allErrors.codes.collect { it[0] }.join(', '))
            return message.toString()
        }
    }
}
