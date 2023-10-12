use fastmedical
go
if exists (select 1 from sysobjects where  name = 'sp_fm_adm_solicitud') 
begin
   drop proc sp_fm_adm_solicitud
end
go
/**************************************************************************/
/* Aplicacion        : TFM - Sistema m�dico                               */
/* Proposito         : SP de Mantenimiento de solicitudes                 */
/* Archivo           : sp_fm_adm_solicitud.sp                             */
/* Base de datos     : fastmedical                                        */
/****************************  MODIFICACIONES  ****************************/
/*   FECHA        AUTOR                RAZON                              */
/* 15/09/2023   Paul Ortiz Vera      Emision inicial                      */
/**************************************************************************/

create proc sp_fm_adm_solicitud
 ( @s_date				datetime, --Fecha
   @s_login				varchar(50), --usuario logeado
   @i_operacion			char(1),
   @i_modo				int = null,
   @i_numero			int = null,
   @i_cliente			int = null,
   @i_profesional		int = null,
   @i_tipo				char(1),
   @i_latitude			varchar(30) = null,
   @i_longitude			varchar(30) = null,
   @i_valor_propuesto	money = null,
   @i_valor_aceptado	money = null,
   @i_direccion			varchar(50) = null,
   @i_descripcion		varchar(300),
   @i_comentario	varchar(300) = null,
   @i_estado		char(1) = null)
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
	if exists(select 1 from cliente where login = @i_cliente)
	begin
		select	@w_error = 402, --El cliente no existe
		@w_return = 402
		goto errors
	end
	
	if(@i_latitude = 0)
	begin
		select	@w_error = 500, --La latitud no puede ser 0
		@w_return = 500
		goto errors
	end
	
	if(@i_longitude = 0)
	begin
		select	@w_error = 501, --La longitud no puede ser 0
		@w_return = 501
		goto errors
	end
	
	if(@i_valor_propuesto = 0)
	begin
		select	@w_error = 502, --El valor propuesto no puede ser 0
		@w_return = 502
		goto errors
	end

	insert into solicitud (direccion, latitude, longitude, tipo, valor_propuesto, descripcion, fecha_registro, id)
	values (@i_direccion, @i_latitude, @i_longitude, @i_tipo, @i_valor_propuesto, @i_descripcion, GETDATE(), @i_cliente)
end
/* Actualizar */
if @i_operacion = 'U' 
begin
	/* Actualizar informaci�n de la solicitud */
	if(@i_modo = 0)
	begin
		/* Validar Login y correo*/
		if not exists(select 1 from solicitud where numero = @i_numero)
		begin
			select  @w_error = 503, --La solicitud no existe
			@w_return = 503
			goto errors
		end

		/* Validar Login */
		if exists(select 1 from cliente where login = @i_cliente)
		begin
			select	@w_error = 402, --El cliente no existe
			@w_return = 402
			goto errors
		end
		
		/* Validar latitud */
		if(@i_latitude = 0)
		begin
			select	@w_error = 500, --La latitud no puede ser 0
			@w_return = 500
			goto errors
		end

		/* Validar longitud */
		if(@i_longitude = 0)
		begin
			select	@w_error = 501, --La longitud no puede ser 0
			@w_return = 501
			goto errors
		end
		
		/* Validar valor propuesto */
		if(@i_valor_propuesto = 0)
		begin
			select	@w_error = 502, --El valor propuesto no puede ser 0
			@w_return = 502
			goto errors
		end

		update solicitud set
			direccion			= @i_direccion,
			latitude			= @i_latitude,
			longitude			= @i_longitude,
			tipo				= @i_tipo,
			valor_propuesto		= @i_valor_propuesto,
			descripcion			= @i_descripcion,
			fecha_modificacion	= GETDATE()
		where numero = @i_numero
	end
	/* Aceptar solicitud */
	if(@i_modo = 1)
	begin
		/* Validar Login y correo*/
		if not exists(select 1 from solicitud where numero = @i_numero)
		begin
			select  @w_error = 503, --La solicitud no existe
			@w_return = 503
			goto errors
		end

		update solicitud set
			valor_aceptado		= @i_valor_aceptado,
			fecha_modificacion	= GETDATE(),
			codigo				= @i_profesional
		where numero = @i_numero
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

		update solicitud set
			estado				= @i_estado,
			comentario			= @i_comentario,
			fecha_modificacion	= GETDATE()
		where numero = @i_numero
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