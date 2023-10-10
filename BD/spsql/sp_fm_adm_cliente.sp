use fastmedical
go
if exists (select 1 from sysobjects where  name = 'sp_fm_adm_cliente') 
begin
   drop proc sp_fm_adm_cliente
end
go
/**************************************************************************/
/* Aplicacion        : TFM - Sistema médico                               */
/* Proposito         : SP de Mantenimiento de clientes                    */
/* Archivo           : sp_fm_adm_cliente.sp                               */
/* Base de datos     : fastmedical                                        */
/****************************  MODIFICACIONES  ****************************/
/*   FECHA        AUTOR                RAZON                              */
/* 15/09/2023   Paul Ortiz Vera      Emision inicial                      */
/**************************************************************************/

create proc sp_fm_adm_cliente
 ( @s_date        datetime, --Fecha
   @s_login       varchar(50), --usuario logeado
   @i_operacion   char(1),
   @i_modo        int = null,
   @i_login       varchar(50) = null,
   @i_enfermedad  char(1) = null,
   @i_documentacion char(1) = null,
   @i_comentario  varchar(300) = null,
   @i_rol         char(1) = null,
   @i_estado      char(1) = null)
as
  declare @w_return  int,
          @w_error   int,
		  @w_sev     int,
          @w_sp_name varchar ( 32 ),
		  @w_tipo    char(1),
		  @w_especialidad char(1),
		  @w_documentacion char(1)

----------------------------------------------------
select @w_sp_name  = object_name( @@procid )

/* Insertar */
if @i_operacion = 'I' 
begin
	
	/* Validar Login */
	if exists(select 1 from cliente where login = @i_login)
	begin
		select	@w_error = 400, --Login ya existe como cliente
		@w_return = 400
		goto errors
	end
	
	insert into cliente (enfermedad, login)
	values (@i_enfermedad, @i_login)
end
/* Actualizar */
if @i_operacion = 'U' 
begin
	/* Actualizar información del cliente */
	if(@i_modo = 0)
	begin
		/* Validar Login y correo*/
		if not exists(select 1 from cliente where login = @i_login)
		begin
			select  @w_error = 401, --El cliente a actualizar no existe
			@w_return = 401
			goto errors
		end

		update cliente set
			enfermedad			= @i_enfermedad,
			fecha_modificacion	= GETDATE()
		where login = @i_login
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

		update cliente set
			documentacion		= @i_documentacion,
			comentario			= @i_comentario,
			fecha_modificacion	= GETDATE()
		where login = @i_login
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
	end
end

-- Manejo de errores
errors:

if @w_return <> 0 begin
    exec sp_fm_error
    @s_date = @s_date,
    @i_num  = @w_error,
	@i_sev  = @w_sev

    return @w_return
end
go
