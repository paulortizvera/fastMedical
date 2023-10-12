use fastmedical
go
if exists (select 1 from sysobjects where  name = 'sp_fm_cons_profesional') 
begin
   drop proc sp_fm_cons_profesional
end
go
/**************************************************************************/
/* Aplicacion        : TFM - Sistema médico                               */
/* Proposito         : SP de Consulta de profesionales                    */
/* Archivo           : sp_fm_cons_profesional.sp                          */
/* Base de datos     : fastmedical                                        */
/****************************  MODIFICACIONES  ****************************/
/*   FECHA        AUTOR                RAZON                              */
/* 15/09/2023   Paul Ortiz Vera      Emision inicial                      */
/**************************************************************************/

create proc sp_fm_cons_profesional
 ( @s_date        datetime,
   @i_operacion   char(1),
   @i_modo        int = null,
   @i_login       varchar(50) = null,
   @i_correo      varchar(50) = null)
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
		select	'CODIGO'          = codigo,
			'TIPO'			      = tipo,
			'ESPECIALIDAD'        = especialidad,
			'FECHA_MODIFICACION'  = fecha_modificacion,
			'DOCUMENTACION'       = documentacion,
			'COMENTARIO'		  = comentario,
			'ESTADO'              = estado,
			'LOGIN'               = login
		from profesional
	end
	/* Consultar solo activos */
	if(@i_modo = 1)
	begin
		select	'CODIGO'          = codigo,
			'TIPO'			      = tipo,
			'ESPECIALIDAD'        = especialidad,
			'FECHA_MODIFICACION'  = fecha_modificacion,
			'DOCUMENTACION'       = documentacion,
			'COMENTARIO'		  = comentario,
			'ESTADO'              = estado,
			'LOGIN'               = login
		from profesional
		where estado = 'A'
	end

end

/* Search */
if @i_operacion = 'Q' 
begin
	/* Consultar usuario por login*/
	if(@i_modo = 0)
	begin
		select	'CODIGO'          = codigo,
			'TIPO'			      = tipo,
			'ESPECIALIDAD'        = especialidad,
			'FECHA_MODIFICACION'  = fecha_modificacion,
			'DOCUMENTACION'       = documentacion,
			'COMENTARIO'          = comentario,
			'ESTADO'              = estado
		from profesional
		where login = @i_login
	end
	/* Consultar usuario por correo*/
	if(@i_modo = 1)
	begin
		select	'CODIGO'          = p.codigo,
			'TIPO'			      = p.tipo,
			'ESPECIALIDAD'        = p.especialidad,
			'FECHA_MODIFICACION'  = p.fecha_modificacion,
			'DOCUMENTACION'       = p.documentacion,
			'COMENTARIO'          = p.comentario,
			'ESTADO'              = p.estado,
			'LOGIN'               = p.login
		from profesional p inner join usuario u
		on p.login = u.login
		where u.correo = @i_correo
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
