package com.oficial.C1739.Enum;

public enum State {

    IN_PROGRESS, /* Este estado indica que el proyecto se encuentra actualmente en desarrollo.
                    Los miembros del equipo están trabajando en las tareas asignadas, escribiendo código,
                    resolviendo problemas y avanzando hacia la siguiente etapa.*/
    PENDING_REVIEW, /* En este estado, el trabajo realizado durante la etapa de desarrollo ha sido completado
                    y el proyecto está listo para ser revisado por otros miembros del equipo o por un líder técnico. */

    READY_FOR_RELEASE, /*  Después de que el código ha sido revisado y aprobado, el proyecto pasa a este estado.
                            Esto significa que el proyecto está listo para ser entregado o implementado en un
                            ambiente de producción o entorno de producción. */

    RELEASED /* Está disponible para su uso en un entorno de producción. */

}
