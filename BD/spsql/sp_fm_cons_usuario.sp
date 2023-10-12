use fastmedical
go
if exists (select 1 from sysobjects where  name = 'sp_fm_cons_usuario') 
begin
   drop proc sp_fm_cons_usuario
end
go
/**************************************************************************/
/* Aplicacion        : TFM - Sistema médico                               */
/* Proposito         : SP de Consulta de usuarios                         */
/* Archivo           : sp_fm_cons_usuario.sp                              */
/* Base de datos     : fastmedical                                        */
/****************************  MODIFICACIONES  ****************************/
/*   FECHA        AUTOR                RAZON                              */
/* 15/09/2023   Paul Ortiz Vera      Emision inicial                      */
/**************************************************************************/

create proc sp_fm_cons_usuario
 ( @s_date        datetime, --Fecha
   @i_operacion   char(1),
   @i_modo        int = null,
   @i_login       varchar(50) = null,
   @i_contrasena  varchar(50) = null,
   @i_correo      varchar(50) = null,
   @i_otp		  int = null)
as
  declare @w_return  int,
          @w_error   int,
		  @w_msg     varchar(200),
		  @w_otp     int,
		  @w_login   varchar(50),
          @w_sp_name varchar ( 32 )

----------------------------------------------------
select @w_sp_name  = object_name( @@procid )

/* Search */
if @i_operacion = 'S' 
begin
	/* Consultar toda la tabla */
	if(@i_modo = 0)
	begin
		select	'LOGIN'				= login,
			'NOMBRE'			= nombre,
			'SEXO'				= sexo,
			'PAIS'				= pais,
			'CIUDAD'			= ciudad,
			'DIRECCION'			= direccion,
			'TELEFONO'			= telefono,
			'CORREO'			= correo,
			'FECHA_NACIMIENTO'	= fecha_nacimiento,
			'FECHA_REGISTRO'	= fecha_registro,
			'FECHA_MODIFICACION'= fecha_modificacion,
			'ROL'               = rol,
			'ESTADO'            = estado
		from usuario
	end
	/* Consultar solo activos */
	if(@i_modo = 1)
	begin
		select	'LOGIN'				= login,
			'NOMBRE'			= nombre,
			'SEXO'				= sexo,
			'PAIS'				= pais,
			'CIUDAD'			= ciudad,
			'DIRECCION'			= direccion,
			'TELEFONO'			= telefono,
			'CORREO'			= correo,
			'FECHA_NACIMIENTO'	= fecha_nacimiento,
			'FECHA_REGISTRO'	= fecha_registro,
			'FECHA_MODIFICACION'= fecha_modificacion,
			'ROL'               = rol,
			'ESTADO'            = estado
		from usuario
		where estado = 'A'
	end

end

/* Querys */
if @i_operacion = 'Q' 
begin
	/* Consultar usuario por login*/
	if(@i_modo = 0)
	begin
		select	'LOGIN'				= login,
			'NOMBRE'			= nombre,
			'SEXO'				= sexo,
			'PAIS'				= pais,
			'CIUDAD'			= ciudad,
			'DIRECCION'			= direccion,
			'TELEFONO'			= telefono,
			'CORREO'			= correo,
			'FECHA_NACIMIENTO'	= fecha_nacimiento
		from usuario
		where login = @i_login
	end
	/* Consultar usuario por correo*/
	if(@i_modo = 1)
	begin
		select	'LOGIN'				= login,
			'NOMBRE'			= nombre,
			'SEXO'				= sexo,
			'PAIS'				= pais,
			'CIUDAD'			= ciudad,
			'DIRECCION'			= direccion,
			'TELEFONO'			= telefono,
			'CORREO'			= correo,
			'FECHA_NACIMIENTO'	= fecha_nacimiento
		from usuario
		where correo = @i_correo
	end
end

-- Manejo de errores
errors:

if @w_return <> 0 begin
    exec sp_fm_error
    @s_date = getDate,
    @i_num  = 1,
    @i_msg  = @w_msg,
	@i_sev  = 0

    return @w_return
end
go
