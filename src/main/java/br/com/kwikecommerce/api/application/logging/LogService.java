package br.com.kwikecommerce.api.application.logging;

public interface LogService {

    void logInfo(String message);

    void logWarning(String message);

    void logWarning(String message, Throwable throwable);

    void logError(String message);

    void logError(String message, Throwable throwable);

}
