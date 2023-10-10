use fastmedical
go
if exists (select 1 from sysobjects where  name = 'sp_fm_cons_solicitud') 
begin
   drop proc sp_fm_cons_solicitud
end
go
/**************************************************************************/
/* Aplicacion        : TFM - Sistema médico                               */
/* Proposito         : SP de Consulta de solicitudes                      */
/* Archivo           : sp_fm_cons_solicitud.sp                            */
/* Base de datos     : fastmedical                                        */
/****************************  MODIFICACIONES  ****************************/
/*   FECHA        AUTOR                RAZON                              */
/* 15/09/2023   Paul Ortiz Vera      Emision inicial                      */
/**************************************************************************/

create proc sp_fm_cons_solicitud
 ( @s_date			datetime,
   @i_operacion		char(1),
   @i_modo			int = null,
   @i_numero		int = null,
   @i_cliente		int = null,
   @i_profesional	int = null,
   @i_login_cli		varchar(50) = null,
   @i_login_pro		varchar(50) = null)
as
  declare @w_return  int,
          @w_error   int,
		  @w_sev     int,
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
		select	'NUMERO'          = numero,
			'DIRECCION'           = direccion,
			'LATITUDE'            = latitude,
			'LONGITUDE'           = longitude,
			'TIPO'			      = tipo,
			'COMENTARIO'          = comentario,
			'VALOR_PROPUESTO'     = valor_propuesto,
			'VALOR_ACEPTADO'      = valor_aceptado,
			'FECHA_registro'      = fecha_registro,
			'FECHA_MODIFICACION'  = fecha_modificacion,
			'ESTADO'              = estado,
			'CLIENTE'             = id,
			'PROFESIONAL'         = codigo
		from solicitud
	end
	/* Consultar solo activos */
	if(@i_modo = 1)
	begin
		select	'NUMERO'          = numero,
			'DIRECCION'           = direccion,
			'LATITUDE'            = latitude,
			'LONGITUDE'           = longitude,
			'TIPO'			      = tipo,
			'COMENTARIO'          = comentario,
			'VALOR_PROPUESTO'     = valor_propuesto,
			'VALOR_ACEPTADO'      = valor_aceptado,
			'FECHA_registro'      = fecha_registro,
			'FECHA_MODIFICACION'  = fecha_modificacion,
			'ESTADO'              = estado,
			'CLIENTE'             = id,
			'PROFESIONAL'         = codigo
		from solicitud
		where estado = 'A'
	end

end

/* Search */
if @i_operacion = 'Q' 
begin
	/* Consultar solicitud por numero*/
	if(@i_modo = 0)
	begin
		select	'NUMERO'          = numero,
			'DIRECCION'           = direccion,
			'LATITUDE'            = latitude,
			'LONGITUDE'           = longitude,
			'TIPO'			      = tipo,
			'COMENTARIO'          = comentario,
			'VALOR_PROPUESTO'     = valor_propuesto,
			'VALOR_ACEPTADO'      = valor_aceptado,
			'FECHA_registro'      = fecha_registro,
			'FECHA_MODIFICACION'  = fecha_modificacion,
			'ESTADO'              = estado,
			'CLIENTE'             = id,
			'PROFESIONAL'         = codigo
		from solicitud
		where numero = @i_numero
	end
	/* Consultar solicitudes de un cliente por id */
	if(@i_modo = 1)
	begin
		select	'NUMERO'          = numero,
			'DIRECCION'           = direccion,
			'LATITUDE'            = latitude,
			'LONGITUDE'           = longitude,
			'TIPO'			      = tipo,
			'COMENTARIO'          = comentario,
			'VALOR_PROPUESTO'     = valor_propuesto,
			'VALOR_ACEPTADO'      = valor_aceptado,
			'FECHA_registro'      = fecha_registro,
			'FECHA_MODIFICACION'  = fecha_modificacion,
			'ESTADO'              = estado,
			'CLIENTE'             = id,
			'PROFESIONAL'         = codigo
		from solicitud
		where id = @i_cliente
	end
	/* Consultar solicitudes de un cliente por login */
	if(@i_modo = 2)
	begin
		select	'NUMERO'          = s.numero,
			'DIRECCION'           = s.direccion,
			'LATITUDE'            = s.latitude,
			'LONGITUDE'           = s.longitude,
			'TIPO'			      = s.tipo,
			'COMENTARIO'          = s.comentario,
			'VALOR_PROPUESTO'     = s.valor_propuesto,
			'VALOR_ACEPTADO'      = s.valor_aceptado,
			'FECHA_registro'      = s.fecha_registro,
			'FECHA_MODIFICACION'  = s.fecha_modificacion,
			'ESTADO'              = s.estado,
			'CLIENTE'             = s.id,
			'PROFESIONAL'         = s.codigo
		from solicitud s inner join cliente c
		on s.id = c.id
		where c.login = @i_login_cli
	end
	/* Consultar solicitudes de un profesional por codigo */
	if(@i_modo = 3)
	begin
		select	'NUMERO'          = numero,
			'DIRECCION'           = direccion,
			'LATITUDE'            = latitude,
			'LONGITUDE'           = longitude,
			'TIPO'			      = tipo,
			'COMENTARIO'          = comentario,
			'VALOR_PROPUESTO'     = valor_propuesto,
			'VALOR_ACEPTADO'      = valor_aceptado,
			'FECHA_registro'      = fecha_registro,
			'FECHA_MODIFICACION'  = fecha_modificacion,
			'ESTADO'              = estado,
			'CLIENTE'             = id,
			'PROFESIONAL'         = codigo
		from solicitud
		where codigo = @i_profesional
	end
	/* Consultar solicitudes de un profesional por login */
	if(@i_modo = 4)
	begin
		select	'NUMERO'          = s.numero,
			'DIRECCION'           = s.direccion,
			'LATITUDE'            = s.latitude,
			'LONGITUDE'           = s.longitude,
			'TIPO'			      = s.tipo,
			'COMENTARIO'          = s.comentario,
			'VALOR_PROPUESTO'     = s.valor_propuesto,
			'VALOR_ACEPTADO'      = s.valor_aceptado,
			'FECHA_registro'      = s.fecha_registro,
			'FECHA_MODIFICACION'  = s.fecha_modificacion,
			'ESTADO'              = s.estado,
			'CLIENTE'             = s.id,
			'PROFESIONAL'         = s.codigo
		from solicitud s inner join profesional p
		on s.codigo = p.codigo
		where p.login = @i_login_pro
	end
	/*  CONSULTAS  */
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
