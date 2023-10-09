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
 ( @s_date        datetime,
   @i_operacion   char(1),
   @i_modo        int = null,
   @i_login       varchar(50) = null,
   @i_contrasena  varchar(50) = null,
   @i_correo      varchar(50) = null,
   @i_otp		  varchar(50) = null,
   @o_exists      int = null out)
as
  declare @w_return  int,
          @w_error   int,
		  @w_msg     varchar(200),
		  @w_otp     int,
		  @w_login   varchar(50),
          @w_sp_name varchar ( 32 )

----------------------------------------------------
select @w_sp_name  = object_name( @@procid )

/* Insertar */
if @i_operacion = 'I' 
begin
	
	/* Validar Login */
	if exists(select 1 from usuario where login = @i_login)
	begin
		select  @o_exists = 0,
		@w_error = 1,
		@w_return = 1,
		@w_msg = 'Login ya existe'
		goto errors
	end

	/* Validar Login */
	if exists(select 1 from usuario where correo = @i_correo)
	begin
		select  @o_exists = 0,
		@w_error = 1,
		@w_return = 1,
		@w_msg = 'El correo ya fue registrado previamente'
		goto errors
	end


	
end

-- Manejo de errores
errors:

if @w_return <> 0 begin
    exec sp_fm_error
    @s_date = @s_date,
    @i_num  = 1,
    @i_msg  = @w_msg,
	@i_sev  = 0

    return @w_return
end
go
