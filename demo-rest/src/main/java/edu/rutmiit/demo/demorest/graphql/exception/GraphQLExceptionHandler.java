package edu.rutmiit.demo.demorest.graphql.exception;

import com.netflix.graphql.types.errors.ErrorType;
import com.netflix.graphql.types.errors.TypedGraphQLError;
import edu.rutmiit.demo.bankapicontract.exception.ClientAlreadyExistsException;
import graphql.GraphQLError;
import graphql.schema.DataFetchingEnvironment;
import jakarta.validation.ConstraintViolationException;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GraphQLExceptionHandler {

    @GraphQlExceptionHandler
    public GraphQLError handleNotFound(NoSuchElementException ex, DataFetchingEnvironment env) {
        return TypedGraphQLError.newNotFoundBuilder()
                .message(ex.getMessage())
                .path(env.getExecutionStepInfo().getPath())
                .build();
    }

    @GraphQlExceptionHandler
    public GraphQLError handleClientExists(ClientAlreadyExistsException ex, DataFetchingEnvironment env) {
        return TypedGraphQLError.newBuilder()
                .errorType(ErrorType.BAD_REQUEST)
                .message(ex.getMessage())
                .path(env.getExecutionStepInfo().getPath())
                .build();
    }

    @GraphQlExceptionHandler
    public GraphQLError handleValidation(ConstraintViolationException ex, DataFetchingEnvironment env) {
        return TypedGraphQLError.newBuilder()
                .errorType(ErrorType.BAD_REQUEST)
                .message("Ошибка валидации данных: " + ex.getMessage())
                .path(env.getExecutionStepInfo().getPath())
                .build();
    }

    @GraphQlExceptionHandler
    public GraphQLError handleGeneral(Exception ex, DataFetchingEnvironment env) {
        return TypedGraphQLError.newInternalErrorBuilder()
                .message("Внутренняя ошибка сервера: " + ex.getMessage())
                .path(env.getExecutionStepInfo().getPath())
                .build();
    }
}