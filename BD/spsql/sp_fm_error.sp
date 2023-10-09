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
   @i_msg         varchar(200),
   @i_sev         int)
as
  declare @w_error   varchar(250),
          @w_sp_name varchar ( 32 )

----------------------------------------------------
select @w_sp_name  = object_name( @@procid )

select @w_error = convert(varchar,@i_num) + ' ' + @i_msg

/* Desplegar mensaje de error con número */
raiserror (@w_error, -1, -1);

/* Si la severidad es 1 ejecuta un rollback */
if(@i_sev = 0)
	return
else
	rollback tran

return

go
