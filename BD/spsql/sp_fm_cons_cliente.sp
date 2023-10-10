use fastmedical
go
if exists (select 1 from sysobjects where  name = 'sp_fm_cons_cliente') 
begin
   drop proc sp_fm_cons_cliente
end
go
/**************************************************************************/
/* Aplicacion        : TFM - Sistema médico                               */
/* Proposito         : SP de Consulta de Clientes                         */
/* Archivo           : sp_fm_cons_cliente.sp                              */
/* Base de datos     : fastmedical                                        */
/****************************  MODIFICACIONES  ****************************/
/*   FECHA        AUTOR                RAZON                              */
/* 15/09/2023   Paul Ortiz Vera      Emision inicial                      */
/**************************************************************************/

create proc sp_fm_cons_cliente
 ( @s_date        datetime,
   @i_operacion   char(1),
   @i_modo        int = null,
   @i_login       varchar(50) = null,
   @i_correo      varchar(50) = null)
as
  declare @w_return  int,
          @w_error   int,
		  @w_sev     int,
          @w_sp_name varchar ( 32 )

----------------------------------------------------
select @w_sp_name  = object_name( @@procid )

/* Search */
if @i_operacion = 'S' 
begin
	/* Consultar toda la tabla */
	if(@i_modo = 0)
	begin
		select	'ID'              = id,
			'ENFERMEDAD'          = enfermedad,
			'DOCUMENTACION'       = documentacion,
			'COMENTARIO'          = comentario,
			'FECHA_MODIFICACION'  = fecha_modificacion,
			'ESTADO'              = estado,
			'LOGIN'               = login
		from cliente
	end
	/* Consultar solo activos */
	if(@i_modo = 1)
	begin
		select	'ID'              = id,
			'ENFERMEDAD'          = enfermedad,
			'DOCUMENTACION'       = documentacion,
			'COMENTARIO'          = comentario,
			'FECHA_MODIFICACION'  = fecha_modificacion,
			'ESTADO'              = estado,
			'LOGIN'               = login
		from cliente
		where estado = 'A'
	end

end

/* Search */
if @i_operacion = 'Q' 
begin
	/* Consultar usuario por login*/
	if(@i_modo = 0)
	begin
		select	'ID'              = id,
			'ENFERMEDAD'          = enfermedad,
			'DOCUMENTACION'       = documentacion,
			'COMENTARIO'          = comentario,
			'FECHA_MODIFICACION'  = fecha_modificacion,
			'ESTADO'              = estado,
			'LOGIN'               = login
		from cliente
		where login = @i_login
	end
	/* Consultar usuario por correo*/
	if(@i_modo = 1)
	begin
		select	'ID'              = c.id,
			'ENFERMEDAD'          = c.enfermedad,
			'DOCUMENTACION'       = c.documentacion,
			'COMENTARIO'          = c.comentario,
			'FECHA_MODIFICACION'  = c.fecha_modificacion,
			'ESTADO'              = c.estado,
			'LOGIN'               = c.login
		from cliente c inner join usuario u
		on c.login = u.login
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
