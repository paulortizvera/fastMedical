use fastmedical
go
if exists (select 1 from sysobjects where  name = 'sp_fm_login') 
begin
   drop proc sp_fm_login
end
go
/**************************************************************************/
/* Aplicacion        : TFM - Sistema médico                               */
/* Proposito         : Creacion de base de datos                          */
/* Archivo           : sp_fm_login.sp                                     */
/* Base de datos     : fastmedical                                        */
/****************************  MODIFICACIONES  ****************************/
/*   FECHA        AUTOR                RAZON                              */
/* 15/09/2023   Paul Ortiz Vera      Emision inicial                      */
/**************************************************************************/

create proc sp_fm_login
 ( @s_date        datetime,
   @i_operacion   char(1),
   @i_modo        int = null,
   @i_login       varchar(50) = null,
   @i_contrasena  varchar(50) = null,
   @i_correo      varchar(50) = null,
   @i_otp		  varchar(50) = null,
   @o_exists      int = null out)
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
	if not exists(select 1 from usuario where login = @i_login and contrasena = @i_contrasena)
	begin
		select  @o_exists = 0,
		@w_error = 1,
		@w_return = 1,
		@w_msg = 'Usuario no existe'
		goto errors
	end
	else
	begin
		/* Validar vigencia de contraseña */
		if exists(select 1 from usuario where estado <> 'A')
		begin
			select  @o_exists = 0,
			@w_error = 2,
			@w_return = 2,
			@w_msg = 'El usuario no se encuentra activo'
			goto errors
		end
		
		/* Si la contraseña tiene caducada más de 3 meses debe actualizar */
		if exists(select * from usuario where fecha_modificacion is not null and datediff(month, GETDATE(),fecha_modificacion) > 3)
		begin
			select  @o_exists = 0,
			@w_error = 3,
			@w_return = 3,
			@w_msg = 'Contraseña caducada'
			goto errors
		end

		select	'NOMBRE'	= nombre,
				'SEXO'		= sexo,
				'PAIS'		= pais,
				'CIUDAD'	= ciudad,
				'ROL'		= rol,
				'ESTADO'	= estado
		from usuario
		where login = @i_login

		select @o_exists = 1
		return 0
	end
end

/* Actualizacion */
/*if @i_operacion = 'U' 
begin
	if not exists(select 1 from usuario where login = @i_login and contrasena = @i_contrasena)
	begin
		select  @o_exists = 0,
		@w_error = 1,
		@w_return = 1,
		@w_msg = 'Usuario no existe'
		goto errors
	end
	else
	begin
		/* Validar vigencia de contraseña */
		if exists(select 1 from usuario where estado <> 'A')
		begin
			select  @o_exists = 0,
			@w_error = 2,
			@w_return = 2,
			@w_msg = 'El usuario no se encuentra activo'
			goto errors
		end

		/* Actualizar contraseña */
		update usuario
			set contrasena = @i_contrasena
		where login = @i_login
	end
end*/

/* Desbloquear */
if @i_operacion = 'B' 
begin
	if(@i_modo = 0)
	begin
		if exists(select 1 from usuario where correo = @i_correo)
		begin
			/* Generar otp */
			select @w_otp = floor(rand()*(999999-111111)+111111)
		
			select @w_login = login from usuario where correo = @i_correo

			/* Actualizar correo anterior */
			if exists(select 1 from correo_otp where correo = @i_correo and estado = 'V')
			begin
				update correo_otp
					set estado = 'C'
				where correo = @i_correo
			end

			insert into correo_otp (correo, otp, fecha_registro, estado, login) 
			values (@i_correo, @w_otp, GETDATE(), 'V', @w_login)

			select	'CORREO'	= @i_correo,
					'OTP'		= @w_otp 
		end
	end
	/* Validar otp */
	if(@i_modo = 1)
	begin
		/* Actualizar correo anterior */
		if exists(select 1 from correo_otp where correo = @i_correo and otp = @i_otp and estado = 'V')
		begin
			select @w_login = login 
			from correo_otp 
			where correo = @i_correo 
			and otp = @i_otp 
			and estado = 'V'

			/* Actualizar contraseña */
			update usuario
				set contrasena = @i_contrasena
			where login = @w_login
		end
	end
end

-- Manejo de errores
errors:

if @w_return <> 0 begin
    exec sp_fm_error
    @s_date = @s_date,
    @i_num  = 1,
    @i_msg  = @w_msg,
	@i_sev  = 0

    return @w_return
end
go
