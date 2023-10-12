use fastmedical
go
if exists (select 1 from sysobjects where  name = 'sp_fm_error')
begin
   drop proc sp_fm_error
end
go
/**************************************************************************/
/* Aplicacion        : TFM - Sistema médico                               */ 
/* Proposito         : Creacion de base de datos                          */
/* Archivo           : sp_fm_error.sp                                     */
/* Base de datos     : fastmedical                                        */
/****************************  MODIFICACIONES  ****************************/
/*   FECHA        AUTOR                RAZON                              */
/* 15/09/2023   Paul Ortiz Vera      Emision inicial                      */
/**************************************************************************/

create proc sp_fm_error
 ( @s_date        datetime,
   @i_num         int,
   @i_msg         varchar(200) = null,
   @i_sev         int,
   @o_msg         varchar(200) = null out)
as
  declare @w_error   varchar(250),
          @w_sp_name varchar ( 32 )

----------------------------------------------------
select @w_sp_name  = object_name( @@procid )

/* Si no se envia un mensaje lo busca en la tabla */
if(@i_msg is null)
begin
	select @i_sev = severidad,
		@i_msg = mensaje
	from errores
	where numero = @i_num

	if @@rowcount != 1
	begin
		select @i_msg = 'No hay mensaje asociado'
		select @i_sev = 0
	end

end

select @w_error = convert(varchar,@i_num) + ' - ' + @i_msg

select @o_msg = @w_error
/* Desplegar mensaje de error con número */

/* Si la severidad es 1 ejecuta un rollback */
if(@i_sev = 1)
	rollback tran
else
	return

return

go
