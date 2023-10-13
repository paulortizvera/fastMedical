use fastmedical
go
if exists (select 1 from sysobjects where  name = 'sp_fm_adm_solicitud') 
begin
   drop proc sp_fm_adm_solicitud
end
go
/**************************************************************************/
/* Aplicacion        : TFM - Sistema médico                               */
/* Proposito         : SP de Mantenimiento de solicitudes                 */
/* Archivo           : sp_fm_adm_solicitud.sp                             */
/* Base de datos     : fastmedical                                        */
/****************************  MODIFICACIONES  ****************************/
/*   FECHA        AUTOR                RAZON                              */
/* 15/09/2023   Paul Ortiz Vera      Emision inicial                      */
/**************************************************************************/

create proc sp_fm_adm_solicitud
 ( @s_date				date = null, --Fecha
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
   @i_comentario		varchar(300) = null,
   @i_estado			char(1) = null,
   @o_rowcount			int = null out,
   @o_msg				varchar(300) = null out)
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
	if not exists(select 1 from cliente where id = @i_cliente)
	begin
		select	@w_error = 402, --El cliente no existe
		@w_return = 402
		goto errors
	end
	
	if(@i_latitude = '0')
	begin
		select	@w_error = 500, --La latitud no puede ser 0
		@w_return = 500
		goto errors
	end
	
	if(@i_longitude = '0')
	begin
		select	@w_error = 501, --La longitud no puede ser 0
		@w_return = 501
		goto errors
	end
	
	if(@i_valor_propuesto <= 0)
	begin
		select	@w_error = 502, --El valor propuesto no puede ser 0
		@w_return = 502
		goto errors
	end

	if exists(select 1 from solicitud where id = @i_cliente and tipo = @i_tipo)
	begin
		select	@w_error = 504, --El cliente ya posee una solicitud de este tipo
		@w_return = 504
		goto errors
	end

	insert into solicitud (direccion, latitude, longitude, tipo, valor_propuesto, descripcion, fecha_registro, id)
	values (@i_direccion, @i_latitude, @i_longitude, @i_tipo, @i_valor_propuesto, @i_descripcion, GETDATE(), @i_cliente)

	select @o_rowcount = 1
end
/* Actualizar */
if @i_operacion = 'U' 
begin
	/* Actualizar información de la solicitud */
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
		if not exists(select 1 from cliente where id = @i_cliente)
		begin
			select	@w_error = 402, --El cliente no existe
			@w_return = 402
			goto errors
		end
		
		/* Validar latitud */
		if(@i_latitude = '0')
		begin
			select	@w_error = 500, --La latitud no puede ser 0
			@w_return = 500
			goto errors
		end

		/* Validar longitud */
		if(@i_longitude = '0')
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
		
		if exists(select 1 from solicitud where id = @i_cliente and tipo = @i_tipo and numero <> @i_numero)
		begin
			select	@w_error = 504, --El cliente ya posee una solicitud de este tipo
			@w_return = 504
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

		select @o_rowcount = 1
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

		/* Validar Login y correo*/
		if not exists(select 1 from profesional where codigo = @i_profesional)
		begin
			select  @w_error = 304, --El profesional no existe
			@w_return = 304
			goto errors
		end

		update solicitud set
			valor_aceptado		= @i_valor_aceptado,
			fecha_modificacion	= GETDATE(),
			codigo				= @i_profesional,
			estado              = 'E'
		where numero = @i_numero

		select @o_rowcount = 1
	end
	/* Cambiar estado */
	if(@i_modo = 2)
	begin
		update solicitud set
			estado				= @i_estado,
			comentario			= @i_comentario,
			fecha_modificacion	= GETDATE()
		where numero = @i_numero

		select @o_rowcount = 1
	end
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
