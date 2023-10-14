use fastmedical
go
if exists (select 1 from sysobjects where  name = 'sp_fm_adm_usuario') 
begin
   drop proc sp_fm_adm_usuario
end
go
/**************************************************************************/
/* Aplicacion        : TFM - Sistema médico                               */
/* Proposito         : SP de Mantenimiento de usuarios                    */
/* Archivo           : sp_fm_adm_usuario.sp                               */
/* Base de datos     : fastmedical                                        */
/****************************  MODIFICACIONES  ****************************/
/*   FECHA        AUTOR                RAZON                              */
/* 15/09/2023   Paul Ortiz Vera      Emision inicial                      */
/**************************************************************************/

create proc sp_fm_adm_usuario
 ( @s_date        datetime = null, --Fecha
   @s_login       varchar(50) = null, --usuario logeado
   @i_operacion   char(1),
   @i_modo        int = null,
   @i_login       varchar(50) = null,
   @i_contrasena  varchar(50) = null,
   @i_correo      varchar(50) = null,
   @i_nombre      varchar(100)= null,
   @i_sexo        char(1) = null,
   @i_pais        varchar(50) = null,
   @i_ciudad      varchar(100) = null,
   @i_direccion   varchar(200) = null,
   @i_telefono    varchar(13) = null,
   @i_fecha_nacimiento    date = null,
   @i_rol         char(1) = null,
   @i_estado      char(1) = null,
   @o_rowcount    int = null out,
   @o_msg         varchar(300) = null out)
as
  declare @w_return  int,
          @w_error   int,
		  @w_sev     int,
		  @w_msg     varchar(200),
		  @w_otp     int,
		  @w_login   varchar(50),
          @w_sp_name varchar ( 32 )

----------------------------------------------------
select @w_sp_name  = object_name( @@procid ),
@o_rowcount = 0

/* Insertar */
if @i_operacion = 'I' 
begin
	
	/* Validar Login */
	if exists(select 1 from usuario where login = @i_login)
	begin
		select  @w_error = 200, --El login ya fue registrado por otro usuario
		@w_return = 200
		goto errors
	end

	/* Validar Login */
	if exists(select 1 from usuario where correo = @i_correo)
	begin
		select  @w_error = 201, --El correo ya fue registrado previamente por otro usuario
		@w_return = 201
		goto errors
	end

	insert into usuario(login, contrasena, nombre, sexo, pais, ciudad, direccion, telefono, correo, fecha_nacimiento, fecha_registro, rol, estado)
	values (@i_login, @i_contrasena, @i_nombre, @i_sexo, @i_pais, @i_ciudad, @i_direccion, @i_telefono, @i_correo, @i_fecha_nacimiento, GETDATE(), 'C', 'P')

	select @o_rowcount = 1

	/* Crear otp para contraseña */
	exec @w_return = sp_fm_login
	@s_date        = @s_date,
	@i_operacion   = 'B',
	@i_modo        = 0,
	@i_tipo_otp    = 'R', --Registro
	@i_correo      = @i_correo

	if(@w_return <> 0)
	begin
		select @w_msg = 'Error al registrar otp',
		@w_sev = 1
		goto errors
	end
	
end
/* Actualizar */
if @i_operacion = 'U' 
begin
	/* Actualizar información del usuario */
	if(@i_modo = 0)
	begin
		/* Validar Login y correo*/
		if not exists(select 1 from usuario where login = @i_login or correo = @i_correo)
		begin
			select  @w_error = 202, --El usuario a actualizar no existe
			@w_return = 202
			goto errors
		end

		update usuario set
			contrasena         = @i_contrasena,
			nombre             = @i_nombre,
			sexo               = @i_sexo,
			pais               = @i_pais,
			ciudad             = @i_ciudad,
			direccion          = @i_direccion,
			telefono           = @i_telefono,
			fecha_nacimiento   = @i_fecha_nacimiento
		where login = @i_login

		select @o_rowcount = 1
	end
	/* Cambiar rol */
	if(@i_modo = 1)
	begin
		/* Validar rol de usuario logeado*/
		if not exists(select 1 from usuario where login = @s_login and rol = 'A')
		begin
			select  @w_error = 203, --El usuario no tiene permisos para actualizar roles
			@w_return = 203
			goto errors
		end
		/* Validar rol */
		if (@i_rol = 'A')
		begin
			select  @w_error = 207, --El usuario no tiene permisos para asignar el rol seleccionado
			@w_return = 207
			goto errors
		end
		
		update usuario set
			rol = @i_rol
		where login = @i_login

		select @o_rowcount = 1
	end
	/* Cambiar estado */
	if(@i_modo = 2)
	begin
		/* Validar rol de usuario logeado*/
		if not exists(select 1 from usuario where login = @s_login and rol = 'A')
		begin
			select  @w_error = 204, --El usuario no tiene permisos para actualizar estados
			@w_return = 204
			goto errors
		end

		update usuario set
			estado = @i_estado
		where login = @i_login

		select @o_rowcount = 1
	end
end

/* Eliminar */
if @i_operacion = 'D' 
begin
	/* Validar Login*/
	if not exists(select 1 from usuario where login = @i_login)
	begin
		select  @w_error = 206, --El usuario a eliminar no existe
		@w_return = 206
		goto errors
	end
	
	/* Se eliminan los correos */
	delete correo_otp where login = @i_login
	/* Se elimina usuario */
	delete usuario where login = @i_login

	select @o_rowcount = 1
end

-- Manejo de errores
errors:

if @w_return <> 0 begin
    exec sp_fm_error
    @s_date = @s_date,
    @i_num  = @w_error,
    @i_msg  = @w_msg,
	@i_sev  = @w_sev,
	@o_msg  = @o_msg out

    return @w_return
end
go
