package com.kandc_android_challenge.utils

class InterfaceNotImplementedException(
    className: String?,
    interfaceName: String
) : ClassCastException(
    String.format(INTERFACE_NOT_IMPLEMENTED_EXCEPTION_MESSAGE, className, interfaceName)
)

class BackendException(message: String?) : Throwable(message)

class CorruptedDataException : Throwable()
