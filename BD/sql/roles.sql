/******************************************/ 
/* Aplicacion: TFM - Sistema médico       */ 
/* Proposito : Script de roles            */
/* Fecha     : 15/09/2023                 */
/* Autor     : Paúl Ortiz Vera            */
/******************************************/ 

use fastmedical
go

declare @w_login varchar(50) 

select @w_login = 'admin'

if not exists (select 1 from usuario where login = @w_login)
begin
	print 'Creando usuario'
		
	insert into usuario(login, contrasena, nombre, sexo, pais, ciudad, direccion, telefono, correo, fecha_nacimiento, fecha_registro, fecha_modificacion, rol, estado)
	values (@w_login, 'admin', 'Paul Ortiz', 'M', 'Ecuador', 'Quito', 'Quito', '0981691113', 'mauricio_14147@gmail.com', '09/14/1993', '10/08/2023', null, 'A', 'A')
		
	print 'Usuario registrado con éxito.'
end
else
begin
	print 'El usuario ya existe.';
end
