package jp.matsuura.model.error

enum class RegisterErrorType {
    AlreadyExistedUser,
    EmailValidationError,
    PasswordValidationError,
    UnknownError,
}