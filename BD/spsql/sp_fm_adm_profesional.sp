use fastmedical
go
if exists (select 1 from sysobjects where  name = 'sp_fm_adm_profesional') 
begin
   drop proc sp_fm_adm_profesional
end
go
/**************************************************************************/
/* Aplicacion        : TFM - Sistema médico                               */
/* Proposito         : SP de Mantenimiento de proifesionales              */
/* Archivo           : sp_fm_adm_profesional.sp                           */
/* Base de datos     : fastmedical                                        */
/****************************  MODIFICACIONES  ****************************/
/*   FECHA        AUTOR                RAZON                              */
/* 15/09/2023   Paul Ortiz Vera      Emision inicial                      */
/**************************************************************************/

create proc sp_fm_adm_profesional
 ( @s_date        datetime, --Fecha
   @s_login       varchar(50), --usuario logeado
   @i_operacion   char(1),
   @i_modo        int = null,
   @i_login       varchar(50) = null,
   @i_tipo        char(1) = null,
   @i_especialidad char(1) = null,
   @i_documentacion char(1) = null,
   @i_comentario  varchar(300) = null,
   @i_estado      char(1) = null,
   @o_rowcount    int = null out,
   @o_msg         varchar(300) = null out)
as
  declare @w_return  int,
          @w_error   int,
		  @w_sev     int,
          @w_sp_name varchar ( 32 ),
		  @w_tipo    char(1),
		  @w_especialidad char(1),
		  @w_documentacion char(1)

----------------------------------------------------
select @w_sp_name  = object_name( @@procid ),
@o_rowcount = 0

/* Insertar */
if @i_operacion = 'I' 
begin
	
	/* Validar Login */
	if exists(select 1 from profesional where login = @i_login)
	begin
		select	@w_error = 300, --Login ya existe como profesional
		@w_return = 300
		goto errors
	end
	
	insert into profesional (tipo, especialidad, login)
	values (@i_tipo, @i_especialidad, @i_login)

	select @o_rowcount = 1
end
/* Actualizar */
if @i_operacion = 'U' 
begin
	/* Actualizar información del profesional */
	if(@i_modo = 0)
	begin
		/* Validar Login y correo*/
		if not exists(select 1 from profesional where login = @i_login)
		begin
			select  @w_error = 301, --El profesional a actualizar no existe
			@w_return = 301
			goto errors
		end

		select @w_tipo		 = tipo,
			@w_especialidad  = especialidad,
			@w_documentacion = documentacion
		from profesional
		where login = @i_login

		/* Si cambia el tipo o la especialidad se debe validar documentación */
		if(@i_tipo <> @w_tipo or @i_especialidad <> @w_especialidad)
			select @w_documentacion = 'P'

		update profesional set
			tipo				= @i_tipo,
			especialidad		= @i_especialidad,
			documentacion       = @w_documentacion,
			fecha_modificacion	= GETDATE()
		where login = @i_login

		select @o_rowcount = 1
	end
	/* Cambiar documentacion */
	if(@i_modo = 1)
	begin
		/* Validar rol de usuario logeado*/
		if not exists(select 1 from usuario where login = @s_login and rol = 'A')
		begin
			select  @w_error = 205, --El usuario no tiene permisos para actualizar documentacion
			@w_return = 205
			goto errors
		end

		update profesional set
			documentacion		= @i_documentacion,
			comentario			= @i_comentario,
			fecha_modificacion	= GETDATE()
		where login = @i_login

		select @o_rowcount = 1
	end
	/* Cambiar estado */
	if(@i_modo = 2)
	begin
		/* Validar rol de usuario logeado*/
		if not exists(select 1 from usuario where login = @s_login and rol = 'A')
		begin
			select  @w_error = 204, --'El usuario no tiene permisos para actualizar estados'
			@w_return = 204
			goto errors
		end

		update profesional set
			estado				= @i_estado,
			comentario			= @i_comentario,
			fecha_modificacion	= GETDATE()
		where login = @i_login

		select @o_rowcount = 1
	end
end

/* Eliminar */
if @i_operacion = 'D' 
begin
	/* Validar Login*/
	if not exists(select 1 from profesional where login = @i_login)
	begin
		select  @w_error = 303, --El profesional a eliminar no existe
		@w_return = 303
		goto errors
	end
	/* Validar solicitudes*/
	if exists(select 1 from profesional p inner join solicitud s on p.codigo = s.codigo where p.login = @i_login and s.estado <> 'C')
	begin
		select  @w_error = 302, --El profesional a actualizar no existe
		@w_return = 302
		goto errors
	end
	
	/* Se eliminan los correos */
	delete profesional where login = @i_login

	select @o_rowcount = 1
end
-- Manejo de errores
errors:

if @w_return <> 0 begin
    exec sp_fm_error
    @s_date = @s_date,
    @i_num  = @w_error,
	@i_sev  = @w_sev,
	@o_msg  = @o_msg out

    return @w_return
end
go
