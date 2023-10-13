/******************************************/ 
/* Aplicacion: TFM - Sistema médico       */ 
/* Proposito : Creacion de base de datos  */
/* Fecha     : 15/09/2023                 */
/* Autor     : Paúl Ortiz Vera            */
/******************************************/

use fastmedical
go

create table usuario(
	login varchar(50) primary key,
	contrasena varchar(1000) null,
	nombre varchar(100) not null,
	sexo char(1) not null DEFAULT 'M',
	pais varchar(50) not null,
	ciudad varchar(100) not null,
	direccion varchar(200) not null,
	telefono varchar(13) not null,
	correo varchar(50) not null,
	fecha_nacimiento date not null,
	fecha_registro date not null,
	fecha_modificacion date NULL,
	rol char(1) not null DEFAULT 'C',
	estado char(1) not null DEFAULT 'P'
);

create table cliente(
	id int identity primary key,
	enfermedad varchar(150) not null,
	documentacion char(1) not null DEFAULT 'P',
	comentario varchar(300) null, --agregado
	fecha_modificacion date null, --agregado
	estado char(1) not null DEFAULT 'P',
	login varchar(50) not null,
	constraint fk_cliente_login foreign key (login) references usuario(login) on update cascade
);

create table profesional(
	codigo int identity primary key,
	tipo char(1) not null,
	especialidad char(1) null,  --agregado
	documentacion char(1) not null DEFAULT 'P',
	comentario varchar(300) null, --agregado
	fecha_modificacion date null,
	estado char(1) not null DEFAULT 'P',
	login varchar(50) not null,
	constraint fk_profesional_login foreign key (login) references usuario(login) on update cascade
);

create table solicitud(
	numero int identity primary key,
	direccion varchar(200) not null,
	latitude varchar(30) not null,
	longitude varchar(30) not null,
	tipo varchar(150) not null,
	valor_propuesto money not null,
	valor_aceptado money null,
	descripcion varchar(300) not null,
	fecha_registro date not null,
	fecha_modificacion date null,
	comentario varchar(300) null,
	estado char(1) not null DEFAULT 'P',
	id int not null,
	codigo int NULL,
	constraint fk_solicitud_cliente foreign key (id) references cliente(id) on update cascade,
	constraint fk_solicitud_profesional foreign key (codigo) references profesional(codigo) on update no action
);

create table correo_otp(
	id int identity primary key,
	correo varchar(50) not null,
	otp int not null,
	fecha_registro datetime not null,
	tipo char(1) not null,
	estado char(1) not null default 'V',
	login varchar(50) not null,
	constraint fk_correo_otp_login foreign key (login) references usuario(login) on update cascade
);

create table errores(
	numero int primary key,
	mensaje varchar(300) not null,
	severidad int not null
);

/*create table tabla(
	codigo int identity primary key,
	tabla varchar(50) not null,
	descripcion varchar(150) not null
);

create table catalogo(
	tabla int not null,
	codigo varchar(10),
	valor 
);*/

