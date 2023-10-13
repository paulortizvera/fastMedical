/******************************************/ 
/* Aplicacion: TFM - Sistema médico       */ 
/* Proposito : Script de errores          */
/* Fecha     : 15/09/2023                 */
/* Autor     : Paúl Ortiz Vera            */
/******************************************/ 

use fastmedical
go

truncate table errores

/* 1 - login */
insert into errores values (100, 'Usuario no existe', 0)
insert into errores values (101, 'El usuario no se encuentra activo', 0)
insert into errores values (102, 'Contraseña caducada', 0)

/* 2 - usuiarios */
insert into errores values (200, 'El login ya fue registrado por otro usuario', 0)
insert into errores values (201, 'El correo ya fue registrado previamente por otro usuario', 0)
insert into errores values (202, 'El usuario a actualizar no existe', 0)
insert into errores values (203, 'El usuario no tiene permisos para actualizar roles', 0)
insert into errores values (204, 'El usuario no tiene permisos para actualizar estados', 0)
insert into errores values (205, 'El usuario no tiene permisos para actualizar documentacion', 0)
insert into errores values (206, 'El usuario a eliminar no existe', 0)

/* 3 - profesionales */
insert into errores values (300, 'El login ya existe como profesional', 0)
insert into errores values (301, 'El profesional a actualizar no existe', 0)
insert into errores values (302, 'El profesional tiene solicitudes pendientes', 0)
insert into errores values (303, 'El profesional a eliminar no existe', 0)
insert into errores values (304, 'El profesional no existe', 0)

/* 4 - clientes */
insert into errores values (400, 'El login ya existe como cliente', 0)
insert into errores values (401, 'El cliente a actualizar no existe', 0)
insert into errores values (402, 'El cliente no existe', 0)

/* 5 - solicitud */
insert into errores values (500, 'La latitud no puede ser 0', 0)
insert into errores values (501, 'La longitud no puede ser 0', 0)
insert into errores values (502, 'El valor propuesto no puede ser 0', 0)
insert into errores values (503, 'La solicitud no existe', 0)
insert into errores values (504, 'El cliente ya posee una solicitud de este tipo', 0)

go