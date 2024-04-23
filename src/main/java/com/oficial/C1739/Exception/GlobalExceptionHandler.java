package com.oficial.C1739.Exception;


import com.oficial.C1739.dto.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;


/**
 * La Anotacion @RestControllerAdvice es una especialización de la anotación @Component,
 * que permite definir un manejador de excepciones para todos los controladores REST de la aplicación.
 *
 *
 *
 * Centraliza el manejo de excepciones para controladores REST en una aplicación Spring.
 * Simplifica el código al definir manejadores de excepciones en una única clase reutilizable.
 * Proporciona un enfoque consistente para el manejo de errores en toda tu API.
 *
 *
 * @RestControllerAdvice en sí mismo combina @ControllerAdvice y @ResponseBody.
 * @ControllerAdvice: Permite que la clase proporcione asesoramiento sobre el manejo de excepciones en todos los controladores.
 * @ResponseBody: Garantiza que los métodos del manejador de excepciones en esta clase devuelvan automáticamente el cuerpo de la respuesta (útil para las API REST).
 */


@RestControllerAdvice
public class GlobalExceptionHandler {


    //Este metodo nos sirve para manejar las excepciones de validaciones que definimos en el DTO
    // por ejemplo, que no cumple el minimo de longitud, es un atributo requerido, no se permite nulo etc.

    //IMPORTANTE, PARA QUE SE LANCE ESTA EXEPCION, EL CONTROLADOR DEBE TENER LA NOTACION @VALID!!!

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception,HttpServletRequest request){
        ApiError apiError = new ApiError();
        apiError.setUrl(request.getRequestURL().toString());
        apiError.setMethod(request.getMethod());
        apiError.setMessage("Error en la peticion: ");


        List<String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        apiError.setErrors(errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);

    }



    /**
     *
     * @param e
     * @param request
     * @return ResponseEntity<?>
     *
     *     Esto nos sirve para manejar las excepciones de tipo generico, es decir, cualquier excepcion que no sea manejada por los otros metodos
     *     por ejemeplo cuando no se guarde correctamente un dato en la base de datos, o cuando se caiga el servidor.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handlerGenericException(Exception e, HttpServletRequest request){

        ApiError apiError = new ApiError();
        apiError.setBackendMessage(e.getLocalizedMessage());
        apiError.setUrl(request.getRequestURL().toString());
        apiError.setMethod(request.getMethod());
        apiError.setMessage("Ha ocurrido un error interno en el servidor");



        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView handleAccessDeniedException(AccessDeniedException ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("error", "Acceso denegado: " + ex.getMessage());
        modelAndView.setViewName("error");
        return modelAndView;
    }



}
